package ie.dublinbuspal.usecase.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.repository.FavouriteStopRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(private val repository: FavouriteStopRepository<FavouriteStop>) {

    fun getFavourites(): Observable<List<FavouriteStop>> {
        return repository.getAll()
    }

    fun insertFavourite(favouriteStop: FavouriteStop) {
        //repository.insert(Collections.singletonList(favouriteStop))
    }

}
