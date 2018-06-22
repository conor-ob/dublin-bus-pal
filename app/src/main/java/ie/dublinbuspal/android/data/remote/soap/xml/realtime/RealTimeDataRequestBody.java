package ie.dublinbuspal.android.data.remote.soap.xml.realtime;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class RealTimeDataRequestBody {

    @Element(name = "GetRealTimeStopData",required = false)
    private RealTimeDataRequestRoot realTimeDataRequestRoot;

    public RealTimeDataRequestRoot getRealTimeDataRequestRoot() {
        return realTimeDataRequestRoot;
    }

    public void setRealTimeDataRequestRoot(RealTimeDataRequestRoot realTimeDataRequestRoot) {
        this.realTimeDataRequestRoot = realTimeDataRequestRoot;
    }

}
