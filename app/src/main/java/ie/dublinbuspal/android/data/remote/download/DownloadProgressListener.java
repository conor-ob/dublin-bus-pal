package ie.dublinbuspal.android.data.remote.download;

public interface DownloadProgressListener {

    void update(int percent, boolean done);

    void registerObserver(DownloadProgressObserver observer);
}