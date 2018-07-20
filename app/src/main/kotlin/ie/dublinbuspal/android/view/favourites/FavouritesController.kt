package ie.dublinbuspal.android.view.favourites

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseMvpController
import ie.dublinbuspal.android.view.search.adapter.StopDiffCallback
import ie.dublinbuspal.android.view.search.adapter.StopItem
import ie.dublinbuspal.domain.model.stop.Stop
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_favourites.view.*
import java.util.*

class FavouritesController(args: Bundle) : BaseMvpController<FavouritesView, FavouritesPresenter>(args), FavouritesView {

    private lateinit var fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>
    private lateinit var stopsAdapter: ItemAdapter<StopItem>

    override fun getLayoutId() = R.layout.view_favourites

    override fun createPresenter(): FavouritesPresenter {
        return applicationComponent()?.favouritesPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        stopsAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(Arrays.asList(stopsAdapter))
        fastAdapter.withSelectable(true)
        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = fastAdapter
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

    override fun showFavourites(favourites: List<Stop>) {
        Single.fromCallable { favourites.map { StopItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(stopsAdapter, it, StopDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(stopsAdapter, it) }
    }

}
