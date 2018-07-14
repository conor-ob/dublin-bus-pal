package ie.dublinbuspal.service.model.stop

import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetAllDestinations", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
class StopsRequestRootXml
