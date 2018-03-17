package com.evcall.mobp2p.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avcon.SDK;
import com.avcon.bean.Business;
import com.avcon.bean.UserInfo;
import com.avcon.callback.CallbackP2PTalk;
import com.avcon.callback.CallbackTalk;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.utils.AudioPlayUtil;
import com.uurobot.yxwlib.R;

/**
 * 呼叫等待界面
 * 向排队服务发起呼叫——>客服应答
 * @author ljx
 */
public class CallWaitingView extends LayoutView{

	private ImageView ivAnswer,ivCallHandup,ivCallCamera;
	private TextView tvBusiness,tvExplan;
	private Business business;
	private TextView tvCalling2,tvCalling3,tvCalling1;
	private FrameLayout cameraPreview;
	private LinearLayout llBusinessInfo;
	@Override
	public void onDestroy() {
		AvcLog.printI(TAG,"onDestroy","--->");
		clickLayoutView=null;
		ivAnswer=null;
		ivCallHandup=null;
		ivCallCamera=null;
		tvBusiness=null;
		tvExplan=null;
		business=null;
		tvCalling1=null;
		tvCalling2=null;
		tvCalling3=null;
		stopPreview();
		AudioPlayUtil.getInstance().stopAudio();
	}

	public CallWaitingView(Context context,OnClickLayoutView clickLayoutView) {
		super(context, R.layout.layoutview_call_waiting,clickLayoutView);
	}

	@Override
	protected void initView() {
		cameraPreview= (FrameLayout) findViewById(R.id.cameraPreview);
		ivAnswer= (ImageView) findViewById(R.id.ivAnswer);
		ivCallHandup= (ImageView) findViewById(R.id.ivCallHandup);
		ivCallCamera= (ImageView) findViewById(R.id.ivCallCamera);
		tvBusiness= (TextView) findViewById(R.id.tvBusiness);
		tvExplan= (TextView) findViewById(R.id.tvExplan);
		tvCalling1= (TextView) findViewById(R.id.tvCalling1);
		tvCalling2= (TextView) findViewById(R.id.tvCalling2);
		tvCalling3= (TextView) findViewById(R.id.tvCalling3);
		llBusinessInfo= (LinearLayout) findViewById(R.id.llBusinessInfo);

		ivAnswer.setOnClickListener(this);
		ivCallHandup.setOnClickListener(this);
//		ivCallCamera.setOnClickListener(this);
		cameraPreview.addView(SDK.onStartLocalPreview());
	}

	/**
	 * 设置业务信息并呼叫
	 * @param business
	 * @param callbackTalk
     */
	public void setBusiness(Business business, CallbackTalk callbackTalk){
		this.business=business;
		tvBusiness.setText(business.getName());
		tvExplan.setText(business.getDescription());
		SDK.onStartCall(business,callbackTalk);
		AudioPlayUtil.getInstance().startAudio(getContext());
	}

	/**
	 * 设置呼叫用户，并呼叫
	 * @param callIn
	 * @param userInfo
	 * @param callbackP2PTalk
     */
	public void setP2P(boolean callIn, UserInfo userInfo, CallbackP2PTalk callbackP2PTalk){
		llBusinessInfo.setVisibility(GONE);
		tvCalling2.setText(userInfo.getUserName());
		if(callIn){
			tvCalling1.setText("收到 ");
			tvCalling3.setText(" 呼叫");
			ivAnswer.setVisibility(VISIBLE);
		}else{
			tvCalling1.setText("呼叫 ");
			tvCalling3.setText(" 中");
			SDK.onStartCall(userInfo,callbackP2PTalk);
		}
		AudioPlayUtil.getInstance().startAudio(getContext());
	}
	private void stopPreview(){
		if(cameraPreview!=null){
			cameraPreview.removeAllViews();
			cameraPreview=null;
		}
	}
	@Override
	public void updateMessage(Message msg) {
		int what=msg.what;
		if(what<0||what>= HandlerType.values().length){return;}
		HandlerType handlerType=HandlerType.values()[what];
		Bundle data=msg.getData();
		switch (handlerType){
			case INDEX_CONNECTING: {
				tvCalling1.setText(R.string.s_geting);
				tvCalling2.setText(business.getName());
				tvCalling3.setText(R.string.s_center_server);
				tvCalling3.setVisibility(VISIBLE);
				tvCalling2.setVisibility(VISIBLE);
				break;
			}
			case INDEX_UPDATE: {
				int i = data.getInt("i");
				tvCalling1.setText(R.string.s_idx_prefix);
				tvCalling2.setText(""+i);
				tvCalling3.setText(R.string.s_idx_suffix);
				break;
			}
			case INDEX_LINEUP: {
				String userName=data.getString("s1");
				tvCalling1.setText(R.string.s_line_prefix);
				tvCalling2.setText(userName);
				tvCalling3.setText(R.string.s_line_suffix);
				break;
			}
			case INDEX_LINEUP_RESPONSE:{
				tvCalling1.setText(R.string.s_line2_prefix);
				tvCalling3.setText(R.string.s_line2_suffix);
				break;
			}
			case INDEX_ANSWER:{
				ivCallHandup.setEnabled(false);
				tvCalling1.setVisibility(GONE);
				tvCalling3.setText(R.string.s_user_answer);
				stopPreview();
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ivAnswer: {
				clickLayoutView.onAccept();
				break;
			}
			case R.id.ivCallHandup:
				hangup();
				break;
			case R.id.ivCallCamera:
				Boolean b = toggleCamera();
				if(b==null){return;}
				if(b){ivCallCamera.setBackgroundResource(R.mipmap.img_camera_normal);}
				else{ivCallCamera.setBackgroundResource(R.mipmap.img_camera_press);}
				break;

		}
	}
}
