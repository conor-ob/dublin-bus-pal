package ie.dublinbuspal.domain.repository.stop

import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import org.junit.Test

class StopRepositoryTest {

    private val repository by lazy { MockStopRepository() }

    @Test
    fun testGet() {
        repository.get(StopsRequestXml(StopsRequestBodyXml(StopsRequestRootXml())))
                .doOnNext { print(it.toString()) }
                .subscribe()
    }

}
