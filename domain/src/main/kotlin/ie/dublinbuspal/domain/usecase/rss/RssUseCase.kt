package ie.dublinbuspal.domain.usecase.rss

import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.rss.NewsItem
import io.reactivex.Observable
import javax.inject.Inject

class RssUseCase @Inject constructor(private val repository: Repository<List<NewsItem>, Any>) {

    fun getRss(): Observable<List<NewsItem>> {
        return repository.get(0)
    }

}
