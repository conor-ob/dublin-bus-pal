package ie.dublinbuspal.android.view.search;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.util.AlphanumComparator;
import ie.dublinbuspal.android.util.CollectionUtilities;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchPresenterImpl extends MvpBasePresenter<SearchQueryView>
        implements SearchPresenter {

    private final PublishSubject<String> subject = PublishSubject.create();
    private final SearchModel model;
    private final DublinBusRepository repository;

    public SearchPresenterImpl(DublinBusRepository repository, SearchModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void onResume(String query) {
        subject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(this::searchObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSearchComplete);

        Single.zip(Single.fromCallable(repository::getRoutes),
                Single.fromCallable(repository::getDetailedBusStops), (r, b) -> {
            model.setSearchableRoutes(r);
            model.setSearchableBusStops(b);
            return search(query);
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSearchComplete, this::onError);
    }

    private void onError(Throwable throwable) {

    }

    @Override
    public void onPause() {
        //TODO unsubscribe observables
    }

    private void onSearchComplete(List<Object> searchResult) {
        ifViewAttached(view -> {
            view.hideLoading();
            view.showSearchResult(searchResult);
        });
    }

    private List<Object> search(String query) {
        query = query.toLowerCase();

        List<Object> searchResult = new ArrayList<>();

        List<Route> routes = new ArrayList<>();
        List<Route> searchableRoutes = model.getSearchableRoutes();
        for (Route route : searchableRoutes) {
            if (route.getRouteId().toLowerCase().contains(query)
                    || route.getOrigin().toLowerCase().contains(query)
                    || route.getDestination().toLowerCase().contains(query)) {
                routes.add(route);
            }
        }

        if (!CollectionUtilities.isNullOrEmpty(routes)) {
            if (routes.size() == searchableRoutes.size()) {
                searchResult.add("All routes");
            } else if (routes.size() == 1) {
                searchResult.add("Found 1 route");
            } else {
                searchResult.add(String.format(Locale.UK, "Found %s routes", routes.size()));
            }
            Collections.sort(routes, (r1, r2)
                    -> AlphanumComparator.getInstance().compare(r1.getRouteId(), r2.getRouteId()));
            searchResult.addAll(routes);
        }

        List<DetailedBusStop> busStops = new ArrayList<>();
        List<DetailedBusStop> searchableBusStops = model.getSearchableBusStops();
        for (DetailedBusStop busStop : searchableBusStops) {
            if (busStop.getId().toLowerCase().contains(query)
                    || busStop.getName().toLowerCase().contains(query)
                    || busStop.getRealName().toLowerCase().contains(query)) {
                busStops.add(busStop);
            }
        }

        if (!CollectionUtilities.isNullOrEmpty(busStops)) {
            if (busStops.size() == searchableBusStops.size()) {
                searchResult.add("All bus stops");
            } else if (busStops.size() == 1) {
                searchResult.add("Found 1 bus stop");
            } else {
                searchResult.add(String.format(Locale.UK, "Found %s bus stops",
                        busStops.size()));
            }
            Collections.sort(busStops, (s1, s2)
                    -> AlphanumComparator.getInstance().compare(s1.getId(), s2.getId()));
            searchResult.addAll(busStops);
        }

        return searchResult;
    }

    private Observable<List<Object>> searchObservable(String query) {
        return Observable.just(search(query));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        subject.onNext(query);
        ifViewAttached(SearchQueryView::showLoading);
        return true;
    }

}
