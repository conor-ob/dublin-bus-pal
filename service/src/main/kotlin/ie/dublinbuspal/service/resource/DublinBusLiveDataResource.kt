package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRealTimeBusInformationJson
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
                            destination = liveData.destination!!,
                            arrivalDateTime = liveData.expectedTimestamp!!,
                            timestampFormat = liveData.timestampFormat!!
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
                .map { response -> response.dublinBusRealTimeStopData
                        .map { it.copy(timestampFormat = "ISO") }
                }
    }

    private fun fetchRtpiLiveData(stopId: String): Single<List<RtpiRealTimeBusInformationJson>> {
        return rtpiApi.realTimeBusInformation(stopId, "gad", "json")
                .map { response -> response.results
                        .map { it.copy(timestampFormat = "dd/MM/yyyy HH:mm:ss") }
                }
    }

}
