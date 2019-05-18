package ie.dublinbuspal.usecase.nearby

import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.util.Coordinate
import net.sf.javaml.core.kdtree.KDTree

class TwoDimensionalKdTree {

    private val delegate = KDTree(2)
    private var isEmpty = true

    fun insert(stops: List<Stop>) {
        for (stop in stops) {
            delegate.insert(doubleArrayOf(stop.coordinate.x, stop.coordinate.y), stop)
        }
        isEmpty = false
    }

    fun nearest(coordinate: Coordinate, limit: Int): List<Stop> {
        return delegate.nearest(doubleArrayOf(coordinate.x, coordinate.y), limit).map { it as Stop }
    }

    fun isEmpty(): Boolean = isEmpty

}
