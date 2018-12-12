package ie.dublinbuspal.android.data.local.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import ie.dublinbuspal.android.util.CollectionUtilities;

public class DetailedBusStop extends BusStop {

    private final List<String> routes;
    private final List<String> uncheckedRoutes;
    private final List<String> customRoutes;
    private final String customName;

    public DetailedBusStop(@NonNull String id, String name, double latitude, double longitude,
                           List<String> routes, List<String> uncheckedRoutes,
                           List<String> customRoutes, String customName) {
        super(id, name, latitude, longitude);
        this.routes = routes;
        this.uncheckedRoutes = uncheckedRoutes;
        this.customRoutes = customRoutes;
        this.customName = customName;
    }

    @Override
    public String getName() {
        return isFavourite() ? customName : super.getName();
    }

    public String getRealName() {
        return super.getName();
    }

    public List<String> getRoutes() {
        List<String> ret = customRoutes;
        if (CollectionUtilities.isNullOrEmpty(ret)) {
            ret = routes;
            if (CollectionUtilities.isNullOrEmpty(ret)) {
                ret = uncheckedRoutes;
            }
        }
        return ret;
    }

    public List<String> getUncheckedRoutes() {
        return uncheckedRoutes;
    }

    public List<String> getCustomRoutes() {
        return customRoutes;
    }

    public String getCustomName() {
        return customName;
    }

    public boolean isFavourite() {
        return customName != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof DetailedBusStop) {
            DetailedBusStop other = (DetailedBusStop) obj;
            return new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(routes, other.routes)
                    .append(customName, other.customName)
                    .append(customRoutes, other.customRoutes)
                    .append(uncheckedRoutes, other.uncheckedRoutes)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(super.hashCode())
                .append(routes)
                .append(customName)
                .append(customRoutes)
                .append(uncheckedRoutes)
                .toHashCode();
    }

}
