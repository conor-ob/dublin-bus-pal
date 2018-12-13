package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.DublinBusGoAheadDublinRestApi
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationVariantJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single

class DublinBusGoAheadDublinRestResourceAdapter(
        private val api: DublinBusGoAheadDublinRestApi
) : DublinBusGoAheadDublinRestResource {

    private val dublinBus = "bac"
    private val goAheadDublin = "gad"
    private val defaultFormat = "json"

    override fun getDublinBusStops(): Single<StopsResponseJson> {
        return api.getStops(dublinBus, defaultFormat)
    }

    override fun getGoAheadDublinStops(): Single<StopsResponseJson> {
        return api.getStops(goAheadDublin, defaultFormat)
    }

    override fun getGoAheadDublinRoutes(): Single<RouteListInformationWithVariantsResponseJson> {
        return api.getRoutes(defaultFormat)
                .map { adaptGoAheadDublinRoutesResponse(it) }
    }

    private fun adaptGoAheadDublinRoutesResponse(response: RouteListInformationWithVariantsResponseJson): RouteListInformationWithVariantsResponseJson {
        val goAheadDublinRoutes = response.routes!!.filter { goAheadDublin == it.operator!!.toLowerCase().trim() }
        val uniques = mutableMapOf<String, List<RouteListInformationVariantJson>>()
        for (route in goAheadDublinRoutes) {
            val result = uniques[route.route]
            if (result == null) {
                uniques[route.route!!] = route.variants!!
            } else {
                val copy = result.toMutableList()
                copy.addAll(route.variants!!)
                uniques[route.route!!] = copy
            }
        }
        val adapted = uniques.map { RouteListInformationWithVariantsJson(goAheadDublin, it.key, it.value) }
        return response.copy(routes = adapted)
    }

    override fun getDublinBusLiveData(id: String): Single<RealTimeBusInformationResponseJson> {
        return api.getLiveData(id, dublinBus, defaultFormat)
    }

    override fun getGoAheadDublinLiveData(id: String): Single<RealTimeBusInformationResponseJson> {
        return api.getLiveData(id, goAheadDublin, defaultFormat)
    }

}
