package ie.dublinbuspal.android.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ie.dublinbuspal.android.R;

import java.util.concurrent.TimeUnit;

public class PreferencesDataSourceImpl implements PreferencesDataSource {

    // Check these match default values in preferences.xml
    private static final long LAST_UPDATE = 0L;
    private static final String FREQUENCY = "7";
    private static final boolean AUTO_UPDATE = true;
    private static final boolean AUTO_UPDATE_GOOD_CONNECTION = true;

    private final SharedPreferences preferences;
    private final String databaseLastUpdatedTimeKey;
    private final String databaseUpdateFrequencyKey;
    private final String databaseAutoUpdateKey;
    private final String databaseUpdateOnlyOnGoodConnection;

    public PreferencesDataSourceImpl(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        databaseLastUpdatedTimeKey = context.getString(R.string.preference_key_update_database);
        databaseUpdateFrequencyKey = context.getString(R.string.preference_key_auto_update_frequency);
        databaseAutoUpdateKey = context.getString(R.string.preference_key_auto_update);
        databaseUpdateOnlyOnGoodConnection = context.getString(R.string.preference_key_auto_update_only_on_good_connection);
    }

    @Override
    public void setDatabaseLastUpdatedTime(long lastUpdatedTime) {
        preferences.edit()
                .putLong(databaseLastUpdatedTimeKey, lastUpdatedTime)
                .apply();
    }

    @Override
    public long getDatabaseLastUpdatedTime() {
        return preferences.getLong(databaseLastUpdatedTimeKey, LAST_UPDATE);
    }

    @Override
    public long getDatabaseUpdateFrequency() {
        String days = preferences.getString(databaseUpdateFrequencyKey, FREQUENCY);
        return TimeUnit.DAYS.toMillis(Long.parseLong(days));
    }

    @Override
    public boolean isDatabaseAutoUpdate() {
        return preferences.getBoolean(databaseAutoUpdateKey, AUTO_UPDATE);
    }

    @Override
    public boolean isUpdateDatabaseOnlyOnGoodConnection() {
        return preferences.getBoolean(databaseUpdateOnlyOnGoodConnection, AUTO_UPDATE_GOOD_CONNECTION);
    }

}
