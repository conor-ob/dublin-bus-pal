package ie.dublinbuspal.service

import ie.rtpi.api.rtpi.RtpiBusStopInformationJson
import ie.rtpi.dublinbus.DublinBus
import io.reactivex.Single

class ClientImpl(private val dublinBus: DublinBus) : Client {

    override fun getDublinBusStops(): Single<List<RtpiBusStopInformationJson>> {
        return Single.fromObservable { dublinBus.getStops() }
    }

}
