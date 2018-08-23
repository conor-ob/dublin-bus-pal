package ie.dublinbuspal.service.model.status

import ie.dublinbuspal.AbstractDataClassTest
import ie.dublinbuspal.service.util.XmlUtils
import org.junit.Test

class ServiceStatusResponseXmlTest : AbstractDataClassTest<ServiceStatusResponseXml>() {

    override fun newInstance1(): ServiceStatusResponseXml {
        return ServiceStatusResponseXml("OK")
    }

    override fun newInstance2(): ServiceStatusResponseXml {
        return ServiceStatusResponseXml("ERROR")
    }

    @Test
    fun `xml should be deserialized correctly`() {
        XmlUtils.resolveXml("service_status_response.xml", ServiceStatusResponseXml::class.java)
    }

}
