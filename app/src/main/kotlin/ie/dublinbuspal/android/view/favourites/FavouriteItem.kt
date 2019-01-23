package ie.dublinbuspal.android.view.favourites

import android.graphics.drawable.Animatable
import android.view.View
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.StringUtils
import kotlinx.android.synthetic.main.list_item_bus_stop.*
import java.util.*

class FavouriteItem(private val favouriteStop: FavouriteStop) : Item(), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    override fun getLayout() = R.layout.list_item_bus_stop

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val formattedStopId = viewHolder.itemView.resources.getString(R.string.formatted_stop_id)
        viewHolder.stop_id.text = String.format(Locale.UK, formattedStopId, favouriteStop.id)
        viewHolder.stop_name.text = favouriteStop.name
        if (CollectionUtils.isNullOrEmpty(favouriteStop.routes)) {
            viewHolder.routes.text = StringUtils.EMPTY_STRING
            viewHolder.routes.visibility = View.GONE
        } else {
            val middleDot = String.format(Locale.UK, " %s ", StringUtils.MIDDLE_DOT)
            viewHolder.routes.text = StringUtils.join(favouriteStop.routes, middleDot)
            viewHolder.routes.visibility = View.VISIBLE
        }
        if (expandableGroup == null) {
            viewHolder.expand.apply {
                visibility = View.GONE
            }
        } else {
            viewHolder.expand.apply {
                visibility = View.VISIBLE
                setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse else R.drawable.expand)
                setOnClickListener {
                    expandableGroup?.onToggleExpanded()
                    bindIcon(viewHolder)
                }
            }
        }
    }

    private fun bindIcon(viewHolder: ViewHolder) {
        viewHolder.expand.apply {
            visibility = View.VISIBLE
            setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse_animated  else R.drawable.expand_animated)
            (drawable as Animatable).start()
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    fun collapse() {
        expandableGroup?.apply {
            if (isExpanded) {
                onToggleExpanded()
            }
        }
    }

    fun isExpanded(): Boolean {
        if (expandableGroup == null) {
            return false
        }
        if (expandableGroup!!.isExpanded) {
            return true
        }
        return false
    }

}
