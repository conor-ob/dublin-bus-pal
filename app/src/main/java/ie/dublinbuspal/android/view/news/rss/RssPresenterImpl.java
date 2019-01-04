package ie.dublinbuspal.android.view.news.rss;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.model.rss.RssNews;
import ie.dublinbuspal.usecase.rss.RssNewsUseCase;
import ie.dublinbuspal.util.CollectionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RssPresenterImpl extends MvpBasePresenter<RssView> implements RssPresenter {

    private final RssNewsUseCase useCase;
    private final RssModel model;
    private CompositeDisposable disposables;

    @Inject
    public RssPresenterImpl(RssNewsUseCase useCase) {
        this.useCase = useCase;
        this.model = new RssModelImpl();
    }

    @Override
    public void onResume() {
        if (CollectionUtils.isNullOrEmpty(getModel().getRss())) {
            getRss();
        } else {
            onGetRss(getModel().getRss());
        }
    }

    @Override
    public void onRefresh() {
        getRss();
    }

    private void getRss() {
        getDisposables().add(useCase.getRssNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRss, this::onGetRssError));
    }

    private void onGetRss(List<RssNews> rss) {
        getModel().setRss(rss);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showRss(rss);
        });
    }

    private void onGetRssError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(view -> {
            view.hideProgress();
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
    public void onDestroy() {
        getDisposables().clear();
        getDisposables().dispose();
    }

    private RssModel getModel() {
        return model;
    }

    private CompositeDisposable getDisposables() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

}
