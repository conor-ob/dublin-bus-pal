package ie.dublinbuspal.view.livedata

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.stop.ResolvedStop

interface LiveDataView : MvpView {

    fun showLiveData(liveData: List<LiveData>)

    fun showBusStop(stop: ResolvedStop)

}
