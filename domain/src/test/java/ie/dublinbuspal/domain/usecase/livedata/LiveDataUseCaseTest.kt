package ie.dublinbuspal.domain.usecase.livedata

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.mapping.livedata.LiveDataMapper
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.domain.repository.livedata.LiveDataRepository
import ie.dublinbuspal.service.MockDublinBusApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class LiveDataUseCaseTest {

    private val useCase by lazy {

        val api = MockDublinBusApi()

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(30)
                .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                .build()

        val mapper = LiveDataMapper()
        val store = StoreBuilder.parsedWithKey<LiveDataRequestXml, LiveDataResponseXml, List<LiveData>>()
                .fetcher { key -> api.getLiveData(key) }
                .parser { json -> mapper.map(json.liveData) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        val repository = LiveDataRepository(store)

        return@lazy LiveDataUseCase(repository, object:Repository<StopService, StopServiceRequestXml> {

            override fun get(key: StopServiceRequestXml): Observable<StopService> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun fetch(key: StopServiceRequestXml): Observable<StopService> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    @Test
    fun testGetLiveData() {
        useCase.getLiveData("769")
                .doOnNext { Assert.assertTrue(8 == it.size) }
                .doOnError { Assert.fail() }
                .subscribe()
    }

}
