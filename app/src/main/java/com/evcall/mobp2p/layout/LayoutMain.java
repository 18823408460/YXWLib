package com.evcall.mobp2p.layout;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.MainActivity;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.NetState;

import org.utils.x;

/**
 * 主布局的基类
 * @author ljx
 */
public abstract class LayoutMain extends RelativeLayout implements View.OnClickListener{

	protected final String TAG=getClass().getSimpleName();

	protected LayoutInflater layoutInflater;
	protected View layoutMain;
	protected MyCore core;
	protected MainActivity activity;
	/**
	 * 构造对象，在对象不再使用时，调用onDestroy方法体，加快垃圾回收
	 * @param myCore
	 * @param mainActivity
	 */
	public LayoutMain(MainActivity mainActivity, MyCore myCore,int layoutResourceId) {
		super(mainActivity);
		this.layoutInflater = LayoutInflater.from(mainActivity);
		this.core=myCore;
		this.activity=mainActivity;
		View convertView=layoutInflater.inflate(layoutResourceId, null);
		this.addView(convertView);
		x.view().inject(this);
		initData();
	}
	/**
	 * 构造函数中的初始化数据
	 */
	protected abstract void initData();
	/**
	 * 主线程中执行
	 * 接收父类中传入的消息通知
	 * @param msg
     */
	public abstract void updataLayoutView(Message msg);
	/**
	 * 主线程中执行
	 * @param state
     */
	public abstract void updateNetState(NetState state);
	/**
	 * 销毁本对象持有的所有外部对象及父对象，加快回收
	 */
	public void onDestroy(){
		AvcLog.printI(TAG,"onDestroy","===>");
		layoutMain =null;
		layoutInflater=null;
		core=null;
		activity=null;
	}
}
