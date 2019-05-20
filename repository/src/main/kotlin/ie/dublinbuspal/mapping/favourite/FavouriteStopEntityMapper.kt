package ie.dublinbuspal.mapping.favourite

import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.Mapper

class FavouriteStopEntityMapper : Mapper<FavouriteStop, FavouriteStopEntity> {

    override fun map(from: FavouriteStop): FavouriteStopEntity {
        return FavouriteStopEntity(from.id, from.name, from.routes.toList(), from.order)
    }

}
