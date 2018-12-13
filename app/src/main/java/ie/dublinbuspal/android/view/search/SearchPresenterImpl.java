package ie.dublinbuspal.android.view.search;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import ie.dublinbuspal.usecase.search.SearchUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class
SearchPresenterImpl extends MvpBasePresenter<SearchQueryView> implements SearchPresenter {

    private final SearchUseCase useCase;

    @Inject public SearchPresenterImpl(SearchUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void onResume(String query) {
        Disposable disposable = useCase.search(query)
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

}
