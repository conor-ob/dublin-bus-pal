package ie.dublinbuspal.android.view.livedata.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.domain.model.livedata.LiveData
import kotlinx.android.synthetic.main.list_item_live_data.view.*

class LiveDataItem(val livedata: LiveData) : AbstractItem<LiveDataItem, LiveDataItem.ViewHolder>() {

    override fun getType() = R.id.view_livedata_item

    override fun getLayoutRes() = R.layout.list_item_live_data

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<LiveDataItem>(itemView) {

        override fun bindView(item: LiveDataItem, payloads: MutableList<Any>?) {

        }

        override fun unbindView(item: LiveDataItem?) {
            //TODO
        }

    }

}
