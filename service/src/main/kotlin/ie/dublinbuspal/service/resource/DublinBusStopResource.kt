package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.RtpiStop
import ie.dublinbuspal.service.api.StopService
import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiBusStopInformationJson
import ie.dublinbuspal.util.Operator
import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class DublinBusStopResource(
    private val dublinBusApi: DublinBusApi,
    private val rtpiApi: RtpiApi,
    private val rtpiFallbackApi: RtpiApi
) {

    fun getStops(): Single<List<RtpiStop>> {
        return Single.zip(
                fetchDefaultDublinBusStops().subscribeOn(Schedulers.newThread()),
                fetchRtpiDublinBusStops().subscribeOn(Schedulers.newThread()),
                fetchRtpiGoAheadBusStops().subscribeOn(Schedulers.newThread()),
                Function3 { defaultStops, dublinBusStops, goAheadDublinStops ->
                    aggregate(defaultStops, dublinBusStops, goAheadDublinStops)
                }
        )
    }

    private fun fetchDefaultDublinBusStops(): Single<List<DublinBusDestinationXml>> {
        val requestRoot = DublinBusDestinationRequestRootXml()
        val requestBody = DublinBusDestinationRequestBodyXml(requestRoot)
        val request = DublinBusDestinationRequestXml(requestBody)
        return dublinBusApi.getAllDestinations(request)
                .map { response ->
                    response.stops
                            .filter {
                                it.id != null
                                        && it.name != null
                                        && it.latitude != null
                                        && it.longitude != null
                            }
                            .map {
                                it.copy(
                                        id = it.id!!.trim(),
                                        name = it.name!!.trim(),
                                        latitude = it.latitude!!.trim(),
                                        longitude = it.longitude!!.trim()
                                )
                            }
                }
    }

    private fun fetchRtpiDublinBusStops(): Single<List<RtpiBusStopInformationJson>> {
        return rtpiApi.busStopInformation(Operator.DUBLIN_BUS.code, RtpiApi.JSON)
            .onErrorResumeNext { rtpiFallbackApi.busStopInformation(Operator.DUBLIN_BUS.code, RtpiApi.JSON) }
                .map { response ->
                    response.results
                            .filter {
                                it.displayId != null
                                        && it.fullName != null
                                        && it.latitude != null
                                        && it.longitude != null
                            }
                            .map {
                                it.copy(
                                        displayId = it.displayId!!.trim(),
                                        fullName = it.fullName!!.trim(),
                                        latitude = it.latitude!!.trim(),
                                        longitude = it.longitude!!.trim(),
                                        operators = it.operators.map { operator ->
                                            operator.copy(
                                                    name = operator.name!!.trim().toUpperCase(),
                                                    routes = operator.routes.map { route -> route.trim() }
                                            )
                                        }
                                )
                            }
                }
    }

    private fun fetchRtpiGoAheadBusStops(): Single<List<RtpiBusStopInformationJson>> {
        return rtpiApi.busStopInformation(Operator.GO_AHEAD.code, RtpiApi.JSON)
                .map { response ->
                    response.results
                            .filter {
                                it.displayId != null
                                        && it.fullName != null
                                        && it.latitude != null
                                        && it.longitude != null
                            }
                            .map {
                                it.copy(
                                        displayId = it.displayId!!.trim(),
                                        fullName = it.fullName!!.trim(),
                                        latitude = it.latitude!!.trim(),
                                        longitude = it.longitude!!.trim(),
                                        operators = it.operators.map { operator ->
                                            operator.copy(
                                                    name = operator.name!!.trim().toUpperCase(),
                                                    routes = operator.routes.map { route -> route.trim() }
                                            )
                                        }
                                )
                            }
                }
    }

    private fun aggregate(
            defaultStops: List<DublinBusDestinationXml>,
            dublinBusStops: List<RtpiBusStopInformationJson>,
            goAheadDublinStops: List<RtpiBusStopInformationJson>
    ): List<RtpiStop> {
        val aggregatedStops = mutableMapOf<String, RtpiStop>()
        for (stop in defaultStops) {
            var aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop == null) {
                aggregatedStops[stop.id!!] = RtpiStop(
                        id = stop.id!!,
                        name = stop.name!!,
                        latitude = stop.latitude!!,
                        longitude = stop.longitude!!,
                        stopServices = emptyList()
                )
            } else {
                aggregatedStop = aggregatedStop.copy(
                        id = stop.id!!,
                        name = stop.name!!,
                        latitude = stop.latitude!!,
                        longitude = stop.longitude!!,
                        stopServices = emptyList()
                )
                aggregatedStops[stop.id!!] = aggregatedStop
            }
        }
        for (stop in dublinBusStops) {
            var aggregatedStop = aggregatedStops[stop.stopId]
            if (aggregatedStop == null) {
                aggregatedStops[stop.stopId!!] = RtpiStop(
                        id = stop.displayId!!,
                        name = stop.fullName!!,
                        latitude = stop.latitude!!,
                        longitude = stop.longitude!!,
                        stopServices = stop.operators.map {
                            StopService(
                                    operatorId = it.name!!,
                                    routeIds = it.routes
                            )
                        }
                )
            } else {
                val existingOperators = aggregatedStop.stopServices.toMutableList()
                existingOperators.addAll(stop.operators.map {
                    StopService(
                            operatorId = it.name!!,
                            routeIds = it.routes
                    )
                }
                )
                aggregatedStop = aggregatedStop.copy(stopServices = existingOperators)
                aggregatedStops[stop.stopId!!] = aggregatedStop
            }
        }
        for (stop in goAheadDublinStops) {
            var aggregatedStop = aggregatedStops[stop.stopId]
            if (aggregatedStop == null) {
                aggregatedStops[stop.stopId!!] = RtpiStop(
                        id = stop.displayId!!,
                        name = stop.fullName!!,
                        latitude = stop.latitude!!,
                        longitude = stop.longitude!!,
                        stopServices = stop.operators.map {
                            StopService(
                                    operatorId = it.name!!,
                                    routeIds = it.routes
                            )
                        }
                )
            } else {
                val existingOperators = aggregatedStop.stopServices.toMutableList()
                existingOperators.addAll(stop.operators.map {
                    StopService(
                            operatorId = it.name!!,
                            routeIds = it.routes
                    )
                }
                )
                aggregatedStop = aggregatedStop.copy(stopServices = existingOperators)
                aggregatedStops[stop.stopId!!] = aggregatedStop
            }
        }
        return aggregatedStops.values.toList()
    }

}
