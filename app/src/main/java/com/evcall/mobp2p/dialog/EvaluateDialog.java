package com.evcall.mobp2p.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avcon.SDK;
import com.avcon.constant.SatisfactionType;
import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2015/10/20.
 */
public class EvaluateDialog extends Dialog {
    private Button btn_very_satisfaction;
    private Button btn_satisfaction;
    private Button btn_basic_satisfaction;
    private Button btn_dissatisfaction;
    private RelativeLayout rl_evaluate;
    private TextView tv_timeCount;
    private Handler handler;
    private String evaluateNo;
    private int timeCount=10;

    public EvaluateDialog(Context context, int theme) {
        super(context, theme);
    }

    public EvaluateDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_evaluate);
        evaluateNo=getContext().getString(R.string.s_evaluate_no);
        handler=new Handler();
        setCancelable(false);
        initUI();
    }

    private void initUI(){
        btn_very_satisfaction = (Button) findViewById(R.id.btn_very_satisfaction);
        btn_very_satisfaction.setOnClickListener(new ClickListener(SatisfactionType.VERY_SATISFIED));
        btn_satisfaction = (Button) findViewById(R.id.btn_satisfaction);
        btn_satisfaction.setOnClickListener(new ClickListener(SatisfactionType.SATISFIED));
        btn_basic_satisfaction = (Button) findViewById(R.id.btn_basic_satisfaction);
        btn_basic_satisfaction.setOnClickListener(new ClickListener(SatisfactionType.BASIC_SATISFIED));
        btn_dissatisfaction = (Button) findViewById(R.id.btn_dissatisfaction);
        btn_dissatisfaction.setOnClickListener(new ClickListener(SatisfactionType.DISSATISFIED));
        tv_timeCount = (TextView) findViewById(R.id.tv_timecount);
        tv_timeCount.setText(timeCount+evaluateNo);
        handler.postDelayed(runnable,1000);
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            timeCount--;
            if(timeCount<0){
                SDK.onJudgeSatisfaction(SatisfactionType.NOT_EVALUATED);
                dismiss();
            }else{
                tv_timeCount.setText(timeCount+evaluateNo);
                handler.postDelayed(runnable,1000);
            }
        }
    };

    class ClickListener implements View.OnClickListener{

        private SatisfactionType stisfaction;
        protected  ClickListener(SatisfactionType stisfaction){
            this.stisfaction=stisfaction;
        }

        @Override
        public void onClick(View v) {
            handler.removeCallbacks(runnable);
            SDK.onJudgeSatisfaction(stisfaction);
            dismiss();
        }
    }

}
