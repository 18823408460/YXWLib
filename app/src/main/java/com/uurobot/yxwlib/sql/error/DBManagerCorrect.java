package com.uurobot.yxwlib.sql.error;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ���ݿ������   �ڷ��������ͬ���������̲߳�����ʱ���ִ��˳��Ϊ������ִ�У��������ݿ��������쳣��
 *   
 */
public class DBManagerCorrect {
	
	  private static DBHelper helper;  
	  private static SQLiteDatabase db;  
	    /**
	     * ������ Ӧ�����þ�̬�������
	     * @param context
	     */
	    private static  int mCount;
	    //ͬһ�����ݿ�����
		private static DBManagerCorrect mMnanagerInstance;  
	    private DBManagerCorrect(Context context) {  
	    	//helper = DBHelper.getInstance(context);
	    }  
	    //����
	    public static synchronized DBManagerCorrect getIntance(Context context){
	    	if(mMnanagerInstance==null){
	    		return new DBManagerCorrect(context);
	    	}
	    	return mMnanagerInstance;
	    }
	    
	    public synchronized SQLiteDatabase openDb(){
	    	if(mCount==0){
	    		db=helper.getWritableDatabase();
	    	}
	    	mCount++;
	    	return db;
	    }
	    public synchronized void CloseDb(SQLiteDatabase database){
	    	mCount--;
	    	if(mCount==0){
	    		database.close();
	    	}
	    }
	    
	    // ����  
	    private void insert(Student student) {  
	            try {  
	                db.execSQL(  
	                        "INSERT INTO " + DBHelper.TABLE + " VALUES(?,?,?)",  
	                        new Object[] {null,student.mName,student.mAge});  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } 
	    }  
	    
	    /**
	     * @param students
	     */
	    public void  insertAll(List<Student> students,Thread thread) {
	    		try {
	    			Log.i("lthj.exchangestock2", "helper����Ϊ��="+helper.toString()+"�߳���Ϊ��"+thread.getName());
	    			Log.i("lthj.exchangestock2", "db����Ϊ="+db.toString()+"�߳���Ϊ��"+thread.getName());
					if (students == null) {  
						return;  
					}  
					for (Student student : students) {  
							insert(student); 
//							Log.i("lthj.exchangestock2", "���ڲ��롣������=+"+student.getmID()+student.getmName());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    
	    /**
	     * @param student
	     */
	    private void update(Student student) {  
	    		try {  
	    			db.execSQL("UPDATE " + DBHelper.TABLE  
	    					+ " SET mName = ? WHERE _id = ?",  
	    					new Object[] { student.mName,student.mID });  
	    			Log.e("lthj.exchangestock2", "���ڸ��¡�������=+"+student.getmID()+student.getmName());
	    		} catch (SQLException e) {  
	    			e.printStackTrace();  
	    		} catch (Exception e) {  
	    			e.printStackTrace();  
	    		} 
	    }  
	    
	    /**
	     * ��������
	     * @param students
	     */
		  public void  updateAll(List<Student> students) {
		        try {
					if (students == null) {  
					    return;  
					}  
					for (Student student : students) {  
					    	update(student);  
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }  
	    
	    // ɾ��ָ������  
	   public void delete(String id) {  
	            try {  
	                db.execSQL("DELETE FROM " + DBHelper.TABLE + " WHERE _id = ? ",  
	                        new String[] { id });  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	    }  
	  
	    // ɾ����������  
	    public void delete() {  
	            try {  
	                db.execSQL("DELETE * FROM " + DBHelper.TABLE);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	    }  
	  
	    // �������е�Students  
	public List<Student> query() {  
	        List<Student> students = new ArrayList<Student>();  
	            Cursor c = queryTheCursor();  
	            Student student = null;  
	            try {  
	                while (c.moveToNext()) {  
	                	student=new Student();
	                	student.mID=c.getString(c.getColumnIndex("_id"));
	                	student.mName=c.getString(c.getColumnIndex("mName"));
	                	student.mAge=c.getInt(c.getColumnIndex("mAge"));
	                    students.add(student);  
	                    Log.i("lthj.exchangestock2", "���ڲ�ѯ��������=+"+student.getmID()+student.getmName());
	                    student=null;
	                }  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	        return students;  
	    }  
	  
	    // ����ָ��ID��Student  
	    public Student query(String id) {  
	        Student student = null;  
	            Cursor c = queryTheCursor(id);  
	            if(c==null)return null;
	            try {  
	                while (c.moveToNext()) { 
	                	student=new Student();
	                	student.mID=c.getString(c.getColumnIndex("_id"));
	                	student.mName=c.getString(c.getColumnIndex("mName"));
	                	student.mAge=c.getInt(c.getColumnIndex("mAge"));  
	                    break;  
	                }  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	        return student;  
	    }  
	  
	    // ��ȡ�α�  
	    public Cursor queryTheCursor(String id) {  
	    	try {
	    		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE  
	    				+ " WHERE _id = ?", new String[] { id });  
	    		return c;  
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	    }  
	  
	    // ��ȡ�α�  
	    public Cursor queryTheCursor() {  
	    	try {
	    		  if (!db.isOpen()) {  
	    			  db = helper.getWritableDatabase();  
		            } 
	    		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE, null);  
	    		return c;  
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();return null;
			}
	    }  
	    
	    
}
