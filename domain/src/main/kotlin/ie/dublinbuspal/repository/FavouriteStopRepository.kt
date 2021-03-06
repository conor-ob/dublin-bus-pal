package ie.dublinbuspal.repository

import io.reactivex.Observable

interface FavouriteStopRepository<T> {

    fun getById(id: String): Observable<T>

    fun getAll(): Observable<List<T>>

    fun insert(entity: T)

    fun insertAll(entities: List<T>)

    fun update(entity: T)

    fun updateAll(entities: List<T>)

    fun delete(id: String)

}
