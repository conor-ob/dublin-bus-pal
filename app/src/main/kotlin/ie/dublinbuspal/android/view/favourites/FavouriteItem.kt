package ie.dublinbuspal.android.view.favourites

import android.view.View
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_bus_stop.*
import java.util.*

class FavouriteItem(private val favouriteStop: FavouriteStop) : Item() {

    override fun getLayout() = R.layout.list_item_bus_stop

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val formattedStopId = viewHolder.itemView.resources.getString(R.string.formatted_stop_id)
        viewHolder.stop_id.text = String.format(Locale.UK, formattedStopId, favouriteStop.id)
        viewHolder.stop_name.text = favouriteStop.name
        if (CollectionUtils.isNullOrEmpty(favouriteStop.routes)) {
            viewHolder.routes.text = StringUtils.EMPTY_STRING
            viewHolder.routes.visibility = View.GONE
        } else {
            val middleDot = String.format(Locale.UK, " %s ", StringUtils.MIDDLE_DOT)
            viewHolder.routes.text = StringUtils.join(favouriteStop.routes, middleDot)
            viewHolder.routes.visibility = View.VISIBLE
        }
    }

}
