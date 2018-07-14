package ie.dublinbuspal.android.view.livedata

import android.os.Bundle
import android.util.Log
import android.view.View
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseViewController
import ie.dublinbuspal.domain.model.livedata.LiveData

class LiveDataController(args: Bundle) : BaseViewController<LiveDataView, LiveDataPresenter>(args) , LiveDataView {

    override fun getLayoutId() = R.layout.view_live_data

    override fun createPresenter(): LiveDataPresenter {
        return applicationComponent()?.liveDataPresenter()!!
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start(args.getString(ID))
    }

    override fun showLiveData(liveData: List<LiveData>) {
        Log.i("LIVE", liveData.toString())
    }

    companion object {

        private const val ID = "stop_id"

    }

    class Builder(private val stopId: String) {

        fun build(): LiveDataController {
            val args = Bundle()
            args.putString(ID, stopId)
            return LiveDataController(args)
        }

    }

}
