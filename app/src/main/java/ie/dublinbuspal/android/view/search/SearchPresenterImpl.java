package ie.dublinbuspal.android.view.search;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.ErrorLog;
import ie.dublinbuspal.usecase.search.SearchUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenterImpl extends MvpBasePresenter<SearchQueryView> implements SearchPresenter {

    private final SearchUseCase useCase;
    private CompositeDisposable disposables;

    @Inject
    public SearchPresenterImpl(SearchUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void onResume(String query) {
        getDisposables().add(useCase.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSearchComplete, this::onError));
    }

    private void onError(Throwable throwable) {
        ErrorLog.e(throwable);
        ifViewAttached(view -> {
            view.hideLoading();
            if (throwable instanceof UnknownHostException) {
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
    public void onPause() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private void onSearchComplete(List<Object> searchResult) {
        ifViewAttached(view -> {
            view.hideLoading();
            view.showSearchResult(searchResult);
        });
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

}
