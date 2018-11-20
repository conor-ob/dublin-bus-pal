package ie.dublinbuspal.view.livedata.adapter

import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_live_data.view.*

class LiveDataItem(val livedata: LiveData) : AbstractItem<LiveDataItem, LiveDataItem.ViewHolder>() {

    override fun getType() = R.id.view_livedata_item

    override fun getLayoutRes() = R.layout.list_item_live_data

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<LiveDataItem>(itemView) {

        override fun bindView(item: LiveDataItem, payloads: MutableList<Any>?) {
            itemView.route_id.text = item.livedata.routeId
            itemView.destination.text = item.livedata.destination.destination
            if (!StringUtils.isNullOrEmpty(item.livedata.destination.via)) {
                itemView.via.text = item.livedata.destination.via
                itemView.via.visibility = View.VISIBLE
            }
            itemView.expected_time.text = item.livedata.dueTime.minutes()
            itemView.animation_view.setBackgroundResource(R.drawable.anim_rtpi)
            val frameAnimation = itemView.animation_view.background as AnimationDrawable
            frameAnimation.start()
        }

        override fun unbindView(item: LiveDataItem?) {
            //TODO
        }
    }
}
