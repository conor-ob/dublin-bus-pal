package ie.dublinbuspal.android.view.search.adapter

import android.widget.TextView
import ie.dublinbuspal.android.R

object SearchHeaderRenderer {

    fun renderStopsHeader(textView: TextView, count: Int) {
        if (count == 0) {
            textView.text = String.format(textView.resources.getString(R.string.view_search_stops_empty), count)
        } else {
            textView.text = String.format(textView.resources.getString(R.string.view_search_stops_header), count)
        }
    }

    fun renderRoutesHeader(textView: TextView, count: Int) {
        if (count == 0) {
            textView.text = String.format(textView.resources.getString(R.string.view_search_routes_empty), count)
        } else {
            textView.text = String.format(textView.resources.getString(R.string.view_search_routes_header), count)
        }
    }

}
