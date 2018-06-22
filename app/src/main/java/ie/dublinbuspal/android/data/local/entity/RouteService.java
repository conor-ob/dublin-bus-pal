package ie.dublinbuspal.android.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ie.dublinbuspal.android.data.local.DbInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Entity(tableName = DbInfo.ROUTE_SERVICE_TABLE)
public class RouteService {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_ID)
    private final String routeId;

    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_NAME)
    private final String routeName;

    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_ORIGIN)
    private final String routeOrigin;

    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_DESTINATION)
    private final String routeDestination;

    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_INBOUND_STOPS)
    private final List<String> inboundStopIds;

    @ColumnInfo(name = DbInfo.ROUTE_SERVICE_OUTBOUND_STOPS)
    private final List<String> outboundStopIds;

    public RouteService(@NonNull String routeId, String routeName, String routeOrigin,
                        String routeDestination, List<String> inboundStopIds,
                        List<String> outboundStopIds) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.routeOrigin = routeOrigin;
        this.routeDestination = routeDestination;
        this.inboundStopIds = inboundStopIds;
        this.outboundStopIds = outboundStopIds;
    }

    @NonNull
    public String getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getRouteOrigin() {
        return routeOrigin;
    }

    public String getRouteDestination() {
        return routeDestination;
    }

    public List<String> getInboundStopIds() {
        return inboundStopIds;
    }

    public List<String> getOutboundStopIds() {
        return outboundStopIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof RouteService) {
            RouteService other = (RouteService) obj;
            return new EqualsBuilder()
                    .append(routeId, other.routeId)
                    .append(routeName, other.routeName)
                    .append(routeOrigin, other.routeOrigin)
                    .append(routeDestination, other.routeDestination)
                    .append(inboundStopIds, other.inboundStopIds)
                    .append(outboundStopIds, other.outboundStopIds)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(routeId)
                .append(routeName)
                .append(routeOrigin)
                .append(routeDestination)
                .append(inboundStopIds)
                .append(outboundStopIds)
                .toHashCode();
    }

}
