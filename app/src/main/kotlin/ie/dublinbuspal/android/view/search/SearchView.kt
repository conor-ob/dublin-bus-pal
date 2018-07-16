package ie.dublinbuspal.android.view.search

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.domain.model.stop.Stop

interface SearchView : MvpView {

    fun showStops(stops: List<Stop>)

    fun showRoutes(routes: List<Route>)

}
