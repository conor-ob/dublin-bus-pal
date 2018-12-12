package ie.dublinbuspal.android.view.favourite;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.util.ErrorLog;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavouritesPresenterImpl extends MvpBasePresenter<FavouritesView>
        implements FavouritesPresenter {

    private final FavouritesModel model;
    private final DublinBusRepository repository;
    private CompositeDisposable disposables;

    public FavouritesPresenterImpl(DublinBusRepository repository, FavouritesModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void onResume() {
        getDisposables().add(Single.fromCallable(getRepository()::getFavourites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFavouritesReceived, this::onError));
    }

    @Override
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private void onError(Throwable throwable) {
        ErrorLog.e(throwable);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showError(R.string.error_unknown);
        });
    }

    private void onFavouritesReceived(List<DetailedBusStop> favourites) {
        getModel().setFavourites(favourites);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showFavourites(getModel().getFavourites());
        });
    }

    private FavouritesModel getModel() {
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