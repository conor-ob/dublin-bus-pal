package ie.dublinbuspal.usecase.livedata

import org.junit.Assert
import org.junit.Test

class LiveDataUseCaseTest : AbstractIntegrationTest() {

    private val useCase = LiveDataUseCase(liveDataRepository(), stopServiceRepository())

    @Test
    fun `assert 8 live data items returned`() {
        useCase.getLiveData("769")
                .doOnNext { Assert.assertTrue(8 == it.size) }
                .doOnError { Assert.fail() }
                .subscribe()
    }

    @Test
    fun `get condensed live data`() {
        useCase.getCondensedLiveData("769")
                .test()
                .assertComplete()
                .assertResult()
    }

}
