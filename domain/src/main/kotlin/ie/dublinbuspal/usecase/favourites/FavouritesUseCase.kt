package ie.dublinbuspal.usecase.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.FavouriteStopRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(private val repository: FavouriteStopRepository<FavouriteStop>) {

    fun getFavourites(): Observable<List<FavouriteStop>> {
        return repository.getAll()
    }

    fun saveFavourite(stopId: String, customName: String, customRoutes: MutableList<String>): Boolean {
        repository.insert(FavouriteStop(stopId, customName, customRoutes, 0))
        return true //TODO
    }

    fun removeFavourite(stopId: String): Boolean {
        repository.delete(stopId)
        return true
    }

}
