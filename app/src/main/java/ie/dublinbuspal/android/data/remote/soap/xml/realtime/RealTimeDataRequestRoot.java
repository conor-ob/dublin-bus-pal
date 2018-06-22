package ie.dublinbuspal.android.data.remote.soap.xml.realtime;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetRealTimeStopData", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class RealTimeDataRequestRoot {

    @Element(name = "stopId", required = false)
    private String stopId;

    @Element(name = "forceRefresh", required = false)
    private String forceRefresh;

    public String getStopId() {
        return stopId;
    }

    public String getForceRefresh() {
        return forceRefresh;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public void setForceRefresh(String forceRefresh) {
        this.forceRefresh = forceRefresh;
    }

}
