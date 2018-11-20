package ie.dublinbuspal.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.view.BaseMvpController
import ie.dublinbuspal.view.favourites.adapter.FavouriteItem
import ie.dublinbuspal.view.favourites.adapter.FavouritesDiffCallback
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_favourites.view.*
import java.util.*

class FavouritesController(args: Bundle) : BaseMvpController<FavouritesView, FavouritesPresenter>(args), FavouritesView {

    private lateinit var fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>
    private lateinit var stopsAdapter: ItemAdapter<FavouriteItem>

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
        fastAdapter = FastAdapter.with(Collections.singletonList(stopsAdapter))
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

    override fun showFavourites(favourites: List<FavouriteStop>) {
        Single.fromCallable { favourites.map { FavouriteItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(stopsAdapter, it, FavouritesDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(stopsAdapter, it) }
    }

    override fun showLiveData(favourites: List<FavouriteStop>, favouriteId: String, livedata: Map<Pair<String, Destination>, List<LiveData>>) {
        Single.fromCallable {
            favourites.map {
                if (it.id == favouriteId) {
                    return@map FavouriteItem(it, livedata)
                }
                return@map FavouriteItem(it)
            }
        }
                .map { FastAdapterDiffUtil.calculateDiff(stopsAdapter, it, FavouritesDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(stopsAdapter, it) }
    }

}
