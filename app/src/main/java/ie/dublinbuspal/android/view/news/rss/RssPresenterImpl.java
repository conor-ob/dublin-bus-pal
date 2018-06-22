package ie.dublinbuspal.android.view.news.rss;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.remote.rss.xml.Item;
import ie.dublinbuspal.android.data.remote.rss.xml.Rss;
import ie.dublinbuspal.android.util.ErrorLog;
import ie.dublinbuspal.android.util.RssDateComparator;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RssPresenterImpl extends MvpBasePresenter<RssView> implements RssPresenter {

    private final DublinBusRepository repository;
    private final RssModel model;
    private CompositeDisposable disposables;

    public RssPresenterImpl(DublinBusRepository repository, RssModel model) {
        this.repository = repository;
        this.model = model;
    }

    @Override
    public void onResume() {
        if (getModel().getRss() == null) {
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
        getDisposables().add(Single.fromCallable(getRepository()::getRss)
                .subscribeOn(Schedulers.io())
                .map(this::sortRss)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRss, this::onGetRssError));
    }

    private Rss sortRss(Rss rss) {
        List<Item> items = rss.getChannel().getItems();
        Collections.sort(items, RssDateComparator.getInstance());
        return rss;
    }

    private void onGetRss(Rss rss) {
        getModel().setRss(rss);
        ifViewAttached(view -> {
            view.hideProgress();
            view.showRss(getModel().getRss().getChannel().getItems());
        });
    }

    private void onGetRssError(Throwable throwable) {
        ErrorLog.e(throwable);
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

    private DublinBusRepository getRepository() {
        return repository;
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
