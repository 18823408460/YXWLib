package com.uurobot.yxwlib.sql.error;

import java.io.Serializable;

/**
 *   ʵ���� 
 */
public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	public  String mID;  
	public  String mName;  
	public int mAge;
	
	public Student() {
	}
	public Student(String mName, int mAge) {
		super();
		this.mName = mName;
		this.mAge = mAge;
	}
	public String getmID() {
		return mID;
	}
	public void setmID(String mID) {
		this.mID = mID;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getmAge() {
		return mAge;
	}
	public void setmAge(int mAge) {
		this.mAge = mAge;
	}
	@Override
	public String toString() {
		return "Student [mID=" + mID + ", mName=" + mName + ", mAge=" + mAge
				+ "]";
	}
	
}
