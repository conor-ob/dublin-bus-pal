package ie.dublinbuspal.view.search.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class StopDiffCallback : DiffCallback<StopItem> {

    override fun areItemsTheSame(oldItem: StopItem, newItem: StopItem): Boolean {
        return oldItem.stop.id() == newItem.stop.id()
    }

    override fun areContentsTheSame(oldItem: StopItem, newItem: StopItem): Boolean {
        return oldItem.stop == newItem.stop
    }

    override fun getChangePayload(oldItem: StopItem, oldItemPosition: Int, newItem: StopItem, newItemPosition: Int): Any? {
        return null
    }

}
