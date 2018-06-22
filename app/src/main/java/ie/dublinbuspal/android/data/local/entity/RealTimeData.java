package ie.dublinbuspal.android.data.local.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RealTimeData {

    private final String route;
    private final String destination;
    private final String expectedTime;
    private final String timestamp;

    public RealTimeData(String route, String destination, String expectedTime, String timestamp) {
        this.route = route;
        this.destination = destination;
        this.expectedTime = expectedTime;
        this.timestamp = timestamp;
    }

    public String getRoute() {
        return route;
    }

    public String getDestination() {
        return destination;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof RealTimeData) {
            RealTimeData other = (RealTimeData) obj;
            return new EqualsBuilder()
                    .append(route, other.route)
                    .append(destination, other.destination)
                    .append(expectedTime, other.expectedTime)
                    .append(timestamp, other.timestamp)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(route)
                .append(destination)
                .append(expectedTime)
                .append(timestamp)
                .toHashCode();
    }

}
