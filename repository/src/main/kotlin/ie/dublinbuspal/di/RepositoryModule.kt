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
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.KeyedRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.repository.favourite.DefaultFavouriteStopRepository
import ie.dublinbuspal.repository.livedata.DublinBusLiveDataMapper
import ie.dublinbuspal.repository.livedata.LiveDataRepository
import ie.dublinbuspal.repository.route.DublinBusRoutePersister
import ie.dublinbuspal.repository.route.DublinBusRouteRepository
import ie.dublinbuspal.repository.routeservice.DublinBusRouteServiceRepository
import ie.dublinbuspal.repository.routeservice.RouteServiceRepository
import ie.dublinbuspal.repository.rss.RssNewsRepository
import ie.dublinbuspal.repository.stop.DublinBusStopPersister
import ie.dublinbuspal.repository.stop.DublinBusStopRepository
import ie.dublinbuspal.repository.stop.StopRepository
import ie.dublinbuspal.service.api.RtpiLiveData
import ie.dublinbuspal.service.api.RtpiRoute
import ie.dublinbuspal.service.api.RtpiRouteService
import ie.dublinbuspal.service.api.RtpiStop
import ie.dublinbuspal.service.api.rss.RssResponseXml
import ie.dublinbuspal.service.resource.*
import ie.dublinbuspal.util.InternetManager
import ie.dublinbuspal.util.Operator
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {

    private val shortTermMemoryPolicy: MemoryPolicy by lazy { newMemoryPolicy(60, TimeUnit.SECONDS) }
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
        val fetcher = Fetcher<List<RtpiStop>, String> { dublinBusStopResource.getStops() }
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
        val fetcher = Fetcher<List<RtpiRoute>, String> { dublinBusRouteResource.getRoutes() }
        val persister = DublinBusRoutePersister(cacheResource, longTermMemoryPolicy, persisterDao, internetManager)
        val store = StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, longTermMemoryPolicy)
        return DublinBusRouteRepository(store)
    }

    @Provides
    @Singleton
    fun liveDataRepository(
            liveDataResource: DublinBusLiveDataResource
    ): Repository<LiveData> {
        val store = StoreBuilder.parsedWithKey<String, List<RtpiLiveData>, List<LiveData>>()
                .fetcher { key -> liveDataResource.getLiveData(key) }
                .parser { xml -> DublinBusLiveDataMapper.map(xml) }
                .memoryPolicy(shortTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return LiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun routeServiceRepository(
            dublinBusRouteServiceRepository: KeyedRepository<Pair<String, String>, RtpiRouteService>,
            stopRepository: Repository<Stop>
    ): KeyedRepository<Pair<String, String>, RouteService> {
        return RouteServiceRepository(dublinBusRouteServiceRepository, stopRepository)
    }

    @Provides
    @Singleton
    fun dublinBusRouteServiceRepository(
            routeServiceResource: DublinBusRouteServiceResource
    ): KeyedRepository<Pair<String, String>, RtpiRouteService> {
        val store = StoreBuilder.key<Pair<String, String>, RtpiRouteService>()
                .fetcher { key -> routeServiceResource.getRouteService(key.first, key.second) }
                .memoryPolicy(shortTermMemoryPolicy)
                .refreshOnStale()
                .open()
        return DublinBusRouteServiceRepository(store)
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
