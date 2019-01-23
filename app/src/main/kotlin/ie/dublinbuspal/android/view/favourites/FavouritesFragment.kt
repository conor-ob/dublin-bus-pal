package ie.dublinbuspal.android.view.favourites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.util.getApplicationComponent
import ie.dublinbuspal.android.view.home.HomeActivity
import ie.dublinbuspal.android.view.realtime.RealTimeActivity
import ie.dublinbuspal.android.view.settings.SettingsActivity
import ie.dublinbuspal.util.CollectionUtils
import kotlinx.android.synthetic.main.fragment_favourites.*
import timber.log.Timber
import java.lang.IndexOutOfBoundsException

class FavouritesFragment : MvpFragment<FavouritesView, FavouritesPresenter>(), FavouritesView {

    private lateinit var adapter: GroupAdapter<ViewHolder>

    override fun createPresenter(): FavouritesPresenter {
        return getApplicationComponent().favouritesPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupListeners()
        setupOptionsMenu()
        setupDragAndDrop()
    }

    @SuppressLint("RestrictedApi")
    private fun setupOptionsMenu() {
        val homeActivity = activity as HomeActivity?
        if (homeActivity != null && isMenuVisible) {
            homeActivity.invalidateOptionsMenu(HomeActivity.FAVOURITES)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.newIntent(context))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        getPresenter().onResume()
    }

    override fun onPause() {
        getPresenter().onPause(shouldSaveFavourites)
        super.onPause()
    }

    override fun render(viewModel: ViewModel) {
        if (viewModel.isInError) {
            Snackbar.make(root, getString(viewModel.errorMessage), Snackbar.LENGTH_LONG).show()
        } else {
            adapter.clear()
            for (favourite in viewModel.favourites) {
                val favouriteItem = FavouriteItem(favourite)
                favouriteItem.extras["id"] = favourite.id
                val expandableGroup = ExpandableGroup(favouriteItem)
                val liveData = viewModel.liveData[favourite.id]
                if (liveData != null) {
                    for (data in liveData) {
                        val liveDataItem = FavouriteLiveDataItem(data)
                        liveDataItem.extras["id"] = data.routeId
                        expandableGroup.add(liveDataItem)
                    }
                    expandableGroup.add(BottomDividerItem())
                }
                adapter.add(expandableGroup)
            }
            if (CollectionUtils.isNullOrEmpty(viewModel.favourites)) {
                no_favourites.visibility = View.VISIBLE
                add_favourites.show()
            } else {
                no_favourites.visibility = View.GONE
                add_favourites.hide()
            }
        }
    }

    private fun setupLayout() {
        toolbar.title = resources.getString(R.string.title_favourites_fragment)
        adapter = GroupAdapter()
        adapter.setOnItemClickListener { item, _ ->
            val stopId = item.extras["id"] as String
            val intent = RealTimeActivity.newIntent(context, stopId)
            startActivity(intent)
        }
        adapter.setOnItemLongClickListener { _, _ ->
            while (anyItemsAreExpanded()) {
                val count = adapter.itemCount
                for (position in 0 until count) {
                    try {
                        val item = adapter.getItem(position)
                        if (item is FavouriteItem) {
                            if (item.isExpanded()) {
                                item.collapse()
                            }
                        }
                    } catch (e: IndexOutOfBoundsException) {
                        // lol
                    }
                }
            }
            return@setOnItemLongClickListener true
        }
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager
    }

    private fun setupListeners() {
        add_favourites.setOnClickListener {
            val homeActivity = activity as HomeActivity?
            homeActivity?.goToSearch()
        }
    }

    private fun setupDragAndDrop() {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    private fun anyItemsAreExpanded(): Boolean {
        val count = adapter.itemCount
        for (position in 0 until count) {
            val item = adapter.getItem(position)
            if (item is FavouriteItem) {
                if (item.isExpanded()) {
                    return true
                }
            }
        }
        return false
    }

    private var shouldSaveFavourites = false
    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            Timber.d("onMove")
            if (anyItemsAreExpanded()) {
                return false
            }
            presenter.onReorderFavourites(viewHolder.adapterPosition, target.adapterPosition)
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            shouldSaveFavourites = true
            return true
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            presenter.onFinishedReorderFavourites()
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Timber.d("onSwiped")
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return ItemTouchHelper.Callback.makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END)
        }

    }

}
