package ie.dublinbuspal.view.search

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.service.Operator
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_bus_stop.view.*
import kotlinx.android.synthetic.main.list_item_route.view.*
import kotlinx.android.synthetic.main.list_item_search_title.view.*
import java.util.*

class SearchAdapter(
        private val view: SearchView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TITLE = 0
    private val ROUTE = 1
    private val BUS_STOP = 2

    private var searchResults: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TITLE -> {
                val titleView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_search_title, parent, false)
                return TitleViewHolder(titleView)
            }
            ROUTE -> {
                val routeView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_route, parent, false)
                val routeViewHolder = RouteViewHolder(routeView)
                routeView.setOnClickListener(routeViewHolder)
                return routeViewHolder
            }
            BUS_STOP -> {
                val busStopView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_bus_stop, parent, false)
                val busStopViewHolder = BusStopViewHolder(busStopView)
                busStopView.setOnClickListener(busStopViewHolder)
                return busStopViewHolder
            }
            else -> throw IllegalStateException(String.format(Locale.UK,
                    "Incorrect view type: %s", viewType.toString()))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResult = searchResults[position]
        if (searchResult is String && holder is TitleViewHolder) {
            holder.bind(searchResult)
        } else if (searchResult is Route && holder is RouteViewHolder) {
            holder.bind(searchResult)
        } else if (searchResult is ResolvedStop && holder is BusStopViewHolder) {
            holder.bind(searchResult)
        }
    }

    override fun getItemCount() =  searchResults.size

    override fun getItemViewType(position: Int): Int {
        val searchResult = searchResults[position]
        return when (searchResult) {
            is String -> TITLE
            is Route -> ROUTE
            is ResolvedStop -> BUS_STOP
            else -> super.getItemViewType(position)
        }
    }

    fun setSearchResult(searchResults: List<Any>) {
        this.searchResults = searchResults
        notifyDataSetChanged()
    }

    inner class TitleViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.title

        fun bind(text: String) {
            title.text = text
        }

    }

    inner class RouteViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(route: Route) {
            itemView.route_id.text = route.id
            if (route.operator == Operator.GO_AHEAD_DUBLIN) {
                itemView.route_id.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                itemView.route_id.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            } else {
                itemView.route_id.setTextColor(ContextCompat.getColor(itemView.context, R.color.textColorPrimary))
                itemView.route_id.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.colorAccent))
            }
            itemView.route_description.text = String.format(Locale.UK, "%s - %s",
                    route.variants[0].origin, route.variants[0].destination) //TODO
        }

        override fun onClick(itemView: View) {
            val searchResult = searchResults[adapterPosition]
            if (searchResult is Route) {
                val route = searchResult
                //view.launchRouteActivity(route.id)
            }
        }

    }

    inner class BusStopViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(busStop: ResolvedStop) {
            itemView.stop_id.text = String.format(Locale.UK, itemView.context.getString(R.string.formatted_stop_id), busStop.id())
            itemView.stop_name.text = busStop.name()
            if (CollectionUtils.isNullOrEmpty(busStop.routes())) {
                itemView.routes.visibility = View.GONE
            } else {
                itemView.routes.text = StringUtils.join(busStop.routes(), " ${StringUtils.MIDDLE_DOT} ")
            }
        }

        override fun onClick(itemView: View) {
            val searchResult = searchResults[adapterPosition]
            if (searchResult is ResolvedStop) {
                view.onBusStopClicked(searchResult.id(), searchResult.name())
            }
        }

    }

}
