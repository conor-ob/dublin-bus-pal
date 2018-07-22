package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stop.Stop

interface FavouritesView : MvpView {

    fun showFavourites(favourites: List<Stop>)

    fun showLiveData(livedata: List<LiveData>)

}
