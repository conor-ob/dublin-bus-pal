package ie.dublinbuspal.android.view.livedata

import android.os.Bundle
import android.view.*
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseMvpController
import ie.dublinbuspal.model.livedata.LiveData
import kotlinx.android.synthetic.main.view_livedata.view.*
import timber.log.Timber

class LiveDataController(args: Bundle) : BaseMvpController<LiveDataView, LiveDataPresenter>(args) , LiveDataView {

    override fun getLayoutId() = R.layout.view_livedata

    override fun createPresenter(): LiveDataPresenter {
        return applicationComponent()?.liveDataPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupActionBar(view)
        setHasOptionsMenu(true)
        setupView(view)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_realtime_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupView(view: View) {
        view.toolbar.stop_name.text = args.getString(NAME)
        view.toolbar.stop_id.text = resources?.getString(R.string.formatted_stop_id, args.getString(ID))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start(args.getString(ID))
    }

    override fun showLiveData(liveData: List<LiveData>) {
        Timber.d(liveData.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            return router.handleBack()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val ID = "stop_id"
        private const val NAME = "stop_name"

    }

    class Builder(private val stopId: String,
                  private val stopName: String) {

        fun build(): LiveDataController {
            val args = Bundle()
            args.putString(ID, stopId)
            args.putString(NAME, stopName)
            return LiveDataController(args)
        }

    }

}
