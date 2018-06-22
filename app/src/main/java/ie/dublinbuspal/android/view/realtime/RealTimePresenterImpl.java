package ie.dublinbuspal.android.view.realtime;

import android.view.Menu;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.remote.soap.SoapServiceUnavailableException;
import ie.dublinbuspal.android.util.ErrorLog;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RealTimePresenterImpl extends MvpBasePresenter<RealTimeView>
        implements RealTimePresenter {

    private RealTimeModel model;
    private final DublinBusRepository repository;
    private CompositeDisposable disposables;

    public RealTimePresenterImpl(DublinBusRepository repository, RealTimeModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void onResume(String stopId) {
        getModel().setStopId(stopId);
        getBusStop();
    }

    private void onForceResume(String stopId) {
        getModel().setStopId(stopId);
        getModel().setBusStop(null);
        getModel().setAdjustedBusStopService(null);
        getModel().setBusStopService(null);
        getBusStop();
    }

    @Override
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private void getBusStop() {
        if (getModel().getBusStop() == null) {
            getDisposables().add(Single.fromCallable(() ->
                    getRepository().getDetailedBusStop(getModel().getStopId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetBusStop, this::onError));
        } else {
            onGetBusStop(getModel().getBusStop());
        }
    }

    private void getBusStopService() {
        if (getModel().getBusStopService() == null) {
            getDisposables().add(Single.fromCallable(() ->
                    getRepository().getBusStopService(getModel().getStopId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetBusStopService, this::onError));
        } else {
            getRealTimeData();
        }
    }

    private void getRealTimeData() {
        getDisposables().add(Single.fromCallable(() ->
                getRepository().getRealTimeData(getModel().getStopId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRealTimeData, this::onError));
    }

    private void onGetBusStop(DetailedBusStop busStop) {
        getModel().setBusStop(busStop);
        if (busStop.isFavourite()) {
            getModel().setAdjustedBusStopService(new BusStopService(busStop.getId(),
                    busStop.getRoutes()));
        }
        showBusStop();
        getBusStopService();
    }

    private void onGetBusStopService(BusStopService service) {
        getModel().setBusStopService(service);
        showBusStopService();
        getRealTimeData();
    }

    private void onGetRealTimeData(List<RealTimeData> realTimeData) {
        getModel().setRealTimeData(realTimeData);
        showRealTimeData();
    }

    private void showBusStop() {
        ifViewAttached(view -> view.showBusStop(getModel().getBusStop()));
    }

    private void showBusStopService() {
        ifViewAttached(view -> view.showBusStopService(getModel().getAdjustedBusStopService()));
    }

    private void showRealTimeData() {
        ifViewAttached(view -> {
            view.hideProgress();
            view.showRealTimeData(getModel().getRealTimeData());
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
    public void saveFavourite(String customName, List<String> customRoutes) {
        getDisposables().add(Observable.fromCallable(() ->
                getRepository().saveBusStopAsFavourite(getModel().getStopId(), customName,
                        customRoutes))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSaveFavourite));
    }

    @Override
    public void removeFavourite() {
        getDisposables().add(Observable.fromCallable(() ->
                getRepository().removeFavourite(getModel().getBusStop().getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRemovedFavourite));
    }

    private void onSaveFavourite(Boolean saved) {
        onForceResume(getModel().getStopId());
        //TODO handle error
        ifViewAttached(RealTimeView::onFavouriteSaved);
    }

    private void onRemovedFavourite(Boolean removed) {
        onForceResume(getModel().getStopId());
        ifViewAttached(RealTimeView::onFavouriteRemoved);
    }

    @Override
    public void routeFilterPressed(String route) {
        model.addOrRemoveRouteFilter(route);
        ifViewAttached(view -> {
            view.showProgress();
            view.applyRouteFiltering(model.getRouteFilters());
        });
        getRealTimeData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {
        if (model.getBusStop() != null) {
            ifViewAttached(view -> {
                if (model.getBusStop().isFavourite()) {
                    view.onCreateFavouriteOptionsMenu(menu);
                } else {
                    view.onCreateDefaultOptionsMenu(menu);
                }
            });
        }
    }

    @Override
    public void onAddFavouritePressed() {
        ifViewAttached(view -> view.presentSaveFavouriteDialog(model.getBusStop(), model.getBusStopService()));
    }

    @Override
    public void onEditFavouritePressed() {

    }

    private RealTimeModel getModel() {
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
