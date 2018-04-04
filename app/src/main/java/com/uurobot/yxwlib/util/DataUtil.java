package com.uurobot.yxwlib.util;

/**
 * Created by Administrator on 2018/3/10.
 */

public class DataUtil {
        public static byte[] intToBytes(int value) {
                byte[] src = new byte[4];
                src[3] = (byte) ((value >> 24) & 0xFF);
                src[2] = (byte) ((value >> 16) & 0xFF);
                src[1] = (byte) ((value >> 8) & 0xFF);
                src[0] = (byte) (value & 0xFF);
                return src;
        }

        public static String bytesToHexString(byte[] src){
                StringBuilder stringBuilder = new StringBuilder("");
                if (src == null || src.length <= 0) {
                        return null;
                }
                for (int i = 0; i < src.length; i++) {
                        int v = src[i] & 0xFF;
                        String hv = Integer.toHexString(v);
                        if (hv.length() < 2) {
                                stringBuilder.append(0);
                        }
                        stringBuilder.append(hv+",  ");
                }
                return stringBuilder.toString();
        }

        public static String bytesToHexString(byte[] src,int len){
                StringBuilder stringBuilder = new StringBuilder("");
                if (src == null || src.length <= 0) {
                        return null;
                }
                for (int i = 0; i < len; i++) {
                        int v = src[i] & 0xFF;
                        String hv = Integer.toHexString(v);
                        if (hv.length() < 2) {
                                stringBuilder.append("0"+hv+", ");
                        }else{
                                stringBuilder.append(hv+",  ");
                        }

                }
                return stringBuilder.toString();
        }
}
