package com.twitter.university.yamba;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class TimelineActivity extends YambaActivity {

  boolean mDetailsFragmentPresent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
    mDetailsFragmentPresent
        = (null != findViewById(R.id.detail_fragment_container));
    if (mDetailsFragmentPresent
        && null == getFragmentManager().findFragmentById(R.id.tweet_detail_fragment)) {
      // Create a placeholder TweetDetailFragment if one not present
      Fragment f = TweetDetailFragment.newInstance(null);
      getFragmentManager().beginTransaction()
          .add(R.id.detail_fragment_container, f)
          .commit();
    }
  }

}
