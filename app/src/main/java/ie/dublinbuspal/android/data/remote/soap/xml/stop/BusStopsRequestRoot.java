package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetAllDestinations", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class BusStopsRequestRoot {

    public BusStopsRequestRoot() {
        super();
    }

}
