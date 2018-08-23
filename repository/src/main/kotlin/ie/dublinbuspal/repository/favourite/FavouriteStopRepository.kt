package ie.dublinbuspal.repository.favourite

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.data.dao.FavouriteStopDao
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Mapper
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
