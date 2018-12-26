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
import ie.dublinbuspal.mapping.livedata.DefaultLiveDataDomainMapper
import ie.dublinbuspal.mapping.livedata.DefaultLiveDataEntityMapper
import ie.dublinbuspal.mapping.livedata.DublinBusGoAheadDublinLiveDataDomainMapper
import ie.dublinbuspal.mapping.livedata.DublinBusGoAheadDublinLiveDataEntityMapper
import ie.dublinbuspal.mapping.route.DefaultRouteDomainMapper
import ie.dublinbuspal.mapping.route.DefaultRouteEntityMapper
import ie.dublinbuspal.mapping.routeservice.DefaultRouteServiceMapper
import ie.dublinbuspal.mapping.rss.RssMapper
import ie.dublinbuspal.mapping.stop.*
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.route.RouteVariant
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.DefaultFavouriteStopRepository
import ie.dublinbuspal.repository.livedata.DefaultLiveDataRepository
import ie.dublinbuspal.repository.livedata.GoAheadDublinLiveDataRepository
import ie.dublinbuspal.repository.livedata.LiveDataRepository
import ie.dublinbuspal.repository.route.DefaultRoutePersister
import ie.dublinbuspal.repository.route.DefaultRouteRepository
import ie.dublinbuspal.repository.route.GoAheadDublinRouteRepository
import ie.dublinbuspal.repository.route.RouteRepository
import ie.dublinbuspal.repository.routeservice.DefaultRouteServiceRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.*
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.rss.RssResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.resource.DublinBusGoAheadDublinRestResource
import ie.dublinbuspal.service.resource.DublinBusRssResource
import ie.dublinbuspal.service.resource.DublinBusSoapResource
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun stopRepository(
            defaultStopRepository: Repository<DefaultStop>,
            @Named("bac") dublinBusStopRepository: Repository<DublinBusGoAheadDublinStop>,
            @Named("gad") goAheadDublinStopRepository: Repository<DublinBusGoAheadDublinStop>,
            favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
    ): Repository<Stop> {
        return StopRepository(defaultStopRepository, dublinBusStopRepository, goAheadDublinStopRepository, favouriteStopRepository)
    }

    @Provides
    @Singleton
    fun defaultStopRepository(
            api: DublinBusSoapResource,
            dao: DefaultStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner
    ): Repository<DefaultStop> {

        val fetcher = Fetcher<StopsResponseXml, String> { api.getDublinBusStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = DefaultStopDomainMapper()
        val entityMapper = DefaultStopEntityMapper()
        val persister = DefaultStopPersister(memoryPolicy, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return DefaultStopRepository(store)
    }

    @Provides
    @Singleton
    @Named("bac")
    fun dublinBusStopRepository(
            api: DublinBusGoAheadDublinRestResource,
            dao: DublinBusStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner
    ): Repository<DublinBusGoAheadDublinStop> {

        val fetcher = Fetcher<StopsResponseJson, String> { api.getDublinBusStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = DublinBusStopDomainMapper()
        val entityMapper = DublinBusStopEntityMapper()
        val persister = DublinBusStopPersister(memoryPolicy, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return DublinBusStopRepository(store)
    }

    @Provides
    @Singleton
    @Named("gad")
    fun goAheadDublinStopRepository(
            api: DublinBusGoAheadDublinRestResource,
            dao: GoAheadDublinStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner
    ): Repository<DublinBusGoAheadDublinStop> {

        val fetcher = Fetcher<StopsResponseJson, String> { api.getGoAheadDublinStops() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = GoAheadDublinStopDomainMapper()
        val entityMapper = GoAheadDublinStopEntityMapper()
        val persister = GoAheadDublinStopPersister(memoryPolicy, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return GoAheadDublinStopRepository(store)
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
            persisterDao: PersisterDao,
            txRunner: TxRunner
    ): Repository<DefaultRoute> {

        val fetcher = Fetcher<RoutesResponseXml, String> { api.getDublinBusRoutes() }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = DefaultRouteDomainMapper()
        val entityMapper = DefaultRouteEntityMapper()
        val persister = DefaultRoutePersister(memoryPolicy, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)

        return DefaultRouteRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinRouteRepository(
            resource: DublinBusGoAheadDublinRestResource
//            dao: GoAheadDublinRouteDao,
//            txRunner: TxRunner
    ): Repository<GoAheadDublinRoute> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val store = StoreBuilder.parsedWithKey<String, RouteListInformationWithVariantsResponseJson, List<GoAheadDublinRoute>>()
                .fetcher { resource.getGoAheadDublinRoutes() }
                .parser { json ->
                    json.routes
                            .map { it -> GoAheadDublinRoute(it.route!!, Collections.singletonList(RouteVariant(it.variants!![0].origin!!, it.variants!![0].destination!!))) }
                }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return GoAheadDublinRouteRepository(store)
    }

    @Provides
    @Singleton
    fun defaultRouteServiceRepository(
            resource: DublinBusSoapResource
    ): Repository<RouteService> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val mapper = DefaultRouteServiceMapper()
        val store = StoreBuilder.parsedWithKey<String, RouteServiceResponseXml, RouteService>()
                .fetcher { key -> resource.getDublinBusRouteService(key) }
                .parser { xml -> mapper.map(xml) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return DefaultRouteServiceRepository(store)
    }

    @Provides
    @Singleton
    fun liveDataRepository(
            defaultLiveDataRepository: Repository<RealTimeStopData>,
            goAheadDublinLiveDataRepository: Repository<DublinBusGoAheadDublinLiveData>
    ): Repository<LiveData> {
        val defaultLiveDataMapper = DefaultLiveDataDomainMapper()
        val goAheadDublinLiveDataMapper = DublinBusGoAheadDublinLiveDataDomainMapper()
        return LiveDataRepository(defaultLiveDataRepository, goAheadDublinLiveDataRepository, defaultLiveDataMapper, goAheadDublinLiveDataMapper)
    }

    @Provides
    @Singleton
    fun defaultLiveDataRepository(api: DublinBusSoapResource): Repository<RealTimeStopData> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = DefaultLiveDataEntityMapper()
        val store = StoreBuilder.parsedWithKey<String, LiveDataResponseXml, List<RealTimeStopData>>()
                .fetcher { key -> api.getDublinBusLiveData(key) }
                .parser { xml -> mapper.map(xml.realTimeStopData) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return DefaultLiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinLiveDataRepository(api: DublinBusGoAheadDublinRestResource): Repository<DublinBusGoAheadDublinLiveData> {

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = DublinBusGoAheadDublinLiveDataEntityMapper()
        val store = StoreBuilder.parsedWithKey<String, RealTimeBusInformationResponseJson, List<DublinBusGoAheadDublinLiveData>>()
                .fetcher { key -> api.getGoAheadDublinLiveData(key) }
                .parser { json -> mapper.map(json.realTimeBusInformation) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        return GoAheadDublinLiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun favouritesRepository(dao: FavouriteStopDao): FavouriteStopRepository<FavouriteStop> {

        val domainMapper = FavouriteStopDomainMapper()
        val entityMapper = FavouriteStopEntityMapper()

        return DefaultFavouriteStopRepository(dao, entityMapper, domainMapper)
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
