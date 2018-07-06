package ie.dublinbuspal.base

interface Mapper<in F, out T> {

    fun map(from: F): T

    fun map(from: List<F>): List<T> {
        return from.map { map(it) }
    }

}
