package ie.dublinbuspal.view.nearby

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.util.Coordinate
import java.util.*

interface NearbyView : MvpView {

    fun showBusStops(stops: SortedMap<Double, ResolvedStop>)

    fun moveCamera(location: Pair<Coordinate, Float>)

}
