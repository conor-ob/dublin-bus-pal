package ie.dublinbuspal.android.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public final class MetaDataUtilities {

    private MetaDataUtilities() {
        throw new UnsupportedOperationException();
    }

    public static String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData == null) {
                throw new IllegalArgumentException("meta-data cannot be null");
            } else {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException("meta-data name not found");
        }
    }

}
