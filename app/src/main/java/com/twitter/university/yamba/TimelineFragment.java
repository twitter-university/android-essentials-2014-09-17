package com.twitter.university.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;

public class TimelineFragment extends ListFragment
    implements LoaderManager.LoaderCallbacks<Cursor>{
  private static final int TIMELINE_LOADER = 1;
  private static final String[] FROM = {
      YambaContract.Timeline.Columns.HANDLE,
      YambaContract.Timeline.Columns.TIMESTAMP,
      YambaContract.Timeline.Columns.TWEET,
  };
  private static final int[] TO = {
      R.id.text_tweet_handle,
      R.id.text_tweet_date,
      R.id.text_tweet_msg,
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
        R.layout.timeline_row,
        null,
        FROM,
        TO,
        0
    );
    mAdapter.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
      @Override
      public boolean setViewValue(View view, Cursor cursor, int i) {
        int id = view.getId();
        if (id == R.id.text_tweet_date) {
          // Handle the Date translation
          long timestamp = cursor.getLong(i);
          CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
          ((TextView) view).setText(relTime);
          return true;
        }
        return false;
      }
    });
    setListAdapter(mAdapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Cursor cursor = mAdapter.getCursor();
    if (cursor != null && cursor.moveToPosition(position)) {
      String user = cursor.getString(cursor.getColumnIndex(YambaContract.Timeline.Columns.HANDLE));
      String msg = cursor.getString(cursor.getColumnIndex(YambaContract.Timeline.Columns.TWEET));
      long timestamp = cursor.getLong(cursor.getColumnIndex(YambaContract.Timeline.Columns.TIMESTAMP));
      String date = DateUtils.getRelativeTimeSpanString(timestamp).toString();

      Intent intent = new Intent(getActivity(), TweetDetailActivity.class);
      intent.putExtra(TweetDetailFragment.ARG_USER, user);
      intent.putExtra(TweetDetailFragment.ARG_MSG, msg);
      intent.putExtra(TweetDetailFragment.ARG_DATE, date);
      startActivity(intent);
    }
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
