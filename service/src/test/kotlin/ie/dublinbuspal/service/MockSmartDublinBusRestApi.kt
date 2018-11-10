package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single

class MockSmartDublinBusRestApi : SmartDublinRestApi {

    override fun getStops(operator: String, format: String): Single<StopsResponseJson> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
