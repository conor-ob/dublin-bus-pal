package ie.dublinbuspal.view.search.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_bus_stop.view.*

class StopItem(val stop: ResolvedStop) : AbstractItem<StopItem, StopItem.ViewHolder>() {

    override fun getType() = R.id.view_search_stop_item

    override fun getLayoutRes() = R.layout.list_item_bus_stop

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<StopItem>(itemView) {

        override fun bindView(item: StopItem, payloads: MutableList<Any>?) {
            itemView.stop_id.text = itemView.resources.getString(R.string.formatted_stop_id, item.stop.id())
            itemView.stop_name.text = item.stop.name()
            if (item.stop.isBacOnly()) {
                itemView.gad_routes.visibility = View.GONE
                itemView.bac_routes.visibility = View.VISIBLE
                itemView.bac_routes.text = StringUtils.join(item.stop.routes(), " ${StringUtils.MIDDLE_DOT} ")
            } else if (item.stop.isGadOnly()) {
                itemView.bac_routes.visibility = View.GONE
                itemView.gad_routes.visibility = View.VISIBLE
                itemView.gad_routes.text = StringUtils.join(item.stop.routes(), " ${StringUtils.MIDDLE_DOT} ")
            } else if (item.stop.isBacAndGad()) {
                itemView.bac_routes.visibility = View.VISIBLE
                itemView.gad_routes.visibility = View.VISIBLE
                itemView.bac_routes.text = StringUtils.join(item.stop.routes(), " ${StringUtils.MIDDLE_DOT} ")
                itemView.gad_routes.text = StringUtils.join(item.stop.routes(), " ${StringUtils.MIDDLE_DOT} ")
            }
        }

        override fun unbindView(item: StopItem?) {
            //TODO
        }

    }

}
