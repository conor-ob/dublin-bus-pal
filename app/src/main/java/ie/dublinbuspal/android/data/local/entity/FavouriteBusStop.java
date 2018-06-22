package ie.dublinbuspal.android.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ie.dublinbuspal.android.data.local.DbInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Entity(tableName = DbInfo.FAVOURITES_TABLE)
public class FavouriteBusStop {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DbInfo.FAVOURITE_ID)
    private final String id;

    @ColumnInfo(name = DbInfo.FAVOURITE_NAME)
    private final String customName;

    @ColumnInfo(name = DbInfo.FAVOURITE_ROUTES)
    private final List<String> customRoutes;

    @ColumnInfo(name = DbInfo.FAVOURITE_ORDER)
    private final int order;

    public FavouriteBusStop(@NonNull String id, String customName, List<String> customRoutes,
                            int order) {
        this.id = id;
        this.customName = customName;
        this.customRoutes = customRoutes;
        this.order = order;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getCustomName() {
        return customName;
    }

    public List<String> getCustomRoutes() {
        return customRoutes;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof FavouriteBusStop) {
            FavouriteBusStop other = (FavouriteBusStop) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(customName, other.customName)
                    .append(customRoutes, other.customRoutes)
                    .append(order, other.order)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(customName)
                .append(customRoutes)
                .append(order)
                .toHashCode();
    }

}
