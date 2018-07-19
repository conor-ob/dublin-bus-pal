package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.DetailedStopEntity
import io.reactivex.Maybe

@Dao
interface DetailedStopDao {

    @Query("SELECT stops.id,stops.name,stops.latitude,stops.longitude," +
            "stop_services.routes AS routes," +
            "smart_dublin_stop_services.routes AS smartDublinRoutes," +
            "favourites.routes AS favouriteRoutes," +
            "favourites.name AS favouriteName FROM stops LEFT JOIN " +
            "stop_services ON stops.id=stop_services.id LEFT " +
            "JOIN smart_dublin_stop_services ON stops.id=" +
            "smart_dublin_stop_services.id LEFT JOIN favourites ON " +
            "stops.id=favourites.id")
    fun selectAll(): Maybe<List<DetailedStopEntity>>

}
