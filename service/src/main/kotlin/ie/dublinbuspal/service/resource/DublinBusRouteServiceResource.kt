package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRouteInformationJson
import ie.dublinbuspal.service.api.rtpi.RtpiRouteService
import ie.dublinbuspal.service.api.rtpi.RtpiRouteServiceVariant
import ie.dublinbuspal.util.Operator
import io.reactivex.Single

class DublinBusRouteServiceResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getRouteService(routeId: String, operator: Operator): Single<RtpiRouteService> {
        if (operator == Operator.DUBLIN_BUS) {
            return fetchDublinBusRouteServices(routeId).map { dublinBusRouteService ->
                val variants = mutableListOf<RtpiRouteServiceVariant>()
                if (dublinBusRouteService.inboundStopXmls.isNotEmpty()) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = dublinBusRouteService.dublinBusStopDataByRoute!!.origin!!,
                                    destination = dublinBusRouteService.dublinBusStopDataByRoute!!.destination!!,
                                    stopIds = dublinBusRouteService.inboundStopXmls.map { it.id!! }
                            )
                    )
                }
                if (dublinBusRouteService.outboundStopXmls.isNotEmpty()) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = dublinBusRouteService.dublinBusStopDataByRoute!!.origin!!,
                                    destination = dublinBusRouteService.dublinBusStopDataByRoute!!.destination!!,
                                    stopIds = dublinBusRouteService.outboundStopXmls.map { it.id!! }
                            )
                    )
                }
                RtpiRouteService(routeId, operator.code, variants)
            }
        } else if (operator == Operator.GO_AHEAD) {
            return fetchRtpiRouteServices(routeId, operator).map { rtpiRouteServices ->
                val variants = mutableListOf<RtpiRouteServiceVariant>()
                for (rtpiRouteService in rtpiRouteServices) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = rtpiRouteService.origin,
                                    destination = rtpiRouteService.destination,
                                    stopIds = rtpiRouteService.stops.map { it.displayId!! }
                            )
                    )
                }
                RtpiRouteService(routeId, operator.code, variants)
            }
        }
        throw IllegalStateException("Unable to get Route Service for route ID: '$routeId' and operator: '$operator'")
    }

    private fun fetchDublinBusRouteServices(routeId: String): Single<DublinBusStopDataByRouteResponseXml> {
        val requestRoot = DublinBusStopDataByRouteRequestRootXml(routeId)
        val requestBody = DublinBusStopDataByRouteRequestBodyXml(requestRoot)
        val request = DublinBusStopDataByRouteRequestXml(requestBody)
        return dublinBusApi.getStopDataByRoute(request)
    }

    private fun fetchRtpiRouteServices(routeId: String, operator: Operator): Single<List<RtpiRouteInformationJson>> {
        return rtpiApi.routeInformation(routeId, operator.code, RtpiApi.JSON).map { it.results }
    }

}
