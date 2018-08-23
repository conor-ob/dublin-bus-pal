package ie.dublinbuspal.android.view.search.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.route.Route
import kotlinx.android.synthetic.main.list_item_route.view.*

class RouteItem(val route: Route) : AbstractItem<RouteItem, RouteItem.ViewHolder>() {

    override fun getType() = R.id.view_search_route_item

    override fun getLayoutRes() = R.layout.list_item_route

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<RouteItem>(itemView) {

        override fun bindView(item: RouteItem, payloads: MutableList<Any>?) {
            itemView.route_id.text = item.route.id
            itemView.route_description.text = itemView.resources.getString(R.string.formatted_route_title, item.route.origin, item.route.destination)
        }

        override fun unbindView(item: RouteItem?) {
            //TODO
        }

    }

}
