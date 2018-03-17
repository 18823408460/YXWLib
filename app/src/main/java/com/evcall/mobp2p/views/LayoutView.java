package com.evcall.mobp2p.views;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 呼叫的视图布局的基类
 * @author ljx
 */
public abstract class LayoutView  extends RelativeLayout  implements View.OnClickListener{
    protected final String TAG=getClass().getSimpleName();

    protected OnClickLayoutView clickLayoutView;
    private boolean isSpeakerOn=true;//默认开启扬声器
    private boolean isFront=true;//默认开启前置

    public LayoutView(Context context,int layoutResId,OnClickLayoutView clickLayoutView) {
        super(context);
        LayoutInflater.from(context).inflate(layoutResId,this);
        this.clickLayoutView=clickLayoutView;
        initView();
    }

    protected abstract void initView();

    public abstract void onDestroy();
    /**
     * 必须在主线程中调用
     * @param msg
     */
    public  abstract  void updateMessage(Message msg);

    /**
     * 扬声器切换
     * @return null:未执行，true:已切换至免提，false:已切换至听筒
     */
    public Boolean toggleSpeaker(){
        if(clickLayoutView!=null){
            isSpeakerOn=!isSpeakerOn;
            clickLayoutView.onToggleSpeaker(isSpeakerOn);
            return isSpeakerOn;
        }
        return null;
    }

    /**
     * 挂断
     * @return true:执行了挂断
     */
    protected boolean hangup(){
        if(clickLayoutView!=null){
            clickLayoutView.onHangup();
            return true;
        }
        return false;
    }
    /**
     * 摄像头切换
     * @return null:未执行，true:已切换至前置，false:已切换至后置
     */
    protected Boolean toggleCamera(){
        if(clickLayoutView!=null){
            isFront=!isFront;
            clickLayoutView.onToggleCamera(isFront);
            return isFront;
        }
        return null;
    }
    /**
     * 接听
     * @return true:已执行接听
     */
    protected boolean accept(){
        if(clickLayoutView!=null){
            clickLayoutView.onAccept();
            return true;
        }
        return false;
    }
    public interface OnClickLayoutView{
        /**
         * 接听
         */
        public void onAccept();
        /**
         * 挂断
         */
        public void onHangup();

        /**
         * 外放和听筒切换
         * @param on true:外放
         */
        public void onToggleSpeaker(boolean on);

        /**
         * 前置和后置摄像头切换
         * @param front true:前置
         */
        public void onToggleCamera(boolean front);
    }
}
