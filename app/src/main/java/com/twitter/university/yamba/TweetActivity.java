package com.twitter.university.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TweetActivity extends Activity
    implements View.OnClickListener {

  private static final String TAG = "TweetActivity";

  EditText mEditTweet;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tweet);

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
        String msg = mEditTweet.getText().toString();
        if (BuildConfig.DEBUG) Log.d(TAG, "User entered: " + msg);
        mEditTweet.setText("");
        if (!TextUtils.isEmpty(msg)) {
          TweetService.startActionPostTweet(this, msg);
        }
        break;
      default:
        Log.w(TAG, "Unknown view clicked");
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
