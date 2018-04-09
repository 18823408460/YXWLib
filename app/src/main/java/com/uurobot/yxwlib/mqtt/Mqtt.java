package com.uurobot.yxwlib.mqtt;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.uurobot.yxwlib.alarm.Logger;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

/**
 * 全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的，
 * 是由一个十六位的数字组成,表现出来的 形式。由以下几部分的组合：当前日期和时间(UUID的第一个部分与时间有关
 */

/**
 * Created by Administrator on 2018/4/9.
 */

public class Mqtt {

        private MqttAndroidClient mqttAndroidClient;

        private MqttConnectOptions options;

        private static final String BROKER_IP = "tcp://120.76.133.10:61613";

        public static final String TOPIC1 = "topic1";

        public static final String TOPIC2 = "topic2";

        private String clientId;

        private static final int RECONNECT = 1;

        private static Mqtt instance;

        private static final String USERNAME = "admin";

        private static final String PASSWORD = "password";

        private static final String TAG = Mqtt.class.getName();

        private Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                        switch (msg.what) {
                                case RECONNECT: {
                                        Logger.e(TAG, "reconnect");
                                        connect();//当发生连接失败的情况时继续连接。通常只发生在服务器未在线情况，一旦服务器上线，将立刻连接。
                                        break;
                                }
                        }
                }
        };

        private Mqtt(Context context) {

                clientId = UUID.randomUUID().toString();
//                clientId = AndroidUniqueIdUtil.getUniqueID(context);
                options = new MqttConnectOptions();
                options.setCleanSession(false);
//            // 设置连接的用户名
                options.setUserName(USERNAME);
                // 设置连接的密码
                options.setPassword(PASSWORD.toCharArray());
                // 设置超时时间 单位为秒
                options.setConnectionTimeout(10);
                // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                options.setKeepAliveInterval(20);
                //options.setAutomaticReconnect(true);
                try {
                        mqttAndroidClient = new MqttAndroidClient(context, BROKER_IP, clientId, new MemoryPersistence());
                }
                catch (Exception e) {
                        e.printStackTrace();
                        Logger.i(TAG, "create mqtt client error");
                }
                mqttAndroidClient.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                                Logger.i(TAG, "lost:" + cause);
                                sendReconnectMsg();
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                                Logger.i(TAG, "receiveMsg:topic" + topic + ",msg:" + message.toString());
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                                Logger.i(TAG, "msg published");
                        }
                });

        }

        private void sendReconnectMsg() {
                if (!handler.hasMessages(RECONNECT)) {
                        handler.sendEmptyMessageDelayed(RECONNECT, 2000);
                }
        }

        public static Mqtt getInstance(Context context) {
                if (null == instance) {
                        synchronized (Mqtt.class) {
                                instance = new Mqtt(context);
                        }
                }
                return instance;
        }

        /**
         * 连接服务器
         * MqttService有自己的重连机制，在断线情况下会重连，但是首次连接失败后，需要再调用connect方法
         */
        public void connect() {

                try {
                        mqttAndroidClient.connect(options, this, new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                        Logger.i(TAG, "connected");
                                        try {
                                                mqttAndroidClient.subscribe(new String[]{TOPIC1, TOPIC2}, new int[]{2, 2});
                                        }
                                        catch (MqttException e) {
                                                e.printStackTrace();
                                        }
                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                        Logger.i(TAG, "connect error:" + exception);
                                        sendReconnectMsg();

                                }
                        });
                }
                catch (MqttException e) {
                        e.printStackTrace();
                        sendReconnectMsg();
                }
        }

        /**
         * 断开服务器链接
         */
        public void disConnect() {
                if (null == mqttAndroidClient) {
                        return;
                }
                try {
                        mqttAndroidClient.disconnect();
                }
                catch (MqttException e) {
                        e.printStackTrace();
                }
                Logger.i(TAG, "disconnected");
        }

        /**
         * 发布消息
         *
         * @param topic topic
         * @param msg   消息内容
         * @param qos   0：最多一次的传输；1：至少一次的传输；2： 只有一次的传输
         */
        public void publish(String topic, String msg, int qos) {
                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setPayload(msg.getBytes());
                mqttMessage.setRetained(true);
                mqttMessage.setQos(qos);
                try {
                        IMqttDeliveryToken token = mqttAndroidClient.publish(topic, mqttMessage);
                        token.waitForCompletion();
                }
                catch (MqttException e) {
                        e.printStackTrace();
                        Logger.e(TAG, "publish===" + e);
                }
        }

}