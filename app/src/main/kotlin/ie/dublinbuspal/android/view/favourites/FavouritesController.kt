package ie.dublinbuspal.android.view.favourites

import android.os.Bundle
import android.view.View
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseViewController

class FavouritesController(args: Bundle) : BaseViewController<FavouritesView, FavouritesPresenter>(args), FavouritesView {

    override fun getLayoutId() = R.layout.view_favourites

    override fun createPresenter(): FavouritesPresenter {
        return applicationComponent()?.favouritesPresenter()!!
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

}
