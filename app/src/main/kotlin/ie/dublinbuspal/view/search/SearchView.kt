package ie.dublinbuspal.view.search

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.Stop

interface SearchView : MvpView {

    fun showStops(stops: List<Stop>)

    fun showRoutes(routes: List<Route>)

}
