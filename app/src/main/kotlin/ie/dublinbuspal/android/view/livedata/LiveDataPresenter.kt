package ie.dublinbuspal.android.view.livedata

import android.view.Menu
import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface LiveDataPresenter : MvpPresenter<LiveDataView> {

    fun onResume(stopId: String)

    fun onPause()

    fun onRouteFilterPressed(route: String)

    fun onCreateOptionsMenu(menu: Menu)

    fun onAddFavouritePressed()

    fun onRemoveFavouritePressed()

    fun onSaveFavourite(name: String, routes: MutableList<String>)

}
