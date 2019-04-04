package ie.dublinbuspal.service

import ie.rtpi.api.rtpi.RtpiBusStopInformationJson
import io.reactivex.Single

interface Client {

    fun getDublinBusStops(): Single<List<RtpiBusStopInformationJson>>

}
