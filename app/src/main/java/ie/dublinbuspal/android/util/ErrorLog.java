package ie.dublinbuspal.android.util;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

public final class ErrorLog {

    private static final String HANDLED_ERROR = "HANDLED_ERROR";
    private static final String UNHANDLED_ERROR = "UNHANDLED_ERROR";

    private ErrorLog() {
        throw new UnsupportedOperationException();
    }

    public static void e(Throwable throwable) {
        l(HANDLED_ERROR, throwable);
    }

    public static void u(Throwable throwable) {
        l(UNHANDLED_ERROR, throwable);
        Crashlytics.logException(throwable);
    }

    private static void l(String tag, Throwable throwable) {
        try {
            if (throwable != null) {
                Log.e(tag, throwable.toString(), throwable);
                Log.e(tag, Log.getStackTraceString(throwable), throwable);
                Throwable cause = throwable.getCause();
                if (cause != null) {
                    Log.e(tag, cause.toString(), cause);
                    Log.e(tag, Log.getStackTraceString(cause), cause);
                }
            }
        } catch (Exception e) {
            // not important
        }
    }

}
