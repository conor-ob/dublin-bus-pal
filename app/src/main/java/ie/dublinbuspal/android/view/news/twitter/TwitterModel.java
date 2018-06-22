package ie.dublinbuspal.android.view.news.twitter;

import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

interface TwitterModel {

    TweetTimelineRecyclerViewAdapter getAdapter();

    void setAdapter(TweetTimelineRecyclerViewAdapter adapter);

}
