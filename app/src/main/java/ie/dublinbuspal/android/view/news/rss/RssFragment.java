package ie.dublinbuspal.android.view.news.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.remote.rss.xml.Item;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

public class RssFragment extends MvpFragment<RssView, RssPresenter> implements RssView {

    private ConstraintLayout root;
    private RssAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject RssPresenter rssPresenter;

    @NonNull
    @Override
    public RssPresenter createPresenter() {
        if (rssPresenter == null && getActivity() != null) {
            DublinBusApplication application = (DublinBusApplication)
                    getActivity().getApplication();
            application.getApplicationComponent().inject(this);
        }
        return rssPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_source, container, false);
    }

    @Override
    public void onViewCreated(View newsFragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(newsFragment, savedInstanceState);
        setupLayout(newsFragment);
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

    private void setupLayout(View newsFragment) {
        root = newsFragment.findViewById(R.id.root);
        adapter = new RssAdapter();
        RecyclerView recyclerView = newsFragment.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = newsFragment.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
    }

    private void setupListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    @Override
    public void showRss(List<Item> items) {
        adapter.setItems(items);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(int stringId) {
        Snackbar.make(root, getString(stringId), Snackbar.LENGTH_LONG).show();
    }

}
