package ie.dublinbuspal.android.view.favourites.adapter

import android.support.design.chip.Chip
import android.view.LayoutInflater
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.base.util.CollectionUtils
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stop.Stop
import kotlinx.android.synthetic.main.list_item_favourite.view.*
import kotlinx.android.synthetic.main.list_item_live_data.view.*

class FavouriteItem(val stop: Stop, var livedata: List<LiveData> = emptyList()) : AbstractItem<FavouriteItem, FavouriteItem.ViewHolder>() {

    override fun getType() = R.id.view_favourite_stop_item

    override fun getLayoutRes() = R.layout.list_item_favourite

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<FavouriteItem>(itemView) {


        override fun bindView(item: FavouriteItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = itemView.resources.getString(R.string.formatted_stop_id, item.stop.id)
            itemView.stop_name.text = item.stop.name
            itemView.routes.removeAllViews()
            for (route in item.stop.routes) {
                val chip = LayoutInflater.from(itemView.context).inflate(R.layout.cat_chip_group_item_filter, itemView.routes, false) as Chip
                chip.text = route
                itemView.routes.addView(chip)
            }
//            if (!CollectionUtils.isNullOrEmpty(item.livedata)) {
//                itemView.live_data.route_id.text = item.livedata[0].routeId
//                itemView.live_data.destination.text = item.livedata[0].destination
//                itemView.live_data.expected_time.text = item.livedata[0].dueTime.minutes.toString()
//            }
        }

        override fun unbindView(item: FavouriteItem?) {
            //TODO
        }

    }

}
