package ie.dublinbuspal.domain.repository

import io.reactivex.Observable

interface Repository<T, K> {

    fun get(key: K): Observable<T>

    fun fetch(key: K): Observable<T>

}
