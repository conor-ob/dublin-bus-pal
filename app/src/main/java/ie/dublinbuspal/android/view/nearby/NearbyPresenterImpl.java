package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.SortedMap;

import javax.inject.Inject;

import ie.dublinbuspal.util.ErrorLog;
import ie.dublinbuspal.model.stop.Stop;
import ie.dublinbuspal.usecase.nearby.NearbyStopsUseCase;
import ie.dublinbuspal.util.Coordinate;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NearbyPresenterImpl extends MvpBasePresenter<NearbyView> implements NearbyPresenter {

    private final NearbyStopsUseCase useCase;
    private CompositeDisposable disposables;

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

    private void onGetBusStops(SortedMap<Double, Stop> busStops) {
        ifViewAttached(view -> {
            view.hideProgress();
            view.showButton();
            view.showNearbyStops(busStops);
        });
    }

    private void onGetBusStopsError(Throwable throwable) {
        ErrorLog.e(throwable);
        ifViewAttached(NearbyView::hideProgress);
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

}
