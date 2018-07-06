package ie.dublinbuspal.android.view.nearby

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.domain.model.BusStop
import java.util.SortedMap

interface NearbyView : MvpView {

    fun showBusStops(busStops: SortedMap<Double, BusStop>)

}
