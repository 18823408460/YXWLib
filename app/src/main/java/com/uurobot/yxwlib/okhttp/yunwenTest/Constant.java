package com.uurobot.yxwlib.okhttp.yunwenTest;

/**
 * Created by HouShuai on 2016/8/31.
 */

public class Constant {
    //发送
    public static final int SENDTXT = 0x1;
    public static final int SENDIMAGE = 0x2;
    public static final int SENDVIDEO = 0x3;
    public static final int SENDAUDIO = 0x4;
    public static final int SENDMORE = 0x5;
    //接受
    public static final int FROMIMAGE = 0x6;
    public static final int FROMVIDEO = 0x7;
    public static final int FROMAUDIO = 0x8;
    public static final int FROMMORE = 0x9;
    public static final int FROMTXT = 0x10;
    public static final int FROMTXTMORE = 0x11;
    //主机地址（可变更）
    public static final String HOSTNAME = "http://95558js.faqrobot.cn/";
    //Access_token（可变更）
    public static String ACCESS_TOKEN;
    //设置客户clientId
    public static final String CLIENDID = "5255525611";
    //设置客户sourceId
    public static final int SOURCEID = 9;
    //设置机器人问题类型
    public static final String QUESTION = "aq";
    //设置机器人常在线类型
    public static final String ROBAT_ONLIONING = "aq";
//    public static final String ROBAT_ONLIONING = "kl";
    //设置机器人常用信息
    public static final String ROBAT_INFORMATION = "p";
    //设置机器人获取流程答案接口
    public static final String ROBAT_GWTFLOW = "getflw";
    //设置转人工
    public static final String ROBAT_TOPERSION = "needperson";
    //设置机器人离线类型
    public static final String ROBAT_NOONLION = "offline";
    //设置机器人appid
    public static final String APPID = "zwxMCclbnc5cJHxZJd";
    //    设置机器人sercet
    public static final String SECRET = "QNbUWR1q7P3500595D0B";

    //类型值
    public static class Direct {
        //发送方
        public static final int SEND = 0;
        //接收方
        public static final int RECEIVE = 1;
    }

    public static class Type {
        public static final String TXT = "text";
        public static final String IMAGE = "image";
        //视频类型
        public static final String VIDEO = "video";
        //声音类型
        public static final String VOICE = "voice";
        public static final String RICHTEXT = "richtext";

    }
}
