package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

public class NearbyModelImpl implements NearbyModel {

    private Location currentLocation;

    public NearbyModelImpl() {
        super();
    }

    @Override
    public void setCurrentLocation(Location location) {
        currentLocation = location;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

}
