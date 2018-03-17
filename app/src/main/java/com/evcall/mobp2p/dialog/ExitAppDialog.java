package com.evcall.mobp2p.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avcon.utils.AvcAnimator;
import com.uurobot.yxwlib.R;


public class ExitAppDialog extends AlertDialog{
	private Button mBtnOK;
	private Button mBtnCancel;
	private ExitAppListener listener;
	private AvcAnimator animator;

 	public ExitAppDialog(Activity context,int theme) {
		super(context, theme);
		animator=new AvcAnimator();
	}

	public ExitAppDialog(Activity context) {
		super(context);
		animator=new AvcAnimator();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_exit_app);
		initUI();
	}
	
	private void initUI() {
		mBtnOK = (Button) findViewById(R.id._btnExitAppOK);
		mBtnCancel = (Button) findViewById(R.id._btnExitAppCancel);
		mBtnOK.setAnimation(animator.getASetPanOpenLeft_Right());
		animator.getASetPanOpenLeft_Right().start();
		mBtnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClickExit();
				dismiss();
			}
		});
		
		mBtnCancel.setAnimation(animator.getASetPanOpenRight_Left());
		animator.getASetPanOpenRight_Left().start();
		mBtnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public void setExitAppListener(ExitAppListener exitAppListener){
		this.listener=exitAppListener;
	}
	
	public interface ExitAppListener{
		void onClickExit();
	}
}
