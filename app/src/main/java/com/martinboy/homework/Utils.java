package com.martinboy.homework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class Utils {

    public static boolean checkNetWorkConnect(Context context) {
        if (checkConnectType(context) == NetWorkType.MobileNetWork) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }

            if (networkInfo != null && networkInfo.getType() == 0) {
                return networkInfo.isConnected();
            } else {
                return false;
            }
        } else {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                if (wifiManager.isWifiEnabled()) {
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (manager != null) {
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        return info != null && info.isConnected();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private static NetWorkType checkConnectType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.getType() == 0) {
                    return NetWorkType.MobileNetWork;
                } else {
                    return networkInfo.getType() == 1 ? NetWorkType.WIFiNetWork : null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public enum NetWorkType {
        MobileNetWork,
        WIFiNetWork
    }

}
