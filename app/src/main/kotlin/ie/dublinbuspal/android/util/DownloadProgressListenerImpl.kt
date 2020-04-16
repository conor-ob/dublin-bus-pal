package ie.dublinbuspal.android.util

import ie.dublinbuspal.util.DownloadProgressListener
import ie.dublinbuspal.util.DownloadProgressObserver
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

class DownloadProgressListenerImpl : DownloadProgressListener {

    private val updates = ConcurrentHashMap<Int, Int>()
    private var observer: DownloadProgressObserver? = null

    override fun update(requestId: Int, percent: Int) {
        updates[requestId] = percent
        val progress = updates.values.sumBy { it } / 2 //TODO updates.size ?
        Timber.d(updates.toString())
        Timber.d(progress.toString())
        observer?.onProgressUpdate(progress)
    }

    override fun registerObserver(observer: DownloadProgressObserver) {
        updates.clear()
        this.observer = observer
    }

    override fun unregisterObserver() {
        this.observer = null
    }

}
