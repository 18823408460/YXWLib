package com.uurobot.yxwlib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.uurobot.yxwlib.MainApplication;
import com.uurobot.yxwlib.alarm.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2018/4/9.
 */

public class NetUtil {

        /**
         * 获取mac地址，
         *
         * @return
         */
        public static String getMacAddress() {
                WifiManager systemService = (WifiManager) MainApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                String macAddress = systemService.getConnectionInfo().getMacAddress();
                return macAddress;
        }

        /**
         * 获取IP地址.
         *
         * @return
         */
        public String getLocalIpAddress() {
                try {
                        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                                NetworkInterface intf = en.nextElement();
                                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                        InetAddress inetAddress = enumIpAddr.nextElement();
                                        if (!inetAddress.isLoopbackAddress()) {
                                                return inetAddress.getHostAddress().toString();
                                        }
                                }
                        }
                } catch (SocketException ex) {
                        Logger.e("WifiPreference IpAddress", ex.toString());
                }
                return null;
        }

        /**
         * 检查网络.
         *
         * @param context
         * @return
         */
        public static boolean checkNetworkConnection(Context context) {
                final ConnectivityManager connMgr = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifi.isConnected() && wifi.isAvailable()) || (mobile.isConnected() && mobile.isAvailable())) {
                        return true;
                } else {
                        return false;
                }
        }



}
