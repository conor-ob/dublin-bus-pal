package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationVariantJson
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationWithVariantsJson
import ie.dublinbuspal.util.StringUtils
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class DublinBusRouteResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getRoutes(): Single<List<RtpiRouteListInformationWithVariantsJson>> {
        return Single.zip(
                fetchDefaultDublinBusRoutes().subscribeOn(Schedulers.io()),
                fetchRtpiBusRoutes().subscribeOn(Schedulers.io()),
                BiFunction { defaultRoutes, rtpiRoutes -> aggregate(defaultRoutes, rtpiRoutes) }
        )
    }

    private fun fetchDefaultDublinBusRoutes(): Single<List<DublinBusRouteXml>> {
        val requestRoot = DublinBusRoutesRequestRootXml(StringUtils.EMPTY_STRING)
        val requestBody = DublinBusRoutesRequestBodyXml(requestRoot)
        val request = DublinBusRoutesRequestXml(requestBody)
        return dublinBusApi.getRoutes(request)
                .map { response -> response.routes
                        .filter { it.id != null && it.origin != null && it.destination != null && it.id != "1C" }
                        .map { it.copy(id = it.id!!.trim(), origin = it.origin!!.trim(), destination = it.destination!!.trim()) }
                }
    }

    private fun fetchRtpiBusRoutes(): Single<List<RtpiRouteListInformationWithVariantsJson>> {
        return rtpiApi.routelistInformationWithVariants("json")
                .map { response -> response.results
                        .filter { it.route != null && it.operator != null && ("bac" == it.operator.toLowerCase().trim() || "gad" == it.operator.toLowerCase().trim()) }
                        .map { it.copy(route = it.route!!.trim()) }
                }
    }

    private fun aggregate(defaultRoutes: List<DublinBusRouteXml>, rtpiRoutes: List<RtpiRouteListInformationWithVariantsJson>): List<RtpiRouteListInformationWithVariantsJson> {
        val aggregatedRoutes = mutableMapOf<String, RtpiRouteListInformationWithVariantsJson>()
        for (route in defaultRoutes) {
            val aggregatedRoute = aggregatedRoutes[route.id!!]
            if (aggregatedRoute == null) {
                aggregatedRoutes[route.id!!] = RtpiRouteListInformationWithVariantsJson(operator = "bac", route = route.id!!,
                        variants = listOf(RtpiRouteListInformationVariantJson(origin = route.origin!!, destination = route.destination!!)))
            } else {
                //shouldn't happen
            }
        }
        for (route in rtpiRoutes) {
            val aggregatedRoute = aggregatedRoutes[route.route!!]
            if (aggregatedRoute == null) {
                aggregatedRoutes[route.route] = route
            } else {
                val newRoutes = aggregatedRoute.variants.toMutableList()
                newRoutes.addAll(route.variants)
                aggregatedRoutes[route.route] = aggregatedRoute.copy(variants = newRoutes)
            }
        }
        return aggregatedRoutes.values.toList()
    }

}
