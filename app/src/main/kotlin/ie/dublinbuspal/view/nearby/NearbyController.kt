package ie.dublinbuspal.view.nearby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.util.AnimationUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.ImageUtils
import ie.dublinbuspal.view.BaseMvpController
import kotlinx.android.synthetic.main.view_nearby.view.*
import java.util.*

class NearbyController(args: Bundle) : BaseMvpController<NearbyView, NearbyPresenter>(args), NearbyView {

//    private lateinit var bottomSheet: ViewGroup
    private lateinit var googleMap: GoogleMap
    private val mapMarkers = hashMapOf<String, Marker>()

    override fun getLayoutId() = R.layout.view_nearby

    override fun createPresenter(): NearbyPresenter {
        return applicationComponent()?.nearbyPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
//        setupView(view)
        setupMap(view)
        return view
    }

//    private fun setupView(view: View) {
//        bottomSheet = view.bottom_sheet_container
//    }

    private fun setupMap(view: View) {
        view.google_map.onCreate(null)
        view.google_map.onStart()
        view.google_map.getMapAsync {
            googleMap = it
            googleMap.apply {
                setOnCameraIdleListener {
                    val coordinate = Coordinate(googleMap.cameraPosition.target.latitude, googleMap.cameraPosition.target.longitude)
                    presenter.refresh(coordinate)
                }
                setOnInfoWindowClickListener { marker ->
                    val tag = marker.tag as String
                    val tags = tag.split("::")
//                    onBusStopClicked(tags[0], tags[1])
                }
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
        view.google_map.onResume()
        presenter.start()
    }

    override fun onDetach(view: View) {
        presenter.stop(Pair(Coordinate(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude),
                googleMap.cameraPosition.zoom))
        view.google_map.onPause()
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        mapMarkers.clear()
        view.google_map.onStop()
        view.google_map.onDestroy()
        super.onDestroyView(view)
    }

    override fun moveCamera(location: Pair<Coordinate, Float>) {
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder()
                .target(LatLng(location.first.x, location.first.y))
                .zoom(location.second)
                .build()))
    }

//    private fun onBusStopClicked(stopId: String, stopName: String) {
//        parentController?.router?.pushController(RouterTransaction
//                .with(LiveDataController
//                        .Builder(stopId, stopName)
//                        .build())
//                .pushChangeHandler(FadeChangeHandler(500L))
//                .popChangeHandler(FadeChangeHandler(500L)))
//    }

    override fun showBusStops(stops: SortedMap<Double, ResolvedStop>) {
        focusOnNearestStop(stops.values)
        addNewMarkers(stops.values)
        removeOldMarkers(stops.values)
    }

    private fun focusOnNearestStop(stops: Collection<ResolvedStop>) {
//        val nearestStop = CollectionUtils.safeFirstElement(stops)
//        val liveDataRouter = getChildRouter(bottomSheet)
//        if (!liveDataRouter.hasRootController()) {
//            liveDataRouter.setRoot(RouterTransaction.with(NearbyLiveDataController()))
//        }
    }

    private fun addNewMarkers(stops: Collection<ResolvedStop>) {
        for (busStop in stops) {
            if (mapMarkers[busStop.id()] == null) {
                val marker = googleMap.addMarker(MarkerOptions()
                        .position(LatLng(busStop.coordinate().x, busStop.coordinate().y))
                        .anchor(0.3f, 1.0f)
                        .infoWindowAnchor(0.3f, 0.0f)
                        .title(busStop.name())
                        .icon(ImageUtils.drawableToBitmap(applicationContext!!, R.drawable.ic_map_marker_bus_double_decker_default)))
                marker.tag = "${busStop.id()}::${busStop.name()}"
                mapMarkers[busStop.id()] = marker
                AnimationUtils.fadeInMarker(marker)
            }
        }
    }
    private fun removeOldMarkers(stops: Collection<ResolvedStop>) {
        val iterator = mapMarkers.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!stops.map { it.id() }.contains(entry.key)) {
                AnimationUtils.fadeOutMarker(entry.value)
                iterator.remove()
            }
        }
    }
}
