package ie.dublinbuspal.android.data.remote.soap.xml.realtime;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class RealTimeDataResponse {

    @Path("soap:Body/GetRealTimeStopDataResponse/GetRealTimeStopDataResult/diffgr:diffgram/DocumentElement")
    @ElementList(inline = true)
    private List<RealTimeDataXml> realTimeData;

    public RealTimeDataResponse() {
        super();
    }

    public List<RealTimeDataXml> getRealTimeData() {
        return realTimeData;
    }

    public void setRealTimeData(List<RealTimeDataXml> realTimeData) {
        this.realTimeData = realTimeData;
    }

}
