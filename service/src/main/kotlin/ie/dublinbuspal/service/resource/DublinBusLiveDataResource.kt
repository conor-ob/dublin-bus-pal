package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRealTimeBusInformationJson
import ie.dublinbuspal.util.Formatter
import ie.dublinbuspal.util.Operator
import ie.dublinbuspal.util.TimeUtils
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class DublinBusLiveDataResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getLiveData(stopId: String): Single<List<RtpiRealTimeBusInformationJson>> {
        return Single.zip(
                fetchDublinBusLiveData(stopId).subscribeOn(Schedulers.newThread()),
                fetchRtpiLiveData(stopId).subscribeOn(Schedulers.newThread()),
                BiFunction { dublinBusLiveData, rtpiLiveData ->
                    aggregate(dublinBusLiveData, rtpiLiveData)
                }
        )
    }

    private fun aggregate(
            dublinBusLiveData: List<DublinBusRealTimeStopDataXml>,
            rtpiLiveData: List<RtpiRealTimeBusInformationJson>
    ): List<RtpiRealTimeBusInformationJson> {
        val aggregated = mutableListOf<RtpiRealTimeBusInformationJson>()
        for (liveData in dublinBusLiveData) {
            aggregated.add(
                    RtpiRealTimeBusInformationJson(
                            route = liveData.routeId!!,
                            operator = Operator.DUBLIN_BUS.code,
                            destination = liveData.destination!!,
                            arrivalDateTime = liveData.expectedTimestamp!!
                    )
            )
        }
        aggregated.addAll(rtpiLiveData)
        return aggregated
    }

    private fun fetchDublinBusLiveData(stopId: String): Single<List<DublinBusRealTimeStopDataXml>> {
        val requestRoot = DublinBusRealTimeStopDataRequestRootXml(stopId, true.toString())
        val requestBody = DublinBusRealTimeStopDataRequestBodyXml(requestRoot)
        val request = DublinBusRealTimeStopDataRequestXml(requestBody)
        return dublinBusApi.getRealTimeStopData(request)
                .map { response -> response.dublinBusRealTimeStopData }
    }

    private fun fetchRtpiLiveData(stopId: String): Single<List<RtpiRealTimeBusInformationJson>> {
        return rtpiApi.realTimeBusInformation(stopId, Operator.GO_AHEAD.code, RtpiApi.JSON)
                .map { response -> response.results
                        .map {
                            it.copy(
                                    arrivalDateTime = toIso8601Timestamp(it.arrivalDateTime),
                                    departureDateTime = toIso8601Timestamp(it.departureDateTime),
                                    scheduledDepartureDateTime = toIso8601Timestamp(it.scheduledArrivalDateTime),
                                    scheduledArrivalDateTime = toIso8601Timestamp(it.scheduledArrivalDateTime),
                                    sourceTimestamp = toIso8601Timestamp(it.sourceTimestamp)
                            )
                        }
                }
    }

    private fun toIso8601Timestamp(timestamp: String?): String? {
        if (timestamp == null) {
            return null
        }
        return TimeUtils.toIso8601Timestamp(timestamp, Formatter.dateTime)
    }

}
