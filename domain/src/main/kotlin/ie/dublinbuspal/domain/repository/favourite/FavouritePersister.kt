package ie.dublinbuspal.domain.repository.favourite

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.dao.FavouriteStopDao
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import io.reactivex.Observable

class FavouritePersister(private val dao: FavouriteStopDao,
                         private val domainMapper: Mapper<FavouriteStopEntity, FavouriteStop>) : RoomPersister<List<FavouriteStopEntity>, List<FavouriteStop>, Any> {

    override fun write(key: Any, raw: List<FavouriteStopEntity>) {
        // do nothing
    }

    override fun read(key: Any): Observable<List<FavouriteStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

}