package ie.dublinbuspal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bluelinelabs.conductor.Controller
import timber.log.Timber

abstract class BaseController(args: Bundle) : Controller(args) {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.d("${javaClass.simpleName}.${object{}.javaClass.enclosingMethod?.name}")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.d("${javaClass.simpleName}.${object{}.javaClass.enclosingMethod?.name}")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        Timber.d("${javaClass.simpleName}.${object{}.javaClass.enclosingMethod?.name}")
        super.onSaveViewState(view, outState)
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        Timber.d("${javaClass.simpleName}.${object{}.javaClass.enclosingMethod?.name}")
        super.onRestoreViewState(view, savedViewState)
    }

}
