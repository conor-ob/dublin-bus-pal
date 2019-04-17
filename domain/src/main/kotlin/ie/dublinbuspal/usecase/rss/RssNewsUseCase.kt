package ie.dublinbuspal.usecase.rss

import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.rss.RssNewsAge
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

class RssNewsUseCase @Inject constructor(
        private val repository: Repository<RssNews>
) {

    fun getRssNews(): Observable<List<RssNews>> {
//        return repository.getAll().map { rssNews -> rssNews.sortedBy { it.age.ageInSeconds } }
        return Observable.just(listOf(
                RssNews(
                        title = "",
                        link = "",
                        description = "",
                        age = RssNewsAge(timestamp = "", ageInSeconds = 0L)
                )
        ))
    }

}
