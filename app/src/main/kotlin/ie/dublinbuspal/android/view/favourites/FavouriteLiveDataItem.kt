package ie.dublinbuspal.android.view.favourites

import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_realtime.*

class FavouriteLiveDataItem(private val liveData: LiveData) : Item() {

    override fun getLayout() = R.layout.list_item_realtime

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.route_id.text = liveData.routeId
        viewHolder.destination.text = liveData.destination.destination
//        if (!StringUtils.isNullOrEmpty(liveData.destination.via)) {
//            viewHolder.via.text = liveData.destination.via
//            viewHolder.via.visibility = View.VISIBLE
//        } else {
            viewHolder.via.text = StringUtils.EMPTY_STRING
            viewHolder.via.visibility = View.GONE
//        }
//        if (showArrivalTime) {
//            expectedTime.setText(realTimeData.dueTime.time)
//        } else {
        viewHolder.expected_time.text = liveData.dueTime.minutes()
//        }
        viewHolder.animation_view.setBackgroundResource(R.drawable.anim_rtpi)
        val frameAnimation = viewHolder.animation_view.background as AnimationDrawable
        frameAnimation.start()
    }

}
