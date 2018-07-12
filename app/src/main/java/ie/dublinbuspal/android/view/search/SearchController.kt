package ie.dublinbuspal.android.view.search

import android.os.Bundle
import android.view.View
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseViewController

class SearchController(args: Bundle) : BaseViewController<SearchView, SearchPresenter>(args), SearchView {

    override fun getLayoutId() = R.layout.view_search

    override fun createPresenter(): SearchPresenter {
        return applicationComponent()?.searchPresenter()!!
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

}
