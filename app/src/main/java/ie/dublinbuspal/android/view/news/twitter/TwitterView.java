package ie.dublinbuspal.android.view.news.twitter;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

interface TwitterView extends MvpView {

    void showTweets(TweetTimelineRecyclerViewAdapter adapter);

    void showProgress();

    void showError(int stringResource);

    void hideProgress();

}
