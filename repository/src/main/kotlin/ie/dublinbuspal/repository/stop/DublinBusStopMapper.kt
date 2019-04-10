package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity
import ie.dublinbuspal.data.entity.DublinBusStopServiceEntity
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.service.api.RtpiStop
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.Operator
import java.util.*

object DublinBusStopMapper {

    fun mapJsonToEntities(
            jsonArray: List<RtpiStop>
    ): Pair<List<DublinBusStopLocationEntity>, List<DublinBusStopServiceEntity>> {
        val locationEntities = mutableListOf<DublinBusStopLocationEntity>()
        val serviceEntities = mutableListOf<DublinBusStopServiceEntity>()
        for (json in jsonArray) {
            locationEntities.add(
                    DublinBusStopLocationEntity(
                            id = json.id,
                            name = json.name,
                            latitude = json.latitude.toDouble(),
                            longitude = json.longitude.toDouble()
                    )
            )
            for (stopService in json.stopServices) {
                for (route in stopService.routeIds) {
                    serviceEntities.add(
                            DublinBusStopServiceEntity(
                                    stopId = json.id,
                                    operator = stopService.operatorId,
                                    route = route
                            )
                    )
                }
            }
        }
        return Pair(locationEntities, serviceEntities)
    }

    fun mapEntitiesToStops(entities: List<DublinBusStopEntity>): List<DublinBusStop> {
        val stops = mutableListOf<DublinBusStop>()
        for (entity in entities) {
            stops.add(
                    DublinBusStop(
                            id = entity.location.id,
                            name = entity.location.name,
                            coordinate = Coordinate(entity.location.latitude, entity.location.longitude),
                            operators = mapOperators(entity.services),
                            routes = mapOperatorsToRoutes(entity.services)
                    )
            )
        }
        return stops
    }

    private fun mapOperators(entities: List<DublinBusStopServiceEntity>): EnumSet<Operator> {
        val operators = EnumSet.noneOf(Operator::class.java)
        for (entity in entities) {
            operators.add(Operator.parse(entity.operator))
        }
        return operators
    }

    private fun mapOperatorsToRoutes(entities: List<DublinBusStopServiceEntity>): Map<Operator, Set<String>> {
        val operatorsByRoute = mutableMapOf<Operator, Set<String>>()
        for (entity in entities) {
            val operator = Operator.parse(entity.operator)
            val routes = operatorsByRoute[operator]
            if (routes == null) {
                operatorsByRoute[operator] = setOf(entity.route)
            } else {
                val newRoutes = routes.toMutableSet()
                newRoutes.add(entity.route)
                operatorsByRoute[operator] = newRoutes
            }
        }
        return operatorsByRoute
    }

}
