package ie.dublinbuspal.android.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseViewController
import kotlinx.android.synthetic.main.view_search.view.*

class SearchController(args: Bundle) : BaseViewController<SearchView, SearchPresenter>(args), SearchView {

    override fun getLayoutId() = R.layout.view_search

    override fun createPresenter(): SearchPresenter {
        return applicationComponent()?.searchPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupSearchBar(view)
        return view
    }

    private fun setupSearchBar(view: View) {
        view.search_view.queryHint = resources?.getString(R.string.search_hint)
        view.search_view.setIconifiedByDefault(false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

}
