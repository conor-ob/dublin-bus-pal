package ie.dublinbuspal.domain.di

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.BusStopDao
import ie.dublinbuspal.domain.mapping.BusStopDomainMapper
import ie.dublinbuspal.domain.mapping.BusStopEntityMapper
import ie.dublinbuspal.domain.mapping.livedata.LiveDataMapper
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stop.BusStop
import ie.dublinbuspal.domain.repository.livedata.LiveDataRepository
import ie.dublinbuspal.domain.repository.stop.BusStopPersister
import ie.dublinbuspal.domain.repository.stop.BusStopRepository
import ie.dublinbuspal.service.DublinBusApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.stop.BusStopsRequestXml
import ie.dublinbuspal.service.model.stop.BusStopsResponseXml
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun busStopRepository(store: StoreRoom<List<BusStop>, BusStopsRequestXml>): Repository<List<BusStop>, BusStopsRequestXml> {
        return BusStopRepository(store)
    }

    @Provides
    @Singleton
    fun busStopStore(api: DublinBusApi, dao: BusStopDao, txRunner: TxRunner): StoreRoom<List<BusStop>, BusStopsRequestXml> {
        val fetcher = Fetcher<BusStopsResponseXml, BusStopsRequestXml> { key -> api.getBusStops(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val domainMapper = BusStopDomainMapper()
        val entityMapper = BusStopEntityMapper()
        val persister = BusStopPersister(dao, txRunner, entityMapper, domainMapper)

        return StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)
    }

    @Provides
    @Singleton
    fun liveDataRepository(store: Store<List<LiveData>, LiveDataRequestXml>): Repository<List<LiveData>, LiveDataRequestXml> {
        return LiveDataRepository(store)
    }

    @Provides
    @Singleton
    fun liveDataStore(api: DublinBusApi): Store<List<LiveData>, LiveDataRequestXml> {
        val mapper = LiveDataMapper()
        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()
        return StoreBuilder.parsedWithKey<LiveDataRequestXml, LiveDataResponseXml, List<LiveData>>()
                .fetcher { key -> api.getLiveData(key) }
                .parser { json -> mapper.map(json.liveData!!) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()
    }

}
