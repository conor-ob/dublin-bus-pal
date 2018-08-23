package ie.dublinbuspal.android.view.nearby

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.Coordinate
import ie.dublinbuspal.model.stop.Stop
import java.util.*

interface NearbyView : MvpView {

    fun showBusStops(stops: SortedMap<Double, Stop>)

    fun moveCamera(location: Pair<Coordinate, Float>)

}
