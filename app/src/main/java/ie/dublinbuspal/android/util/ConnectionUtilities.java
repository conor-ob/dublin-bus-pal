package ie.dublinbuspal.android.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public final class ConnectionUtilities {

    private ConnectionUtilities() {
        throw new UnsupportedOperationException();
    }

    public static Connection getConnection(Object systemService) {
        NetworkInfo networkInfo = getNetworkInfo(systemService);
        if (isConnected(networkInfo)) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return Connection.GOOD;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (networkInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                    case TelephonyManager.NETWORK_TYPE_GSM:
                        return Connection.WEAK;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                        return Connection.OK;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                        return Connection.GOOD;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        return Connection.OK;
                }
            }
            return Connection.OK;
        }
        return Connection.OFF;
    }

    public static boolean isConnected(Object systemService) {
        return isConnected(getNetworkInfo(systemService));
    }

    private static boolean isConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    private static NetworkInfo getNetworkInfo(Object systemService) {
        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    public enum Connection {

        GOOD,
        OK,
        WEAK,
        OFF

    }

}
