package com.uurobot.yxwlib.lingju;

import com.lingju.common.adapter.LocationAdapter;

import android.content.Context;

/**
 * 鏋楄仛鎺ュ彛涓幏鍙栧綋鍓嶄綅缃殑鎺ュ彛
 * 
 * @author xiaowei
 *
 */
public class LinJuLocation extends LocationAdapter {

	// 榛樿鐨勫湴鍧�淇℃伅--姣忔寮�鏈洪噸鏂拌幏鍙�
	private double mCurLng = 113.947553;// 缁忓害 锛�
	private double mCurLat = 22.554541; // 绾害 锛�
	private String mCurCity = "娣卞湷甯�";
	private String mCurAddressDetail = "骞夸笢鐪佹繁鍦冲競鍗楀北鍖烘湕灞变簲鍙疯矾闈犺繎绱厜淇℃伅娓�";
	private Context mContext;

	public LinJuLocation(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public double getCurLng() {
		// Logger.d("test", "--" + lng);
		return mCurLng;
	}

	@Override
	public double getCurLat() {
		// Logger.d("test", "--" + lat);
		return mCurLat;
	}

	@Override
	public String getCurCity() {
		return mCurCity;
	}

	@Override
	public String getCurAddressDetail() {
		return mCurAddressDetail;
	}
}
