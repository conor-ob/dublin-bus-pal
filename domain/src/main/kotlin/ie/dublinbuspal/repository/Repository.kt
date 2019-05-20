package ie.dublinbuspal.repository

import io.reactivex.Observable

interface Repository<T> {

    fun getById(id: String): Observable<T>

    fun getAll(): Observable<List<T>>

    fun getAllById(id: String): Observable<List<T>>

    fun refresh(): Observable<Boolean>

}

interface KeyedRepository<K, T> {

    fun getById(key: K): Observable<T>

    fun getAll(): Observable<List<T>>

    fun getAllById(key: K): Observable<List<T>>

    fun refresh(): Observable<Boolean>

}
