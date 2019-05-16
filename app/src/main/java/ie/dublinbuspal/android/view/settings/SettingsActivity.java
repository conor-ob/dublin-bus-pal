package ie.dublinbuspal.android.view.settings;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.Instant;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

import javax.inject.Inject;

import ie.dublinbuspal.android.BuildConfig;
import ie.dublinbuspal.android.DublinBusApplication;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.view.web.WebViewActivity;
import ie.dublinbuspal.usecase.update.UpdateStopsAndRoutesUseCase;
import ie.dublinbuspal.util.StringUtils;
import ie.dublinbuspal.util.TimeUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MainPreferenceFragment extends PreferenceFragment {

//        @Inject DublinBusRepository repository;
//        @Inject DownloadProgressListener listener;

        @Inject
        UpdateStopsAndRoutesUseCase useCase;
        private CompositeDisposable disposables = new CompositeDisposable();

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setupInjection();
            addPreferencesFromResource(R.xml.preferences);
            bindDynamicSummaries();
            bindSwitchEnabledPreferences();
            bindListeners();
        }

        @Override
        public void onDestroy() {
            disposables.clear();
            disposables.dispose();
            useCase.unregisterObserver();
            super.onDestroy();
        }

        private void setupInjection() {
            if (getActivity() != null) {
                DublinBusApplication application = (DublinBusApplication)
                        getActivity().getApplication();
                application.getApplicationComponent().inject(this);
            }
        }

        private void bindDynamicSummaries() {
            bindPreferenceSummaryToValue(findPreference(
                    getString(R.string.preference_key_home_screen)));
            bindPreferenceSummaryToValue(findPreference(
                    getString(R.string.preference_key_auto_refresh_interval)));
            bindLastUpdatedTimestampSummaryToValue(findPreference(
                    getString(R.string.preference_key_update_database)), null);
            bindAppVersionSummaryToValue(findPreference(
                    getString(R.string.preference_key_app_version)));
        }

        private void bindSwitchEnabledPreferences() {
            bindSwitchEnabledPair(findPreference(getString(R.string.preference_key_auto_refresh)),
                    findPreference(getString(R.string.preference_key_auto_refresh_interval)));
        }

        private void bindListeners() {
            Preference autoRefreshPreference = findPreference(
                    getString(R.string.preference_key_auto_refresh));
            autoRefreshPreference.setOnPreferenceClickListener(preference -> {
                bindSwitchEnabledPair(autoRefreshPreference, findPreference(
                        getString(R.string.preference_key_auto_refresh_interval)));
                return true;
            });
            SyncPreference updatePreference = (SyncPreference) findPreference(
                    getString(R.string.preference_key_update_database));
            updatePreference.setOnPreferenceClickListener(preference -> {
                updatePreference.setRefreshing(true);

                useCase.registerObserver(percent ->
                        disposables.add(Single.just(percent)
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSuccess(val -> updatePreference.setSummary("Downloading " + val + " %"))
                                .subscribe())
                );

                disposables.add(useCase.update()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFinished -> {

                            if (isFinished) {
                                useCase.unregisterObserver();
                                updatePreference.setRefreshing(false);
                                PreferenceManager.getDefaultSharedPreferences(updatePreference.getContext())
                                        .edit()
                                        .putLong(getString(R.string.preference_key_update_database), TimeUtils.now().toEpochMilli())
                                        .apply();
                                try {
                                    bindLastUpdatedTimestampSummaryToValue(updatePreference, TimeUtils.now());
                                } catch (Exception e) {
                                    //TODO check if view is attached
                                }
                            }
                            Timber.d(isFinished.toString());
                        }));

                return true;
            });
//            updatePreference.setOnPreferenceClickListener(preference -> {
//
//                if (!updatePreference.isRefreshing()) {
//                    updatePreference.setRefreshing(true);
//                    AtomicInteger total = new AtomicInteger(0);
//                    AtomicInteger totalPercent = new AtomicInteger(0);
//                    listener.registerObserver(percent -> {
//                        totalPercent.incrementAndGet();
//                        if (totalPercent.get() % 3 == 0) {
//                            int refresh = Math.min(totalPercent.get() / 3, 100);
//                            updatePreference.setSummary("Downloading " + String.valueOf(refresh) + " %");
//                        }
//                        if (percent == 100) {
//                            total.incrementAndGet();
//                            Log.d("DOWNLOADING", String.valueOf(percent));
//                        }
//                        if (total.get() == 3) {
//                            //TODO fix bugs around here (see crashlytics report) refactor into MVP
//                            updatePreference.setRefreshing(false);
//                            PreferenceManager.getDefaultSharedPreferences(updatePreference.getContext())
//                                    .edit()
//                                    .putLong(getString(R.string.preference_key_update_database), new Date().getTime())
//                                    .apply();
//                            bindLastUpdatedTimestampSummaryToValue(updatePreference);
//                        }
//                    });
//
//                    repository.invalidateCache();
//
//                    //TODO check lifecycle methods and dispose of singles appropriately. make sure refresh can continue if screen pauses
//                    Single.fromCallable(repository::getBusStopsRemote)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(busStops -> {
//                                // ok
//                            }, throwable -> {
//                                updatePreference.setRefreshing(false);
//                                handleError(throwable);
//                            });
//
//                    Single.fromCallable(repository::getRoutesRemote)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(routes -> {
//                                // ok
//                            }, throwable -> {
//                                updatePreference.setRefreshing(false);
//                                handleError(throwable);
//                            });
//
//                    Single.fromCallable(repository::getBusStopServicesRemote)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(busStopsServices -> {
//                                // ok
//                            }, throwable -> {
//                                updatePreference.setRefreshing(false);
//                                handleError(throwable);
//                            });
//
//                }
//                return true;
//            });

            Preference share = findPreference(getString(R.string.preference_key_share));
            share.setOnPreferenceClickListener(preference -> {
                shareApp(getActivity());
                return true;
            });
            Preference rate = findPreference(getString(R.string.preference_key_rate));
            rate.setOnPreferenceClickListener(preference -> {
                rateApp(getActivity());
                return true;
            });
            Preference sendFeedback = findPreference(
                    getString(R.string.preference_key_send_feedback));
            sendFeedback.setOnPreferenceClickListener(preference -> {
                sendFeedback(getActivity());
                return true;
            });
            Preference privacyPolicy = findPreference(
                    getString(R.string.preference_key_privacy_policy));
            privacyPolicy.setOnPreferenceClickListener(preference -> {
                startActivity(WebViewActivity.newIntent(getActivity(),
                        getString(R.string.preference_title_privacy_policy),
                        getString(R.string.preference_privacy_policy_url)));
                return true;
            });
//            Preference termsOfService = findPreference(
//                    getString(R.string.preference_key_terms_of_service));
//            termsOfService.setOnPreferenceClickListener(preference -> {
//                startActivity(WebViewActivity.newIntent(getActivity(),
//                        getString(R.string.preference_title_title_terms_of_service),
//                        getString(R.string.preference_terms_of_service_url)));
//                return true;
//            });
//            Preference dataSources = findPreference(
//                    getString(R.string.preference_key_data_sources));
//            dataSources.setOnPreferenceClickListener(preference -> {
//                startActivity(WebViewActivity.newIntent(getActivity(),
//                        getString(R.string.preference_title_data_sources),
//                        getString(R.string.preference_data_sources_url)));
//                return true;
//            });
//            Preference openSourceLibraries = findPreference(
//                    getString(R.string.preference_key_open_source_libraries));
//            openSourceLibraries.setOnPreferenceClickListener(preference -> {
//                startActivity(WebViewActivity.newIntent(getActivity(),
//                        getString(R.string.preference_title_open_source_libraries),
//                        getString(R.string.preference_open_source_libraries_url)));
//                return true;
//            });
        }

        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(preferenceSummaryListener);
            preferenceSummaryListener.onPreferenceChange(preference,
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), StringUtils.EMPTY_STRING));
        }

        private static void bindAppVersionSummaryToValue(Preference preference) {
            preferenceSummaryListener.onPreferenceChange(preference, BuildConfig.VERSION_NAME);
        }

        private static void bindLastUpdatedTimestampSummaryToValue(Preference lastUpdated, Instant timestamp) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(lastUpdated.getContext());
            long time;
            if (timestamp == null) {
                time = preferences.getLong(lastUpdated.getKey(), TimeUtils.now().toEpochMilli());
            } else {
                time = timestamp.toEpochMilli();
            }
            String format = String.format(Locale.UK, "Last updated %s",
                    TimeUtils.formatAsDate(Instant.ofEpochMilli(time)));
            preferences.edit().putLong(lastUpdated.getKey(), time).apply();
            preferenceSummaryListener.onPreferenceChange(lastUpdated, format);
        }

        private static void bindSwitchEnabledPair(Preference preference,
                                                  Preference enabledPreference) {
            SwitchPreference switchPreference = (SwitchPreference) preference;
            enabledPreference.setEnabled(switchPreference.isChecked());
        }

        private void handleError(Throwable throwable) {
            Timber.e(throwable);
            String message;
//            if (throwable instanceof SoapServiceUnavailableException) {
//                message = getString(R.string.error_no_service);
            if (throwable instanceof UnknownHostException) {
                message = getString(R.string.error_no_internet);
            } else if (throwable instanceof SocketException) {
                message = getString(R.string.error_interrupted);
            } else if (throwable instanceof SocketTimeoutException) {
                message = getString(R.string.error_timeout);
            } else {
                message = getString(R.string.error_unknown);
            }
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }

    }

    private static Preference.OnPreferenceChangeListener preferenceSummaryListener =
            (preference, newValue) -> {
                String stringValue = newValue.toString();
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);
                    preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
                } else {
                    preference.setSummary(stringValue);
                }
                return true;
            };

    private static void shareApp(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dublin Bus Pal");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "The best Dublin Bus app!\n\n"
                        + "https://play.google.com/store/apps/details?id="
                        + context.getPackageName());
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }

    private static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, uri);
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(playStoreIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + context.getPackageName())));
        }
    }

    private static void sendFeedback(Context context) {
        String emailBody =
                "\n\n-----------------------------" +
                        "\nPlease don't remove this information" +
                        "\nApp Version: " + BuildConfig.VERSION_NAME +
                        "\nDevice OS: Android" +
                        "\nDevice OS Version: " + Build.VERSION.RELEASE +
                        "\nDevice Brand: " + Build.BRAND +
                        "\nDevice Model: " + Build.MODEL +
                        "\nDevice Manufacturer: " + Build.MANUFACTURER;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "dublinbuspal@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Dublin Bus Pal Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
        context.startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

}
