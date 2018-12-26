package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.GoAheadDublinRouteEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsJson

class GoAheadDublinRouteEntityMapper : Mapper<RouteListInformationWithVariantsJson, GoAheadDublinRouteEntity> {

    override fun map(from: RouteListInformationWithVariantsJson): GoAheadDublinRouteEntity {
        return GoAheadDublinRouteEntity(from.route!!, from.variants!![0].origin!!, from.variants!![0].destination!!)
    }

}
