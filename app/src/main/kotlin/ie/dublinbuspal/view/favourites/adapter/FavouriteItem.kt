package ie.dublinbuspal.view.favourites.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_favourite.view.*

class FavouriteItem(val stop: FavouriteStop, var livedata: Map<Pair<String, Destination>, List<LiveData>> = emptyMap()) : AbstractItem<FavouriteItem, FavouriteItem.ViewHolder>() {

    override fun getType() = R.id.view_favourite_stop_item

    override fun getLayoutRes() = R.layout.list_item_favourite

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<FavouriteItem>(itemView) {

        override fun bindView(item: FavouriteItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = itemView.resources.getString(R.string.formatted_stop_id, item.stop.id)
            itemView.stop_name.text = item.stop.name
            itemView.routes.text = StringUtils.join(item.stop.routes, " ${StringUtils.MIDDLE_DOT} ")
        }

        override fun unbindView(item: FavouriteItem?) {
            //TODO
        }
    }
}
