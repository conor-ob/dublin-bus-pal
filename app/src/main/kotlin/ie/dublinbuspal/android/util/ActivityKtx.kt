package ie.dublinbuspal.android.util

import android.app.Activity
import ie.dublinbuspal.android.DublinBusApplication
import ie.dublinbuspal.di.ApplicationComponent

fun Activity.getApplicationComponenta(): ApplicationComponent {
    return (application as DublinBusApplication).applicationComponent
}
