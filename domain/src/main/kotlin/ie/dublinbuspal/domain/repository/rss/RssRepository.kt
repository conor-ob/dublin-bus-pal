package ie.dublinbuspal.domain.repository.rss

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.rss.NewsItem
import io.reactivex.Observable

class RssRepository(private val store: Store<List<NewsItem>, Any>) : Repository<List<NewsItem>, Any> {

    override fun get(key: Any): Observable<List<NewsItem>> {
        return store.get(key).toObservable()
    }

    override fun fetch(key: Any): Observable<List<NewsItem>> {
        return store.fetch(key).toObservable()
    }

}
