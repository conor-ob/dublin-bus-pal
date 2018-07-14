package ie.dublinbuspal.service.model.status

import ie.dublinbuspal.base.AbstractDataClassTest
import ie.dublinbuspal.service.model.livedata.LiveDataXml

class LiveDataXmlTest : AbstractDataClassTest<LiveDataXml>() {

    override fun newInstance1(): LiveDataXml {
        return LiveDataXml("145", "Heuston Station via Donnybrook", "2018-07-11T22:46:23.02+01:00", "2018-07-11T22:56:51+01:00")
    }

    override fun newInstance2(): LiveDataXml {
        return LiveDataXml("46A", "Phoenix Pk via Donnybrook", "2018-07-11T22:46:23.02+01:00", "2018-07-11T23:07:49+01:00")
    }

}
