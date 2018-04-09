
package com.uurobot.yxwlib.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * ������Ϣ�Ļص���
 * 
 * ����ʵ��MqttCallback�Ľӿڲ�ʵ�ֶ�Ӧ����ؽӿڷ���CallBack �ཫʵ�� MqttCallBack��
 * ÿ���ͻ�����ʶ����Ҫһ���ص�ʵ�����ڴ�ʾ���У����캯�����ݿͻ�����ʶ�����Ϊʵ�����ݡ� �ڻص��У�����������ʶ�Ѿ������˸ûص����ĸ�ʵ����
 * �����ڻص�����ʵ������������
 * 
 * public void messageArrived(MqttTopic topic, MqttMessage message)�����Ѿ�Ԥ���ķ�����
 * 
 * public void connectionLost(Throwable cause)�ڶϿ�����ʱ���á�
 * 
 * public void deliveryComplete(MqttDeliveryToken token)) ���յ��Ѿ������� QoS 1 �� QoS 2
 * ��Ϣ�Ĵ�������ʱ���á� �� MqttClient.connect ����˻ص���
 * 
 */
public class PushCallback implements MqttCallback {

	public void connectionLost(Throwable cause) {
		// 连接丢失后，一般在这里面进行重连
		System.out.println("连接断开，可以做重连");
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete---------" + token.isComplete());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		System.out.println("接收消息主题 : " + topic);
		System.out.println("接收消息Qos : " + message.getQos());
		System.out.println("接收消息内容 : " + new String(message.getPayload()));
	}
}