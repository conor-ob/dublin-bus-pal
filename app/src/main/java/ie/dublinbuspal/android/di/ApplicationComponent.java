package ie.dublinbuspal.android.di;

import javax.inject.Singleton;

import dagger.Component;
import ie.dublinbuspal.android.view.route.RouteActivity;
import ie.dublinbuspal.android.view.settings.SettingsActivity;

@Singleton
@Component(modules = {ApplicationModule.class, PresenterModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(RouteActivity routeActivity);

    void inject(SettingsActivity.MainPreferenceFragment mainPreferenceFragment);

}
