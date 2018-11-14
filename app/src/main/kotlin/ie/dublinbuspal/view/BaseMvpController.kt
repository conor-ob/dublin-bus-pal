package ie.dublinbuspal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController
import ie.dublinbuspal.android.DublinBusApplication
import ie.dublinbuspal.android.R
import ie.dublinbuspal.di.ApplicationComponent

abstract class BaseMvpController<V : MvpView, P : MvpPresenter<V>>(args: Bundle) : MvpController<V, P>(args) {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected fun applicationComponent(): ApplicationComponent? {
        return ((activity?.application as? DublinBusApplication)?.applicationComponent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

//    protected fun setupActionBar(view: View) {
//        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
//        getActionBar()?.setDisplayHomeAsUpEnabled(router.backstackSize > 1)
//        getActionBar()?.setDisplayShowTitleEnabled(false)
//    }

    protected fun setActionBarTitle(value: String) {
        getActionBar()?.setDisplayShowTitleEnabled(true)
        getActionBar()?.title = value
    }

    private fun getActionBar(): ActionBar? {
        return (activity as? AppCompatActivity)?.supportActionBar
    }

}
