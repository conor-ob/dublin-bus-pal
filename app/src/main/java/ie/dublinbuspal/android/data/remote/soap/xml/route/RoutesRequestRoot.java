package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetRoutes", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class RoutesRequestRoot {

    @Element(name = "filter", required = false)
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
