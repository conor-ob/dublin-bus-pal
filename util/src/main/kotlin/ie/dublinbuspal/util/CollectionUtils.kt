package ie.dublinbuspal.util

import java.util.*

object CollectionUtils {

    @JvmStatic
    fun <T> isNullOrEmpty(collection: Collection<T>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun <K, V> headMap(map: SortedMap<K, V>, limit: Int): SortedMap<K, V> {
        var count = 0
        val headMap = TreeMap<K, V>()
        for ((key, value) in map) {
            if (count >= limit) {
                break
            }
            headMap[key] = value
            count++
        }
        return headMap
    }

    fun <T> safeFirstElement(collection: Collection<T>?): T? {
        if (isNullOrEmpty(collection)) {
            return null
        }
        return collection!!.iterator().next()
    }

}
