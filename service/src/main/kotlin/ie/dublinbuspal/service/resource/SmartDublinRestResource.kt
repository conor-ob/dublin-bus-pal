package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single

interface SmartDublinRestResource {

    fun getDublinBusStops(): Single<StopsResponseJson>

    fun getGoAheadDublinStops(): Single<StopsResponseJson>

    fun getGoAheadDublinRoutes(): Single<RouteListInformationWithVariantsResponseJson>

    fun getDublinBusLiveData(id: String): Single<RealTimeBusInformationResponseJson>

    fun getGoAheadDublinLiveData(id: String): Single<RealTimeBusInformationResponseJson>

}
