package ie.dublinbuspal.android.view.favourite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import ie.dublinbuspal.model.favourite.FavouriteStop;
import ie.dublinbuspal.util.CollectionUtils;
import timber.log.Timber;

public class FavouritesFragment extends MvpFragment<FavouritesView, FavouritesPresenter>
        implements FavouritesView {

    private View root;
    private RecyclerView recyclerView;
    private FavouritesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout noFavouritesView;
    private FloatingActionButton addFavouritesButton;
    @Inject FavouritesPresenter presenter;

    @NonNull
    @Override
    public FavouritesPresenter createPresenter() {
        if (getActivity() != null) {
            return ((DublinBusApplication) getActivity().getApplication()).getApplicationComponent().favouritesPresenter();
        }
        return null;
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
        setupDragAndDrop();
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
        if (shouldSaveFavourites) {
            getPresenter().onPause(adapter.getFavourites());
        } else {
            getPresenter().onPause();
        }
        super.onPause();
    }

    @Override
    public void showFavourites(List<FavouriteStop> favourites) {
        adapter.setFavourites(favourites);
        if (CollectionUtils.isNullOrEmpty(favourites)) {
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
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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

    private void setupDragAndDrop() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private boolean shouldSaveFavourites = false;
    private ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Collections.swap(adapter.getFavourites(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            shouldSaveFavourites = true;
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Timber.d("onSwiped");
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
        }

    };

}
