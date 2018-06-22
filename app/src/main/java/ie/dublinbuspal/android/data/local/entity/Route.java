package ie.dublinbuspal.android.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ie.dublinbuspal.android.data.local.DbInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(tableName = DbInfo.ROUTES_TABLE)
public class Route {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DbInfo.ROUTE_ID)
    private final String routeId;

    @ColumnInfo(name = DbInfo.ROUTE_ORIGIN)
    private final String origin;

    @ColumnInfo(name = DbInfo.ROUTE_DESTINATION)
    private final String destination;

    public Route(String routeId, String origin, String destination) {
        this.routeId = routeId;
        this.origin = origin;
        this.destination = destination;
    }

    @NonNull
    public String getRouteId() {
        return routeId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof Route) {
            Route other = (Route) obj;
            return new EqualsBuilder()
                    .append(routeId, other.routeId)
                    .append(origin, other.origin)
                    .append(destination, other.destination)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(routeId)
                .append(origin)
                .append(destination)
                .toHashCode();
    }

}
