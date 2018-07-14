package ie.dublinbuspal.service.model.status

import org.simpleframework.xml.Element
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class ServiceStatusResponseXml(
        @field:Path("soap:Body/TestServiceResponse")
        @field:Element(name = "TestServiceResult") var result: String? = null
)
