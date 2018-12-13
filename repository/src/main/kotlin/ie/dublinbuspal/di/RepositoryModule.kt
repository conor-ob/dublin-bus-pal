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
import ie.dublinbuspal.mapping.favourite.FavouriteStopDomainMapper
import ie.dublinbuspal.mapping.favourite.FavouriteStopEntityMapper
import ie.dublinbuspal.mapping.livedata.RealTimeBusInformationLiveDataMapper
import ie.dublinbuspal.mapping.livedata.RealTimeBusInformationMapper
import ie.dublinbuspal.mapping.livedata.RealTimeStopDataLiveDataMapper
import ie.dublinbuspal.mapping.livedata.RealTimeStopDataMapper
import ie.dublinbuspal.mapping.route.RouteDomainMapper
import ie.dublinbuspal.mapping.route.RouteEntityMapper
import ie.dublinbuspal.mapping.routeservice.RouteServiceDomainMapper
import ie.dublinbuspal.mapping.routeservice.RouteServiceEntityMapper
import ie.dublinbuspal.mapping.rss.RssMapper
import ie.dublinbuspal.mapping.stop.*
import ie.dublinbuspal.mapping.stopservice.StopServiceDomainMapper
import ie.dublinbuspal.mapping.stopservice.StopServiceEntityMapper
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeBusInformation
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.route.RouteVariant
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.FavouriteStopRepository
import ie.dublinbuspal.repository.livedata.LiveDataRepository
import ie.dublinbuspal.repository.livedata.RealTimeBusInformationRepository
import ie.dublinbuspal.repository.livedata.RealTimeStopDataRepository
import ie.dublinbuspal.repository.route.DefaultRouteRepository
import ie.dublinbuspal.repository.route.GadRouteRepository
import ie.dublinbuspal.repository.route.RoutePersister
import ie.dublinbuspal.repository.route.RouteRepository
import ie.dublinbuspal.repository.routeservice.RouteServicePersister
import ie.dublinbuspal.repository.routeservice.RouteServiceRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.*
import ie.dublinbuspal.repository.stopservice.StopServicePersister
import ie.dublinbuspal.repository.stopservice.StopServiceRepository
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.rss.RssResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import ie.dublinbuspal.service.resource.DublinBusRssResource
import ie.dublinbuspal.service.resource.DublinBusSoapResource
import ie.dublinbuspal.service.resource.SmartDublinRestResource
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun resolvedStopRepository(
            stopRepository: Repository<Stop>,
            @Named("bac") bacStopRepository: Repository<SmartDublinStop>,
            @Named("gad") gadStopRepository: Repository<SmartDublinStop>,
            favouritesRepository: FavouriteRepository<FavouriteStop>
    ): Repository<ResolvedStop> {
        return ResolvedStopRepository(stopRepository, bacStopRepository, gadStopRepository, favouritesRepository)
    }

    @Provides
    @Singleton
    fun stopRepository(
            api: DublinBusSoapResource,
            stopDao: StopDao,
            txRunner: TxRunner
    ): Repository<Stop> {

        val fetcher = Fetcher<StopsResponseXml, String> { api.getDublinBusStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = StopDomainMapper()
        val entityMapper = StopEntityMapper()
        val persister = StopPersister(stopDao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return StopRepository(store)
    }

    @Provides
    @Singleton
    @Named("bac")
    fun bacStopRepository(
            api: SmartDublinRestResource,
            dao: BacStopDao,
            txRunner: TxRunner
    ): Repository<SmartDublinStop> {

        val fetcher = Fetcher<StopsResponseJson, String> { api.getDublinBusStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = BacStopDomainMapper()
        val entityMapper = BacStopEntityMapper()
        val persister = BacStopPersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return BacStopRepository(store)
    }

    @Provides
    @Singleton
    @Named("gad")
    fun gadStopRepository(
            api: SmartDublinRestResource,
            dao: GadStopDao,
            txRunner: TxRunner
    ): Repository<SmartDublinStop> {

        val fetcher = Fetcher<StopsResponseJson, String> { api.getGoAheadDublinStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = GadStopDomainMapper()
        val entityMapper = GadStopEntityMapper()
        val persister = GadStopPersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return GadStopRepository(store)
    }

    @Provides
    @Singleton
    fun routeRepository(
            defaultRouteRepository: Repository<DefaultRoute>,
            goAheadDublinRouteRepository: Repository<GoAheadDublinRoute>
    ): Repository<Route> {
        return RouteRepository(defaultRouteRepository, goAheadDublinRouteRepository)
    }

    @Provides
    @Singleton
    fun defaultRouteRepository(
            api: DublinBusSoapResource,
            dao: DefaultRouteDao,
            txRunner: TxRunner
    ): Repository<DefaultRoute> {

        val fetcher = Fetcher<RoutesResponseXml, String> { api.getDublinBusRoutes() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = RouteDomainMapper()
        val entityMapper = RouteEntityMapper()
        val persister = RoutePersister(dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return DefaultRouteRepository(store)
    }

    @Provides
    @Singleton
    fun gadRouteRepository(
            resource: SmartDublinRestResource,
//            dao: GadRouteDao,
            txRunner: TxRunner
    ): Repository<GoAheadDublinRoute> {
//        val fetcher = Fetcher<RouteListInformationWithVariantsResponseJson, Any> { key -> api.getRoutes(key.format) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val store = StoreBuilder.parsedWithKey<String, RouteListInformationWithVariantsResponseJson, List<GoAheadDublinRoute>>()
                .fetcher { resource.getGoAheadDublinRoutes() }
                .parser { json ->
                    json.routes!!
                            .map { it -> GoAheadDublinRoute(it.route!!, Collections.singletonList(RouteVariant(it.variants!![0].origin!!, it.variants!![0].destination!!))) }
                }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return GadRouteRepository(store)
    }

    @Provides
    @Singleton
    fun stopServiceRepository(api: DublinBusSoapResource,
                              dao: StopServiceDao): Repository<StopService> {

        val fetcher = Fetcher<StopServiceResponseXml, String> { key -> api.getDublinBusStopService(key) }

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
    fun routeServiceRepository(api: DublinBusSoapResource,
                               dao: RouteServiceDao): Repository<RouteService> {

        val fetcher = Fetcher<RouteServiceResponseXml, String> { key -> api.getDublinBusRouteService(key) }

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
    fun liveDataRepository(
            realTimeStopDataRepository: Repository<RealTimeStopData>,
            realTimeBusInformationRepository: Repository<RealTimeBusInformation>
    ): Repository<LiveData> {
        val realTimeStopDataMapper = RealTimeStopDataLiveDataMapper()
        val realTimeBusInformationMapper = RealTimeBusInformationLiveDataMapper()
        return LiveDataRepository(realTimeStopDataRepository, realTimeBusInformationRepository, realTimeStopDataMapper, realTimeBusInformationMapper)
    }

    @Provides
    @Singleton
    fun realTimeStopDataRepository(api: DublinBusSoapResource): Repository<RealTimeStopData> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = RealTimeStopDataMapper()
        val store = StoreBuilder.parsedWithKey<String, LiveDataResponseXml, List<RealTimeStopData>>()
                .fetcher { key -> api.getDublinBusLiveData(key) }
                .parser { xml -> mapper.map(xml.realTimeStopData) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return RealTimeStopDataRepository(store)
    }

    @Provides
    @Singleton
    fun realTimeBusInformationRepository(api: SmartDublinRestResource): Repository<RealTimeBusInformation> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = RealTimeBusInformationMapper()
        val store = StoreBuilder.parsedWithKey<String, RealTimeBusInformationResponseJson, List<RealTimeBusInformation>>()
                .fetcher { key -> api.getGoAheadDublinLiveData(key) }
                .parser { json -> mapper.map(json.realTimeBusInformation) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return RealTimeBusInformationRepository(store)
    }

    @Provides
    @Singleton
    fun favouritesRepository(dao: FavouriteStopDao): FavouriteRepository<FavouriteStop> {

        val domainMapper = FavouriteStopDomainMapper()
        val entityMapper = FavouriteStopEntityMapper()

        return FavouriteStopRepository(dao, entityMapper, domainMapper)
    }

    @Provides
    @Singleton
    fun rssRepository(api: DublinBusRssResource): Repository<RssNews> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val mapper = RssMapper()
        val store = StoreBuilder.parsedWithKey<String, RssResponseXml, List<RssNews>>()
                .fetcher { api.getDublinBusNews() }
                .parser { xml -> mapper.map(xml.channel!!.newsItems) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return RssNewsRepository(store)
    }

}
