package ie.dublinbuspal.android.data.remote.rest.json.timetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimetableResult {

    @SerializedName("arrivaldatetime")
    @Expose
    private String arrivaldatetime;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("destinationlocalized")
    @Expose
    private String destinationlocalized;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("lowfloorstatus")
    @Expose
    private String lowfloorstatus;
    @SerializedName("route")
    @Expose
    private String route;

    public String getArrivaldatetime() {
        return arrivaldatetime;
    }

    public void setArrivaldatetime(String arrivaldatetime) {
        this.arrivaldatetime = arrivaldatetime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationlocalized() {
        return destinationlocalized;
    }

    public void setDestinationlocalized(String destinationlocalized) {
        this.destinationlocalized = destinationlocalized;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLowfloorstatus() {
        return lowfloorstatus;
    }

    public void setLowfloorstatus(String lowfloorstatus) {
        this.lowfloorstatus = lowfloorstatus;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
