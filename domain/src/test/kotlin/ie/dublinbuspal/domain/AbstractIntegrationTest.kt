package ie.dublinbuspal.domain

import ie.dublinbuspal.base.MockTxRunner
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.database.dao.MockStopDao
import ie.dublinbuspal.domain.di.RepositoryModule
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.service.MockDublinBusSoapApi
import ie.dublinbuspal.service.model.stop.StopsRequestXml

abstract class AbstractIntegrationTest {

    private val repositoryModule = RepositoryModule()
    private val soapApi = MockDublinBusSoapApi()
    private val stopDao = MockStopDao()
    private val txRunner = MockTxRunner()

    fun stopRepository(): Repository<List<Stop>, StopsRequestXml> {
        return repositoryModule.stopRepository(soapApi, stopDao, txRunner)
    }

}
