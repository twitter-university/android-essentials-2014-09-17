package com.twitter.university.yamba;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class TimelineActivity extends YambaActivity {

  boolean mDetailsFragmentContainerPresent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
    mDetailsFragmentContainerPresent = (null != findViewById(R.id.detail_fragment_container));
    if (mDetailsFragmentContainerPresent && null == savedInstanceState) {
      // Create a placeholder TweetDetailFragment if one not present
      Fragment f = TweetDetailFragment.newInstance(null);
      getFragmentManager().beginTransaction()
          .add(R.id.detail_fragment_container, f)
          .commit();
    }
  }

  private void showDetails(Bundle args) {
    getFragmentManager().beginTransaction()
        .replace(R.id.detail_fragment_container, TweetDetailFragment.newInstance(args))
        .commit();
  }

  @Override
  public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
    if (mDetailsFragmentContainerPresent) {
      Bundle args = intent.getExtras();
      showDetails(args);
    } else {
      startActivity(intent);
    }
  }

}
