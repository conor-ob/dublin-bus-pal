package ie.dublinbuspal.repository

import io.reactivex.Observable

interface FavouriteRepository<T, K> {

    fun get(key: K): Observable<T>

    fun insert(entity: T)

}