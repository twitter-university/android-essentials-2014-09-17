package com.twitter.university.yamba;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TweetActivity extends Activity
        implements View.OnClickListener {

    private static final String TAG = "TweetActivity";

    EditText mEditTweet;
    Toast mToast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mToast = Toast.makeText(this, null, Toast.LENGTH_LONG);
        mEditTweet = (EditText) findViewById(R.id.edit_tweet);
        Button buttonTweet = (Button) findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_tweet:
                // Tweet button clicked
                if (BuildConfig.DEBUG) Log.d(TAG, "Tweet button clicked");
                String msg = mEditTweet.getText().toString();
                if (BuildConfig.DEBUG) Log.d(TAG, "User entered: " + msg);
                mEditTweet.setText("");
                if (!TextUtils.isEmpty(msg)) {
                    new PostToTwitter().execute(msg);
                }
                break;
            default:
                // Unknown button clicked?!?
        }
    }

    class PostToTwitter extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String msg;
            int result = R.string.tweet_post_fail;
            if (params.length >= 1) {
                msg = params[0];
                // Emulate posting over the network
                if (BuildConfig.DEBUG) Log.d(TAG, "Posting tweet: " + msg);
                try {
                    Thread.sleep(6000);
                    result = R.string.tweet_post_success;
                } catch (InterruptedException e) {
                    Log.d(TAG, "We got interrupted", e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mToast.setText(result);
            mToast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
