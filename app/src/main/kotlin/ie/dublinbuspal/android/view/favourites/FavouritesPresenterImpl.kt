package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ie.dublinbuspal.android.R
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.usecase.favourites.FavouritesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouritesPresenterImpl @Inject constructor(
        private val useCase: FavouritesUseCase
) : MvpBasePresenter<FavouritesView>(), FavouritesPresenter {

    private var subscriptions: CompositeDisposable? = null
    private var viewModel = ViewModel()

    override fun onResume() {
        subscriptions().add(useCase.getFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewModel = viewModel.copy(
                            isInError = false,
                            errorMessage = -1,
                            isLoading = true,
                            favourites = emptyList()
                    )
                    renderView()
                }
                .doOnNext {
                    viewModel = viewModel.copy(
                            isInError = false,
                            errorMessage = -1,
                            isLoading = false,
                            favourites = it
                    )
                    renderView()
                }
                .doOnError {
                    viewModel = viewModel.copy(
                            isInError = true,
                            errorMessage = R.string.error_unknown,
                            isLoading = false,
                            favourites = emptyList()
                    )
                    renderView()
                }
                .subscribe()
        )
    }

    override fun onPause() {
        subscriptions().clear()
        subscriptions().dispose()
    }

    override fun onPause(favourites: List<FavouriteStop>) {
        subscriptions().add(useCase.saveFavourites(favourites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { onPause() }
                .doOnError {
                    viewModel = viewModel.copy(
                            isInError = true,
                            errorMessage = R.string.error_unknown,
                            isLoading = false,
                            favourites = emptyList()
                    )
                    renderView()
                }
                .subscribe()
        )
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
    val isLoading: Boolean = true,
    val favourites: List<FavouriteStop> = emptyList()
)
