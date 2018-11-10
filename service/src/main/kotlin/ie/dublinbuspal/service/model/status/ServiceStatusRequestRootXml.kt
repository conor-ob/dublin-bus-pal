package ie.dublinbuspal.service.model.status

import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "TestService", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
class ServiceStatusRequestRootXml
