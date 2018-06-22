package ie.dublinbuspal.android.view.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.route.RouteActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

public class SearchFragment
        extends MvpFragment<SearchQueryView, SearchPresenter> implements SearchQueryView {

    private SearchAdapter adapter;
    private SearchView searchView;
    private TextView noResultsMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject SearchPresenter presenter;

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        if (presenter == null) {
            if (getActivity() != null) {
                DublinBusApplication application = (DublinBusApplication) getActivity().getApplication();
                application.getApplicationComponent().inject(this);
            }
        }
        return presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View searchFragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(searchFragment, savedInstanceState);
        setupLayout(searchFragment);
        setupSearch(searchFragment);
        setupOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    private void setupOptionsMenu() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null && isMenuVisible()) {
            homeActivity.invalidateOptionsMenu(HomeActivity.SEARCH);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(SettingsActivity.newIntent(getContext()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume(searchView.getQuery().toString());
    }

    @Override
    public void onPause() {
        getPresenter().onPause();
        super.onPause();
    }

    private void setupLayout(View searchFragment) {
        adapter = new SearchAdapter(this);
        RecyclerView recyclerView = searchFragment.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = searchFragment.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        noResultsMessage = searchFragment.findViewById(R.id.no_results_message);
    }

    public void setupSearch(View searchFragment) {
        searchView = searchFragment.findViewById(R.id.search_view);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(getPresenter());
    }

    @Override
    public void launchRouteActivity(String routeId) {
        Intent intent = RouteActivity.newIntent(getContext(), routeId);
        startActivity(intent);
    }

    @Override
    public void launchRealTimeActivity(String stopId) {
        Intent intent = RealTimeActivity.newIntent(getContext(), stopId);
        startActivity(intent);
    }

    @Override
    public void showSearchResult(List<Object> searchResult) {
        adapter.setSearchResult(searchResult);
        if (CollectionUtilities.isNullOrEmpty(searchResult)) {
            swipeRefreshLayout.setVisibility(View.GONE);
            noResultsMessage.setVisibility(View.VISIBLE);
        } else {
            noResultsMessage.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

}
