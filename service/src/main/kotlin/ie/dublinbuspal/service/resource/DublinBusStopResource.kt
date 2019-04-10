package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiBusStopInformationJson
import ie.dublinbuspal.util.Operator
import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class DublinBusStopResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getStops(): Single<List<RtpiBusStopInformationJson>> {
        return Single.zip(
                fetchDefaultDublinBusStops().subscribeOn(Schedulers.newThread()),
                fetchRtpiDublinBusStops().subscribeOn(Schedulers.newThread()),
                fetchRtpiGoAheadBusStops().subscribeOn(Schedulers.newThread()),
                Function3 { defaultStops, dublinBusStops, goAheadDublinStops -> aggregate(defaultStops, dublinBusStops, goAheadDublinStops) }
        )
    }

    private fun fetchDefaultDublinBusStops(): Single<List<DublinBusDestinationXml>> {
        val requestRoot = DublinBusDestinationRequestRootXml()
        val requestBody = DublinBusDestinationRequestBodyXml(requestRoot)
        val request = DublinBusDestinationRequestXml(requestBody)
        return dublinBusApi.getAllDestinations(request)
            .map { response -> response.stops
                .filter { it.id != null && it.name != null && it.latitude != null && it.longitude != null }
                .map { it.copy(id = it.id!!.trim(), name = it.name!!.trim(), latitude = it.latitude!!.trim(), longitude = it.longitude!!.trim()) }
            }
    }

    private fun fetchRtpiDublinBusStops(): Single<List<RtpiBusStopInformationJson>> {
        return rtpiApi.busStopInformation(Operator.DUBLIN_BUS.code, RtpiApi.JSON)
            .map { response -> response.results
                .filter { it.displayId != null && it.fullName != null && it.latitude != null && it.longitude != null }
                .map { it.copy(displayId = it.displayId!!.trim(), fullName = it.fullName!!.trim(), latitude = it.latitude!!.trim(), longitude = it.longitude!!.trim()) }
            }
    }

    private fun fetchRtpiGoAheadBusStops(): Single<List<RtpiBusStopInformationJson>> {
        return rtpiApi.busStopInformation(Operator.GO_AHEAD.code, RtpiApi.JSON)
            .map { response -> response.results
                .filter { it.displayId != null && it.fullName != null && it.latitude != null && it.longitude != null }
                .map { it.copy(displayId = it.displayId!!.trim(), fullName = it.fullName!!.trim(), latitude = it.latitude!!.trim(), longitude = it.longitude!!.trim()) }
            }
    }

    private fun aggregate(
        defaultStops: List<DublinBusDestinationXml>,
        dublinBusStops: List<RtpiBusStopInformationJson>,
        goAheadDublinStops: List<RtpiBusStopInformationJson>
    ): List<RtpiBusStopInformationJson> {
        val aggregatedStops = mutableMapOf<String, RtpiBusStopInformationJson>()
        for (stop in defaultStops) {
            var aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop == null) {
                aggregatedStops[stop.id!!] = RtpiBusStopInformationJson(
                    stopId = stop.id!!,
                    fullName = stop.name!!,
                    latitude = stop.latitude!!,
                    longitude = stop.longitude!!
                )
            } else {
                aggregatedStop = aggregatedStop.copy(
                    stopId = stop.id!!,
                    fullName = stop.name!!,
                    latitude = stop.latitude!!,
                    longitude = stop.longitude!!
                )
                aggregatedStops[stop.id!!] = aggregatedStop
            }
        }
        for (stop in dublinBusStops) {
            var aggregatedStop = aggregatedStops[stop.stopId]
            if (aggregatedStop == null) {
                aggregatedStops[stop.stopId!!] = stop
            } else {
                val existingOperators = aggregatedStop.operators.toMutableList()
                existingOperators.addAll(stop.operators)
                aggregatedStop = aggregatedStop.copy(operators = existingOperators)
                aggregatedStops[stop.stopId!!] = aggregatedStop
            }
        }
        for (stop in goAheadDublinStops) {
            var aggregatedStop = aggregatedStops[stop.stopId]
            if (aggregatedStop == null) {
                aggregatedStops[stop.stopId!!] = stop
            } else {
                val existingOperators = aggregatedStop.operators.toMutableList()
                existingOperators.addAll(stop.operators)
                aggregatedStop = aggregatedStop.copy(operators = existingOperators)
                aggregatedStops[stop.stopId!!] = aggregatedStop
            }
        }
        return aggregatedStops.values.toList()
    }

}
