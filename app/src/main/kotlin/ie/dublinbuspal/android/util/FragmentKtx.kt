package ie.dublinbuspal.android.util

import androidx.fragment.app.Fragment
import ie.dublinbuspal.android.DublinBusApplication
import ie.dublinbuspal.di.ApplicationComponent

fun Fragment.getApplicationComponent(): ApplicationComponent {
    return (requireActivity().application as DublinBusApplication).applicationComponent
}
