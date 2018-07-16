package ie.dublinbuspal.base

import io.reactivex.Completable
import io.reactivex.Observable

interface PreferencesRepository {

    fun saveLastLocation(location: Pair<Coordinate, Float>): Completable

    fun getLastLocation(): Observable<Pair<Coordinate, Float>>

}
