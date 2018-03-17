package com.evcall.mobp2p.constant;
/**
 * 本地网络状态
 * @author Liu
 *
 */
public enum NetState {
	CONNECT_UNKNOW,//未知连接
	CONNECT_ERROR,//无网络连接
	CONNECT_FAIL,//连接不到Internet
	CONNECT_CHANGE,//连接改变
	WIFI_CONNECTING,//WIFI连接中
	WIFI_CONNECTED,//wifi已连接
	MOB_CONNECTING,//移动网络连接中
	MOB_CONNECTED,//已连接移动网络
}
