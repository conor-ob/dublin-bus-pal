package ie.dublinbuspal.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ie.dublinbuspal.android.R
import ie.dublinbuspal.view.BaseMvpController
import ie.dublinbuspal.view.livedata.LiveDataController
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.view_search.view.*
import java.util.concurrent.TimeUnit

class SearchController(args: Bundle) : BaseMvpController<SearchView, SearchPresenter>(args), SearchView {

    private lateinit var searchAdapter: SearchAdapter

    override fun getLayoutId() = R.layout.view_search

    override fun createPresenter(): SearchPresenter {
        return applicationComponent()?.searchPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupSearch(view)
        setupRecyclerView(view)
        return view
    }

    private fun setupSearch(view: View) {
        view.search_view.queryHint = resources?.getString(R.string.search_hint)?.toString()
        view.search_view.setIconifiedByDefault(false)

        val disposable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
            view.search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
                .map { it.toLowerCase().trim() }
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.search(it)
                }
    }

    private fun setupRecyclerView(view: View) {
        searchAdapter = SearchAdapter(this)
        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

    override fun showSearchResult(searchResult: List<Any>) {
        searchAdapter.setSearchResult(searchResult)
    }

    override fun onBusStopClicked(id: String, name: String) {
        parentController?.router?.pushController(RouterTransaction
                .with(LiveDataController
                        .Builder(id, name)
                        .build())
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L)))
    }

}
