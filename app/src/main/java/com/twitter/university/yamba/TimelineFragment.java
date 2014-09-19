package com.twitter.university.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.twitter.university.android.yamba.service.YambaContract;

public class TimelineFragment extends ListFragment
    implements LoaderManager.LoaderCallbacks<Cursor>{
  private static final int TIMELINE_LOADER = 1;
  private static final String[] FROM = {
      YambaContract.Timeline.Columns.HANDLE,
      YambaContract.Timeline.Columns.TWEET,
  };
  private static final int[] TO = {
      android.R.id.text1,
      android.R.id.text2,
  };
  SimpleCursorAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mAdapter = new SimpleCursorAdapter(
        getActivity(),
        android.R.layout.simple_list_item_2,
        null,
        FROM,
        TO,
        0
    );
    setListAdapter(mAdapter);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
    return new CursorLoader(
        getActivity().getApplicationContext(),
        YambaContract.Timeline.URI,
        null,
        null,
        null,
        YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
  }

  @Override
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
    // Our Cursor is ready to use. Hook it up to our adapter!
    mAdapter.swapCursor(cursor);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> cursorLoader) {
    // Old Cursor is invalid. Stop using it.
    mAdapter.swapCursor(null);
  }
}
