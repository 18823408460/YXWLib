package com.evcall.mobp2p.constant;
/**
 * 用户及连接状态
 * @author ljx
 *
 */
public enum LoginState {
	/**
	 * 初始化
	 */
	INITSTATE,
	/**
	 * 与服务器断开连接
	 */
	DISCONNECT,
	/**
	 * 连接服务器中
	 */
	CONNECTING,
	/**
	 * 已连接服务器
	 */
	CONNECTED,
	/**
	 * 服务器繁忙
	 */
	CONNECT_BUSY,
	/**
	 * 连接服务器失败
	 */
	CONNECT_FAIL,
	/**
	 * 登录成功
	 */
	LOGIN_SUCCESS,
	/**
	 * 登录失败
	 */
	LOGIN_FAIL,
	/**
	 * 脱机
	 */
	LOGIN_OUT,
	/**
	 * 登录中
	 */
	LOGINING
}
