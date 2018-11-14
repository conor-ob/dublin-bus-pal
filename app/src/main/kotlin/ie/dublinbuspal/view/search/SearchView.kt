package ie.dublinbuspal.view.search

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.ResolvedStop

interface SearchView : MvpView {

    fun showStops(stops: List<ResolvedStop>)

    fun showRoutes(routes: List<Route>)

}
