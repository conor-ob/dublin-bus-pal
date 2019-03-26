package ie.dublinbuspal.android.view.livedata

import android.view.Menu
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.usecase.favourites.FavouritesUseCase
import ie.dublinbuspal.usecase.livedata.LiveDataUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class LiveDataPresenterImpl @Inject constructor(
        private val liveDataUseCase: LiveDataUseCase,
        private val favouritesUseCase: FavouritesUseCase
) : MvpBasePresenter<LiveDataView>(), LiveDataPresenter {

    private var subscriptions: CompositeDisposable? = null
    private var viewModel = ViewModel()

    override fun onResume(stopId: String) {
        subscriptions().add(liveDataUseCase.getBusStop(stopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { renderError(it) }
                .doOnNext {
                    viewModel = viewModel.copy(
                            stop = it,
                            routeFilter = it.routes().toSet()
                    )
                    renderBusStop()
                    getLiveData()
                }
                .subscribe()
        )
    }

    private fun getLiveData() {
        subscriptions().add(liveDataUseCase.getLiveDataStream(viewModel.stop!!.id(), viewModel.routeFilter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { renderError(it) }
                .doOnNext {
                    viewModel = viewModel.copy(
                        liveData = it,
                        isLoading = false
                    )
                    renderLiveData()
                }
                .subscribe()
        )
    }

    override fun onRouteFilterPressed(route: String) {
        val newRouteFilter = viewModel.routeFilter.toMutableSet()
        newRouteFilter.add(route)
        viewModel = viewModel.copy(routeFilter = newRouteFilter)
        renderRouteFilters()
        renderLiveData()
    }

    override fun onSaveFavourite(name: String, routes: MutableList<String>) {
        subscriptions().add(Observable.fromCallable { favouritesUseCase.saveFavourite(viewModel.stop!!.id(), name, routes) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { renderSaveFavouriteSuccess() }
                .doOnError { renderError(it) }
                .subscribe()
        )
    }

    override fun onRemoveFavouritePressed() {
        subscriptions().add(Observable.fromCallable { favouritesUseCase.removeFavourite(viewModel.stop!!.id()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { renderRemoveFavouriteSuccess() }
                .doOnError { renderError(it) }
                .subscribe()
        )
    }

    private fun renderSaveFavouriteSuccess() {
        onResume(viewModel.stop!!.id())
        ifViewAttached { view -> view.renderSnackbar(R.string.saved_favorite_message) }
    }

    private fun renderRemoveFavouriteSuccess() {
        onResume(viewModel.stop!!.id())
        ifViewAttached { view -> view.renderSnackbar(R.string.removed_favorite_message) }
    }

    private fun renderError(throwable: Throwable) {
        Timber.e(throwable)
        ifViewAttached { view ->
            when (throwable) {
                is UnknownHostException -> view.renderError(R.string.error_no_internet)
                is SocketException -> view.renderError(R.string.error_interrupted)
                is SocketTimeoutException -> view.renderError(R.string.error_timeout)
                else -> view.renderError(R.string.error_unknown)
            }
        }
    }

    override fun onAddFavouritePressed() {
        ifViewAttached { view -> view.renderAddFavouritesDialog(viewModel) }
    }

    override fun onPause() {
        subscriptions().clear()
        subscriptions().dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu) {
        renderOptionsMenu(menu)
    }

    private fun renderBusStop() {
        ifViewAttached { view -> view.renderBusStop(viewModel) }
    }

    private fun renderOptionsMenu(menu: Menu) {
        ifViewAttached { view -> view.renderOptionsMenu(viewModel, menu) }
    }

    private fun renderRouteFilters() {
        ifViewAttached { view -> view.renderRouteFilters(viewModel) }
    }

    private fun renderLiveData() {
        ifViewAttached { view -> view.renderLiveData(viewModel) }
    }

    private fun subscriptions(): CompositeDisposable {
        if (subscriptions == null || subscriptions!!.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions!!
    }

}

data class ViewModel(
        val stop: Stop? = null,
        val liveData: List<LiveData> = emptyList(),
        val routeFilter: Set<String> = emptySet(),
        val isLoading: Boolean = true
)
