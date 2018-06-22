package ie.dublinbuspal.android.view.route;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.local.entity.DetailedRouteService;
import ie.dublinbuspal.android.data.remote.soap.SoapServiceUnavailableException;
import ie.dublinbuspal.android.util.ErrorLog;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RoutePresenterImpl extends MvpBasePresenter<RouteView> implements RoutePresenter {

    private final RouteModel model;
    private final DublinBusRepository repository;
    private CompositeDisposable disposables;

    public RoutePresenterImpl(DublinBusRepository repository, RouteModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void onResume(String routeId, String stopId) {
        getModel().setRouteId(routeId);
        getModel().setStopId(stopId);
        getDisposables().add(Single.fromCallable(() ->
                getRepository().getDetailedRouteService(getModel().getRouteId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRouteService, this::onError));
    }

    private void onGetRouteService(DetailedRouteService routeService) {
        getModel().setRouteService(routeService);
        showRouteService();
    }

    private void showRouteService() {
        ifViewAttached(view -> {
            view.hideProgress();
            view.setTitle(getModel().getOrigin(), getModel().getDestination());
            view.setTowards(getModel().getTowards());
            view.displayBusStops(getModel().getDetailedBusStops());
            view.setBiDirectional(getModel().isBiDirectional());
        });
    }

    private void onError(Throwable throwable) {
        ErrorLog.e(throwable);
        ifViewAttached(view -> {
            view.hideProgress();
            if (throwable instanceof SoapServiceUnavailableException) {
                view.showError(R.string.error_no_service);
            } else if (throwable instanceof UnknownHostException) {
                view.showError(R.string.error_no_internet);
            } else if (throwable instanceof SocketException) {
                view.showError(R.string.error_interrupted);
            } else if (throwable instanceof SocketTimeoutException) {
                view.showError(R.string.error_timeout);
            } else {
                view.showError(R.string.error_unknown);
            }
        });
    }

    @Override
    public void onDestroy() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    @Override
    public void changeDirectionPressed() {
        getModel().changeDirection();
        showRouteService();
    }

    private RouteModel getModel() {
        return model;
    }

    private DublinBusRepository getRepository() {
        return repository;
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

}
