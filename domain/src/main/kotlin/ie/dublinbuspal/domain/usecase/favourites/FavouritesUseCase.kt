package ie.dublinbuspal.domain.usecase.favourites

import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.repository.FavouriteRepository
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(private val repository: FavouriteRepository<List<Stop>, Any>) {

    fun getFavourites(): Observable<List<Stop>> {
        return repository.get(0)
    }

    fun insertFavourite(favouriteStop: FavouriteStop) {
        //repository.insert(Collections.singletonList(favouriteStop))
    }

}
