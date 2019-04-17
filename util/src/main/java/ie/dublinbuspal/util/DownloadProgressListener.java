package ie.dublinbuspal.util;

public interface DownloadProgressListener {

    void update(int requestId, int percent);

    void registerObserver(DownloadProgressObserver observer);

}
