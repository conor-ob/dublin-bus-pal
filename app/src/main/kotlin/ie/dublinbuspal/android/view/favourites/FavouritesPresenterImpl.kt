package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.usecase.favourites.FavouritesUseCase
import ie.dublinbuspal.usecase.livedata.LiveDataUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class FavouritesPresenterImpl @Inject constructor(
        private val favouritesUseCase: FavouritesUseCase,
        private val liveDataUseCase: LiveDataUseCase
) : MvpBasePresenter<FavouritesView>(), FavouritesPresenter {

    private var subscriptions: CompositeDisposable? = null
    private var viewModel = ViewModel()

    override fun onResume() {
        subscriptions().add(favouritesUseCase.getFavourites()
                .subscribeOn(Schedulers.io())
                .map {
                    viewModel.copy(
                            isInError = false,
                            errorMessage = -1,
                            favourites = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    viewModel = it
                    renderView()
                    getLiveData(it.favourites)
                }
                .doOnError {
                    viewModel = viewModel.copy(
                            isInError = true,
                            errorMessage = R.string.error_unknown,
                            favourites = emptyList()
                    )
                    renderView()
                }
                .subscribe()
        )
    }

    private fun getLiveData(favourites: List<FavouriteStop>) {
        for (favourite in favourites) {
            subscriptions().add(liveDataUseCase.getLiveDataStream(favourite.id, favourite.routes)
                    .map {
                        val newLiveData = viewModel.liveData
                        newLiveData[favourite.id] = it.take(3)
                        return@map viewModel.copy(
                                liveData = newLiveData
                        )
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        viewModel = it
                        renderView()
                    }
                    .subscribe()
            )
        }
    }

    override fun onPause(shouldSaveFavourites: Boolean) {
        if (shouldSaveFavourites) {
            subscriptions().add(favouritesUseCase.saveFavourites(viewModel.favourites)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { onPause() }
                    .doOnError {
                        viewModel = viewModel.copy(
                                isInError = true,
                                errorMessage = R.string.error_unknown,
                                favourites = emptyList()
                        )
                        renderView()
                    }
                    .subscribe()
            )
        } else {
            onPause()
        }
    }

    private fun onPause() {
        subscriptions().clear()
        subscriptions().dispose()
    }

    override fun onReorderFavourites(position1: Int, position2: Int) {
        val copy = viewModel.favourites
        Collections.swap(copy, position1, position2)
        viewModel = viewModel.copy(favourites = copy)
    }

    override fun onFinishedReorderFavourites() {
        renderView()
    }

    private fun renderView() {
        ifViewAttached { view -> view.render(viewModel) }
    }

    private fun subscriptions(): CompositeDisposable {
        if (subscriptions == null || subscriptions!!.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions!!
    }

}

data class ViewModel(
        val isInError: Boolean = false,
        val errorMessage: Int = -1,
        val favourites: List<FavouriteStop> = emptyList(),
        val liveData: MutableMap<String, List<LiveData>> = mutableMapOf()
)
