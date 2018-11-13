package ie.dublinbuspal.mapping.favourite

import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.Mapper

class FavouriteStopDomainMapper : Mapper<FavouriteStopEntity, FavouriteStop> {

    override fun map(from: FavouriteStopEntity): FavouriteStop {
        return FavouriteStop(from.id, from.name, from.routes, from.order)
    }

}
