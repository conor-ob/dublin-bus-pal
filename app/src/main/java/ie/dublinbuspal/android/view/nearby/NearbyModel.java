package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

public interface NearbyModel {

    void setCurrentLocation(Location location);

    Location getLocation();

}
