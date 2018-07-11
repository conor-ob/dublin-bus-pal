package ie.dublinbuspal.android.view.nearby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.util.AnimationUtils
import ie.dublinbuspal.android.util.ImageUtils
import ie.dublinbuspal.android.view.BaseViewController
import ie.dublinbuspal.android.view.livedata.LiveDataController
import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.domain.model.stop.BusStop
import java.util.*

class NearbyController(args: Bundle) : BaseViewController<NearbyView, NearbyPresenter>(args), NearbyView {

    private lateinit var mapView: NearbyMapView
    private lateinit var googleMap: GoogleMap
    private val mapMarkers = hashMapOf<BusStop, Marker>()

    override fun getLayoutId() = R.layout.view_nearby

    override fun createPresenter(): NearbyPresenter {
        return applicationComponent()?.nearbyPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupMap(view)
        return view
    }

    private fun setupMap(view: View) {
        mapView = view.findViewById(R.id.google_map)
        mapView.onCreate(null)
        mapView.onStart()
        mapView.getMapAsync {
            googleMap = it
            googleMap.apply {
                setOnCameraIdleListener {
                    val coordinate = Coordinate(googleMap.cameraPosition.target.latitude, googleMap.cameraPosition.target.longitude)
                    presenter.start(coordinate)
                }
                setOnInfoWindowClickListener {
                    onBusStopClicked(it.tag as String)
                }
    //                setOnCameraMoveListener {
    //                    if (mapView.mapIsTouched()) {
    //                        gpsButton.setImageResource(R.drawable.ic_gps_fixed_inactive)
    //                        presenter.onRemoveLocationUpdates()
    //                    }
    //                }
                uiSettings.isMyLocationButtonEnabled = false
                uiSettings.isMapToolbarEnabled = false
                uiSettings.isCompassEnabled = false
                uiSettings.isRotateGesturesEnabled = false
                uiSettings.isTiltGesturesEnabled = false
            }
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        mapView.onResume()
    }

    override fun onDetach(view: View) {
        mapView.onPause()
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        mapView.onStop()
        mapView.onDestroy()
        super.onDestroyView(view)
    }

    private fun onBusStopClicked(stopId: String) {
        router.pushController(RouterTransaction
                .with(LiveDataController
                        .Builder(stopId)
                        .build())
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }

    override fun showBusStops(busStops: SortedMap<Double, BusStop>) {
        addNewMarkers(busStops.values)
        removeOldMarkers(busStops.values)
    }

    private fun addNewMarkers(busStops: Collection<BusStop>) {
        for (busStop in busStops) {
            if (mapMarkers[busStop] == null) {
                val marker = googleMap.addMarker(MarkerOptions()
                        .position(LatLng(busStop.coordinate.x, busStop.coordinate.y))
                        .anchor(0.3f, 1.0f)
                        .infoWindowAnchor(0.3f, 0.0f)
                        .title(busStop.name)
                        .icon(ImageUtils.drawableToBitmap(applicationContext!!, R.drawable.ic_map_marker_bus_double_decker_default)))
                marker.tag = busStop.id
                mapMarkers[busStop] = marker
                AnimationUtils.fadeInMarker(marker)
            }
        }
    }

    private fun removeOldMarkers(busStops: Collection<BusStop>) {
        val iterator = mapMarkers.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!busStops.contains(entry.key)) {
                AnimationUtils.fadeOutMarker(entry.value)
                iterator.remove()
            }
        }
    }

}
