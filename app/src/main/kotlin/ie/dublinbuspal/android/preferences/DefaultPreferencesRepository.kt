package ie.dublinbuspal.android.preferences

import android.content.Context
import android.preference.PreferenceManager
import ie.dublinbuspal.android.R
import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Observable

class DefaultPreferencesRepository(context: Context) : PreferencesRepository {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val lastLocationLatitudeKey = context.getString(R.string.preference_key_last_location_latitude)
    private val lastLocationLongitudeKey = context.getString(R.string.preference_key_last_location_longitude)

    override fun saveLastLocation(coordinate: Coordinate): Completable {
        preferences.edit()
                .putFloat(lastLocationLatitudeKey, coordinate.x.toFloat())
                .putFloat(lastLocationLongitudeKey, coordinate.y.toFloat())
                .apply()
        return Completable.complete()
    }

    override fun getLastLocation(): Observable<Coordinate> {
        val latitude = preferences.getFloat(lastLocationLatitudeKey, 53.347261f)
        val longitude = preferences.getFloat(lastLocationLongitudeKey, -6.259102f)
        return Observable.just(Coordinate(latitude.toDouble(), longitude.toDouble()))
    }

}
