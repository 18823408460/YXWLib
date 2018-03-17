package com.evcall.mobp2p.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

import com.avcon.base.AvcApp;
import com.evcall.mobp2p.constant.NetState;

public class NetReceiver extends BroadcastReceiver {
	static String TAG="NetReceiver";
	private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	
	private ConnectivityManager connectivityManager;
	private NetworkInfo  mobNetInfo,wifiNetInfo,networkInfo;
	private OnNetConnectListener connectListener;

	public NetReceiver(Context context,OnNetConnectListener connectListener) {
		super();
		this.connectListener = connectListener;
		this.connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		this.connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			this.mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.networkInfo = connectivityManager.getActiveNetworkInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.networkInfo != null) {
			if (intent.getAction().equals(NET_CHANGE_ACTION)) {
				connectListener.OnConnectChange();
			}
			if(!this.networkInfo.isAvailable()){
				connectListener.OnConnectFail();
				return;
			}
			if(wifiNetInfo!=null){
				State stateWifi = wifiNetInfo.getState();
				if (stateWifi == State.CONNECTING) {
					connectListener.OnWifiConnecting();
	            }else if(stateWifi == State.CONNECTED){
	            	connectListener.OnWifiConnected();
	            }
			}
			if(mobNetInfo!=null){
				State stateMob = mobNetInfo.getState();
				if (stateMob == State.CONNECTING) {
					connectListener.OnMobConnecting();
	            }else if(stateMob == State.CONNECTED){
	            	connectListener.OnMobConnected();
	            }
			}
		}else{
			connectListener.OnConnectError();
//			Toast.makeText(AvcApp.the(), context.getResources().getString(R.string.n_net_error), Toast.LENGTH_LONG).show();
		}
	}
	/**
	 * 获取当前网络连接状态
	 * @return
	 */
	public NetState getNetState(){
		this.networkInfo = connectivityManager.getActiveNetworkInfo(); 
		if(networkInfo==null){ 
			return NetState.CONNECT_ERROR;
		}else if(!networkInfo.isAvailable()){
			return NetState.CONNECT_FAIL;
		}
		int nType = networkInfo.getType(); 
		State state;
		if(nType==ConnectivityManager.TYPE_MOBILE){ 
			this.mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(mobNetInfo!=null){
				state = mobNetInfo.getState();
				if (state == State.CONNECTING) {
					return NetState.MOB_CONNECTING;
	            }else if(state == State.CONNECTED){
	            	return NetState.MOB_CONNECTED;
	            }
			}
		}else if(nType==ConnectivityManager.TYPE_WIFI){ 
			this.wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifiNetInfo!=null){
				state = wifiNetInfo.getState();
				if (state == State.CONNECTING) {
					return NetState.WIFI_CONNECTING;
	            }else if(state == State.CONNECTED){
	            	return NetState.WIFI_CONNECTED;
	            }
			}
		}
		return NetState.CONNECT_UNKNOW;
	}
	
	public interface OnNetConnectListener{
		/**
		 * 无可用网络
		 */
		void OnConnectError();
		/**
		 * 无法连接到网络
		 */
		void OnConnectFail();
		/**
		 * 网络改变
		 */
		void OnConnectChange();
		/**
		 * Wifi连接中
		 */
		void OnWifiConnecting();
		/**
		 * Wifi连接成功
		 */
		void OnWifiConnected();
		/**
		 * 移动网络连接中
		 */
		void OnMobConnecting();
		/**
		 * 移动网络连接成功
		 */
		void OnMobConnected();
	}
	
}
