package ie.dublinbuspal.android.view.routeservice

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.route.RouteServicePresenter
import ie.dublinbuspal.android.view.route.RouteServiceView
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.usecase.routeservice.RouteServiceUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RouteServicePresenterImpl @Inject constructor(
        private val useCase: RouteServiceUseCase
) : MvpBasePresenter<RouteServiceView>(), RouteServicePresenter {

    private lateinit var viewModel: RouteServiceViewModel
    private var subscriptions: CompositeDisposable? = null

    override fun onResume(routeId: String, operatorId: String, stopId: String?) {
        viewModel = RouteServiceViewModel(routeId = routeId, operatorId = operatorId, stopId = stopId)
        subscriptions().add(
                useCase.getRouteService(viewModel.routeId!!, viewModel.operatorId!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            viewModel = viewModel.copy(isLoading = false, routeService = it, selectedVariant = 0)
                            renderView()
                        }
                        .doOnError {
                            Timber.e(it)
                            val errorResource = when (it) {
                                is UnknownHostException -> R.string.error_no_internet
                                is SocketException -> R.string.error_interrupted
                                is SocketTimeoutException -> R.string.error_timeout
                                else -> R.string.error_unknown
                            }
                            viewModel = viewModel.copy(isLoading = false, isError = true, errorResource = errorResource)
                            renderView()
                        }
                        .subscribe()
        )
    }

    private fun renderView() {
        ifViewAttached { view -> view.render(viewModel) }
    }

    override fun onMapReady() {
        viewModel = viewModel.copy(isMapReady = true)
        renderView()
    }

    override fun onNextVariantPressed() {
        val selectedVariant = viewModel.selectedVariant
        val maxVariants = viewModel.routeService!!.variants.size
        viewModel = if (selectedVariant < maxVariants - 1) {
            viewModel.copy(selectedVariant = selectedVariant + 1)
        } else {
            viewModel.copy(selectedVariant = 0)
        }
        renderView()
    }

    override fun onDestroy() {
        subscriptions?.clear()
        subscriptions?.dispose()
        subscriptions = null
    }

    private fun subscriptions(): CompositeDisposable {
        if (subscriptions == null || subscriptions!!.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions!!
    }

}

data class RouteServiceViewModel(
        val routeId: String? = null,
        val operatorId: String? = null,
        val stopId: String? = null,
        val isLoading: Boolean = true,
        val isMapReady: Boolean = false,
        val routeService: RouteService? = null,
        val selectedVariant: Int = -1,
        val isError: Boolean = false,
        val errorResource: Int? = null
) {

    fun selectedOrigin() = routeService!!.variants[selectedVariant].origin

    fun selectedDestination() = routeService!!.variants[selectedVariant].destination

    fun selectedStops() = routeService!!.variants[selectedVariant].stops

}
