package ie.dublinbuspal.di

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.mapping.favourite.FavouriteStopEntityMapper
import ie.dublinbuspal.mapping.livedata.LiveDataMapper
import ie.dublinbuspal.mapping.route.RouteDomainMapper
import ie.dublinbuspal.mapping.route.RouteEntityMapper
import ie.dublinbuspal.mapping.routeservice.RouteServiceDomainMapper
import ie.dublinbuspal.mapping.routeservice.RouteServiceEntityMapper
import ie.dublinbuspal.mapping.rss.RssMapper
import ie.dublinbuspal.mapping.stop.SmartDublinStopServiceDomainMapper
import ie.dublinbuspal.mapping.stop.SmartDublinStopServiceEntityMapper
import ie.dublinbuspal.mapping.stop.StopDomainMapper
import ie.dublinbuspal.mapping.stop.StopEntityMapper
import ie.dublinbuspal.mapping.stopservice.StopServiceDomainMapper
import ie.dublinbuspal.mapping.stopservice.StopServiceEntityMapper
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.model.stopservice.SmartDublinStopService
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.FavouritePersister
import ie.dublinbuspal.repository.favourite.FavouriteStopRepository
import ie.dublinbuspal.repository.livedata.LiveDataRepository
import ie.dublinbuspal.repository.route.RoutePersister
import ie.dublinbuspal.repository.route.RouteRepository
import ie.dublinbuspal.repository.routeservice.RouteServicePersister
import ie.dublinbuspal.repository.routeservice.RouteServiceRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.*
import ie.dublinbuspal.repository.stopservice.StopServicePersister
import ie.dublinbuspal.repository.stopservice.StopServiceRepository
import ie.dublinbuspal.service.DublinBusRssApi
import ie.dublinbuspal.service.DublinBusSoapApi
import ie.dublinbuspal.service.SmartDublinRestApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.rss.RssResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun stopRepository(api: DublinBusSoapApi,
                       stopDao: StopDao,
                       detailedStopDao: DetailedStopDao,
                       txRunner: TxRunner): Repository<List<Stop>, Any> {

        val fetcher = Fetcher<StopsResponseXml, StopsRequestXml> { key -> api.getStops(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = StopDomainMapper()
        val entityMapper = StopEntityMapper()
        val persister = StopPersister(stopDao, detailedStopDao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return StopRepository(store)
    }

    @Provides
    @Singleton
    fun routeRepository(api: DublinBusSoapApi,
                        dao: RouteDao,
                        txRunner: TxRunner): Repository<List<Route>, Any> {

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
    fun stopServiceRepository(api: DublinBusSoapApi,
                              dao: StopServiceDao): Repository<StopService, String> {

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
    fun routeServiceRepository(api: DublinBusSoapApi,
                               dao: RouteServiceDao): Repository<RouteService, String> {

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
    fun liveDataRepository(api: DublinBusSoapApi): Repository<List<LiveData>, String> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = LiveDataMapper()
        val store = StoreBuilder.parsedWithKey<LiveDataRequestXml, LiveDataResponseXml, List<LiveData>>()
                .fetcher { key -> api.getLiveData(key) }
                .parser { xml -> mapper.map(xml.liveData) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return LiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun favouritesRepository(stopDao: FavouriteStopDao,
                             detailedStopDao: DetailedStopDao): FavouriteRepository<List<Stop>, Any> {

        val fetcher = Fetcher<List<FavouriteStopEntity>, Any> { stopDao.selectAll().toSingle() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = StopDomainMapper()
        val entityMapper = FavouriteStopEntityMapper()
        val persister = FavouritePersister(stopDao, detailedStopDao, domainMapper)

        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return FavouriteStopRepository(store, stopDao, entityMapper)
    }

    @Provides
    @Singleton
    fun rssRepository(api: DublinBusRssApi): Repository<List<RssNews>, Any> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val mapper = RssMapper()
        val store = StoreBuilder.parsedWithKey<Any, RssResponseXml, List<RssNews>>()
                .fetcher { api.getRssNews() }
                .parser { xml -> mapper.map(xml.channel!!.newsItems) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return RssNewsRepository(store)
    }

    @Provides
    @Singleton
    fun smartDublinStopServiceRepository(api: SmartDublinRestApi,
                                         dao: SmartDublinStopServiceDao,
                                         txRunner: TxRunner): Repository<List<SmartDublinStopService>, Any> {

        val fetcher = Fetcher<StopsResponseJson, SmartDublinKey> { key -> api.getStops(key.operator, key.format) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val entityMapper = SmartDublinStopServiceEntityMapper()
        val domainMapper = SmartDublinStopServiceDomainMapper()
        val persister = SmartDublinStopServicePersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return SmartDublinStopServiceRepository(store)
    }

}