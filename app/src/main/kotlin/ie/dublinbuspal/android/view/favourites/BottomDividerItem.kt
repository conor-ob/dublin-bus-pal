package ie.dublinbuspal.android.view.favourites

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ie.dublinbuspal.android.R

class BottomDividerItem : Item() {

    override fun getLayout() = R.layout.list_item_bottom_divider

    override fun bind(viewHolder: ViewHolder, position: Int) { }

}
