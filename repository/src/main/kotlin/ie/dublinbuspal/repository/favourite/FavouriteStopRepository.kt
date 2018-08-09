package ie.dublinbuspal.repository.favourite

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.database.dao.FavouriteStopDao
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.repository.FavouriteRepository
import ie.dublinbuspal.domain.repository.Mapper
import io.reactivex.Observable

class FavouriteStopRepository(private val store: StoreRoom<List<Stop>, Any>,
                              private val dao: FavouriteStopDao,
                              private val mapper: Mapper<FavouriteStop, FavouriteStopEntity>) : FavouriteRepository<List<Stop>, Any> {

    override fun get(key: Any): Observable<List<Stop>> {
        return store.get(key)
    }

    override fun insert(entity: List<Stop>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun insert(favourite: FavouriteStop) {
//        dao.insert(mapper.map(favourite))
//    }
//
//    fun update(favourites: List<FavouriteStop>) {
//        dao.updateAll(mapper.map(favourites))
//    }

}
