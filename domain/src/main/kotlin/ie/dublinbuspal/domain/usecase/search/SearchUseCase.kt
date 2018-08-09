package ie.dublinbuspal.domain.usecase.search

import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.service.model.route.RoutesRequestBodyXml
import ie.dublinbuspal.service.model.route.RoutesRequestRootXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import io.reactivex.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val stopsRepository: Repository<List<Stop>, StopsRequestXml>,
                                        private val routesRepository: Repository<List<Route>, RoutesRequestXml>) {

    private val tempKey1: StopsRequestXml by lazy {
        val root = StopsRequestRootXml()
        val body = StopsRequestBodyXml(root)
        return@lazy StopsRequestXml(body)
    }

    fun tempFunction1(): Observable<List<Stop>> {
        return stopsRepository.get(tempKey1)
    }

    private val tempKey2: RoutesRequestXml by lazy {
        val root = RoutesRequestRootXml("")
        val body = RoutesRequestBodyXml(root)
        return@lazy RoutesRequestXml(body)
    }

    fun tempFunction2(): Observable<List<Route>> {
        return routesRepository.get(tempKey2)
    }

}