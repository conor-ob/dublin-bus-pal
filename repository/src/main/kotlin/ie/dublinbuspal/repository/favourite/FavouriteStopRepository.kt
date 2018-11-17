package ie.dublinbuspal.repository.favourite

import ie.dublinbuspal.data.dao.FavouriteStopDao
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Mapper
import io.reactivex.Observable

class FavouriteStopRepository(private val dao: FavouriteStopDao,
                              private val entityMapper: Mapper<FavouriteStop, FavouriteStopEntity>,
                              private val domainMapper: Mapper<FavouriteStopEntity, FavouriteStop>) : FavouriteRepository<FavouriteStop> {

    override fun getAll(): Observable<List<FavouriteStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun getById(id: String): Observable<FavouriteStop> {
        return dao.select(id)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun insert(entity: FavouriteStop) {
        dao.insert(entityMapper.map(entity))
    }

    override fun insertAll(entities: List<FavouriteStop>) {
        dao.insertAll(entityMapper.map(entities))
    }

    override fun update(entity: FavouriteStop) {
        dao.update(entityMapper.map(entity))
    }

    override fun updateAll(entities: List<FavouriteStop>) {
        dao.updateAll(entityMapper.map(entities))
    }

}
