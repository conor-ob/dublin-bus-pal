package ie.dublinbuspal.android.view.routeservice

import android.os.Bundle
import android.view.View
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseMvpController

class RouteServiceController(args: Bundle) : BaseMvpController<RouteServiceView, RouteServicePresenter>(args), RouteServiceView {

    override fun getLayoutId() = R.layout.view_route

    override fun createPresenter(): RouteServicePresenter {
        return applicationComponent()?.routePresenter()!!
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start(args.getString(ID))
    }

    companion object {

        private const val ID = "route_id"

    }

    class Builder(private val stopId: String) {

        fun build(): RouteServiceController {
            val args = Bundle()
            args.putString(ID, stopId)
            return RouteServiceController(args)
        }

    }

}
