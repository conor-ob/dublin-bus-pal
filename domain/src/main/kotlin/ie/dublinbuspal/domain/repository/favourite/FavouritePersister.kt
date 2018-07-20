package ie.dublinbuspal.domain.repository.favourite

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.dao.DetailedStopDao
import ie.dublinbuspal.database.dao.FavouriteStopDao
import ie.dublinbuspal.database.entity.DetailedStopEntity
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.stop.Stop
import io.reactivex.Observable
import java.util.*

class FavouritePersister(private val stopDao: FavouriteStopDao,
                         private val detailedStopDao: DetailedStopDao,
                         private val domainMapper: Mapper<DetailedStopEntity, Stop>) : RoomPersister<List<FavouriteStopEntity>, List<Stop>, Any> {

    override fun read(key: Any): Observable<List<Stop>> {
        return detailedStopDao.selectAllFavourites()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: Any, raw: List<FavouriteStopEntity>) {
        stopDao.insert(FavouriteStopEntity("315", "Howiye", Arrays.asList("46A", "145"), 1))
        stopDao.insert(FavouriteStopEntity("769", "Blah Blah", Arrays.asList("46A"), 2))
    }

}