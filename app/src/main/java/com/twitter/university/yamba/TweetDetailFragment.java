package com.twitter.university.yamba;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TweetDetailFragment extends Fragment {
  public static final String ARG_USER = "user";
  public static final String ARG_MSG = "msg";
  public static final String ARG_DATE = "date";

  private String mUser;
  private String mMsg;
  private String mDate;

  public static TweetDetailFragment newInstance(Bundle args) {
    TweetDetailFragment fragment = new TweetDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mUser = getArguments().getString(ARG_USER);
      mMsg = getArguments().getString(ARG_MSG);
      mDate = getArguments().getString(ARG_DATE);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View top = inflater.inflate(R.layout.fragment_tweet_detail, container, false);
    ((TextView) top.findViewById(R.id.tweet_detail_user)).setText(mUser);
    ((TextView) top.findViewById(R.id.tweet_detail_date)).setText(mDate);
    ((TextView) top.findViewById(R.id.tweet_detail_msg)).setText(mMsg);
    return top;
  }


}
