package ie.dublinbuspal.android.view.realtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.GoogleMapConstants;
import ie.dublinbuspal.android.util.ImageUtils;
import ie.dublinbuspal.android.view.route.RouteActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.Stop;
import ie.dublinbuspal.util.AlphanumComparator;
import ie.dublinbuspal.util.CollectionUtils;

public class RealTimeActivity
        extends MvpActivity<RealTimeView, RealTimePresenter>
        implements RealTimeView, OnMapReadyCallback, OnStreetViewPanoramaReadyCallback,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String STOP_ID = "stop_id";

    private GoogleMap googleMap;
    private StreetViewPanorama streetViewPanorama;
    private SupportMapFragment googleMapFragment;
    private SupportStreetViewPanoramaFragment streetViewPanoramaFragment;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private BottomSheetBehavior<View> behavior;
    private RealTimeAdapter adapter;
    private List<TextView> routeFilters;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton showBusTimesButton;
    private FloatingActionButton mapSwitcherButton;
    //private ViewFlipper viewFlipper;
    private Timer autoRefreshTimer;

    @NonNull
    @Override
    public RealTimePresenter createPresenter() {
        return ((DublinBusApplication) getApplication()).getApplicationComponent().liveDataPresenter();
    }

    public static Intent newIntent(Context context, String stopId) {
        Intent intent = new Intent(context, RealTimeActivity.class);
        intent.putExtra(STOP_ID, stopId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);
        setupGoogleMap();
        setupLayout();
        setupListeners();
        setupPreferences();
        setupAnalytics();
    }

    private void setupAnalytics() {
//        AnalyticsUtilities.logScreenViewedEvent("Real Time Screen",
//                ImmutableMap.of("Stop ID", getStopId()));
    }

    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        adapter.setShowArrivalTime(sharedPreferences.getBoolean(getString(
                R.string.preference_key_show_arrival_time), false));

        if (sharedPreferences.getBoolean(getString(R.string.preference_key_auto_refresh), false)) {
            String val = sharedPreferences.getString(getString(
                    R.string.preference_key_auto_refresh_interval), "30");
            int valInt = Integer.valueOf(val);
            long ms = TimeUnit.SECONDS.toMillis(valInt);

            final Handler handler = new Handler();
            autoRefreshTimer = new Timer();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    handler.post(() -> refreshNoProgress());
                }
            };
            autoRefreshTimer.scheduleAtFixedRate(task, ms, ms);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (getString(R.string.preference_key_show_arrival_time).equals(key)) {
            adapter.setShowArrivalTime(sharedPreferences.getBoolean(key, false));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume(getStopId());
    }

    @Override
    protected void onPause() {
        getPresenter().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        if (autoRefreshTimer != null) {
            autoRefreshTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void showError(int stringId) {
        Snackbar.make(coordinatorLayout, stringId, Snackbar.LENGTH_LONG).show();
    }

    private void setupGoogleMap() {
        if (googleMapFragment == null) {
            googleMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.google_map);
            googleMapFragment.getMapAsync(this);
        }
//        if (streetViewPanoramaFragment == null) {
//            streetViewPanoramaFragment =
//                    (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
//                            .findFragmentById(R.id.street_view);
//            streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
//        }
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_no_stops));
//        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                550, getResources().getDisplayMetrics());
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        View view = googleMapFragment.getView();
//        if (view != null) {
//            view.setTranslationY(-px/2);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getPresenter().onCreateOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateDefaultOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_realtime_default, menu);
    }

    @Override
    public void onCreateFavouriteOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_realtime_favourite, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_add_favourite:
                getPresenter().onAddFavouritePressed();
                return true;
            case R.id.action_remove_favourite:
                presentRemoveFavouriteDialog();
                return true;
//            case R.id.action_edit_favourite:
//                getPresenter().onEditFavouritePressed();
//                return true;
            case R.id.action_show_map:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                return true;
            case R.id.action_settings:
                startActivity(SettingsActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        getPresenter().onResume(getStopId());
    }

    private void refreshNoProgress() {
        getPresenter().onResume(getStopId());
    }

    private void presentRemoveFavouriteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.FavouriteDialogTheme);
        builder.setTitle(getString(R.string.action_remove_favourite));
        builder.setMessage(getString(R.string.remove_favourite_confirm));
        String yesText = getString(R.string.yes);
        builder.setPositiveButton(yesText, (dialog, which) -> getPresenter().removeFavourite());
        String noText = getString(R.string.no);
        builder.setNegativeButton(noText, (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void presentSaveFavouriteDialog(Stop busStop, List<String> service) {
        Set<String> routesToSave = new HashSet<>();
        FrameLayout frameView = new FrameLayout(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_save_favourite, frameView);
        EditText editText = dialogView.findViewById(R.id.custom_name);
        editText.setHint(busStop.name());

        GridView gridView = dialogView.findViewById(R.id.grid_view);
        gridView.setAdapter(new ArrayAdapter<>(this,
                R.layout.dialog_save_favourite_grid_item, R.id.route, service));
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String route = service.get(position);
            CheckBox checkbox = view.findViewById(R.id.checkbox);
            if (routesToSave.contains(route)) {
                routesToSave.remove(route);
                checkbox.setChecked(false);
            } else {
                routesToSave.add(service.get(position));
                checkbox.setChecked(true);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this,
                R.style.FavouriteDialogTheme)
                .setTitle(getString(R.string.save_favourite_prompt))
                .setView(frameView)
                .setNeutralButton(getString(R.string.select_all_routes), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.save), null)
                .create();
        dialog.setOnShowListener(dialog1 -> {
            Button neutralButton = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(view -> {
                for (int i = 0; i < gridView.getChildCount(); i++) {
                    LinearLayout child = (LinearLayout) gridView.getChildAt(i);
                    CheckBox check = child.findViewById(R.id.checkbox);
                    check.setChecked(true);
                    routesToSave.clear();
                    routesToSave.addAll(service);
                }
            });
            Button negativeButton = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(view -> dialog1.dismiss());
            Button positiveButton = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                if (CollectionUtils.isNullOrEmpty(routesToSave)) {
                    Snackbar.make(dialogView,
                            getString(R.string.error_not_enough_routes),
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    String editedText = editText.getText().toString();
//                    String name = editedText.isEmpty() ? busStop.getRealName() : editedText; //TODO
                    String name = editedText.isEmpty() ? busStop.name() : editedText;
                    List<String> routes = new ArrayList<>(routesToSave);
                    Collections.sort(routes, AlphanumComparator.getInstance());
                    presenter.saveFavourite(name, routes);
                    dialog1.dismiss();
                }
            });
        });
        dialog.show();
    }

    private Marker busStopMarker;

    @Override
    public void showBusStop(Stop busStop) {
        invalidateOptionsMenu(); //make sure we set default/favourite correctly
        TextView name = toolbar.findViewById(R.id.stop_name);
        TextView id = toolbar.findViewById(R.id.stop_id);
        name.setText(busStop.name());
        id.setText(String.format(Locale.UK, "Stop %s", busStop.id()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()))
                .zoom(GoogleMapConstants.DEFAULT_ZOOM)
                .build();
        if (googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            if (busStopMarker != null) {
                busStopMarker.remove();
            }
            busStopMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()))
                    .anchor(0.3f, 1.0f)
                    .icon(ImageUtils.drawableToBitmap(getApplicationContext(), R.drawable.ic_map_marker_bus_double_decker_default)));

            if (streetViewPanorama != null) {
                streetViewPanorama.setPosition(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()));
            }
        }
    }

    @Override
    public void showRealTimeData(List<LiveData> realTimeData) {
        adapter.setRealTimeData(realTimeData);
    }

    @Override
    public void showBusStopService(List<String> busStopService) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int min = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        LinearLayout routeButtonContainer = findViewById(R.id.routes_for_stop);
        routeButtonContainer.removeAllViewsInLayout();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        MarginLayoutParamsCompat.setMarginEnd(layoutParams, px);
        routeFilters = new ArrayList<>();
        for (String route : busStopService) {
            TextView routeView = new TextView(this);
            routeView.setLayoutParams(layoutParams);
            routeView.setPadding(px, 0, px, 0);
            routeView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.text_view_rounded_corner));
            routeView.setMinWidth(min);
            routeView.setGravity(Gravity.CENTER);
            routeView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD));
            routeView.setTextSize(18);
            routeView.setText(route);
            routeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
            routeButtonContainer.addView(routeView);
            routeView.setOnClickListener(v -> {
                //adapter.setRealTimeData(Collections.emptyList());
                getPresenter().routeFilterPressed(route);
            });
            routeFilters.add(routeView);
        }
    }

    @Override
    public void launchRouteActivity(String routeId) {
        Intent intent = RouteActivity.newIntent(this, routeId, getStopId());
        startActivity(intent);
    }

    @Override
    public void onFavouriteSaved() {
        Snackbar.make(getWindow().getDecorView().getRootView(),
                getString(R.string.saved_favorite_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onFavouriteRemoved() {
        Snackbar.make(getWindow().getDecorView().getRootView(),
                getString(R.string.removed_favorite_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showStreetView(Stop busStop) {

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
    public void applyRouteFiltering(Set<String> routes) {
        if (routes.isEmpty()) {
            for (TextView routeView : routeFilters) {
                routeView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.text_view_rounded_corner));
                routeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
            }
            return;
        }
        for (TextView routeView : routeFilters) {
            if (routes.contains(routeView.getText().toString())) {
                routeView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.text_view_rounded_corner));
                routeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
            } else {
                routeView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.text_view_rounded_corner_faded));
                routeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blackFaded));
            }
        }
    }

    private void setupLayout() {
        toolbar = findViewById(R.id.activity_real_time_tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        coordinatorLayout = findViewById(R.id.google_map_container);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        adapter = new RealTimeAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.activity_real_time_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        showBusTimesButton = findViewById(R.id.show_bus_times_button);
//        viewFlipper = findViewById(R.id.view_flipper);
//        viewFlipper.setDisplayedChild(0);
//        mapSwitcherButton = findViewById(R.id.map_switcher_button);
    }

    private void setupListeners() {
        behavior.setBottomSheetCallback(new ParallaxGoogleMapCallback());
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        showBusTimesButton.setOnClickListener(l ->
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED));
//        mapSwitcherButton.setOnClickListener(l -> {
//            if (viewFlipper.getDisplayedChild() == 0) {
//                viewFlipper.setDisplayedChild(1);
//                mapSwitcherButton.setImageResource(R.drawable.ic_google_map_white);
//            } else if (viewFlipper.getDisplayedChild() == 1) {
//                viewFlipper.setDisplayedChild(0);
//                mapSwitcherButton.setImageResource(R.drawable.ic_streetview_white);
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private String getStopId() {
        return getIntent().getStringExtra(STOP_ID);
    }

    private void setShowBusTimesButtonVisibility(int visibility) {
        if (showBusTimesButton.getVisibility() != visibility) {
            showBusTimesButton.setVisibility(visibility);
        }
    }

    private void setMapSwitcherButtonVisibility(int visibility) {
        if (mapSwitcherButton.getVisibility() != visibility) {
            mapSwitcherButton.setVisibility(visibility);
        }
    }

    private final class ParallaxGoogleMapCallback extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_DRAGGING:
                case BottomSheetBehavior.STATE_SETTLING:
                case BottomSheetBehavior.STATE_EXPANDED:
                case BottomSheetBehavior.STATE_COLLAPSED:
                    setShowBusTimesButtonVisibility(View.GONE);
                    //setMapSwitcherButtonVisibility(View.GONE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    setShowBusTimesButtonVisibility(View.VISIBLE);
                    //setMapSwitcherButtonVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            if (googleMapFragment != null) {
                View view = googleMapFragment.getView();
                if (view != null) {
                    int dy = bottomSheet.getTop() - coordinatorLayout.getHeight();
                    view.setTranslationY(dy / 2);
                }
            }
        }

    }

}
