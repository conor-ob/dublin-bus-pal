package ie.dublinbuspal.domain.repository.favourite

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.database.dao.FavouriteStopDao
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import io.reactivex.Observable

class FavouriteRepository(private val store: StoreRoom<List<FavouriteStop>, Any>,
                          private val dao: FavouriteStopDao,
                          private val mapper: Mapper<FavouriteStop, FavouriteStopEntity>) : Repository<List<FavouriteStop>, Any> {

    override fun get(key: Any): Observable<List<FavouriteStop>> {
        return store.get(key)
    }

    override fun fetch(key: Any): Observable<List<FavouriteStop>> {
        TODO()
    }

    fun insert(favourite: FavouriteStop) {
        dao.insert(mapper.map(favourite))
    }

    fun update(favourites: List<FavouriteStop>) {
        dao.updateAll(mapper.map(favourites))
    }

}
