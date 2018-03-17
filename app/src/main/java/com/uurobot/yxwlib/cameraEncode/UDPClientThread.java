package com.uurobot.yxwlib.cameraEncode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.Handler;
import android.util.Log;

/**
*
 *
 */
public class UDPClientThread extends Thread {
	private static final String TAG = "UDPClientThread";

	private  IRev iRev;

//	private String szIp = "192.168.31.127";
//	private String szIp = "192.168.12.222";
	private String szIp = "127.0.0.1";
	private int nPort = 3288;
	private DatagramSocket mSocket = null;
	private volatile boolean flag = true;
	private byte[] buffer = new byte[1541];// 每一帧1536byte + 5byte


	public UDPClientThread(IRev iRev) {
		this.iRev = iRev ;
		InetSocketAddress socketAddress = new InetSocketAddress(szIp, nPort);
		try {
			mSocket = new DatagramSocket(socketAddress);
		} catch (SocketException e) {
			e.printStackTrace();
			Log.e(TAG, " SocketException ==" + e);
		}
	}

	@Override
	public void run() {
		startReceiver();
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
		while (flag) {
			try {
				mSocket.receive(pack);
				pack.getData();
				parseAudioData(pack);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从接受到的数据中获取 音频数据。。。。。
	 * 
	 * @param pack
	 */
	private void parseAudioData(DatagramPacket pack) {
		byte[] data = pack.getData();
		byte cmd = data[0];
		Log.e(TAG, " cmd ==" + cmd);
		if (cmd == 0) {
			int len = data[1] | (data[2] << 8) | (data[3] << 16) | (data[4] << 24);
			Log.d(TAG, " len ==" + len);
			byte[] audioData = new byte[len]; // audioData就是每一帧的音频数据
			System.arraycopy(data, 5, audioData, 0, len);
			this.iRev.onData(audioData);
		}
	}

	/**
	 * 开始接受数据
	 */
	public void startReceiver() {
		if (mSocket != null) {
			byte[] buffer = new byte[] { 1, 1 };
			try {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(szIp), nPort);
				mSocket.send(dp);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 停止接受数据
	 */
	public void stopReceiver() {
		if (mSocket != null) {
			byte[] buffer = new byte[] { 1, 0 };
			try {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(szIp), nPort);
				mSocket.send(dp);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public interface  IRev{
		void onData(byte[] data);
	}
}
