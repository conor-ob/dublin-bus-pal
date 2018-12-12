package ie.dublinbuspal.android.view.nearby;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.util.AnimationUtilities;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.GoogleMapConstants;
import ie.dublinbuspal.android.util.LocationUtilities;
import ie.dublinbuspal.android.util.SVGUtils;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class NearbyFragment
        extends MvpFragment<NearbyView, NearbyPresenter>
        implements NearbyView, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private static final int BOTTOM_SHEET_PEEK_HEIGHT_DP = 250;
    public static boolean mapIsTouched;
    private RxPermissions rxPermissions;
    private ReactiveLocationProvider locationProvider;
    private int bottomSheetPeekHeightPx;
    private SupportMapFragment googleMapFragment;
    private GoogleMap googleMap;
    private CoordinatorLayout coordinatorLayout;
    private NearbyAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button showNearbyStopsButton;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MapMarkerManager mapMarkerManager;
    private FloatingActionButton gps;
    private FloatingActionButton traffic;
    private boolean showTraffic;
    private CompositeDisposable disposables;
    private Location currentLocation;
    private ImageView crosshair;
    @Inject NearbyPresenter presenter;

    @NonNull
    @Override
    public NearbyPresenter createPresenter() {
        if (presenter == null) {
            if (getActivity() != null) {
                DublinBusApplication application = (DublinBusApplication)
                        getActivity().getApplication();
                application.getOldApplicationComponent().inject(this);
            }
        }
        return presenter;
    }

    public NearbyFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupConstants();
        showTraffic = false;
    }

    private void setupConstants() {
        Resources resources = getResources();
        bottomSheetPeekHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                BOTTOM_SHEET_PEEK_HEIGHT_DP, resources.getDisplayMetrics());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupGoogleMap();
        setupLayout(view);
        setupListeners();
        setupOptionsMenu();
        requestLocationUpdates();
    }

    @SuppressLint("RestrictedApi")
    private void setupOptionsMenu() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null && isMenuVisible()) {
            homeActivity.invalidateOptionsMenu(HomeActivity.NEARBY);
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
    public void onPause() {
        if (googleMap != null) {
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                    .putFloat(getString(R.string.preference_key_nearby_map_latitude),
                            (float) googleMap.getCameraPosition().target.latitude)
                    .putFloat(getString(R.string.preference_key_nearby_map_longitude),
                            (float) googleMap.getCameraPosition().target.longitude)
                    .putFloat(getString(R.string.preference_key_nearby_map_zoom),
                            googleMap.getCameraPosition().zoom)
                    .apply();
        }
        removeLocationUpdates();
        getPresenter().onPause();
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
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
        mapMarkerManager = new MapMarkerManager(position.zoom);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraMoveListener(mapMarkerManager);
        googleMap.setOnInfoWindowClickListener(mapMarkerManager);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),
                R.raw.map_style_no_stops));
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap = googleMap;
    }

    private void setupGoogleMap() {
        if (googleMapFragment == null) {
            googleMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
            googleMapFragment.getMapAsync(this);
        }
    }

    private void setupLayout(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_nearby_fragment));
        coordinatorLayout = view.findViewById(R.id.google_map_container);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(bottomSheetPeekHeightPx);
        adapter = new NearbyAdapter(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        showNearbyStopsButton = view.findViewById(R.id.show_nearby_stops_button);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        gps = view.findViewById(R.id.gps);
        traffic = view.findViewById(R.id.traffic);
        gps.setOnClickListener(v -> requestLocationUpdates());
        traffic.setOnClickListener(view1 -> {
            showTraffic = !showTraffic;
            if (showTraffic) {
                googleMap.setTrafficEnabled(true);
                traffic.setImageResource(R.drawable.ic_traffic_blue);
            } else {
                googleMap.setTrafficEnabled(false);
                traffic.setImageResource(R.drawable.ic_traffic_grey);
            }
        });
        crosshair = view.findViewById(R.id.crosshair);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showButton() {
        if (BottomSheetBehavior.STATE_HIDDEN == bottomSheetBehavior.getState()) {
            setShowNearbyStopsButtonVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        bottomSheetBehavior.setBottomSheetCallback(new ParallaxGoogleMapCallback());
        showNearbyStopsButton.setOnClickListener(v ->
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
    }

    public RxPermissions getRxPermissions() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        return rxPermissions;
    }

    public ReactiveLocationProvider getLocationProvider() {
        if (locationProvider == null) {
            locationProvider = new ReactiveLocationProvider(getContext());
        }
        return locationProvider;
    }

    //TODO fix this mess
    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        getDisposables().add(getRxPermissions().request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        if (currentLocation == null) {
                            getDisposables().add(getLocationProvider().getLastKnownLocation()
                                    .subscribe(location -> {
                                        if (googleMap != null) {
                                            Log.d("LOCATION_UPDATE", "better location");
                                            currentLocation = location;
                                            googleMap.setMyLocationEnabled(true);
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(LocationUtilities.toLatLng(location))
                                                    .zoom(googleMap.getCameraPosition().zoom)
                                                    .build();
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        }
                                    }));
                        } else {
                            if (googleMap != null) {
                                Log.d("LOCATION_UPDATE", "better location");
                                googleMap.setMyLocationEnabled(true);
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(LocationUtilities.toLatLng(currentLocation))
                                        .zoom(googleMap.getCameraPosition().zoom)
                                        .build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        }
                        gps.setImageResource(R.drawable.ic_gps_fixed_blue);
                        LocationRequest request = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(500L);
                        getDisposables().add(getLocationProvider().getUpdatedLocation(request)
                                .subscribe(location -> {
                                    if (isBetterLocation(location, currentLocation) && googleMap != null) {
                                        Log.d("LOCATION_UPDATE", "better location");
                                        currentLocation = location;
                                        googleMap.setMyLocationEnabled(true);
                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(LocationUtilities.toLatLng(location))
                                                .zoom(googleMap.getCameraPosition().zoom)
                                                .build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    }
                                }));
                    }
                }));
    }

    public CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

    @Override
    public void launchRealTimeActivity(String stopId) {
        Intent intent = RealTimeActivity.newIntent(getContext(), stopId);
        startActivity(intent);
    }

    @Override
    public void showNearbyStops(SortedMap<Double, DetailedBusStop> busStops) {
        List<DetailedBusStop> nearbyStops = new ArrayList<>(busStops.values());
        toolbar.setTitle(String.format(Locale.UK, "Stops near %s",
                LocationUtilities.getCoarseAddress(CollectionUtilities
                        .safeFirstElement(nearbyStops))));
        adapter.setDistances(new ArrayList<>(busStops.keySet()));
        adapter.setBusStops(new ArrayList<>(nearbyStops));
        mapMarkerManager.update(nearbyStops);
        //resizeCircle(busStops);
    }

    @Override
    public void onCameraIdle() {
        getPresenter().refreshNearby(LocationUtilities.toLocation(this.googleMap.getCameraPosition().target));
        checkLocation();
    }

    private void checkLocation() {
        if (currentLocation != null) {
            Location newLocation = LocationUtilities.toLocation(googleMap.getCameraPosition().target);
            double distance = currentLocation.distanceTo(newLocation);
            if (distance > 100) {
                crosshair.setVisibility(View.VISIBLE);
            } else {
                crosshair.setVisibility(View.GONE);
            }
        }
    }

    private void setShowNearbyStopsButtonVisibility(int visibility) {
        if (showNearbyStopsButton.getVisibility() != visibility) {
            showNearbyStopsButton.setVisibility(visibility);
        }
    }

    private boolean removeLocationUpdates() {
        Log.d("LOCATION_UPDATE", "remove location updates");
        gps.setImageResource(R.drawable.ic_gps_fixed_grey);
        getDisposables().clear();
        getDisposables().dispose();
        return true;
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public boolean canGoBack() {
        if (BottomSheetBehavior.STATE_HIDDEN == bottomSheetBehavior.getState()) {
            return true;
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        return false;
    }

    private final class MapMarkerManager implements GoogleMap.OnCameraMoveListener, GoogleMap.OnInfoWindowClickListener {

        private static final float ICON_TYPE_ZOOM = 12f;
        private static final float TEXT_VISIBILITY_ZOOM = 16f;

        private Map<DetailedBusStop, Marker> googleMapMarkers;
        private Map<DetailedBusStop, Marker> googleMapTextMarkers;

        private Map<DetailedBusStop, MarkerOptions> googleMapMarkerOptions;
        private Map<DetailedBusStop, MarkerOptions> googleMapTextMarkerOptions;

        private BitmapDescriptor defaultIcon;
        private BitmapDescriptor zoomedOutIcon;

        private float previousZoom;

        private MapMarkerManager(float initialZoom) {
            super();
            previousZoom = initialZoom;
        }

        public void update(List<DetailedBusStop> busStops) {
            addNewMarkers(busStops);
            removeOldMarkers(busStops);
        }

        @Override
        public void onCameraMove() {
            if (mapIsTouched) {
                removeLocationUpdates();
            }

            float currentZoom = googleMap.getCameraPosition().zoom;

            //TODO bug here where some ids are visible when zoomed out
            if (currentZoom <= TEXT_VISIBILITY_ZOOM && previousZoom >= TEXT_VISIBILITY_ZOOM) {
                for (Marker marker : getGoogleMapTextMarkers().values()) {
                    marker.setVisible(false);
                }
            } else if (currentZoom >= TEXT_VISIBILITY_ZOOM && previousZoom <= TEXT_VISIBILITY_ZOOM) {
                for (Marker marker : getGoogleMapTextMarkers().values()) {
                    marker.setVisible(true);
                }
            }

            if (currentZoom <= ICON_TYPE_ZOOM && previousZoom >= ICON_TYPE_ZOOM) {
                for (Marker marker : getGoogleMapMarkers().values()) {
                    marker.setIcon(getZoomedOutIcon());
                }
            } else if (currentZoom >= ICON_TYPE_ZOOM && previousZoom <= ICON_TYPE_ZOOM) {
                for (Marker marker : getGoogleMapMarkers().values()) {
                    marker.setIcon(getDefaultIcon());
                }
            }

            previousZoom = currentZoom;
        }

        private void addNewMarkers(Collection<DetailedBusStop> busStops) {
            BitmapDescriptor icon;
            if (googleMap.getCameraPosition().zoom <= ICON_TYPE_ZOOM) {
                icon = getZoomedOutIcon();
            } else {
                icon = getDefaultIcon();
            }
            for (DetailedBusStop busStop : busStops) {
                if (getGoogleMapMarkers().get(busStop) == null) {
                    Marker marker = googleMap.addMarker(getMarkerOptions(busStop, icon));
                    marker.setIcon(icon);
                    Marker textMarker = googleMap.addMarker(getTextMarkerOptions(busStop));
                    textMarker.setVisible(googleMap.getCameraPosition().zoom >= TEXT_VISIBILITY_ZOOM);
                    AnimationUtilities.fadeInMarker(marker);
                    AnimationUtilities.fadeInMarker(textMarker);
                    getGoogleMapMarkers().put(busStop, marker);
                    getGoogleMapTextMarkers().put(busStop, textMarker);
                }
            }
        }

        private void removeOldMarkers(Collection<DetailedBusStop> busStops) {
            Iterator<Map.Entry<DetailedBusStop, Marker>> iterator = getGoogleMapMarkers().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<DetailedBusStop, Marker> entry = iterator.next();
                DetailedBusStop busStop = entry.getKey();
                if (!busStops.contains(busStop)) {
                    final Marker marker = entry.getValue();
                    AnimationUtilities.fadeOutMarker(marker);
                    iterator.remove();
                }
            }
            Iterator<Map.Entry<DetailedBusStop, Marker>> iterator2 = getGoogleMapTextMarkers().entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<DetailedBusStop, Marker> entry = iterator2.next();
                DetailedBusStop busStop = entry.getKey();
                if (!busStops.contains(busStop)) {
                    final Marker marker = entry.getValue();
                    AnimationUtilities.fadeOutMarker(marker);
                    iterator2.remove();
                }
            }
        }

        private MarkerOptions getMarkerOptions(DetailedBusStop busStop, BitmapDescriptor icon) {
            MarkerOptions markerOptions = getGoogleMapMarkerOptions().get(busStop);
            if (markerOptions == null) {
                markerOptions = newMarkerOptions(busStop, icon);
                getGoogleMapMarkerOptions().put(busStop, markerOptions);
            }
            return markerOptions;
        }

        private MarkerOptions getTextMarkerOptions(DetailedBusStop busStop) {
            MarkerOptions markerOptions = getGoogleMapTextMarkerOptions().get(busStop);
            if (markerOptions == null) {
                markerOptions = newTextMarkerOptions(busStop);
                getGoogleMapTextMarkerOptions().put(busStop, markerOptions);
            }
            return markerOptions;
        }

        private MarkerOptions newMarkerOptions(DetailedBusStop busStop, BitmapDescriptor icon) {
            return new MarkerOptions()
                    .position(new LatLng(busStop.getLatitude(), busStop.getLongitude()))
                    .anchor(0.3f, 1.0f)
                    .title(busStop.getName())
                    .snippet(String.format(Locale.UK, getString(R.string.formatted_stop_id), busStop.getId()))
                    .infoWindowAnchor(0.3f, 0.0f)
                    .icon(icon);
        }

        private MarkerOptions newTextMarkerOptions(DetailedBusStop busStop) {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            Paint stkPaint = new Paint(ANTI_ALIAS_FLAG);
            stkPaint.setTextSize(px);
            stkPaint.setTextAlign(Paint.Align.LEFT);
            stkPaint.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD));
            stkPaint.setStyle(Paint.Style.STROKE);
            stkPaint.setStrokeWidth(5);
            stkPaint.setColor(Color.WHITE);
            float baseline = -stkPaint.ascent(); // ascent() is negative
            int width = (int) (stkPaint.measureText(busStop.getId()) + 0.5f); // round
            int height = (int) (baseline + stkPaint.descent() + 0.5f);
            Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(image);
            canvas.drawText(busStop.getId(), 0, baseline, stkPaint);

            Paint fillPaint = new Paint(ANTI_ALIAS_FLAG);
            fillPaint.setTextSize(px);
            fillPaint.setColor(ContextCompat.getColor(getContext(), R.color.textColorSecondary));
            fillPaint.setTextAlign(Paint.Align.LEFT);
            fillPaint.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            canvas.drawText(busStop.getId(), 0, baseline, fillPaint);

            return new MarkerOptions()
                    .position(new LatLng(busStop.getLatitude(), busStop.getLongitude()))
                    .anchor(0.5f, -0.2f)
                    .visible(googleMap.getCameraPosition().zoom >= TEXT_VISIBILITY_ZOOM)
                    .icon(BitmapDescriptorFactory.fromBitmap(image));
        }

        private BitmapDescriptor getDefaultIcon() {
            if (defaultIcon == null) {
                defaultIcon = SVGUtils.vectorToBitmap(getContext(), R.drawable.ic_map_marker_bus_double_decker_default);
            }
            return defaultIcon;
        }

        private BitmapDescriptor getZoomedOutIcon() {
            if (zoomedOutIcon == null) {
                zoomedOutIcon = SVGUtils.vectorToBitmap(getContext(), R.drawable.ic_map_marker_bus_double_decker_far);
            }
            return zoomedOutIcon;
        }

        private Map<DetailedBusStop, Marker> getGoogleMapMarkers() {
            if (googleMapMarkers == null) {
                googleMapMarkers = new HashMap<>();
            }
            return googleMapMarkers;
        }

        private Map<DetailedBusStop, Marker> getGoogleMapTextMarkers() {
            if (googleMapTextMarkers == null) {
                googleMapTextMarkers = new HashMap<>();
            }
            return googleMapTextMarkers;
        }

        private Map<DetailedBusStop, MarkerOptions> getGoogleMapMarkerOptions() {
            if (googleMapMarkerOptions == null) {
                googleMapMarkerOptions = new HashMap<>();
            }
            return googleMapMarkerOptions;
        }

        private Map<DetailedBusStop, MarkerOptions> getGoogleMapTextMarkerOptions() {
            if (googleMapTextMarkerOptions == null) {
                googleMapTextMarkerOptions = new HashMap<>();
            }
            return googleMapTextMarkerOptions;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            //TODO check hardcoded string if you ever do translations
            try {
                String stopId = marker.getSnippet().split("Stop")[1].trim();
                launchRealTimeActivity(stopId);
            } catch (Exception e) {
                // nbd
            }
        }

    }

    private final class ParallaxGoogleMapCallback extends BottomSheetBehavior.BottomSheetCallback {

        private ParallaxGoogleMapCallback() {
            super();
        }

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_DRAGGING:
                case BottomSheetBehavior.STATE_SETTLING:
                case BottomSheetBehavior.STATE_EXPANDED:
                case BottomSheetBehavior.STATE_COLLAPSED:
                    setShowNearbyStopsButtonVisibility(View.GONE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    setShowNearbyStopsButtonVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            View view = googleMapFragment.getView();
            if (view != null) {
                int dy = bottomSheet.getTop() - coordinatorLayout.getHeight();
                view.setTranslationY(dy / 2);
                crosshair.setTranslationY(dy / 2);
                if (dy >= -bottomSheetPeekHeightPx) {
                    gps.setTranslationY(dy);
                    traffic.setTranslationY(dy);
                }
            }
        }

    }

//    Polygon polygon;
//    private void resizeCircle(SortedMap<Double, DetailedBusStop> busStops) {
//        Double distance = CollectionUtilities.safeLastElement(busStops.keySet());
//        Log.i("DISTANCE", String.valueOf(distance));
//        if (distance != null) {
//            if (polygon == null) {
//                polygon = googleMap.addPolygon(createPolygonWithCircle(getContext(),
//                        googleMap.getCameraPosition().target, (int) distance.doubleValue() / 1000 ));
//            }
//            polygon.setHoles(Collections.singletonList(createHole(googleMap.getCameraPosition().target, distance / 1000)));
//        }
//    }
//
//    private static List<LatLng> createOuterBounds() {
//        float delta = 0.01f;
//
//        return new ArrayList<LatLng>() {{
//            add(new LatLng(90 - delta, -180 + delta));
//            add(new LatLng(0, -180 + delta));
//            add(new LatLng(-90 + delta, -180 + delta));
//            add(new LatLng(-90 + delta, 0));
//            add(new LatLng(-90 + delta, 180 - delta));
//            add(new LatLng(0, 180 - delta));
//            add(new LatLng(90 - delta, 180 - delta));
//            add(new LatLng(90 - delta, 0));
//            add(new LatLng(90 - delta, -180 + delta));
//        }};
//    }
//
//    private static List<LatLng> createHole(LatLng center, double radius) {
//        radius = radius + 0.1;
//        int points = 50; // number of corners of inscribed polygon
//
//        double radiusLatitude = Math.toDegrees(radius / (float) 6371);
//        double radiusLongitude = radiusLatitude / Math.cos(Math.toRadians(center.latitude));
//
//        List<LatLng> result = new ArrayList<>(points);
//
//        double anglePerCircleRegion = 2 * Math.PI / points;
//
//        for (int i = 0; i < points; i++) {
//            double theta = i * anglePerCircleRegion;
//            double latitude = center.latitude + (radiusLatitude * Math.sin(theta));
//            double longitude = center.longitude + (radiusLongitude * Math.cos(theta));
//
//            result.add(new LatLng(latitude, longitude));
//        }
//
//        return result;
//    }
//
//    static PolygonOptions createPolygonWithCircle(Context context, LatLng center, int radius) {
//        return new PolygonOptions()
//                .fillColor(ContextCompat.getColor(context, R.color.map_overlay))
//                .addAll(createOuterBounds())
//                .strokeWidth(0);
//    }

}
