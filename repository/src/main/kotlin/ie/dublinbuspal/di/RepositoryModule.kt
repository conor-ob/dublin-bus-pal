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
import ie.dublinbuspal.mapping.route.GoAheadDublinRouteDomainMapper
import ie.dublinbuspal.mapping.route.GoAheadDublinRouteEntityMapper
import ie.dublinbuspal.mapping.routeservice.DefaultRouteServiceMapper
import ie.dublinbuspal.mapping.routeservice.GoAheadDublinRouteServiceMapper
import ie.dublinbuspal.mapping.rss.RssMapper
import ie.dublinbuspal.mapping.stop.*
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.routeservice.DefaultRouteService
import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteService
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.model.stop.GoAheadDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.DefaultFavouriteStopRepository
import ie.dublinbuspal.repository.livedata.DefaultLiveDataRepository
import ie.dublinbuspal.repository.livedata.GoAheadDublinLiveDataRepository
import ie.dublinbuspal.repository.livedata.LiveDataRepository
import ie.dublinbuspal.repository.route.*
import ie.dublinbuspal.repository.routeservice.DefaultRouteServiceRepository
import ie.dublinbuspal.repository.routeservice.GoAheadDublinRouteServiceRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.*
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteInformationResponseJson
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.rss.RssResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.resource.DublinBusGoAheadDublinRestResource
import ie.dublinbuspal.service.resource.DublinBusRssResource
import ie.dublinbuspal.service.resource.DublinBusSoapResource
import ie.dublinbuspal.util.InternetManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {

    private val shortTermMemoryPolicy: MemoryPolicy by lazy { newMemoryPolicy(30, TimeUnit.SECONDS) }
    private val midTermMemoryPolicy: MemoryPolicy by lazy { newMemoryPolicy(3, TimeUnit.HOURS) }
    private val longTermMemoryPolicy: MemoryPolicy by lazy { newMemoryPolicy(7, TimeUnit.DAYS) }

    private fun newMemoryPolicy(value: Long, timeUnit: TimeUnit): MemoryPolicy {
        return MemoryPolicy.builder()
                .setExpireAfterWrite(value)
                .setExpireAfterTimeUnit(timeUnit)
                .build()
    }

    @Provides
    @Singleton
    fun stopRepository(
            defaultStopRepository: Repository<DefaultStop>,
            dublinBusStopRepository: Repository<DublinBusStop>,
            goAheadDublinStopRepository: Repository<GoAheadDublinStop>,
            favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
    ): Repository<Stop> {
        return StopRepository(defaultStopRepository, dublinBusStopRepository, goAheadDublinStopRepository, favouriteStopRepository)
    }

    @Provides
    @Singleton
    fun defaultStopRepository(
            resource: DublinBusSoapResource,
            dao: DefaultStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner,
            internetManager: InternetManager
    ): Repository<DefaultStop> {
        val fetcher = Fetcher<StopsResponseXml, String> { resource.getDublinBusStops() }
        val domainMapper = DefaultStopDomainMapper()
        val entityMapper = DefaultStopEntityMapper()
        val persister = DefaultStopPersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DefaultStopRepository(store)
    }

    @Provides
    @Singleton
    fun dublinBusStopRepository(
            resource: DublinBusGoAheadDublinRestResource,
            dao: DublinBusStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner,
            internetManager: InternetManager
    ): Repository<DublinBusStop> {
        val fetcher = Fetcher<StopsResponseJson, String> { resource.getDublinBusStops() }
        val domainMapper = DublinBusStopDomainMapper()
        val entityMapper = DublinBusStopEntityMapper()
        val persister = DublinBusStopPersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DublinBusStopRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinStopRepository(
            resource: DublinBusGoAheadDublinRestResource,
            dao: GoAheadDublinStopDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner,
            internetManager: InternetManager
    ): Repository<GoAheadDublinStop> {
        val fetcher = Fetcher<StopsResponseJson, String> { resource.getGoAheadDublinStops() }
        val domainMapper = GoAheadDublinStopDomainMapper()
        val entityMapper = GoAheadDublinStopEntityMapper()
        val persister = GoAheadDublinStopPersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
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
            resource: DublinBusSoapResource,
            dao: DefaultRouteDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner,
            internetManager: InternetManager
    ): Repository<DefaultRoute> {
        val fetcher = Fetcher<RoutesResponseXml, String> { resource.getDublinBusRoutes() }
        val domainMapper = DefaultRouteDomainMapper()
        val entityMapper = DefaultRouteEntityMapper()
        val persister = DefaultRoutePersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DefaultRouteRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinRouteRepository(
            resource: DublinBusGoAheadDublinRestResource,
            dao: GoAheadDublinRouteDao,
            persisterDao: PersisterDao,
            txRunner: TxRunner,
            internetManager: InternetManager
    ): Repository<GoAheadDublinRoute> {
        val fetcher = Fetcher<RouteListInformationWithVariantsResponseJson, String> { resource.getGoAheadDublinRoutes() }
        val domainMapper = GoAheadDublinRouteDomainMapper()
        val entityMapper = GoAheadDublinRouteEntityMapper()
        val persister = GoAheadDublinRoutePersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return GoAheadDublinRouteRepository(store)
    }

    @Provides
    @Singleton
    fun defaultRouteServiceRepository(
            resource: DublinBusSoapResource
    ): Repository<DefaultRouteService> {
        val mapper = DefaultRouteServiceMapper()
        val store = StoreBuilder.parsedWithKey<String, RouteServiceResponseXml, DefaultRouteService>()
                .fetcher { key -> resource.getDublinBusRouteService(key) }
                .parser { xml -> mapper.map(xml) }
                .memoryPolicy(midTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return DefaultRouteServiceRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinRouteServiceRepository(
            resource: DublinBusGoAheadDublinRestResource
    ): Repository<GoAheadDublinRouteService> {
        val mapper = GoAheadDublinRouteServiceMapper()
        val store = StoreBuilder.parsedWithKey<String, RouteInformationResponseJson, GoAheadDublinRouteService>()
                .fetcher { key -> resource.getGoAheadDublinRouteService(key) }
                .parser { xml -> mapper.map(xml) }
                .memoryPolicy(midTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return GoAheadDublinRouteServiceRepository(store)
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
    fun defaultLiveDataRepository(resource: DublinBusSoapResource): Repository<RealTimeStopData> {
        val mapper = DefaultLiveDataEntityMapper()
        val store = StoreBuilder.parsedWithKey<String, LiveDataResponseXml, List<RealTimeStopData>>()
                .fetcher { key -> resource.getDublinBusLiveData(key) }
                .parser { xml -> mapper.map(xml.realTimeStopData) }
                .memoryPolicy(shortTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return DefaultLiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun goAheadDublinLiveDataRepository(resource: DublinBusGoAheadDublinRestResource): Repository<DublinBusGoAheadDublinLiveData> {
        val mapper = DublinBusGoAheadDublinLiveDataEntityMapper()
        val store = StoreBuilder.parsedWithKey<String, RealTimeBusInformationResponseJson, List<DublinBusGoAheadDublinLiveData>>()
                .fetcher { key -> resource.getGoAheadDublinLiveData(key) }
                .parser { json -> mapper.map(json.realTimeBusInformation) }
                .memoryPolicy(shortTermMemoryPolicy)
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
    fun rssRepository(resource: DublinBusRssResource): Repository<RssNews> {
        val mapper = RssMapper()
        val store = StoreBuilder.parsedWithKey<String, RssResponseXml, List<RssNews>>()
                .fetcher { resource.getDublinBusNews() }
                .parser { xml -> mapper.map(xml.channel!!.newsItems) }
                .memoryPolicy(midTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return RssNewsRepository(store)
    }

}
