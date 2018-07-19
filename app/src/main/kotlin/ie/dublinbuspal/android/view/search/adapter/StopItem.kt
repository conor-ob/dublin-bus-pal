package ie.dublinbuspal.android.view.search.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.domain.model.stop.Stop
import kotlinx.android.synthetic.main.list_item_bus_stop.view.*

class StopItem(val stop: Stop) : AbstractItem<StopItem, StopItem.ViewHolder>() {

    override fun getType() = R.id.view_search_stop_item

    override fun getLayoutRes() = R.layout.list_item_bus_stop

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<StopItem>(itemView) {

        override fun bindView(item: StopItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = item.stop.id
            itemView.stop_name.text = item.stop.name
            itemView.routes.text = item.stop.routes.toString()
        }

        override fun unbindView(item: StopItem?) {
            //TODO
        }

    }

}
