package ie.dublinbuspal.base

import io.reactivex.Observable

interface Repository<T, K> {

    fun get(key: K): Observable<T>

}
