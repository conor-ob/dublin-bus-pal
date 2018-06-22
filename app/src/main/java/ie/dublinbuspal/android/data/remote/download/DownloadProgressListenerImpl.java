package ie.dublinbuspal.android.data.remote.download;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DownloadProgressListenerImpl implements DownloadProgressListener {

    private DownloadProgressObserver observer;

    public DownloadProgressListenerImpl() {
        super();
    }

    @Override
    public void update(int percent, boolean done) {
        if (observer != null) {
            Single.just(percent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer::onProgressUpdate);
        }
    }

    @Override
    public void registerObserver(DownloadProgressObserver observer) {
        this.observer = observer;
    }

}
