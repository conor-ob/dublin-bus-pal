package ie.dublinbuspal.service.model.status

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class ServiceStatusRequestBodyXml(
        @field:Element(name = "TestService", required = false) val serviceStatusRequestRootXml: ServiceStatusRequestRootXml
)
