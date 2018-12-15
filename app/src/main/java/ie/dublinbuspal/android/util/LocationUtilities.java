package ie.dublinbuspal.android.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.model.stop.Stop;

public final class LocationUtilities {

    private static final double METRES_PER_MINUTE = 80 * 0.75;

    public static LatLng toLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static Location toLocation(LatLng latLng) {
        Location location = new Location("LocationUtilities");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

    public static Location getLocation(BusStop stop) {
        Location location = new Location("LocationUtilities");
        location.setLatitude(stop.getLatitude());
        location.setLongitude(stop.getLongitude());
        return location;
    }

    public static double distanceBetween(Location location, BusStop busStop) {
        Location busStopLocation = getLocation(busStop);
        return (double) location.distanceTo(busStopLocation);
    }

    public static String getWalkTime(Location location, BusStop busStop) {
        double distance = distanceBetween(location, busStop);
        int time = (int) (distance / METRES_PER_MINUTE);
        if (time <= 1) {
            return "1 min";
        } else if (time < 60) {
            return time + " min";
        }
        return ">1 hour walk";
    }

    public static String getWalkTime(double distance) {
        int time = (int) (distance / METRES_PER_MINUTE);
        if (time <= 1) {
            return "1 min walk";
        } else if (time < 60) {
            return time + " min walk";
        }
        return ">1 hour walk";
    }

    public static String getCoarseAddress(Stop busStop) {
        String address = busStop.name();
        if (address.contains(",")) {
            String[] split = address.split(",");
            return split[0];
        }
        return address;
    }

}
