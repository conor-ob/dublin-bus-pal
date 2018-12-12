package ie.dublinbuspal.android.view.news.twitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;

import com.google.android.material.snackbar.Snackbar;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import javax.inject.Inject;

public class TwitterFragment extends MvpFragment<TwitterView, TwitterPresenter>
        implements TwitterView {

    private ConstraintLayout root;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject TwitterPresenter presenter;

    @NonNull
    @Override
    public TwitterPresenter createPresenter() {
        if (presenter == null && getActivity() != null) {
            DublinBusApplication application = (DublinBusApplication)
                    getActivity().getApplication();
            application.getApplicationComponent().inject(this);
        }
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_source, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLayout(view);
        setupListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onDestroy() {
        getPresenter().onDestroy();
        super.onDestroy();
    }

    @Override
    public void showTweets(TweetTimelineRecyclerViewAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void setupLayout(View view) {
        root = view.findViewById(R.id.root);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
    }

    private void setupListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(int stringResource) {
        Snackbar.make(root, stringResource, Snackbar.LENGTH_LONG).show();
    }

}
