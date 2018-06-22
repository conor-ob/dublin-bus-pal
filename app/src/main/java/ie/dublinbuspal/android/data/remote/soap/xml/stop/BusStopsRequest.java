package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace(prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope")
})
public class BusStopsRequest {

    @Element(name = "soap12:Body", required = false)
    private BusStopsRequestBody busStopsRequestBody;

    public BusStopsRequest() {
        super();
    }

    public BusStopsRequestBody getBusStopsRequestBody() {
        return busStopsRequestBody;
    }

    public void setBusStopsRequestBody(BusStopsRequestBody busStopsRequestBody) {
        this.busStopsRequestBody = busStopsRequestBody;
    }

}
