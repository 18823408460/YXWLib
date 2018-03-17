package com.uurobot.yxwlib.lingju;

public interface IChatListener {
	/**
	 * 鍝嶅簲 鐨勭粨鏋�
	 * 
	 * @param isSuccess
	 *            鏄惁鎴愬姛
	 * @param result
	 *            鍝嶅簲缁撴灉
	 */
	public void onResponseResult(boolean isSuccess, String result, String session);
}
