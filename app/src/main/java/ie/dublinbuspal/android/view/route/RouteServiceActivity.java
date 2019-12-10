package ie.dublinbuspal.android.view.route;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.GoogleMapConstants;
import ie.dublinbuspal.android.util.ImageUtils;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.routeservice.RouteServiceViewModel;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import ie.dublinbuspal.model.stop.Stop;

public class RouteServiceActivity extends MvpActivity<RouteServiceView, RouteServicePresenter>
        implements RouteServiceView, OnMapReadyCallback {

    private static final String ROUTE_ID = "route_id";
    private static final String OPERATOR = "operator";

    private SupportMapFragment googleMapFragment;
    private GoogleMap googleMap;
    private CoordinatorLayout coordinatorLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button showRouteButton;
    private RouteAdapter adapter;
    private TextView towards;
    private TextView title;
    private FloatingActionButton swapDirectionButton;
    private List<Marker> markers = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;

    @NonNull
    @Override
    public RouteServicePresenter createPresenter() {
        return ((DublinBusApplication) getApplication()).getApplicationComponent().routeServicePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setupGoogleMap();
        setupLayout();
        setupListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                startActivity(SettingsActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume(getRouteId(), getOperator(), getStopId());
    }


    @Override
    protected void onDestroy() {
        getPresenter().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        double latitude = prefs.getFloat(getString(R.string.preference_key_nearby_map_latitude),
                (float) GoogleMapConstants.DUBLIN_COORDINATES.latitude);
        double longitude = prefs.getFloat(getString(R.string.preference_key_nearby_map_longitude),
                (float) GoogleMapConstants.DUBLIN_COORDINATES.longitude);
        float zoom = prefs.getFloat(getString(R.string.preference_key_nearby_map_zoom),
                GoogleMapConstants.DEFAULT_ZOOM);
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(zoom)
                .build();
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,
//                R.raw.map_style_no_stops));

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getBaseContext(), R.raw.map_style_no_stops));

        googleMap.setOnInfoWindowClickListener(marker -> {
            //TODO check hardcoded string if you ever do translations
            try {
                String stopId = marker.getSnippet().split("Stop")[1].trim();
                launchRealTimeActivity(stopId);
            } catch (Exception e) {
                // nbd
            }
        });
        this.googleMap = googleMap;
        getPresenter().onMapReady();
    }

    private void setupGoogleMap() {
        if (googleMapFragment == null) {
            googleMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.google_map);
            googleMapFragment.getMapAsync(this);
        }
    }

    private void setupLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TextView routeView = findViewById(R.id.route_id);
        routeView.setText(getRouteId());
        towards = findViewById(R.id.towards);
        coordinatorLayout = findViewById(R.id.google_map_container);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        showRouteButton = findViewById(R.id.show_stops_button);
        adapter = new RouteAdapter(this);
        title = findViewById(R.id.route_title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        swapDirectionButton = findViewById(R.id.button_swap);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefresh.setEnabled(false);
        swipeRefresh.setRefreshing(true);
    }

    private void setupListeners() {
        bottomSheetBehavior.setBottomSheetCallback(new ParallaxGoogleMapCallback());
        showRouteButton.setOnClickListener(v ->
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
        swapDirectionButton.setOnClickListener(v -> getPresenter().onNextVariantPressed());
    }

    private String getRouteId() {
        return getIntent().getStringExtra(ROUTE_ID);
    }

    private String getOperator() {
        return getIntent().getStringExtra(OPERATOR);
    }

    private String getStopId() {
        return getIntent().getStringExtra(RealTimeActivity.STOP_ID);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //TODO temporary fix blah blah
    private boolean firstResponse = false;

    @Override
    public void render(@NotNull RouteServiceViewModel viewModel) {
        showLoading(viewModel.isLoading());
        if (viewModel.isError() && viewModel.getErrorResource() != null) {
            showError(viewModel.getErrorResource());
        } else if (viewModel.getSelectedVariant() > -1) {
            title.setText(String.format(
                    Locale.UK,
                    getString(R.string.formatted_route_title),
                    viewModel.selectedOrigin(),
                    viewModel.selectedDestination())
            );
            this.towards.setText(String.format(Locale.UK,
                    getString(R.string.formatted_towards), viewModel.selectedDestination()));
            if (viewModel.isMapReady()) {
                displayBusStops(viewModel.selectedStops());
            }
        }
    }

    private void displayBusStops(List<Stop> busStops) {
        if (!firstResponse) {
            setShowBusTimesButtonVisibility(View.VISIBLE);
            swapDirectionButton.setVisibility(View.VISIBLE);
        }
        firstResponse = true;
        adapter.setBusStops(busStops);
        if (googleMap != null) {
            clearMap();
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(8);
            polylineOptions.color(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
            for (Stop busStop : busStops) {
                markers.add(googleMap.addMarker(newMarkerOptions(busStop)));
                polylineOptions.add(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()));
            }
            polylines.add(googleMap.addPolyline(polylineOptions));

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Stop busStop : busStops) {
                builder.include(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()));
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 64);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    private void clearMap() {
        for (Marker marker : markers) {
            marker.remove();
        }
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
    }

    private void showError(int stringId) {
        Snackbar.make(coordinatorLayout, stringId, Snackbar.LENGTH_LONG).show();
    }

    private void showLoading(boolean isLoading) {
        swipeRefresh.setRefreshing(isLoading);
    }

    @Override
    public void launchRealTimeActivity(String stopId) {
        Intent intent = RealTimeActivity.newIntent(this, stopId);
        startActivity(intent);
    }

    private MarkerOptions newMarkerOptions(Stop busStop) {
        return new MarkerOptions()
                .position(new LatLng(busStop.coordinate().getX(), busStop.coordinate().getY()))
                .anchor(0.5f, 0.5f)
                .title(busStop.name())
                .snippet(String.format(Locale.UK, getString(R.string.formatted_stop_id), busStop.id()))
                .icon(ImageUtils.drawableToBitmap(getApplicationContext(),
                        R.drawable.ic_map_marker_route_stop));
    }

    private void setShowBusTimesButtonVisibility(int visibility) {
        if (showRouteButton.getVisibility() != visibility) {
            showRouteButton.setVisibility(visibility);
        }
    }

    public static Intent newIntent(Context context, String routeId, String operator) {
        Intent intent = new Intent(context, RouteServiceActivity.class);
        intent.putExtra(ROUTE_ID, routeId);
        intent.putExtra(OPERATOR, operator);
        return intent;
    }

    public static Intent newIntent(Context context, String routeId, String operator, String stopId) {
        Intent intent = new Intent(context, RouteServiceActivity.class);
        intent.putExtra(ROUTE_ID, routeId);
        intent.putExtra(RealTimeActivity.STOP_ID, stopId);
        intent.putExtra(OPERATOR, operator);
        return intent;
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
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    setShowBusTimesButtonVisibility(View.VISIBLE);
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
