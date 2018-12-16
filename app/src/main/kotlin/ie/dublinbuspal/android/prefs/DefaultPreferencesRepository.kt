package ie.dublinbuspal.android.prefs

//class DefaultPreferencesRepository(context: Context) : PreferencesRepository {
//
//    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
//
//    private val lastLocationLatitudeKey = context.getString(R.string.preference_key_last_location_latitude)
//    private val lastLocationLongitudeKey = context.getString(R.string.preference_key_last_location_longitude)
//    private val lastLocationZoomKey = context.getString(R.string.preference_key_last_location_zoom)
//
//    override fun saveLastLocation(location: Pair<Coordinate, Float>): Completable {
//        preferences.edit()
//                .putFloat(lastLocationLatitudeKey, location.first.x.toFloat())
//                .putFloat(lastLocationLongitudeKey, location.first.y.toFloat())
//                .putFloat(lastLocationZoomKey, location.second)
//                .apply()
//        return Completable.complete()
//    }
//
//    override fun getLastLocation(): Observable<Pair<Coordinate, Float>> {
//        val latitude = preferences.getFloat(lastLocationLatitudeKey, 53.347261F)
//        val longitude = preferences.getFloat(lastLocationLongitudeKey, -6.259102F)
//        val zoom = preferences.getFloat(lastLocationZoomKey, 15.9F)
//        return Observable.just(Pair(Coordinate(latitude.toDouble(), longitude.toDouble()), zoom))
//    }
//
//}
