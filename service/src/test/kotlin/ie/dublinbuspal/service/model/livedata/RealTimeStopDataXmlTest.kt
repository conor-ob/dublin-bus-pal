package ie.dublinbuspal.service.model.livedata

import ie.dublinbuspal.AbstractDataClassTest

class RealTimeStopDataXmlTest : AbstractDataClassTest<RealTimeStopDataDataXml>() {

    override fun newInstance1(): RealTimeStopDataDataXml {
        return RealTimeStopDataDataXml("145", "Heuston Station via Donnybrook", "2018-07-11T22:46:23.02+01:00", "2018-07-11T22:56:51+01:00")
    }

    override fun newInstance2(): RealTimeStopDataDataXml {
        return RealTimeStopDataDataXml("46A", "Phoenix Pk via Donnybrook", "2018-07-11T22:46:23.02+01:00", "2018-07-11T23:07:49+01:00")
    }

}
