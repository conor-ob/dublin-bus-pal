package ie.dublinbuspal.view.search

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
import com.mikepenz.fastadapter.listeners.ItemFilterListener
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.view.BaseMvpController
import ie.dublinbuspal.view.search.adapter.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_search.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class SearchController(args: Bundle) : BaseMvpController<SearchView, SearchPresenter>(args), SearchView {

    private lateinit var fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>
    private lateinit var stickyHeaderAdapter: SearchStickyHeaderAdapter
    private lateinit var stopsAdapter: ItemAdapter<StopItem>
    private lateinit var routesAdapter: ItemAdapter<RouteItem>

    override fun getLayoutId() = R.layout.view_search

    override fun createPresenter(): SearchPresenter {
        return applicationComponent()?.searchPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
//        setupSearch(view)
        setupRecyclerView(view)
        return view
    }

//    private fun setupSearch(view: View) {
//        view.search_view.queryHint = resources?.getString(R.string.search_hint)?.toString()
//        view.search_view.setIconifiedByDefault(false)
//
//        Observable.create(ObservableOnSubscribe<String> { subscriber ->
//            view.search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    subscriber.onNext(newText!!)
//                    return false
//                }
//
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    subscriber.onNext(query!!)
//                    return false
//                }
//            })
//        })
//                .map { it.toLowerCase().trim() }
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .subscribe {
//                    stopsAdapter.filter(it)
//                    routesAdapter.filter(it)
//                }
//    }

    private fun setupRecyclerView(view: View) {
        stickyHeaderAdapter = SearchStickyHeaderAdapter()
        stopsAdapter = ItemAdapter()
        routesAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(Arrays.asList(routesAdapter, stopsAdapter))
        fastAdapter.withSelectable(true)
        val decoration = StickyRecyclerHeadersDecoration(stickyHeaderAdapter)
        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = stickyHeaderAdapter.wrap(fastAdapter)
            addItemDecoration(decoration)
        }
        stopsAdapter.itemFilter.withFilterPredicate { item, constraint ->
            String.format("%s %s", item.stop.id, item.stop.name).toLowerCase().contains(constraint!!)
        }
        stopsAdapter.itemFilter.withItemFilterListener(object: ItemFilterListener<StopItem> {

            override fun itemsFiltered(constraint: CharSequence?, results: List<StopItem>?) {
                stickyHeaderAdapter.notifyDataSetChanged()
            }

            override fun onReset() { }

        })
        routesAdapter.itemFilter.withFilterPredicate { item, constraint ->
            String.format("%s %s %s", item.route.id, item.route.origin, item.route.destination).toLowerCase().contains(constraint!!)
        }
        routesAdapter.itemFilter.withItemFilterListener(object:ItemFilterListener<RouteItem> {

            override fun itemsFiltered(constraint: CharSequence?, results: List<RouteItem>?) {
                stickyHeaderAdapter.notifyDataSetChanged()
            }

            override fun onReset() { }

        })
        stickyHeaderAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                decoration.invalidateHeaders()
            }
        })
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

    override fun showStops(stops: List<Stop>) {
        Single.fromCallable { stops.map { StopItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(stopsAdapter, it, StopDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(stopsAdapter, it) }
    }

    override fun showRoutes(routes: List<Route>) {
        Single.fromCallable { routes.map { RouteItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(routesAdapter, it, RouteDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(routesAdapter, it) }
    }

}
