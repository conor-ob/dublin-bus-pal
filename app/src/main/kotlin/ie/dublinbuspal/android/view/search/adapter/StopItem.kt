package ie.dublinbuspal.android.view.search.adapter

import android.support.design.chip.Chip
import android.view.LayoutInflater
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.stop.Stop
import kotlinx.android.synthetic.main.list_item_bus_stop.view.*

class StopItem(val stop: Stop) : AbstractItem<StopItem, StopItem.ViewHolder>() {

    override fun getType() = R.id.view_search_stop_item

    override fun getLayoutRes() = R.layout.list_item_bus_stop

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<StopItem>(itemView) {

        override fun bindView(item: StopItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = itemView.resources.getString(R.string.formatted_stop_id, item.stop.id)
            itemView.stop_name.text = item.stop.name
            itemView.routes.removeAllViews()
            for (route in item.stop.routes) {
                val chip = LayoutInflater.from(itemView.context).inflate(R.layout.cat_chip_group_item_filter, itemView.routes, false) as Chip
                chip.text = route
                itemView.routes.addView(chip)
            }
        }

        override fun unbindView(item: StopItem?) {
            //TODO
        }

    }

}
