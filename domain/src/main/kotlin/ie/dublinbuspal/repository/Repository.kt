package ie.dublinbuspal.repository

import io.reactivex.Observable

interface Repository<T> {

    fun getById(id: String): Observable<T>

    fun getAll(): Observable<List<T>>

    fun getAllById(id: String): Observable<List<T>>

}