package ie.dublinbuspal.android.view.favourite;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.ErrorLog;
import ie.dublinbuspal.model.favourite.FavouriteStop;
import ie.dublinbuspal.usecase.favourites.FavouritesUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavouritesPresenterImpl extends MvpBasePresenter<FavouritesView>
        implements FavouritesPresenter {

    private final FavouritesModel model;
    private final FavouritesUseCase useCase;
    private CompositeDisposable disposables;

    @Inject
    public FavouritesPresenterImpl(FavouritesUseCase useCase) {
        this.useCase = useCase;
        this.model = new FavouritesModelImpl();
    }

    @Override
    public void onResume() {
        getDisposables().add(useCase.getFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFavouritesReceived, this::onError));
    }

    @Override
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    @Override
    public void onPause(List<FavouriteStop> favourites) {
        getDisposables().add(useCase.saveFavourites(favourites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::onPause)
                .doOnError(this::onError)
                .subscribe()
        );
    }

    private void onError(Throwable throwable) {
        ErrorLog.e(throwable);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showError(R.string.error_unknown);
        });
    }

    private void onFavouritesReceived(List<FavouriteStop> favourites) {
        getModel().setFavourites(favourites);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showFavourites(getModel().getFavourites());
        });
    }

    private FavouritesModel getModel() {
        return model;
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

}
