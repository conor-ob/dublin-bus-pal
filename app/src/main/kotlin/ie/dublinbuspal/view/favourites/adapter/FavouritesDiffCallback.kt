package ie.dublinbuspal.view.favourites.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class FavouritesDiffCallback : DiffCallback<FavouriteItem> {

    override fun areItemsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem.stop.id == newItem.stop.id
    }

    override fun areContentsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem.stop == newItem.stop && oldItem.livedata == newItem.livedata
    }

    override fun getChangePayload(oldItem: FavouriteItem, oldItemPosition: Int, newItem: FavouriteItem, newItemPosition: Int): Any? {
        return null
    }

}
