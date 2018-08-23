package ie.dublinbuspal.repository

import ie.dublinbuspal.data.MockTxRunner
import ie.dublinbuspal.data.dao.MockDetailedStopDao
import ie.dublinbuspal.data.dao.MockStopDao
import ie.dublinbuspal.data.dao.MockStopServiceDao
import ie.dublinbuspal.di.RepositoryModule
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.service.MockDublinBusSoapApi

abstract class AbstractIntegrationTest {

    private val repositoryModule = RepositoryModule()
    private val soapApi = MockDublinBusSoapApi()
    private val stopDao = MockStopDao()
    private val detailedStopDao = MockDetailedStopDao()
    private val stopServiceDao = MockStopServiceDao()
    private val txRunner = MockTxRunner()

    fun stopRepository(): Repository<List<Stop>, Any> {
        return repositoryModule.stopRepository(soapApi, stopDao, detailedStopDao, txRunner)
    }

    fun liveDataRepository(): Repository<List<LiveData>, String> {
        return repositoryModule.liveDataRepository(soapApi)
    }

    fun stopServiceRepository(): Repository<StopService, String> {
        return repositoryModule.stopServiceRepository(soapApi, stopServiceDao)
    }

}
