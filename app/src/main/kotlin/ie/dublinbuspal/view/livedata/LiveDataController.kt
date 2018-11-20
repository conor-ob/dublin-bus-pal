package ie.dublinbuspal.view.livedata

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.view.BaseMvpController
import ie.dublinbuspal.view.livedata.adapter.LiveDataDiffCallback
import ie.dublinbuspal.view.livedata.adapter.LiveDataItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_favourites.view.*
import timber.log.Timber
import java.util.*

class LiveDataController(args: Bundle) : BaseMvpController<LiveDataView, LiveDataPresenter>(args) , LiveDataView {

    private lateinit var fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>
    private lateinit var liveDataAdapter: ItemAdapter<LiveDataItem>

    override fun getLayoutId() = R.layout.view_live_data

    override fun createPresenter(): LiveDataPresenter {
        return applicationComponent()?.liveDataPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
//        setupActionBar(view)
//        setHasOptionsMenu(true)
        setupView(view)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_realtime_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupView(view: View) {
//        view.toolbar.stop_name.text = args.getString(NAME)
//        view.toolbar.stop_id.text = resources?.getString(R.string.formatted_stop_id, args.getString(ID))
        liveDataAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(Collections.singletonList(liveDataAdapter))
        fastAdapter.withSelectable(true)
        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = fastAdapter
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start(args.getString(ID))
    }

    override fun onDestroyView(view: View) {
        presenter.stop()
        super.onDestroyView(view)
    }

    override fun showLiveData(liveData: List<LiveData>) {
        Single.fromCallable { liveData.map { LiveDataItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(liveDataAdapter, it, LiveDataDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { FastAdapterDiffUtil.set(liveDataAdapter, it) }
                .subscribe()
    }

    override fun showBusStop(stop: ResolvedStop) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
