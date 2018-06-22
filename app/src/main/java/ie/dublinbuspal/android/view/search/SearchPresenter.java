package ie.dublinbuspal.android.view.search;

import android.support.v7.widget.SearchView;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface SearchPresenter extends MvpPresenter<SearchQueryView>,
        SearchView.OnQueryTextListener {

    void onResume(String query);

    void onPause();

}
