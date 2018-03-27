package com.uurobot.yxwlib.sql.error;

import android.database.sqlite.SQLiteDatabase;


public abstract class DBUtil {

    /**
     * ������ Ӧ�����þ�̬�������
     * @param context
     */
/**
 * �����ݿ��db  �����������ж�����ݿ�
 */
    public  abstract SQLiteDatabase openDb();
	
	/**
	 * �ر����ݿ��db
	 */
    public abstract void CloseDb(SQLiteDatabase database);
    
}
