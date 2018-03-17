package com.uurobot.yxwlib.wifi;

import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;

import com.uurobot.yxwlib.alarm.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class WifiMgr {

        public WifiMgr(Context context) {
                this.context = context;
        }

        private Context context;

        public boolean setIpWithTfiStaticIp() {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                WifiConfiguration wifiConfig = null;
                WifiInfo connectionInfo = wifiManager.getConnectionInfo(); //得到连接的wifi网络

                List<WifiConfiguration> configuredNetworks = wifiManager

                        .getConfiguredNetworks();
                for (WifiConfiguration conf : configuredNetworks) {

                        if (conf.networkId == connectionInfo.getNetworkId()) {
                                wifiConfig = conf;

                                break;
                        }

                }
                if (android.os.Build.VERSION.SDK_INT < 11) { // 如果是android2.x版本的话

                        ContentResolver ctRes = context.getContentResolver();
                        android.provider.Settings.System.putInt(ctRes,

                                android.provider.Settings.System.WIFI_USE_STATIC_IP, 1);
                        android.provider.Settings.System.putString(ctRes,

                                android.provider.Settings.System.WIFI_STATIC_IP, "192.168.31.202");
                       Logger.e(TAG,",,,,,,,,,,,！");
                        return true;
                }
                else { // 如果是android3.x版本及以上的话

                        try {

//                                Method[] f = wifiConfig.getClass().getDeclaredMethods() ;
//                                for (Method temp:f
//                                        ) {
//                                        Logger.e(TAG,"methd===="+temp.toString());
//                                }
                                Class aClass = Class.forName("android.net.IpConfiguration$IpAssignment");
                                Object aStatic = Enum.valueOf(aClass, "STATIC");
                                Logger.e(TAG,"aStatic====="+aStatic.toString());

                                Class paramsClass = Class.forName("android.net.IpConfiguration$IpAssignment");
                                Method method = wifiConfig.getClass().getDeclaredMethod("setIpAssignment",paramsClass);
                                method.invoke(wifiConfig,aStatic);


                                Field declaredField = wifiConfig.getClass().getDeclaredField("linkProperties");
                                //setIpAssignment("STATIC", wifiConfig);



                                setIpAddress(InetAddress.getByName("192.168.31.102"), 24, wifiConfig);
                                wifiManager.updateNetwork(wifiConfig); // apply the setting
                                Logger.e(TAG,"静态ip设置成功！");
                                return true;

                        }
                        catch (Exception e) {
                                e.printStackTrace();
                                Logger.e(TAG,"静态ip设置失败！" +  e);
                                return false;
                        }
                }

        }

        private static final String TAG = "WifiMgr";


        private static void setIpAssignment(String assign, WifiConfiguration wifiConf)
                throws SecurityException, IllegalArgumentException,
                NoSuchFieldException, IllegalAccessException {
                setEnumField(wifiConf, assign, "ipAssignment");
        }

        private static void setIpAddress(InetAddress addr, int prefixLength,
                                         WifiConfiguration wifiConf) throws SecurityException,
                IllegalArgumentException, NoSuchFieldException,
                IllegalAccessException, NoSuchMethodException,
                ClassNotFoundException, InstantiationException,
                InvocationTargetException {
                Object linkProperties = getField(wifiConf, "linkProperties");
                if (linkProperties == null)
                        return;
                Class<?> laClass = Class.forName("android.net.LinkAddress");
                Constructor<?> laConstructor = laClass.getConstructor(new Class[]{
                        InetAddress.class, int.class});
                Object linkAddress = laConstructor.newInstance(addr, prefixLength);
                ArrayList<Object> mLinkAddresses = (ArrayList<Object>) getDeclaredField(
                        linkProperties, "mLinkAddresses");
                mLinkAddresses.clear();
                mLinkAddresses.add(linkAddress);
        }

        private static Object getField(Object obj, String name)
                throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
                Field f = obj.getClass().getField(name);
                Object out = f.get(obj);
                return out;
        }

        private static Object getDeclaredField(Object obj, String name)
                throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
                Field f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
                Object out = f.get(obj);
                return out;

        }

        private static void setEnumField(Object obj, String value, String name)
                throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        }
}
