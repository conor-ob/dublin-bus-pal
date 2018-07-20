package ie.dublinbuspal.domain.usecase.favourites

import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.repository.favourite.FavouriteRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(private val repository: Repository<List<Stop>, Any>) {

    fun getFavourites(): Observable<List<Stop>> {
        return repository.get(0)
    }

    fun insertFavourite(favouriteStop: FavouriteStop) {
        (repository as FavouriteRepository).insert(favouriteStop)
    }

}
