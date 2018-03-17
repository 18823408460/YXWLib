package com.uurobot.yxwlib.lingju;

public class Motion {
	
	private int motion;
	
	private long index;
	
	private String level;

	public int getMotion() {
		return motion;
	}

	public void setMotion(int motion) {
		this.motion = motion;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Motion(int motion, long index, String level) {
		super();
		this.motion = motion;
		this.index = index;
		this.level = level;
	}

	public Motion() {
		super();
	}

	@Override
	public String toString() {
		return "MotionRaw [motion=" + motion + ", index=" + index + ", level=" + level + "]";
	}
	
	

}
