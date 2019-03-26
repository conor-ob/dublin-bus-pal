package ie.dublinbuspal.android.view.livedata

import android.view.Menu
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LiveDataView : MvpView {

    fun renderBusStop(viewModel: ViewModel)

    fun renderLiveData(viewModel: ViewModel)

    fun renderOptionsMenu(viewModel: ViewModel, menu: Menu)

    fun renderRouteFilters(viewModel: ViewModel)

    fun renderAddFavouritesDialog(viewModel: ViewModel)

    fun renderError(errorMessageId: Int)

    fun renderSnackbar(messageId: Int)

    fun launchRouteActivity(routeId: String)

}
