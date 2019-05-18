package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import ie.dublinbuspal.model.stop.Stop;
import ie.dublinbuspal.usecase.nearby.NearbyStopsUseCase;
import ie.dublinbuspal.util.Coordinate;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NearbyPresenterImpl extends MvpBasePresenter<NearbyView> implements NearbyPresenter {

    private final NearbyStopsUseCase useCase;
    private CompositeDisposable disposables;
    private CompositeDisposable locationDisposables;

    @Inject
    public NearbyPresenterImpl(NearbyStopsUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void refreshNearby(Location location) {
        getDisposables().add(useCase.getNearbyBusStops(new Coordinate(location.getLatitude(), location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetBusStops, this::onGetBusStopsError));
    }

    @Override
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private void onGetBusStops(List<Stop> busStops) {
        ifViewAttached(view -> {
            view.hideProgress();
            view.showButton();
            view.showNearbyStops(busStops);
        });
    }

    private void onGetBusStopsError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(NearbyView::hideProgress);
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

    public CompositeDisposable getLocationDisposables() {
        if (locationDisposables == null || locationDisposables.isDisposed()) {
            locationDisposables = new CompositeDisposable();
        }
        return locationDisposables;
    }

    @Override
    public void onLocationRequested() {
        getLocationDisposables().add(useCase.getLocationUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLocationReceived, this::onLocationError));
    }

    @Override
    public void onRemoveLocationUpdates() {
        getLocationDisposables().clear();
    }

    private void onLocationReceived(Coordinate coordinate) {
        ifViewAttached(view -> view.updateLocation(coordinate));
    }

    private void onLocationError(Throwable throwable) {

    }

}
