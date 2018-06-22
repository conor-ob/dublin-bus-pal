package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Destination", strict = false)
public class BusStopXml {

    @Element(name = "StopNumber", required = false)
    private String id;

    @Element(name = "Description", required = false)
    private String address;

    @Element(name = "Latitude", required = false)
    private String latitude;

    @Element(name = "Longitude", required = false)
    private String longitude;

    public BusStopXml(){
        super();
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
