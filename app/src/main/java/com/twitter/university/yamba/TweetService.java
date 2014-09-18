package com.twitter.university.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TweetService extends IntentService {
  private static final String ACTION_POST_TWEET = "com.twitter.university.yamba.action.POST_TWEET";
  private static final String EXTRA_TWEET_TEXT = "com.twitter.university.yamba.extra.TWEET_TEXT";

  private static final int RESULT_POST_TWEET = 1;

  private static final String TAG = "TweetService";
  private Toast mToast;
  private Handler mHandler;

  public static void startActionPostTweet(Context context, String text) {
    Intent intent = new Intent(context, TweetService.class);
    intent.setAction(ACTION_POST_TWEET);
    intent.putExtra(EXTRA_TWEET_TEXT, text);
    context.startService(intent);
  }

  public TweetService() {
    super("TweetService");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_LONG);
    // Create Handler to display Toast on main thread
    mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == RESULT_POST_TWEET) {
          // Fallback in case result message not set
          int result = (msg.arg1 == 0) ? R.string.tweet_post_fail : msg.arg1;
          mToast.setText(result);
          mToast.show();
        }
      }
    };
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_POST_TWEET.equals(action)) {
        final String msg = intent.getStringExtra(EXTRA_TWEET_TEXT);
        handleActionPostTweet(msg);
      }
    }
  }

  private void handleActionPostTweet(String text) {
    int result = R.string.tweet_post_fail;
    if (!TextUtils.isEmpty(text)) {
      if (BuildConfig.DEBUG) Log.d(TAG, "Posting tweet: " + text);
      try {
        YambaClient yambaClient = new YambaClient("student", "password");
        yambaClient.postStatus(text);
        result = R.string.tweet_post_success;
      } catch (YambaClientException e) {
        Log.w(TAG, "Failed to post tweet", e);
      }
      Message message = mHandler.obtainMessage(RESULT_POST_TWEET);
      message.arg1 = result;
      mHandler.sendMessage(message);
    }
  }
}
