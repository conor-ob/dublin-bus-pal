package ie.dublinbuspal.android.view.home;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.view.favourite.FavouritesFragment;
import ie.dublinbuspal.android.view.nearby.NearbyFragment;
import ie.dublinbuspal.android.view.news.NewsFragment;
import ie.dublinbuspal.android.view.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int RADIUS = 2;
    public static final int NEWS = 0;
    public static final int NEARBY = 1;
    public static final int FAVOURITES = 2;
    public static final int SEARCH = 3;

    private BottomNavigationView bottomNavigationView;
    private HomePagerAdapter adapter;
    private ViewPager viewPager;
    private MenuItem previousMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigation();
        setupViewPager();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == NEARBY) {
            NearbyFragment nearbyFragment = (NearbyFragment) adapter.getItem(NEARBY);
            if (nearbyFragment.isVisible()) {
                if (nearbyFragment.canGoBack()) {
                    super.onBackPressed();
                }
                return;
            }
        }
        super.onBackPressed();
    }

    protected void setupViewPager() {
        viewPager = findViewById(R.id.view_pager);
        adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFragment());
        adapter.addFragment(new NearbyFragment());
        adapter.addFragment(new FavouritesFragment());
        adapter.addFragment(new SearchFragment());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(RADIUS);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //nada
            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu(position);
                if (previousMenuItem != null) {
                    previousMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                previousMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //nada
            }

        });

        String screenIndex = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(getString(R.string.preference_key_home_screen), String.valueOf(NEARBY));

        viewPager.setCurrentItem(Integer.parseInt(screenIndex));
    }

    public void goToSearch() {
        viewPager.setCurrentItem(SEARCH);
    }

    public void invalidateOptionsMenu(int position) {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.getItem(i).setHasOptionsMenu(i == position);
        }
        View view = adapter.getItem(position).getView();
        if (view != null) {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        invalidateOptionsMenu();
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_news:
                            viewPager.setCurrentItem(NEWS);
                            maybeHideKeyboard(adapter.getItem(NEWS));
                            return true;
                        case R.id.navigation_nearby:
                            viewPager.setCurrentItem(NEARBY);
                            maybeHideKeyboard(adapter.getItem(NEARBY));
                            return true;
                        case R.id.navigation_favourites:
                            viewPager.setCurrentItem(FAVOURITES);
                            maybeHideKeyboard(adapter.getItem(FAVOURITES));
                            return true;
                        case R.id.navigation_search:
                            viewPager.setCurrentItem(SEARCH);
                            //showKeyboard(adapter.getItem(SEARCH));
                            return true;
                    }
                    return false;
                });
    }

    protected class HomePagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        private HomePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragmentList().get(position);
        }

        @Override
        public int getCount() {
            return getFragmentList().size();
        }

        void addFragment(Fragment fragment) {
            getFragmentList().add(fragment);
        }

        private List<Fragment> getFragmentList() {
            if (fragmentList == null) {
                fragmentList = new ArrayList<>();
            }
            return fragmentList;
        }

    }

    //TODO not working
    protected void showKeyboard(Fragment fragment) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) fragment.getContext()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInputFromInputMethod(fragment.getView().getWindowToken(),
                    0);
        } catch (Exception e) {
            //unimortant
        }
    }

    protected void maybeHideKeyboard(Fragment fragment) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) fragment.getContext()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(fragment.getView().getWindowToken(),
                    0);
        } catch (Exception e) {
            //unimortant
        }
    }

}
