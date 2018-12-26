package ie.dublinbuspal.repository

import ie.dublinbuspal.util.Coordinate
import io.reactivex.Completable
import io.reactivex.Observable

interface PreferencesRepository {

    fun saveLastLocation(location: Pair<Coordinate, Float>): Completable

    fun getLastLocation(): Observable<Pair<Coordinate, Float>>

}
