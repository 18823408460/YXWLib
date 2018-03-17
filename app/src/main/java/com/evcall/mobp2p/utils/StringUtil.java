package com.evcall.mobp2p.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ljx on 2017/7/12.
 */
public class StringUtil {
    /**
     * 判断输入的IP格式是否正确
     *
     * @param addr
     * @return
     */
    public static boolean isIpAddr(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        //判断IP格式和范围
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }

    /**
     * 判断输入的手机号是否正确
     *
     * @param phoneNumber
     * @return
     */
    public static  boolean isPhoneNumber(String phoneNumber) {
        String telRegex = "[1][358]\\d{9}";//"[1]"&#x4ee3;&#x8868;&#x7b2c;1&#x4f4d;&#x4e3a;&#x6570;&#x5b57;1&#xff0c;"[358]"&#x4ee3;&#x8868;&#x7b2c;&#x4e8c;&#x4f4d;&#x53ef;&#x4ee5;&#x4e3a;3&#x3001;5&#x3001;8&#x4e2d;&#x7684;&#x4e00;&#x4e2a;&#xff0c;"\\d{9}"&#x4ee3;&#x8868;&#x540e;&#x9762;&#x662f;&#x53ef;&#x4ee5;&#x662f;0&#xff5e;9&#x7684;&#x6570;&#x5b57;&#xff0c;&#x6709;9&#x4f4d;&#x3002;
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            return phoneNumber.matches(telRegex);
        }
    }
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
