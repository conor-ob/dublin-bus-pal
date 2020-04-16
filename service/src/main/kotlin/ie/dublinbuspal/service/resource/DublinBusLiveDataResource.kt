package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.RtpiLiveData
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
    private val rtpiApi: RtpiApi,
    private val rtpiFallbackApi: RtpiApi
) {

    fun getLiveData(stopId: String): Single<List<RtpiLiveData>> {
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
    ): List<RtpiLiveData> {
        val aggregated = mutableListOf<RtpiLiveData>()
        for (liveData in dublinBusLiveData) {
            aggregated.add(
                    RtpiLiveData(
                            routeId = liveData.routeId!!,
                            operatorId = Operator.DUBLIN_BUS.code,
                            destination = liveData.destination!!,
                            expectedTimestamp = liveData.expectedTimestamp!!,
                            minutes = minutesBetweenTimestamps(liveData.expectedTimestamp!!, liveData.responseTimestamp!!)
                    )
            )
        }
        for (liveData in rtpiLiveData) {
            aggregated.add(
                    RtpiLiveData(
                            routeId = liveData.route!!,
                            operatorId = Operator.GO_AHEAD.code,
                            destination = liveData.destination!!,
                            expectedTimestamp = liveData.arrivalDateTime!!,
                            minutes = parseDueTime(liveData.dueTime!!)
                    )
            )
        }
        return aggregated
    }

    private fun fetchDublinBusLiveData(stopId: String): Single<List<DublinBusRealTimeStopDataXml>> {
        val requestRoot = DublinBusRealTimeStopDataRequestRootXml(stopId, true.toString())
        val requestBody = DublinBusRealTimeStopDataRequestBodyXml(requestRoot)
        val request = DublinBusRealTimeStopDataRequestXml(requestBody)
        return dublinBusApi.getRealTimeStopData(request)
                .map { response ->
                    response.dublinBusRealTimeStopData
                            .filter {
                                it.routeId != null
                                        && it.destination != null
                                        && it.expectedTimestamp != null
                            }
                            .map {
                                it.copy(
                                        routeId = it.routeId!!.trim(),
                                        destination = it.destination!!.trim(),
                                        expectedTimestamp = it.expectedTimestamp!!.trim()
                                )
                            }
                }
    }

    private fun fetchRtpiLiveData(stopId: String): Single<List<RtpiRealTimeBusInformationJson>> {
        return rtpiApi.realTimeBusInformation(stopId, Operator.GO_AHEAD.code, RtpiApi.JSON)
            .onErrorResumeNext { rtpiFallbackApi.realTimeBusInformation(stopId, Operator.GO_AHEAD.code, RtpiApi.JSON) }
                .map { response ->
                    response.results
                            .filter {
                                it.route != null
                                        && it.destination != null
                                        && it.operator != null
                                        && it.arrivalDateTime != null
                            }
                            .map {
                                it.copy(
                                        route = it.route!!.trim(),
                                        operator = it.operator!!.trim().toUpperCase(),
                                        destination = it.destination!!.trim(),
                                        arrivalDateTime = toIso8601Timestamp(it.arrivalDateTime)
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

    private fun parseDueTime(dueTime: String): Long {
        if ("Due".equals(dueTime, ignoreCase = true)) {
            return 0L
        }
        try {
            return dueTime.toLong()
        } catch (e: NumberFormatException) {
            // safety
        }
        return 0L
    }

    private fun minutesBetweenTimestamps(expectedTimestamp: String, responseTimestamp: String): Long {
        val responseInstant = TimeUtils.dateTimeStampToInstant(responseTimestamp, Formatter.isoDateTime)
        val expectedInstant = TimeUtils.dateTimeStampToInstant(expectedTimestamp, Formatter.isoDateTime)
        return TimeUtils.minutesBetween(responseInstant, expectedInstant)
    }

}
