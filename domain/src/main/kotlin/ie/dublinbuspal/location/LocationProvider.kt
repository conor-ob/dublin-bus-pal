package ie.dublinbuspal.location

import ie.dublinbuspal.util.Coordinate
import io.reactivex.Observable

interface LocationProvider {

    fun getLastKnownLocation(): Observable<Coordinate>

    fun getLocationUpdates(): Observable<Coordinate>

}
