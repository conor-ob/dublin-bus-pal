package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity
import ie.dublinbuspal.data.entity.DublinBusStopServiceEntity
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.Operator
import ie.rtpi.api.rtpi.RtpiBusStopInformationJson
import java.util.*

object DublinBusStopMapper {

    fun mapJsonToEntities(
            jsonArray: List<RtpiBusStopInformationJson>
    ): Pair<List<DublinBusStopLocationEntity>, List<DublinBusStopServiceEntity>> {
        val locationEntities = mutableListOf<DublinBusStopLocationEntity>()
        val serviceEntities = mutableListOf<DublinBusStopServiceEntity>()
        for (json in jsonArray) {
            locationEntities.add(
                    DublinBusStopLocationEntity(
                            id = json.stopId!!,
                            name = json.fullName!!,
                            latitude = json.latitude!!.toDouble(),
                            longitude = json.longitude!!.toDouble()
                    )
            )
            for (operator in json.operators) {
                for (route in operator.routes) {
                    serviceEntities.add(
                            DublinBusStopServiceEntity(
                                    stopId = json.stopId!!,
                                    operator = operator.name!!,
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
