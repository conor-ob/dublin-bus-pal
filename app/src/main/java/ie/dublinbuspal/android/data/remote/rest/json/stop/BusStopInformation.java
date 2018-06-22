
package ie.dublinbuspal.android.data.remote.rest.json.stop;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusStopInformation {

    @SerializedName("errorcode")
    @Expose
    private String errorcode;
    @SerializedName("errormessage")
    @Expose
    private String errormessage;
    @SerializedName("numberofresults")
    @Expose
    private Integer numberofresults;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("results")
    @Expose
    private List<BusStopResult> busStopResults = null;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public Integer getNumberofresults() {
        return numberofresults;
    }

    public void setNumberofresults(Integer numberofresults) {
        this.numberofresults = numberofresults;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<BusStopResult> getBusStopResults() {
        return busStopResults;
    }

    public void setBusStopResults(List<BusStopResult> busStopResults) {
        this.busStopResults = busStopResults;
    }

}
