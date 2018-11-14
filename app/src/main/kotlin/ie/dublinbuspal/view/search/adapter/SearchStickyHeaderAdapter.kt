package ie.dublinbuspal.view.search.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import ie.dublinbuspal.android.R
import kotlinx.android.synthetic.main.list_item_search_title.view.*

class SearchStickyHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private lateinit var fastAdapter: FastAdapter<*>

    override fun getHeaderId(position: Int): Long {
        val item = getItem(position)
        if (item is StopItem) {
            return R.id.view_search_stop_header.toLong()
        } else if (item is RouteItem) {
            return R.id.view_search_route_header.toLong()
        }
        return -1L
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val item = getItem(position)
        if (item is StopItem) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_title, parent, false)
            object : RecyclerView.ViewHolder(view) {}
        } else if (item is RouteItem) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_title, parent, false)
            object : RecyclerView.ViewHolder(view) {}
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_title, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is StopItem) {
            val count = fastAdapter.getAdapter(position)?.adapterItemCount
            SearchHeaderRenderer.renderStopsHeader(holder.itemView.title, count ?: 0)
        } else if (item is RouteItem) {
            val count = fastAdapter.getAdapter(position)?.adapterItemCount
            SearchHeaderRenderer.renderRoutesHeader(holder.itemView.title, count ?: 0)
        }
    }

    fun wrap(fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>): SearchStickyHeaderAdapter {
        this.fastAdapter = fastAdapter
        return this
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        fastAdapter.registerAdapterDataObserver(observer)
    }

    override fun getItemViewType(position: Int) = fastAdapter.getItem(position).type

    override fun getItemId(position: Int) = fastAdapter.getItemId(position)

    override fun getItemCount() = fastAdapter.itemCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return fastAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        fastAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        fastAdapter.onBindViewHolder(holder, position, emptyList())
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        fastAdapter.setHasStableIds(hasStableIds)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return fastAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewAttachedToWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    private fun getItem(position: Int) = fastAdapter.getItem(position)

}
