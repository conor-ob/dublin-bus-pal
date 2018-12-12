package ie.dublinbuspal.android.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.view.favourite.FavouritesModelImpl;
import ie.dublinbuspal.android.view.favourite.FavouritesPresenter;
import ie.dublinbuspal.android.view.favourite.FavouritesPresenterImpl;
import ie.dublinbuspal.android.view.nearby.NearbyModelImpl;
import ie.dublinbuspal.android.view.nearby.NearbyPresenter;
import ie.dublinbuspal.android.view.nearby.NearbyPresenterImpl;
import ie.dublinbuspal.android.view.news.rss.RssModelImpl;
import ie.dublinbuspal.android.view.news.rss.RssPresenter;
import ie.dublinbuspal.android.view.news.rss.RssPresenterImpl;
import ie.dublinbuspal.android.view.news.twitter.TwitterModelImpl;
import ie.dublinbuspal.android.view.news.twitter.TwitterPresenter;
import ie.dublinbuspal.android.view.news.twitter.TwitterPresenterImpl;
import ie.dublinbuspal.android.view.route.RouteModelImpl;
import ie.dublinbuspal.android.view.route.RoutePresenter;
import ie.dublinbuspal.android.view.route.RoutePresenterImpl;

@Module
class PresenterModule {

    @Provides
    TwitterPresenter provideTwitterPresenter(Context context) {
        return new TwitterPresenterImpl(context, new TwitterModelImpl());
    }

    @Provides
    RssPresenter provideRssPresenter(DublinBusRepository repository) {
        return new RssPresenterImpl(repository, new RssModelImpl());
    }

    @Provides
    NearbyPresenter provideNearbyPresenter(DublinBusRepository repository) {
        return new NearbyPresenterImpl(repository, new NearbyModelImpl());
    }

    @Provides
    FavouritesPresenter provideFavouritesPresenter(DublinBusRepository repository) {
        return new FavouritesPresenterImpl(repository, new FavouritesModelImpl());
    }

//    @Provides
//    SearchPresenter provideSearchPresenter(DublinBusRepository repository) {
//        return new SearchPresenterImpl(repository, new SearchModelImpl());
//    }

//    @Provides
//    RealTimePresenter provideRealTimePresenter(DublinBusRepository repository) {
//        return new RealTimePresenterImpl(repository, new RealTimeModelImpl());
//    }

    @Provides
    RoutePresenter provideRoutePresenter(DublinBusRepository repository) {
        return new RoutePresenterImpl(repository, new RouteModelImpl());
    }

}
