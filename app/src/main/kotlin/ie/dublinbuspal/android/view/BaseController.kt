package ie.dublinbuspal.android.view

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller

abstract class BaseController : Controller() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

}
