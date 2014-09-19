package com.twitter.university.yamba;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class YambaActivity extends Activity {
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.yamba_activity, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    Intent intent;
    switch (id) {
      case R.id.action_tweet:
        // Display the TweetActivity
        intent = new Intent(this, TweetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        return true;
      case R.id.action_timeline:
        // Display the TimelineActivity
        intent = new Intent(this, TimelineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        return true;
      default:
        // Not one of ours
        return super.onOptionsItemSelected(item);
    }
  }
}
