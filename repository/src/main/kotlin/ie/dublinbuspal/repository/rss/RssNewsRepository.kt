package ie.dublinbuspal.repository.rss

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class RssNewsRepository(private val store: Store<List<RssNews>, String>) : Repository<RssNews> {

    private val key = javaClass.simpleName

    override fun getAll(): Observable<List<RssNews>> {
        return store.get(key).toObservable()
    }

    override fun getById(id: String): Observable<RssNews> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<RssNews>> {
        throw UnsupportedOperationException()
    }

}
