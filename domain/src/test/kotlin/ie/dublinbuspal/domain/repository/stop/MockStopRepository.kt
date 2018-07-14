package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.MockTxRunner
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.database.dao.MockStopDao
import ie.dublinbuspal.domain.mapping.stop.StopDomainMapper
import ie.dublinbuspal.domain.mapping.stop.StopEntityMapper
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.service.MockDublinBusApi
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MockStopRepository : Repository<List<Stop>, StopsRequestXml> {

    private val store by lazy { buildStore() }

    override fun get(key: StopsRequestXml): Observable<List<Stop>> {
        return store.get(key)
    }

    override fun fetch(key: StopsRequestXml): Observable<List<Stop>> {
        return store.fetch(key)
    }

    private fun buildStore(): StoreRoom<List<Stop>, StopsRequestXml> {
        val api = MockDublinBusApi()

        val fetcher = Fetcher<StopsResponseXml, StopsRequestXml> { key -> api.getBusStops(key) }

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val dao = MockStopDao()
        val txRunner = MockTxRunner()

        val domainMapper = StopDomainMapper()
        val entityMapper = StopEntityMapper()
        val persister = StopPersister(dao, txRunner, entityMapper, domainMapper)
        return StoreRoom.from(fetcher, persister, StalePolicy.REFRESH_ON_STALE, memoryPolicy)
    }

}
