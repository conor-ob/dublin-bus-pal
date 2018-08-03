package ie.dublinbuspal.domain

import ie.dublinbuspal.base.MockTxRunner
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.database.dao.MockDetailedStopDao
import ie.dublinbuspal.database.dao.MockStopDao
import ie.dublinbuspal.database.dao.MockStopServiceDao
import ie.dublinbuspal.domain.di.RepositoryModule
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.service.MockDublinBusSoapApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml

abstract class AbstractIntegrationTest {

    private val repositoryModule = RepositoryModule()
    private val soapApi = MockDublinBusSoapApi()
    private val stopDao = MockStopDao()
    private val detailedStopDao = MockDetailedStopDao()
    private val stopServiceDao = MockStopServiceDao()
    private val txRunner = MockTxRunner()

    fun stopRepository(): Repository<List<Stop>, StopsRequestXml> {
        return repositoryModule.stopRepository(soapApi, stopDao, detailedStopDao, txRunner)
    }

    fun liveDataRepository(): Repository<List<LiveData>, LiveDataRequestXml> {
        return repositoryModule.liveDataRepository(soapApi)
    }

    fun stopServiceRepository(): Repository<StopService, StopServiceRequestXml> {
        return repositoryModule.stopServiceRepository(soapApi, stopServiceDao)
    }

}
