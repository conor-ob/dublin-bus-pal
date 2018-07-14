package ie.dublinbuspal.android.view.news

import android.os.Bundle
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseViewController

class NewsController(args: Bundle) : BaseViewController<NewsView, NewsPresenter>(args), NewsView {

    override fun getLayoutId() = R.layout.view_news

    override fun createPresenter(): NewsPresenter {
        return applicationComponent()?.newsPresenter()!!
    }

}
