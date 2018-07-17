package ie.dublinbuspal.android.view

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController
import ie.dublinbuspal.android.DublinBusApplication
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.di.ApplicationComponent

abstract class BaseMvpController<V : MvpView, P : MvpPresenter<V>>(args: Bundle) : MvpController<V, P>(args) {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected fun applicationComponent(): ApplicationComponent? {
        return ((activity?.application as? DublinBusApplication)?.applicationComponent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    protected fun setupActionBar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        getActionBar()?.setDisplayHomeAsUpEnabled(router.backstackSize > 1)
        getActionBar()?.setDisplayShowTitleEnabled(false)
    }

    protected fun setActionBarTitle(value: String) {
        getActionBar()?.setDisplayShowTitleEnabled(true)
        getActionBar()?.title = value
    }

    private fun getActionBar(): ActionBar? {
        return (activity as? AppCompatActivity)?.supportActionBar
    }

}
