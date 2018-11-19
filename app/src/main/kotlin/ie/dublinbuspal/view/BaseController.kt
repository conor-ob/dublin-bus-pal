package ie.dublinbuspal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bluelinelabs.conductor.Controller

abstract class BaseController(args: Bundle) : Controller(args) {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

}
