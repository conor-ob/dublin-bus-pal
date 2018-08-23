package ie.dublinbuspal.android.view.livedata

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.livedata.LiveData

interface LiveDataView : MvpView {

    fun showLiveData(liveData: List<LiveData>)

}
