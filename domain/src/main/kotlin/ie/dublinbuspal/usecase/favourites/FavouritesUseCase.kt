package ie.dublinbuspal.usecase.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(private val repository: FavouriteRepository<List<Stop>, Any>) {

    fun getFavourites(): Observable<List<Stop>> {
        return repository.get(0)
    }

    fun insertFavourite(favouriteStop: FavouriteStop) {
        //repository.insert(Collections.singletonList(favouriteStop))
    }

}
