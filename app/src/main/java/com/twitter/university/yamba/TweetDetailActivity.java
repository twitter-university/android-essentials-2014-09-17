package com.twitter.university.yamba;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TweetDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        if (savedInstanceState == null) {
          Bundle args = getIntent().getExtras();
          Fragment fragment = TweetDetailFragment.newInstance(args);
          getFragmentManager().beginTransaction()
              .add(R.id.timeline_detail_container, fragment)
              .commit();
        }
    }
}
