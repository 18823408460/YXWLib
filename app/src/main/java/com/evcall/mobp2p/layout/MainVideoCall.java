package com.evcall.mobp2p.layout;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avcon.SDK;
import com.avcon.callback.CallbackP2PTalk;
import com.avcon.callback.CallbackShareResource;
import com.avcon.callback.CallbackTalk;
import com.avcon.constant.CodeType;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.MainActivity;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.constant.NetState;
import com.evcall.mobp2p.views.CallVideoView;
import com.evcall.mobp2p.views.CallWaitingView;
import com.evcall.mobp2p.views.LayoutView;
import com.uurobot.yxwlib.R;

import org.utils.view.annotation.ViewInject;

/**
 * 主界面之 视频呼叫
 *
 * @author ljx
 */
public class MainVideoCall extends LayoutMain implements LayoutView.OnClickLayoutView {


    @ViewInject(value = R.id.llRoot)
    LinearLayout llRoot;

    private Dialog offlineDialog;
    private Dialog exitDialog;
    private boolean showEvaluate;
    private boolean cameraException;
    private SurfaceView locationView;
    private View remoteView;
    private int remoteWidth,remoteHeight;
    private boolean remoteRo=true;

    /**
     * 构造对象，在对象不再使用时，调用onDestroy方法体，加快垃圾回收
     *
     * @param mainActivity
     * @param myCore
     */
    public MainVideoCall(MainActivity mainActivity, MyCore myCore) {
        super(mainActivity, myCore,R.layout.main_video_call);
    }

    @Override
    protected void initData() {
        core.setbCalling(true);
        CallWaitingView callWaitingView = new CallWaitingView(activity, this);
        setLayoutView(callWaitingView);
        SDK.setSendStreamMode(true);
    }

    public void onStartCall(boolean p2p){
        if(layoutMain instanceof CallWaitingView){
            if(p2p){
                core.setbWaitCall(false,null);
                ((CallWaitingView)layoutMain).setP2P(core.isbCallIn(),core.getP2pUser(),callbackP2PTalk );
            }else{
                ((CallWaitingView)layoutMain).setBusiness(core.getClickBusiness(), callbackTalk);
            }
        }
    }
    @Override
    public void updataLayoutView(Message msg) {

    }

    @Override
    public void updateNetState(NetState state) {

    }


    @Override
    public void onDestroy() {
        AvcLog.printI(TAG,"onDestroy","===>");
        if (layoutMain != null) ((LayoutView) layoutMain).onDestroy();
        if(activity!=null)activity.changeAudioMode(false);
        super.onDestroy();
        showDialogExiting(false);
        showDialogOffline(false);
    }
    private void setLayoutView(LayoutView view) {
        if (layoutMain != null) {
            llRoot.removeView(layoutMain);
            ((LayoutView) layoutMain).onDestroy();
        }
        layoutMain = view;
        llRoot.addView(view);
    }

    /**
     * 显示视频界面
     */
    private void showVideoTalkView() {
        if (cameraException) {
            Toast.makeText(activity, R.string.error_connect_camera, Toast.LENGTH_SHORT).show();
        }
        CallVideoView callViedeoView = new CallVideoView(activity, this);
        setLayoutView(callViedeoView);
        callViedeoView.setVideoSize(locationView, remoteView);
        SDK.setCallbackShareResource(callbackShareResource);
        activity.changeAudioMode(true);
        if(remoteWidth<=0||remoteHeight<=0)
            return;
        Bundle bundle = new Bundle();
        bundle.putBoolean("b", remoteRo);
        bundle.putInt("w", remoteWidth);
        bundle.putInt("h", remoteHeight);
        Message message = Message.obtain();
        message.what = HandlerType.TALK_REMOTE_SIZE.ordinal();
        message.setData(bundle);
  //      callViedeoView.updateMessage(message);
    }

    private void showDialogExiting(boolean isShow) {
        if (isShow) {
            if (exitDialog == null&&activity!=null) {
                exitDialog = new Dialog(activity, R.style.Dialog);
                exitDialog.setContentView(R.layout.dialog_loading);
                exitDialog.setCancelable(false);
                exitDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
                exitDialog.show();
            }
        } else {
            if (exitDialog != null) {
                exitDialog.dismiss();
                exitDialog = null;
            }
        }
    }

    private void showDialogOffline(boolean isShow) {
        if (isShow) {
            if (offlineDialog == null&&activity!=null) {
                offlineDialog = new Dialog(activity, R.style.Dialog);
                offlineDialog.setContentView(R.layout.dialog_tip_offline);
                offlineDialog.setCancelable(false);
                offlineDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
                TextView tv_tip_sure= (TextView) offlineDialog.findViewById(R.id.tv_tip_sure);
                tv_tip_sure.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onExitMainVideoCall(showEvaluate);
                    }
                });
                offlineDialog.show();
            }
        } else {
            if (offlineDialog != null) {
                offlineDialog.dismiss();
                offlineDialog = null;
            }
        }
    }

    @Override
    public void onAccept() {
        SDK.onCallResponse(callbackP2PTalk);
    }

    private int clickNum = 0;
    private long clickTimeMillis = 0;

    @Override
    public void onHangup() {
        long currentTimeMillis = System.currentTimeMillis();
        SDK.onStopCall();
        if ((currentTimeMillis - clickTimeMillis) <= 1000 && clickNum >= 3) {
            activity.onExitMainVideoCall(showEvaluate);
            return;
        }
        clickTimeMillis = currentTimeMillis;
        clickNum++;
    }

    @Override
    public void onToggleSpeaker(boolean on) {
        if (on)
            activity.changeToHandfree();
        else
            activity.changeToHead();
    }

    @Override
    public void onToggleCamera(boolean front) {
        SDK.onToggleCamera();
    }

    private void serverAnswer(boolean isAgree) {
        if (isAgree) {
            sendMessage(HandlerType.INDEX_ANSWER, null);
        } else {
            String msg = activity.getResources().getString(R.string.s_user_hangup);
            showToast(msg);
        }
    }

    private void p2p() {
        sendMessage(HandlerType.TALK_ENTER, null);
    }

    void showToast(String msg) {
        Message message = Message.obtain();
        message.what = -1;
        message.obj = msg;
        handler.sendMessage(message);
    }
    private CallbackTalk callbackTalk=new CallbackTalk() {

        @Override
        public void OnException(int errCode, boolean isMcuCode) {
            exception(errCode);
        }

        @Override
        public void OnConnecting() {
            sendMessage(HandlerType.INDEX_CONNECTING, null);
        }

        @Override
        public void OnIndex(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("i", i);
            sendMessage(HandlerType.INDEX_UPDATE, bundle);
        }

        @Override
        public void OnBusinessListener(Boolean aBoolean) {
        }

        @Override
        public void OnLineup(String userID, String userName,
                             String nodeID, boolean errLineup) {
            if (errLineup) {
                String msg = activity.getResources().getString(R.string.error_center_line) + userName;
                showToast(msg);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("s", userID);
            bundle.putString("s1", userName);
            bundle.putString("s2", nodeID);
            sendMessage(HandlerType.INDEX_LINEUP, bundle);
        }

        @Override
        public void OnLineupResponse(String userID, String userName, String headUrl) {
            Bundle bundle = new Bundle();
            bundle.putString("s", userID);
            bundle.putString("s1", userName);
            bundle.putString("s2", headUrl);
            sendMessage(HandlerType.INDEX_LINEUP_RESPONSE, null);
        }

        @Override
        public void OnServerAnswer(String userID, boolean isAgree, int errCode) {
            serverAnswer(isAgree);
        }

        @Override
        public void OnExitWaiting(int code) {
            Bundle bundle = new Bundle();
            bundle.putInt("i", code);
            sendMessage(HandlerType.TALK_EXIT_WAITING, bundle);
        }

        @Override
        public void OnExitSuccess() {
            sendMessage(HandlerType.TALK_EXIT_SUCCESS, null);
        }

        @Override
        public void OnP2P(boolean cameraException, SurfaceView locationView, View remoteView) {
            MainVideoCall.this.cameraException = cameraException;
            MainVideoCall.this.locationView = locationView;
            MainVideoCall.this.remoteView = remoteView;
            p2p();
        }

        @Override
        public void OnRemoteVideoSize(boolean normal, int width, int height) {
            remoteVideoSize(normal, width, height);
        }

        @Override
        public void OnPauseOrRever(boolean isPause) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("b", isPause);
            sendMessage(HandlerType.TALK_PAUSE_REVER, bundle);
        }

        @Override
        public void OnAudioOrVideo(boolean isAudio) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("b", isAudio);
            sendMessage(HandlerType.TALK_AUDIO_VIDEO, bundle);
        }

        @Override
        public void OnGetSignFile(String s) {

        }

        @Override
        public void OnShowSign(String s) {

        }

        @Override
        public void OnCloseSign() {

        }

        @Override
        public void OnImReceiveMessage(String cmdType, String userData) {
        }

        @Override
        public void OnTransferBusiness(String s, String s1) {

        }

    };

    private void exception(int errCode) {
        String msg = activity.getResources().getString(R.string.error_code) + errCode;
        showToast(msg);
    }

    private void remoteVideoSize(boolean normal, int width, int height) {
        AvcLog.printD(TAG,"OnRemoteVideoSize","normal:"+normal+",width:"+width+",height:"+height);
        remoteRo=normal;
        remoteWidth=width;
        remoteHeight=height;
        Bundle bundle = new Bundle();
        bundle.putBoolean("b", normal);
        bundle.putInt("w", width);
        bundle.putInt("h", height);
        sendMessage(HandlerType.TALK_REMOTE_SIZE, bundle);
    }

    private CallbackP2PTalk callbackP2PTalk=new CallbackP2PTalk() {
        @Override
        public void OnAnswer(String userID, boolean isAgree, int errCode) {
            serverAnswer(isAgree);
        }

        @Override
        public void OnP2P(boolean cameraException, SurfaceView locationView, View remoteView) {
            MainVideoCall.this.cameraException = cameraException;
            MainVideoCall.this.locationView = locationView;
            MainVideoCall.this.remoteView = remoteView;
            p2p();
        }

        @Override
        public void OnRemoteVideoSize(boolean normal, int width, int height) {
            remoteVideoSize(normal, width, height);
        }

        @Override
        public void OnException(int errCode, boolean isMcuCode) {
            exception(errCode);
        }

        @Override
        public void OnExitWaiting(int code) {
            Bundle bundle = new Bundle();
            bundle.putInt("i", code);
            sendMessage(HandlerType.TALK_EXIT_WAITING, bundle);
        }

        @Override
        public void OnExitSuccess() {
            sendMessage(HandlerType.TALK_EXIT_SUCCESS, null);
        }

        @Override
        public void OnImReceiveMessage(String cmdType, String userData) {

        }
    };
    private CallbackShareResource callbackShareResource = new CallbackShareResource() {
        @Override
        public void OnShowShareResource(String imageId) {
            sendMessage(HandlerType.TALK_SHARE_RESOURCE_SHOW, null);
        }

        @Override
        public void OnCloseShareResource() {
            sendMessage(HandlerType.TALK_SHARE_RESOURCE_CLOSE, null);
        }

        @Override
        public void OnWaitDownload(String imageId) {

        }

        @Override
        public void OnDownloading(String imageId) {

        }

        @Override
        public void OnSuccess(String imageId, Bitmap bitmap, boolean isClick) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("SHARE_IMAGE", bitmap);
            sendMessage(HandlerType.TALK_SHARE_RESOURCE_SUCCESS, bundle);
        }

        @Override
        public void OnFail(String imageId) {

        }
    };

    synchronized void sendMessage(HandlerType handlerType, Bundle data) {
        if (layoutMain == null || !(layoutMain instanceof LayoutView)) {
            return;
        }
        Message message = Message.obtain();
        message.what = handlerType.ordinal();
        if (data != null) message.setData(data);
        handler.sendMessage(message);
    }

    Handler handler = new Handler() {
        int waitingInt=0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what < 0) {
                Object obj = msg.obj;
                if (obj != null) {
                    Toast.makeText(activity, obj.toString(), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            HandlerType handlerType = HandlerType.values()[what];
            switch (handlerType) {
                case TALK_EXIT_WAITING:
                    Bundle b=msg.getData();
                    try {
                        waitingInt=b.getInt("i");
                    } catch (Exception e) {
                    }
                    if(waitingInt== CodeType.CALL_WAITING_NO_SERVER){
                        showDialogOffline(true);
                    }else{
                        showDialogExiting(true);
                    }
                    break;
                case TALK_EXIT_SUCCESS:
                    if(waitingInt!= CodeType.CALL_WAITING_NO_SERVER&&activity!=null){
                        activity.onExitMainVideoCall(showEvaluate);
                    }
                    break;
                case TALK_ENTER:
                    showEvaluate = true;
                    showVideoTalkView();
                    break;
                default:
                    LayoutView callView = (LayoutView) layoutMain;
                    callView.updateMessage(msg);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

    }
}
