package ie.dublinbuspal.android.view.search;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import androidx.appcompat.widget.SearchView;

public interface SearchPresenter extends MvpPresenter<SearchQueryView> {

    void onResume(String query);

    void onPause();

}
