package ie.dublinbuspal.android.view.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.view.home.HomeActivity;
import ie.dublinbuspal.android.view.news.rss.RssFragment;
import ie.dublinbuspal.android.view.news.twitter.TwitterFragment;
import ie.dublinbuspal.android.view.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLayout(view);
        setupOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    private void setupOptionsMenu() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null && isMenuVisible()) {
            homeActivity.invalidateOptionsMenu(HomeActivity.NEWS);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(SettingsActivity.newIntent(getContext()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupLayout(View view) {
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager());
        newsPagerAdapter.addFragment(new TwitterFragment());
        newsPagerAdapter.addFragment(new RssFragment());
        ViewPager newsViewPager = view.findViewById(R.id.container);
        newsViewPager.setAdapter(newsPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        newsViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(newsViewPager));
    }

    public class NewsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        private NewsPagerAdapter(FragmentManager manager) {
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

        private void addFragment(Fragment fragment) {
            getFragmentList().add(fragment);
        }

        private List<Fragment> getFragmentList() {
            if (fragmentList == null) {
                fragmentList = new ArrayList<>();
            }
            return fragmentList;
        }

    }

}
