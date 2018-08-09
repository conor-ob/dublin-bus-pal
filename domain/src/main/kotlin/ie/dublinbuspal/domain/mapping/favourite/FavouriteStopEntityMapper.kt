package ie.dublinbuspal.domain.mapping.favourite

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.favourite.FavouriteStop

class FavouriteStopEntityMapper : Mapper<FavouriteStop, FavouriteStopEntity> {

    override fun map(from: FavouriteStop): FavouriteStopEntity {
        return FavouriteStopEntity(from.id, from.name, from.routes, from.order)
    }

}
