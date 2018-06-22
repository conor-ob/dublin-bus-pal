package ie.dublinbuspal.android.view.favourite;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface FavouritesPresenter extends MvpPresenter<FavouritesView> {

    void onResume();

    void onPause();

}
