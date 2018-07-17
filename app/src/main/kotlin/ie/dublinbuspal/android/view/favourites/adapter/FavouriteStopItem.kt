package ie.dublinbuspal.android.view.favourites.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import kotlinx.android.synthetic.main.list_item_bus_stop.view.*

class FavouriteStopItem(val stop: FavouriteStop) : AbstractItem<FavouriteStopItem, FavouriteStopItem.ViewHolder>() {

    override fun getType() = R.id.view_search_stop_item

    override fun getLayoutRes() = R.layout.list_item_bus_stop

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<FavouriteStopItem>(itemView) {

        override fun bindView(item: FavouriteStopItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = item.stop.id
            itemView.stop_name.text = item.stop.name
        }

        override fun unbindView(item: FavouriteStopItem?) {
            //TODO
        }

    }

}
