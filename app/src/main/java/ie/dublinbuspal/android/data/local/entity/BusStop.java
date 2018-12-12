package ie.dublinbuspal.android.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ie.dublinbuspal.android.data.local.DbInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(tableName = DbInfo.BUS_STOP_TABLE)
public class BusStop {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DbInfo.BUS_STOP_ID)
    private final String id;

    @ColumnInfo(name = DbInfo.BUS_STOP_NAME)
    private final String name;

    @ColumnInfo(name = DbInfo.BUS_STOP_LATITUDE)
    private final double latitude;

    @ColumnInfo(name = DbInfo.BUS_STOP_LONGITUDE)
    private final double longitude;

    public BusStop(@NonNull String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof BusStop) {
            BusStop other = (BusStop) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(name, other.name)
                    .append(latitude, other.latitude)
                    .append(longitude, other.longitude)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(latitude)
                .append(longitude)
                .toHashCode();
    }

}
