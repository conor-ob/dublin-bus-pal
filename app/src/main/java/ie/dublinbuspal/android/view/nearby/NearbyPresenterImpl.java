package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.util.ErrorLog;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static ie.dublinbuspal.android.util.LocationUtilities.distanceBetween;

public class NearbyPresenterImpl extends MvpBasePresenter<NearbyView> implements NearbyPresenter {

    private static final int LIMIT = 30;

    private final NearbyModel model;
    private final DublinBusRepository repository;
    private CompositeDisposable disposables;

    public NearbyPresenterImpl(DublinBusRepository repository, NearbyModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void refreshNearby(Location location) {
        getModel().setCurrentLocation(location);
        getDisposables().add(Single.fromCallable(() -> getRepository().getDetailedBusStops())
                .subscribeOn(Schedulers.io())
                .map(this::filterBusStops)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetBusStops, this::onGetBusStopsError));
    }

    @Override
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private SortedMap<Double, DetailedBusStop> filterBusStops(List<DetailedBusStop> busStops) {
        SortedMap<Double, DetailedBusStop> sorted = new TreeMap<>();
        SortedMap<Double, DetailedBusStop> filtered = new TreeMap<>();

        for (DetailedBusStop busStop : busStops) {
            sorted.put(distanceBetween(getModel().getLocation(), busStop), busStop);
        }

        int i = 0;
        Iterator<Map.Entry<Double, DetailedBusStop>> iterator = sorted.entrySet().iterator();
        while (iterator.hasNext() && i < LIMIT) {
            Map.Entry<Double, DetailedBusStop> entry = iterator.next();
            filtered.put(entry.getKey(), entry.getValue());
            i++;
        }

        return filtered;
    }

    private void onGetBusStops(SortedMap<Double, DetailedBusStop> busStops) {
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

    private DublinBusRepository getRepository() {
        return repository;
    }

    private NearbyModel getModel() {
        return model;
    }

}
