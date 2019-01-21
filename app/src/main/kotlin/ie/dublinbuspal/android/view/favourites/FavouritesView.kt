package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpView

interface FavouritesView : MvpView {

    fun render(viewModel: ViewModel)

    fun launchRealTimeActivity(stopId: String)

}
