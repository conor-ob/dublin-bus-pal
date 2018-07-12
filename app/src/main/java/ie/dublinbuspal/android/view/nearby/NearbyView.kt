package ie.dublinbuspal.android.view.nearby

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.domain.model.stop.Stop
import java.util.SortedMap

interface NearbyView : MvpView {

    fun showBusStops(stops: SortedMap<Double, Stop>)

}
