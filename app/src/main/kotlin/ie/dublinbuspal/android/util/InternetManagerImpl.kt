package ie.dublinbuspal.android.util

import android.content.Context
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import ie.dublinbuspal.util.InternetManager

class InternetManagerImpl(private val context: Context) : InternetManager {

    override fun isConnectedToWiFi(): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        if (wifiManager != null && wifiManager.isWifiEnabled) {
            return wifiManager.connectionInfo.supplicantState == SupplicantState.COMPLETED
        }
        return false
    }

}
