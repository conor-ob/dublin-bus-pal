package ie.dublinbuspal.android.view.news.twitter;

import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

public class TwitterModelImpl implements TwitterModel {

    private TweetTimelineRecyclerViewAdapter adapter;

    public TwitterModelImpl() {
        super();
    }

    @Override
    public TweetTimelineRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(TweetTimelineRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

}
