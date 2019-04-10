package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.RouteVariant
import ie.dublinbuspal.service.api.RtpiRoute
import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationWithVariantsJson
import ie.dublinbuspal.util.Operator
import ie.dublinbuspal.util.StringUtils
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class DublinBusRouteResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getRoutes(): Single<List<RtpiRoute>> {
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
                .map { response ->
                    response.routes
                            .filter {
                                it.id != null
                                        && it.origin != null
                                        && it.destination != null
                                        && it.id != "1C"
                            }
                            .map {
                                it.copy(
                                        id = it.id!!.trim(),
                                        origin = it.origin!!.trim(),
                                        destination = it.destination!!.trim()
                                )
                            }
                }
    }

    private fun fetchRtpiBusRoutes(): Single<List<RtpiRouteListInformationWithVariantsJson>> {
        return rtpiApi.routelistInformationWithVariants(RtpiApi.JSON)
                .map { response ->
                    response.results
                            .filter {
                                it.route != null
                                        && it.operator != null
                                        && (Operator.DUBLIN_BUS.code.equals(it.operator.trim(), ignoreCase = true)
                                        || Operator.GO_AHEAD.code.equals(it.operator.trim(), ignoreCase = true))
                            }
                            .map { it.copy(route = it.route!!.trim()) }
                }
    }

    private fun aggregate(defaultRoutes: List<DublinBusRouteXml>, rtpiRoutes: List<RtpiRouteListInformationWithVariantsJson>): List<RtpiRoute> {
        val aggregatedRoutes = mutableMapOf<String, RtpiRoute>()
        for (route in defaultRoutes) {
            val aggregatedRoute = aggregatedRoutes[route.id!!]
            if (aggregatedRoute == null) {
                aggregatedRoutes[route.id!!] = RtpiRoute(
                        routeId = route.id!!,
                        operatorId = Operator.DUBLIN_BUS.code,
                        variants = listOf(RouteVariant(origin = route.origin!!, destination = route.destination!!))
                )
            } else {
                //shouldn't happen
            }
        }
        for (route in rtpiRoutes) {
            val aggregatedRoute = aggregatedRoutes[route.route!!]
            if (aggregatedRoute == null) {
                aggregatedRoutes[route.route] = RtpiRoute(
                        routeId = route.route,
                        operatorId = route.operator!!,
                        variants = route.variants.map {
                            RouteVariant(
                                    origin = it.origin,
                                    destination = it.destination
                            )
                        }
                )
            } else {
                val newRoutes = aggregatedRoute.variants.toMutableList()
                newRoutes.addAll(
                        route.variants.map {
                            RouteVariant(
                                    origin = it.origin,
                                    destination = it.destination
                            )
                        }
                )
                aggregatedRoutes[route.route] = aggregatedRoute.copy(variants = newRoutes)
            }
        }
        return aggregatedRoutes.values.toList()
    }

}
