package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.DublinBusGoAheadDublinRestApi
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationVariantJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.routeservice.RouteInformationResponseJson
import ie.dublinbuspal.service.model.stop.StopJson
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
                .map { adaptDublinBusGoAheadDublinStopsResponse(it) }
    }

    override fun getGoAheadDublinStops(): Single<StopsResponseJson> {
        return api.getStops(goAheadDublin, defaultFormat)
                .map { adaptDublinBusGoAheadDublinStopsResponse(it) }
    }

    override fun getGoAheadDublinRoutes(): Single<RouteListInformationWithVariantsResponseJson> {
        return api.getRoutes(defaultFormat)
                .map { adaptGoAheadDublinRoutesResponse(it) }
    }

    override fun getGoAheadDublinRouteService(id: String): Single<RouteInformationResponseJson> {
        return api.getRouteService(id, goAheadDublin, defaultFormat)
    }

    override fun getDublinBusLiveData(id: String): Single<RealTimeBusInformationResponseJson> {
        return api.getLiveData(id, dublinBus, defaultFormat)
    }

    override fun getGoAheadDublinLiveData(id: String): Single<RealTimeBusInformationResponseJson> {
        return api.getLiveData(id, goAheadDublin, defaultFormat)
    }

    private fun adaptDublinBusGoAheadDublinStopsResponse(response: StopsResponseJson): StopsResponseJson {
        val filtered = response.stops
                .filter { it.displayId != null && it.fullName != null && it.latitude != null && it.longitude != null }
                .map { it.copy(displayId = it.displayId!!.trim(), fullName = it.fullName!!.trim(), latitude = it.latitude!!.trim(), longitude = it.longitude!!.trim()) }
        return response.copy(stops = filtered)
    }

    private fun adaptGoAheadDublinRoutesResponse(response: RouteListInformationWithVariantsResponseJson): RouteListInformationWithVariantsResponseJson {
        val filtered = response.routes.filter { it.route != null && it.operator != null && (goAheadDublin == it.operator!!.toLowerCase().trim()) }
        val uniques = mutableMapOf<String, List<RouteListInformationVariantJson>>()
        for (route in filtered) {
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

}
