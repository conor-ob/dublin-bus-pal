package ie.dublinbuspal.view.search.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class RouteDiffCallback : DiffCallback<RouteItem> {

    override fun areItemsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean {
        return oldItem.route.id == newItem.route.id
    }

    override fun areContentsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean {
        return oldItem.route == newItem.route
    }

    override fun getChangePayload(oldItem: RouteItem, oldItemPosition: Int, newItem: RouteItem, newItemPosition: Int): Any? {
        return null
    }

}
