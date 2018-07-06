package ie.dublinbuspal.domain.di

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.BusStopDao
import ie.dublinbuspal.domain.mapping.BusStopDomainMapper
import ie.dublinbuspal.domain.mapping.BusStopEntityMapper
import ie.dublinbuspal.domain.model.BusStop
import ie.dublinbuspal.domain.repository.BusStopPersister
import ie.dublinbuspal.domain.repository.BusStopRepository
import ie.dublinbuspal.service.DublinBusApi
import ie.dublinbuspal.service.busstops.BusStopsRequestXml
import ie.dublinbuspal.service.busstops.BusStopsResponseXml
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

}
