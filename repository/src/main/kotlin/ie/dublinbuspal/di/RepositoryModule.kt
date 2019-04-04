package ie.dublinbuspal.di

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.data.dao.FavouriteStopDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.resource.DublinBusRouteCacheResource
import ie.dublinbuspal.data.resource.DublinBusStopCacheResource
import ie.dublinbuspal.mapping.favourite.FavouriteStopDomainMapper
import ie.dublinbuspal.mapping.favourite.FavouriteStopEntityMapper
import ie.dublinbuspal.mapping.rss.RssMapper
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.DefaultFavouriteStopRepository
import ie.dublinbuspal.repository.route.DublinBusRoutePersister
import ie.dublinbuspal.repository.route.DublinBusRouteRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.DublinBusStopPersister
import ie.dublinbuspal.repository.stop.DublinBusStopRepository
import ie.dublinbuspal.repository.stop.StopRepository
import ie.dublinbuspal.service.api.rss.RssResponseXml
import ie.dublinbuspal.service.api.rtpi.RtpiBusStopInformationJson
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationWithVariantsJson
import ie.dublinbuspal.service.resource.DublinBusRouteResource
import ie.dublinbuspal.service.resource.DublinBusRssResource
import ie.dublinbuspal.service.resource.DublinBusStopResource
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
            dublinBusStopRepository: Repository<DublinBusStop>,
            favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
    ): Repository<Stop> {
        return StopRepository(dublinBusStopRepository, favouriteStopRepository)
    }

    @Provides
    @Singleton
    fun dublinBusStopRepository(
            dublinBusStopResource: DublinBusStopResource,
            cacheResource: DublinBusStopCacheResource,
            persisterDao: PersisterDao,
            internetManager: InternetManager
    ): Repository<DublinBusStop> {
        val fetcher = Fetcher<List<RtpiBusStopInformationJson>, String> { dublinBusStopResource.getStops() }
        val persister = DublinBusStopPersister(cacheResource, longTermMemoryPolicy, persisterDao, internetManager)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DublinBusStopRepository(store)
    }

    @Provides
    @Singleton
    fun routeRepository(
            dublinBusRouteResource: DublinBusRouteResource,
            cacheResource: DublinBusRouteCacheResource,
            persisterDao: PersisterDao,
            internetManager: InternetManager
    ): Repository<Route> {
        val fetcher = Fetcher<List<RtpiRouteListInformationWithVariantsJson>, String> { dublinBusRouteResource.getRoutes() }
        val persister = DublinBusRoutePersister(cacheResource, longTermMemoryPolicy, persisterDao, internetManager)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DublinBusRouteRepository(store)
    }

//    @Provides
//    @Singleton
//    fun defaultRouteRepository(
//            resource: DublinBusSoapResource,
//            dao: DefaultRouteDao,
//            persisterDao: PersisterDao,
//            txRunner: TxRunner,
//            internetManager: InternetManager
//    ): Repository<DefaultRoute> {
//        val fetcher = Fetcher<RoutesResponseXml, String> { resource.getDublinBusRoutes() }
//        val domainMapper = DefaultRouteDomainMapper()
//        val entityMapper = DefaultRouteEntityMapper()
//        val persister = DefaultRoutePersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
//        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
//        return DefaultRouteRepository(store)
//    }
//
//    @Provides
//    @Singleton
//    fun goAheadDublinRouteRepository(
//            resource: DublinBusGoAheadDublinRestResource,
//            dao: GoAheadDublinRouteDao,
//            persisterDao: PersisterDao,
//            txRunner: TxRunner,
//            internetManager: InternetManager
//    ): Repository<GoAheadDublinRoute> {
//        val fetcher = Fetcher<RouteListInformationWithVariantsResponseJson, String> { resource.getGoAheadDublinRoutes() }
//        val domainMapper = GoAheadDublinRouteDomainMapper()
//        val entityMapper = GoAheadDublinRouteEntityMapper()
//        val persister = GoAheadDublinRoutePersister(longTermMemoryPolicy, internetManager, persisterDao, dao, txRunner, entityMapper, domainMapper)
//        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
//        return GoAheadDublinRouteRepository(store)
//    }
//
//    @Provides
//    @Singleton
//    fun defaultRouteServiceRepository(
//            resource: DublinBusSoapResource
//    ): Repository<DefaultRouteService> {
//        val mapper = DefaultRouteServiceMapper()
//        val store = StoreBuilder.parsedWithKey<String, RouteServiceResponseXml, DefaultRouteService>()
//                .fetcher { key -> resource.getDublinBusRouteService(key) }
//                .parser { xml -> mapper.map(xml) }
//                .memoryPolicy(midTermMemoryPolicy)
//                .refreshOnStale()
//                .open()
//        return DefaultRouteServiceRepository(store)
//    }
//
//    @Provides
//    @Singleton
//    fun goAheadDublinRouteServiceRepository(
//            resource: DublinBusGoAheadDublinRestResource
//    ): Repository<GoAheadDublinRouteService> {
//        val mapper = GoAheadDublinRouteServiceMapper()
//        val store = StoreBuilder.parsedWithKey<String, RouteInformationResponseJson, GoAheadDublinRouteService>()
//                .fetcher { key -> resource.getGoAheadDublinRouteService(key) }
//                .parser { xml -> mapper.map(xml) }
//                .memoryPolicy(midTermMemoryPolicy)
//                .refreshOnStale()
//                .open()
//        return GoAheadDublinRouteServiceRepository(store)
//    }
//
//    @Provides
//    @Singleton
//    fun liveDataRepository(
//            defaultLiveDataRepository: Repository<RealTimeStopData>,
//            goAheadDublinLiveDataRepository: Repository<DublinBusGoAheadDublinLiveData>
//    ): Repository<LiveData> {
//        val defaultLiveDataMapper = DefaultLiveDataDomainMapper()
//        val goAheadDublinLiveDataMapper = DublinBusGoAheadDublinLiveDataDomainMapper()
//        return LiveDataRepository(defaultLiveDataRepository, goAheadDublinLiveDataRepository, defaultLiveDataMapper, goAheadDublinLiveDataMapper)
//    }
//
//    @Provides
//    @Singleton
//    fun defaultLiveDataRepository(resource: DublinBusSoapResource): Repository<RealTimeStopData> {
//        val mapper = DefaultLiveDataEntityMapper()
//        val store = StoreBuilder.parsedWithKey<String, LiveDataResponseXml, List<RealTimeStopData>>()
//                .fetcher { key -> resource.getDublinBusLiveData(key) }
//                .parser { xml -> mapper.map(xml.realTimeStopData) }
//                .memoryPolicy(shortTermMemoryPolicy)
//                .refreshOnStale()
//                .open()
//        return DefaultLiveDataRepository(store)
//    }
//
//    @Provides
//    @Singleton
//    fun goAheadDublinLiveDataRepository(resource: DublinBusGoAheadDublinRestResource): Repository<DublinBusGoAheadDublinLiveData> {
//        val mapper = DublinBusGoAheadDublinLiveDataEntityMapper()
//        val store = StoreBuilder.parsedWithKey<String, RealTimeBusInformationResponseJson, List<DublinBusGoAheadDublinLiveData>>()
//                .fetcher { key -> resource.getGoAheadDublinLiveData(key) }
//                .parser { json -> mapper.map(json.realTimeBusInformation) }
//                .memoryPolicy(shortTermMemoryPolicy)
//                .refreshOnStale()
//                .open()
//        return GoAheadDublinLiveDataRepository(store)
//    }

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
