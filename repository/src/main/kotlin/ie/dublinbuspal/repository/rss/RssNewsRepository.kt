package ie.dublinbuspal.repository.rss

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class RssNewsRepository(private val store: Store<List<RssNews>, Any>) : Repository<List<RssNews>, Any> {

    override fun get(key: Any): Observable<List<RssNews>> {
        return store.get(key).toObservable()
    }

    override fun fetch(key: Any): Observable<List<RssNews>> {
        return store.fetch(key).toObservable()
    }

}
