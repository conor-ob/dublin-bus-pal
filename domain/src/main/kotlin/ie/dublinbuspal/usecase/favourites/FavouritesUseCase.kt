package ie.dublinbuspal.usecase.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.FavouriteStopRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(
        private val repository: FavouriteStopRepository<FavouriteStop>
) {

    fun getFavourites(): Observable<List<FavouriteStop>> {
        return repository.getAll()
    }

    fun saveFavourite(stopId: String, customName: String, customRoutes: Set<String>): Boolean {
        repository.insert(FavouriteStop(stopId, customName, customRoutes, 0))
        return true //TODO
    }

    fun saveFavourites(favourites: List<FavouriteStop>): Completable {
        return Completable.fromCallable { repository.updateAll(applyOrder(favourites)) }
    }

    private fun applyOrder(favourites: List<FavouriteStop>): List<FavouriteStop> {
        val ordered = mutableListOf<FavouriteStop>()
        for (i in 0 until favourites.size) {
            ordered.add(favourites[i].copy(order = i))
        }
        return ordered
    }

    fun removeFavourite(stopId: String): Boolean {
        repository.delete(stopId)
        return true
    }

}
