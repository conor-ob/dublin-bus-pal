package ie.dublinbuspal.android.view.news.twitter;

import android.content.Context;

import android.content.res.Configuration;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import ie.dublinbuspal.android.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TwitterPresenterImpl extends MvpBasePresenter<TwitterView>
        implements TwitterPresenter {

    private static final String HANDLE = "dublinbuspal";
    private static final String LIST = "dublin-bus";

    private final Context context;
    private final TwitterModelImpl model;
    private CompositeDisposable disposables;

    @Inject
    public TwitterPresenterImpl(Context context) {
        this.context = context;
        this.model = new TwitterModelImpl();
    }

    @Override
    public void onResume() {
        if (getModel().getAdapter() == null) {
            getDisposables().add(Single.fromCallable(this::getAdapter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetAdapter, this::onError));
        } else {
            onGetAdapter(getModel().getAdapter());
        }
    }

    @Override
    public void onRefresh() {
        ifViewAttached(TwitterView::showProgress);
        getModel().getAdapter().refresh(new Callback<TimelineResult<Tweet>>() {

            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                ifViewAttached(TwitterView::hideProgress);
            }

            @Override
            public void failure(TwitterException exception) {
                Timber.e(exception);
                if (exception.getCause() != null) {
                    onError(exception.getCause());
                } else {
                    onError(exception);
                }
            }

        });
    }

    @Override
    public void onDestroy() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private TweetTimelineRecyclerViewAdapter getAdapter() {
        TwitterListTimeline timeline = new TwitterListTimeline.Builder()
                .slugWithOwnerScreenName(LIST, HANDLE)
                .includeRetweets(Boolean.TRUE)
                .build();
        return new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                .setTimeline(timeline)
                .setViewStyle(getViewStyle())
                .build();
    }

    private int getViewStyle() {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                return  R.style.tw__TweetLightStyle;
            case Configuration.UI_MODE_NIGHT_YES:
                return  R.style.tw__TweetDarkStyle;
        }
        return R.style.tw__TweetLightStyle;
    }

    private void onGetAdapter(TweetTimelineRecyclerViewAdapter adapter) {
        getModel().setAdapter(adapter);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showTweets(getModel().getAdapter());
        });
    }

    private void onError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(view -> {
            view.hideProgress();
            if (throwable instanceof UnknownHostException) {
                view.showError(R.string.error_no_internet);
            } else if (throwable instanceof SocketException) {
                view.showError(R.string.error_interrupted);
            } else if (throwable instanceof SocketTimeoutException) {
                view.showError(R.string.error_timeout);
            } else {
                view.showError(R.string.error_unknown);
            }
        });
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

    private Context getContext() {
        return context;
    }

    private TwitterModelImpl getModel() {
        return model;
    }

}
