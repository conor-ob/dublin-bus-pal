package ie.dublinbuspal.view.search

import com.hannesdorfmann.mosby3.mvp.MvpView

interface SearchView : MvpView {

    fun showSearchResult(searchResult: List<Any>)

    fun onBusStopClicked(id: String, name: String)

}
