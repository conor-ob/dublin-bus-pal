package ie.dublinbuspal.android.view.favourite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

public class FavouritesFragment extends MvpFragment<FavouritesView, FavouritesPresenter>
        implements FavouritesView {

    private View root;
    private FavouritesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout noFavouritesView;
    private FloatingActionButton addFavouritesButton;
    @Inject FavouritesPresenter presenter;

    @NonNull
    @Override
    public FavouritesPresenter createPresenter() {
        if (getActivity() != null) {
            DublinBusApplication application = (DublinBusApplication) getActivity().getApplication();
            application.getApplicationComponent().inject(this);
        }
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLayout(view);
        setupListeners();
        setupOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    private void setupOptionsMenu() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null && isMenuVisible()) {
            homeActivity.invalidateOptionsMenu(HomeActivity.FAVOURITES);
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
        getPresenter().onResume();
    }

    @Override
    public void onPause() {
        getPresenter().onPause();
        super.onPause();
    }

    @Override
    public void showFavourites(List<DetailedBusStop> favourites) {
        adapter.setFavourites(favourites);
        if (CollectionUtilities.isNullOrEmpty(favourites)) {
            swipeRefreshLayout.setVisibility(View.GONE);
            noFavouritesView.setVisibility(View.VISIBLE);
            addFavouritesButton.setVisibility(View.VISIBLE);
        } else {
            noFavouritesView.setVisibility(View.GONE);
            addFavouritesButton.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(int stringId) {
        Snackbar.make(root, getString(stringId), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void launchRealTimeActivity(String stopId) {
        Intent intent = RealTimeActivity.newIntent(getContext(), stopId);
        startActivity(intent);
    }

    private void setupLayout(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_favourites_fragment));
        root = view.findViewById(R.id.root);
        adapter = new FavouritesAdapter(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        noFavouritesView = view.findViewById(R.id.no_favourites);
        addFavouritesButton = view.findViewById(R.id.add_favourites);
    }

    private void setupListeners() {
        addFavouritesButton.setOnClickListener(v -> {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            if (homeActivity != null) {
                homeActivity.goToSearch();
            }
        });
    }

}
