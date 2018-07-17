package ie.dublinbuspal.android.view.livedata

import android.os.Bundle
import android.view.View
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseMvpController
import ie.dublinbuspal.domain.model.livedata.LiveData
import timber.log.Timber

class LiveDataController(args: Bundle) : BaseMvpController<LiveDataView, LiveDataPresenter>(args) , LiveDataView {

    override fun getLayoutId() = R.layout.view_livedata

    override fun createPresenter(): LiveDataPresenter {
        return applicationComponent()?.liveDataPresenter()!!
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start(args.getString(ID))
    }

    override fun showLiveData(liveData: List<LiveData>) {
        Timber.d(liveData.toString())
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
