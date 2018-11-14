package ie.dublinbuspal.repository.favourite

import ie.dublinbuspal.data.dao.FavouriteStopDao
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Mapper
import io.reactivex.Observable

class FavouriteStopRepository(private val dao: FavouriteStopDao,
                              private val entityMapper: Mapper<FavouriteStop, FavouriteStopEntity>,
                              private val domainMapper: Mapper<FavouriteStopEntity, FavouriteStop>) : FavouriteRepository<List<FavouriteStop>, Any> {

    override fun get(key: Any): Observable<List<FavouriteStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun insert(entity: List<FavouriteStop>) {
        dao.insertAll(entityMapper.map(entity))
    }

}
