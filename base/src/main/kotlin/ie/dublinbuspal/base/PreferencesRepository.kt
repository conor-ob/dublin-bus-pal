package ie.dublinbuspal.base

import io.reactivex.Completable
import io.reactivex.Observable

interface PreferencesRepository {

    fun saveLastLocation(coordinate: Coordinate): Completable

    fun getLastLocation(): Observable<Coordinate>

}
