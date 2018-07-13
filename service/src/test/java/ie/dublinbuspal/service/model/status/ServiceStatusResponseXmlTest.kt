package ie.dublinbuspal.service.model.status

import ie.dublinbuspal.base.AbstractDataClassTest

class ServiceStatusResponseXmlTest : AbstractDataClassTest<ServiceStatusResponseXml>() {

    override fun newInstance1(): ServiceStatusResponseXml {
        return ServiceStatusResponseXml("OK")
    }

    override fun newInstance2(): ServiceStatusResponseXml {
        return ServiceStatusResponseXml("ERROR")
    }

}
