package ie.dublinbuspal.domain.di

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.RouteDao
import ie.dublinbuspal.database.dao.RouteServiceDao
import ie.dublinbuspal.database.dao.StopDao
import ie.dublinbuspal.database.dao.StopServiceDao
import ie.dublinbuspal.domain.mapping.livedata.LiveDataMapper
import ie.dublinbuspal.domain.mapping.route.RouteDomainMapper
import ie.dublinbuspal.domain.mapping.route.RouteEntityMapper
import ie.dublinbuspal.domain.mapping.routeservice.RouteServiceDomainMapper
import ie.dublinbuspal.domain.mapping.routeservice.RouteServiceEntityMapper
import ie.dublinbuspal.domain.mapping.stop.StopDomainMapper
import ie.dublinbuspal.domain.mapping.stop.StopEntityMapper
import ie.dublinbuspal.domain.mapping.stopservice.StopServiceDomainMapper
import ie.dublinbuspal.domain.mapping.stopservice.StopServiceEntityMapper
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.domain.model.routeservice.RouteService
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.domain.repository.livedata.LiveDataRepository
import ie.dublinbuspal.domain.repository.route.RoutePersister
import ie.dublinbuspal.domain.repository.route.RouteRepository
import ie.dublinbuspal.domain.repository.routeservice.RouteServicePersister
import ie.dublinbuspal.domain.repository.routeservice.RouteServiceRepository
import ie.dublinbuspal.domain.repository.stop.StopPersister
import ie.dublinbuspal.domain.repository.stop.StopRepository
import ie.dublinbuspal.domain.repository.stopservice.StopServicePersister
import ie.dublinbuspal.domain.repository.stopservice.StopServiceRepository
import ie.dublinbuspal.service.DublinBusApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun stopRepository(api: DublinBusApi,
                       dao: StopDao,
                       txRunner: TxRunner): Repository<List<Stop>, StopsRequestXml> {

        val fetcher = Fetcher<StopsResponseXml, StopsRequestXml> { key -> api.getBusStops(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = StopDomainMapper()
        val entityMapper = StopEntityMapper()
        val persister = StopPersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return StopRepository(store)
    }

    @Provides
    @Singleton
    fun routeRepository(api: DublinBusApi,
                        dao: RouteDao,
                        txRunner: TxRunner): Repository<List<Route>, RoutesRequestXml> {

        val fetcher = Fetcher<RoutesResponseXml, RoutesRequestXml> { key -> api.getRoutes(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = RouteDomainMapper()
        val entityMapper = RouteEntityMapper()
        val persister = RoutePersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return RouteRepository(store)
    }

    @Provides
    @Singleton
    fun stopServiceRepository(api: DublinBusApi,
                              dao: StopServiceDao): Repository<StopService, StopServiceRequestXml> {

        val fetcher = Fetcher<StopServiceResponseXml, StopServiceRequestXml> { key -> api.getStopService(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = StopServiceDomainMapper()
        val entityMapper = StopServiceEntityMapper()
        val persister = StopServicePersister(dao, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return StopServiceRepository(store)
    }

    @Provides
    @Singleton
    fun routeServiceRepository(api: DublinBusApi,
                               dao: RouteServiceDao): Repository<RouteService, RouteServiceRequestXml> {

        val fetcher = Fetcher<RouteServiceResponseXml, RouteServiceRequestXml> { key -> api.getRouteService(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = RouteServiceDomainMapper()
        val entityMapper = RouteServiceEntityMapper()
        val persister = RouteServicePersister(dao, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return RouteServiceRepository(store)
    }

    @Provides
    @Singleton
    fun liveDataRepository(api: DublinBusApi): Repository<List<LiveData>, LiveDataRequestXml> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = LiveDataMapper()
        val store = StoreBuilder.parsedWithKey<LiveDataRequestXml, LiveDataResponseXml, List<LiveData>>()
                .fetcher { key -> api.getLiveData(key) }
                .parser { json -> mapper.map(json.liveData!!) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return LiveDataRepository(store)
    }

}
