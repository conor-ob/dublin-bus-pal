package ie.dublinbuspal.util

import ie.dublinbuspal.Coordinate

object LocationUtils {

    private const val EARTH_RADIUS = 6371001

    fun haversineDistance(coordinate1: Coordinate, coordinate2: Coordinate): Double {
        val dLat = Math.toRadians(coordinate2.x - coordinate1.x)
        val dLong = Math.toRadians(coordinate2.y - coordinate1.y)

        val startLat = Math.toRadians(coordinate1.x)
        val endLat = Math.toRadians(coordinate2.x)

        val a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return EARTH_RADIUS * c
    }

    private fun haversine(value: Double): Double {
        return Math.pow(Math.sin(value / 2), 2.0)
    }

}
