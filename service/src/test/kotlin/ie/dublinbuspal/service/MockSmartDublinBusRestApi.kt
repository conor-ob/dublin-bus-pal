package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.livedata.LiveDataResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single

class MockSmartDublinBusRestApi : SmartDublinRestApi {

    override fun getStops(operator: String, format: String): Single<StopsResponseJson> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveData(id: String, operator: String, format: String): Single<LiveDataResponseJson> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
