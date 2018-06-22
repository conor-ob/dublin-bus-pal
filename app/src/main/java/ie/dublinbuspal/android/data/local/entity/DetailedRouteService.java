package ie.dublinbuspal.android.data.local.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class DetailedRouteService {

    private final RouteService routeService;
    private final List<DetailedBusStop> inboundStops;
    private final List<DetailedBusStop> outboundStops;

    public DetailedRouteService(RouteService routeService,
                                List<DetailedBusStop> inboundStops,
                                List<DetailedBusStop> outboundStops) {
        this.routeService = routeService;
        this.inboundStops = inboundStops;
        this.outboundStops = outboundStops;
    }

    public RouteService getRouteService() {
        return routeService;
    }

    public List<DetailedBusStop> getInboundBusStops() {
        return inboundStops;
    }

    public List<DetailedBusStop> getOutboundBusStops() {
        return outboundStops;
    }

    public String getOrigin() {
        return routeService.getRouteOrigin();
    }

    public String getDestination() {
        return routeService.getRouteDestination();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof DetailedRouteService) {
            DetailedRouteService other = (DetailedRouteService) obj;
            return new EqualsBuilder()
                    .append(routeService, other.routeService)
                    .append(inboundStops, other.inboundStops)
                    .append(outboundStops, other.outboundStops)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(routeService)
                .append(inboundStops)
                .append(outboundStops)
                .toHashCode();
    }

}
