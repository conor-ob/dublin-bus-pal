package ie.dublinbuspal.repository.routeservice

import ie.dublinbuspal.model.routeservice.DefaultRouteService
import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteService
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class RouteServiceRepository(
        private val defaultRouteServiceRepository: Repository<DefaultRouteService>,
        private val goAheadDublinRouteServiceRepository: Repository<GoAheadDublinRouteService>
) : Repository<RouteService> {

    override fun getById(id: String): Observable<RouteService> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
