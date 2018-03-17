package com.uurobot.yxwlib.cameraEncode;

/**
 * Created by Administrator on 2018/3/7.
 */

public class DataUtil {
        /**
         * 拼接两个数组
         *
         * @param a
         * @param b
         * @return
         */
        public static byte[] concat(byte[] a, byte[] b) {
                byte[] c = new byte[a.length + b.length];
                System.arraycopy(a, 0, c, 0, a.length);
                System.arraycopy(b, 0, c, a.length, b.length);
                return c;
        }
}
