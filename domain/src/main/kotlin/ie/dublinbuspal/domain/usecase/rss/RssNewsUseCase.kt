package ie.dublinbuspal.domain.usecase.rss

import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.domain.model.rss.RssNews
import io.reactivex.Observable
import javax.inject.Inject

class RssNewsUseCase @Inject constructor(private val repository: Repository<List<RssNews>, Any>) {

    fun getRssNews(): Observable<List<RssNews>> {
        return repository.get(0)
    }

}
