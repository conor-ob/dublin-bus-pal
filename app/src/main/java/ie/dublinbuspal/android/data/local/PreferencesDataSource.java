package ie.dublinbuspal.android.data.local;

public interface PreferencesDataSource {

    void setDatabaseLastUpdatedTime(long lastUpdatedTime);

    long getDatabaseLastUpdatedTime();

    long getDatabaseUpdateFrequency();

    boolean isDatabaseAutoUpdate();

    boolean isUpdateDatabaseOnlyOnGoodConnection();

}
