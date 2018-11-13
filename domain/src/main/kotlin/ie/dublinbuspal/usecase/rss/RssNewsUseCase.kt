package ie.dublinbuspal.usecase.rss

import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class RssNewsUseCase @Inject constructor(private val repository: Repository<List<RssNews>, Any>) {

    fun getRssNews(): Observable<List<RssNews>> {
        return repository.get(0)
    }

}
