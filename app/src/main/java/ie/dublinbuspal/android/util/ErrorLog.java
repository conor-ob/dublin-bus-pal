package ie.dublinbuspal.android.util;

import com.crashlytics.android.Crashlytics;

import ie.dublinbuspal.android.BuildConfig;
import timber.log.Timber;

public final class ErrorLog {

    private ErrorLog() {
        throw new UnsupportedOperationException();
    }

    public static void e(Throwable throwable) {
        l(throwable);
    }

    public static void u(Throwable throwable) {
        l(throwable);
        if (!BuildConfig.DEBUG) {
            Crashlytics.logException(throwable);
        }
    }

    private static void l(Throwable throwable) {
        try {
            if (throwable != null) {
                Timber.e(throwable);
                Throwable cause = throwable.getCause();
                if (cause != null) {
                    Timber.e(cause);
                }
            }
        } catch (Exception e) {
            // not important
        }
    }

}
