package ie.dublinbuspal.domain.mapping.favourite

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import ie.dublinbuspal.domain.model.favourite.FavouriteStop

class FavouriteStopDomainMapper : Mapper<FavouriteStopEntity, FavouriteStop> {

    override fun map(from: FavouriteStopEntity): FavouriteStop {
        return FavouriteStop(from.id, from.name, from.routes, from.order)
    }

}
