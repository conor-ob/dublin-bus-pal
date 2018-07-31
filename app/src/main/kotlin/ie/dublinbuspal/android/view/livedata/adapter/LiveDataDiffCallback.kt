package ie.dublinbuspal.android.view.livedata.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class LiveDataDiffCallback : DiffCallback<LiveDataItem> {

    override fun areItemsTheSame(oldItem: LiveDataItem, newItem: LiveDataItem): Boolean {
        return oldItem.livedata.routeId == newItem.livedata.routeId
    }

    override fun areContentsTheSame(oldItem: LiveDataItem, newItem: LiveDataItem): Boolean {
        return oldItem.livedata == newItem.livedata
    }

    override fun getChangePayload(oldItem: LiveDataItem?, oldItemPosition: Int, newItem: LiveDataItem?, newItemPosition: Int): Any? {
        return null
    }

}
