package com.uurobot.yxwlib.cameraEncode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import android.util.Log;

/**
 * 协议：
 * 1.rk3288 android listen udp port=3288
 * 2.rk3288 recv  cmd(byte)+value(byte):
 * cmd =1
 * value = 1(start send);value = 0 (stop send)
 * 3.rk3288 send voice data: cmd(byte)+len(int)+(16k voice data)//cmd = 0
 */

/**
 * 发送音频数据给Client
 *
 * @author xiaowei
 */
public class UDPServer implements Runnable {

	private static final String TAG = "UDPServer";

	private static final boolean DEBUG = false;

	private String ip = null;

	private static final int port = 3288;

	private DatagramPacket dpRcv = null, dpSend = null;

	private static DatagramSocket ds = null;

	private InetSocketAddress inetSocketAddress = null;

	private byte[] msgRcv = new byte[1024];

	private boolean udpLife = true; // udp生命线程

	private boolean udpLifeOver = true; // 生命结束标志，false为结束

	/**
	 * 是否开始发送数据
	 */
	private boolean startSend = false;

	private void SetSoTime(int ms) throws SocketException {
		ds.setSoTimeout(ms);
	}

	// 返回udp生命线程因子是否存活
	public boolean isUdpLife() {
		return udpLife;
	}

	// 返回具体线程生命信息是否完结
	public boolean getLifeMsg() {
		return udpLifeOver;
	}

	// 更改UDP生命线程因子
	public void setUdpLife(boolean b) {
		udpLife = b;
	}

	/**
	 * 发送音频数据
	 *
	 * @param data
	 */
	public synchronized void send(byte data[]) {
		if (dpRcv != null && startSend) {
			try {
				int length = data.length;
				byte[] cmdData = new byte[1 + 4];
				cmdData[0] = 0; // cmd
				cmdData[1] = (byte) (length & 0xFF);// len
				cmdData[2] = (byte) ((length >> 8) & 0xFF);
				cmdData[3] = (byte) ((length >> 16) & 0xFF);
				cmdData[4] = (byte) ((length >> 24) & 0xFF);
				byte[] packageData = concat(cmdData, data);
				dpSend = new DatagramPacket(packageData, packageData.length, dpRcv.getAddress(), dpRcv.getPort());
				ds.send(dpSend);
				Log.e(TAG, "正在发送数据，长度是 " + data.length);
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "Send IOException");
			}
		}else{
			Log.e(TAG, " null null............... " + data.length);
		}
	}

	/**
	 * 拼接两个数组
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	private byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	@Override
	public void run() {
		// inetSocketAddress = new InetSocketAddress(ip, port);
		try {
			ds = new DatagramSocket(port);
			Log.i(TAG, "UDP服务器已经启动");
			SetSoTime(10000); // ds.receive(dpRcv);超时设置
			// 设置超时，不需要可以删除
		} catch (SocketException e) {
			e.printStackTrace();
			Log.e(TAG, "SocketException--UDP服务器已经启动");
		}
		dpRcv = new DatagramPacket(msgRcv, msgRcv.length);
		while (udpLife) {
			try {
				Log.i(TAG, "UDP监听中");
				ds.receive(dpRcv);
				parseClientCmd(dpRcv.getData(), dpRcv.getLength());
				Log.i(TAG, "客户端IP：" + dpRcv.getAddress().getHostAddress() + "客户端Port:" + dpRcv.getPort());
			} catch (IOException e) {
				e.printStackTrace();
				if (DEBUG) {
					Log.e(TAG, "IOException--UDP监听中");
				}

			}
		}
		ds.close();
		Log.i(TAG, "UDP监听关闭");
		// udp生命结束
		udpLifeOver = false;
	}

	/**
	 * 解析Client发送过来的控制命令
	 */
	private void parseClientCmd(byte data[], int len) {
		if (data != null && len >= 2) {
			if (data[0] == 1) {
				if (data[1] == 1) {
					startSend = true;
					Log.d(TAG, "start send!");
					// MySpeechCompound.getInstance().speaking("开始发送音频数据");
					if (DEBUG) {
						Log.e(TAG, "开始发送数据给客户端");
					}

				} else if (data[1] == 0) {
					startSend = false;
					// MySpeechCompound.getInstance().speaking("停止发送音频数据");
					if (DEBUG) {
						Log.d(TAG, "stop send!");
						Log.e(TAG, "停止发送数据给客户端");
					}

				}
			}
		}
	}
}
