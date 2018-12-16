package ie.dublinbuspal.android.view.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.util.CollectionUtilities;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.route.RouteActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.internal.Intrinsics;

public class SearchFragment
        extends MvpFragment<SearchQueryView, SearchPresenter> implements SearchQueryView {

    private SearchAdapter adapter;
    private SearchView searchView;
    private TextView noResultsMessage;
    private SwipeRefreshLayout swipeRefreshLayout;

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        if (getActivity() != null) {
            return ((DublinBusApplication) getActivity().getApplication()).getApplicationComponent().searchPresenter();
        }
        return null;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = searchFragment.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        noResultsMessage = searchFragment.findViewById(R.id.no_results_message);
    }

    private void setupSearch(View searchFragment) {
        searchView = searchFragment.findViewById(R.id.search_view);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);

        Disposable disposable = Observable.create(subscriber -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(@Nullable String newText) {
                if (newText == null) {
                    Intrinsics.throwNpe();
                }

                subscriber.onNext(newText);
                return false;
            }

            public boolean onQueryTextSubmit(@Nullable String query) {
                if (query == null) {
                    Intrinsics.throwNpe();
                }

                subscriber.onNext(query);
                return false;
            }
        }))
                .map(string -> string.toString().toLowerCase().trim())
                .debounce(400L, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe(string -> getPresenter().onResume(string));
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
