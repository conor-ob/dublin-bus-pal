package ie.dublinbuspal.android.data.local.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ie.dublinbuspal.android.data.local.DbInfo;

@Entity(tableName = DbInfo.UNCHECKED_BUS_STOP_SERVICE_TABLE)
public class UncheckedBusStopService {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DbInfo.UNCHECKED_BUS_STOP_SERVICE_ID)
    private final String stopId;

    @ColumnInfo(name = DbInfo.UNCHECKED_BUS_STOP_SERVICE_ROUTES)
    private final List<String> routes;

    public UncheckedBusStopService(@NonNull String stopId, List<String> routes) {
        this.stopId = stopId;
        this.routes = routes;
    }

    @NonNull
    public String getStopId() {
        return stopId;
    }

    public List<String> getRoutes() {
        return routes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof UncheckedBusStopService) {
            UncheckedBusStopService other = (UncheckedBusStopService) obj;
            return new EqualsBuilder()
                    .append(stopId, other.stopId)
                    .append(routes, other.routes)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(stopId)
                .append(routes)
                .toHashCode();
    }

}
