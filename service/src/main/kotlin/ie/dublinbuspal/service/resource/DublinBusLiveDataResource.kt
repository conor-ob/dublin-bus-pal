package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.dublinbus.DublinBusApi
import ie.dublinbuspal.service.api.rtpi.RtpiApi

class DublinBusLiveDataResource(
        private val dublinBusApi: DublinBusApi,
        private val rtpiApi: RtpiApi
) {

    fun getLiveData(stopId: String) {

    }

}
