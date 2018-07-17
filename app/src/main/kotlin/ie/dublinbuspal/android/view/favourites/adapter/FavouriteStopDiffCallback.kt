package ie.dublinbuspal.android.view.favourites.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class FavouriteStopDiffCallback : DiffCallback<FavouriteStopItem> {

    override fun areItemsTheSame(oldItem: FavouriteStopItem, newItem: FavouriteStopItem): Boolean {
        return oldItem.stop.id == newItem.stop.id
    }

    override fun areContentsTheSame(oldItem: FavouriteStopItem, newItem: FavouriteStopItem): Boolean {
        return oldItem.stop == newItem.stop
    }

    override fun getChangePayload(oldItem: FavouriteStopItem, oldItemPosition: Int, newItem: FavouriteStopItem, newItemPosition: Int): Any? {
        return null
    }

}
