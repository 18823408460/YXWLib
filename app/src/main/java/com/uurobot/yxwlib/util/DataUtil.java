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
}
