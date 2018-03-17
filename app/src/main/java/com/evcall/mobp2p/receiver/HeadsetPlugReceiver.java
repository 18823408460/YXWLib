package com.evcall.mobp2p.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HeadsetPlugReceiver extends BroadcastReceiver {
	
	private HeadsetPlugListener listener;
	
	public HeadsetPlugReceiver(HeadsetPlugListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		if(intent.hasExtra("state")){  
            if(intent.getIntExtra("state", 0)==0){//未插入
            	listener.onPullout();
            } else if(intent.getIntExtra("state", 0)==1){//插入
            	listener.onCutin();
            }  
        }  
	}

	public interface HeadsetPlugListener{
		/**
		 * 拔出耳机
		 */
		void onPullout();
		/**
		 * 插入耳机
		 */
		void onCutin();
	}
}
