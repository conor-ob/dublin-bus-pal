
package ie.dublinbuspal.android.data.remote.rest.json.stop;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operator {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("routes")
    @Expose
    private List<String> routes = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

}
