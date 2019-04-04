package ie.dublinbuspal.repository.route

import ie.dublinbuspal.data.entity.DublinBusRouteEntity
import ie.dublinbuspal.data.entity.DublinBusRouteInfoEntity
import ie.dublinbuspal.data.entity.DublinBusRouteVariantEntity
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.route.RouteVariant
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationWithVariantsJson
import ie.dublinbuspal.util.Operator

object DublinBusRouteMapper {

    fun mapJsonToEntities(
            jsonArray: List<RtpiRouteListInformationWithVariantsJson>
    ): Pair<List<DublinBusRouteInfoEntity>, List<DublinBusRouteVariantEntity>> {
        val infoEntities = mutableListOf<DublinBusRouteInfoEntity>()
        val variantEntities = mutableListOf<DublinBusRouteVariantEntity>()
        for (json in jsonArray) {
            infoEntities.add(
                    DublinBusRouteInfoEntity(
                            id = json.route!!,
                            operator = json.operator!!
                    )
            )
            for (variant in json.variants) {
                variantEntities.add(
                        DublinBusRouteVariantEntity(
                                routeId = json.route!!,
                                origin = variant.origin,
                                destination = variant.destination
                        )
                )
            }
        }
        return Pair(infoEntities, variantEntities)
    }

    fun mapEntitiesToStops(entities: List<DublinBusRouteEntity>): List<Route> {
        val routes = mutableListOf<Route>()
        for (entity in entities) {
            val variants = mutableListOf<RouteVariant>()
            for (variant in entity.variants) {
                variants.add(RouteVariant(variant.origin, variant.destination))
            }
            routes.add(Route(id = entity.info.id, variants = variants, operator = Operator.parse(entity.info.operator)))
        }
        return routes
    }

}
