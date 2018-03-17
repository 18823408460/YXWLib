package com.evcall.mobp2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avcon.Config;
import com.avcon.SDK;
import com.avcon.bean.Business;
import com.avcon.bean.BusinessCategoriy;
import com.avcon.bean.UserInfo;
import com.avcon.callback.CallbackBusiness;
import com.avcon.callback.CallbackLogin;
import com.avcon.constant.CodeType;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.base.App;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.constant.NetState;
import com.evcall.mobp2p.dialog.EvaluateDialog;
import com.evcall.mobp2p.dialog.ExitAppDialog;
import com.evcall.mobp2p.layout.LayoutMain;
import com.evcall.mobp2p.layout.MainBusiness;
import com.evcall.mobp2p.layout.MainCategory;
import com.evcall.mobp2p.layout.MainLogin;
import com.evcall.mobp2p.layout.MainVideoCall;
import com.evcall.mobp2p.receiver.NetReceiver;
import com.uurobot.yxwlib.R;

import org.utils.view.annotation.ContentView;
import org.utils.view.annotation.ViewInject;
import org.utils.x;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    final String TAG = getClass().getSimpleName();
    public static final String IP="IP";
    public static final String USER_NAME="USERNAME";
    public static final String USER_PWD="USERPWD";

    @ViewInject(value = R.id.rootView)
    RelativeLayout rootView;
    @ViewInject(value = R.id.tvShow)
    TextView tvShow;

    private MyCore core;
    private SharedPreferences spf;
    private Display display;
    private NetReceiver netReceiver;
    private LayoutMain layoutMain;
    private MainCategory mainCategory;


    /**
     * 显示初始化时的布局视图中的按钮
     */
    final int HANDLE_INIT_BTN = -2;
    final int HANDLE_P2P_CALL=-3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        rootView = findViewById(R.id.rootView);
        core = App.CORE;
        SDK.initSDK(2, true, true);
        spf = getSharedPreferences("EVCALL", MODE_PRIVATE);
        audioManager = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));
        netReceiver = new NetReceiver(this, connectListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, intentFilter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mHeadSetReceiver, filter);
        WindowManager windowManager = getWindowManager();
        display = windowManager.getDefaultDisplay();
        SDK.setDepartId(Config.DEPART_ALL);
        SDK.setUserMcu(true);
        SDK.setTcpOrUdp(false,false);
        SDK.setCallbackLogin(callbackLogin);
        SDK.setCallbackBusiness(callbackBusiness);
        SDK.setMediaParame(15, 512, core.getPreviewWidth(), core.getPreviewHeight());
        initLoginView();
    }
    /**
     * 开始登录
     */
    public void startLogin() {
        boolean b=core.onLogin();
        if(!b){
            Toast.makeText(this, "参数异常，登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeLayoutMain(LayoutMain layout){
        if(layoutMain!=null&&!(layoutMain instanceof MainCategory)){
            layoutMain.onDestroy();
        }
        rootView.removeAllViews();
        layoutMain=layout;
        rootView.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }
    /**
     * 登录界面
     */
    private void initLoginView(){
        changeLayoutMain(new MainLogin(this, core,spf));
    }
    /**
     * 显示业务明细视图
     */
    public void onShowMainBsiness() {
        changeLayoutMain(new MainBusiness(this,core));
    }

    /**
     * 显示类别视图
     */
    public void onShowMainCategory() {
        if(mainCategory==null){
            mainCategory=new MainCategory(this,core);
        }
        core.setbCalling(false);
        core.setP2pUser(false,null);
        changeLayoutMain(mainCategory);
    }

    /**
     * 显示呼叫视图
     */
    public void onShowMainCall(boolean p2p) {
        if (!core.isUserOnline()||core.isCalling()) return;
        MainVideoCall mainVideoCall=new MainVideoCall(this,core);
        changeLayoutMain(mainVideoCall);
        mainVideoCall.onStartCall(p2p);
    }
    /**
     * 退出呼叫通话界面
     * 会自动使用上一个界面显示
     *
     * @param showEvaluate 是否显示评价窗体
     */
    public void onExitMainVideoCall(boolean showEvaluate) {
        if (showEvaluate) {
            EvaluateDialog dialog = new EvaluateDialog(this, R.style.Dialog);
            dialog.show();
        }
        onShowMainCategory();
    }
    private boolean selectHead;
    private AudioManager audioManager;
    private boolean hasHeadset = false;//是否插入耳机，默认没有
    /**
     * 检测耳机的广播
     */
    private BroadcastReceiver mHeadSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int n = 0;
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                n++;
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        hasHeadset = false;
                        if (!selectHead) {
                            audioManager.setSpeakerphoneOn(true);
                        }
                        if (n > 1)
                            Toast.makeText(context, R.string.s_headset_out, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        hasHeadset = true;
                        audioManager.setSpeakerphoneOn(false);//关闭扬声器
                        if (n > 1)
                            Toast.makeText(context, R.string.s_headset_find, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    };
    /**
     * 切换到免提
     */
    public void changeToHandfree() {
        if (!hasHeadset)
            audioManager.setSpeakerphoneOn(true);
        selectHead = false;
    }

    /**
     * 切换到听筒
     */
    public void changeToHead() {
        audioManager.setSpeakerphoneOn(false);//关闭扬声器
        selectHead = true;
    }

    /**
     * 改变音频模式
     *
     * @param call
     */
    public void changeAudioMode(boolean call) {
        changeToHandfree();
        if (call) {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            } else {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
            }
        } else {
            audioManager.setMode(AudioManager.STREAM_SYSTEM);
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        }
    }

    /**
     * 发送消息到主线程
     * 本类处理
     *
     * @param what
     * @param obj
     */
    private synchronized void sendActivityMessage(int what, Object obj) {
        Message message = Message.obtain();
        message.obj = obj;
        message.what = what;
        handlerActivity.sendMessage(message);
    }

    /**
     * 发送消息给子布局视图
     * 由子布局负责通知主线程 负责更新本身的视图
     *
     * @param handlerType
     * @param data
     */
    private synchronized void sendLayoutMessage(HandlerType handlerType, Bundle data) {
//        if (layoutMain == null) return;
        Message message = Message.obtain();
        message.what = handlerType.ordinal();
        if (data != null) message.setData(data);
        handlerLayout.sendMessage(message);
    }


/************************************** 监听实现 ******************************************/
    private NetReceiver.OnNetConnectListener connectListener = new NetReceiver.OnNetConnectListener() {
        private void sendNetMessage(NetState netState) {
//            if (layoutMain != null) {
//                layoutMain.updateNetState(netState);
//            }
//            if (layoutMain instanceof MainVideoCall && (NetState.WIFI_CONNECTED == netState || NetState.MOB_CONNECTED == netState)) {
//                SDK.onReflashStream();
//            }
        }

        @Override
        public void OnWifiConnecting() {
            AvcLog.printD(TAG, "OnWifiConnecting", "------>OnWifiConnecting");
            sendNetMessage(NetState.WIFI_CONNECTING);
        }

        @Override
        public void OnWifiConnected() {
            AvcLog.printD(TAG, "OnWifiConnected", "------>OnWifiConnected");
            sendNetMessage(NetState.WIFI_CONNECTED);
//            login();
            Toast.makeText(MainActivity.this, getResources().getString(R.string.n_net_connected_wifi), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnMobConnecting() {
            AvcLog.printD(TAG, "OnMobConnecting", "------>OnMobConnecting");
            sendNetMessage(NetState.MOB_CONNECTING);
        }

        @Override
        public void OnMobConnected() {
            AvcLog.printD(TAG, "OnMobConnected", "------>OnMobConnected");
//            login();
            sendNetMessage(NetState.MOB_CONNECTED);
            Toast.makeText(MainActivity.this, getResources().getString(R.string.n_net_connected_mob), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnConnectFail() {
            AvcLog.printD(TAG, "OnConnectFail", "------>OnConnectFail");
            sendNetMessage(NetState.CONNECT_FAIL);
            Toast.makeText(MainActivity.this, getResources().getString(R.string.n_net_fail), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnConnectError() {
            AvcLog.printD(TAG, "OnConnectError", "------>OnConnectError");
            sendNetMessage(NetState.CONNECT_ERROR);
        }

        @Override
        public void OnConnectChange() {
            AvcLog.printD(TAG, "OnConnectChange", "------>OnConnectChange");
            sendNetMessage(NetState.CONNECT_CHANGE);
        }
    };

/**************************************** 实现回调接口 **************************************************/
    private CallbackLogin callbackLogin = new CallbackLogin() {
        @Override
        public void OnServerConnecting() {
            HandlerType handlerType = HandlerType.LOGIN_CONNECTING;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnServerBusy() {
            HandlerType handlerType = HandlerType.LOGIN_CONNECT_BUSY;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnServerFailed() {
            HandlerType handlerType = HandlerType.LOGIN_CONNECT_FAIL;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
            sendLayoutMessage(handlerType, null);
        }

        @Override
        public void OnServerConnected() {
            HandlerType handlerType = HandlerType.LOGIN_CONNECTED;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnServerReconnected() {
            HandlerType handlerType = HandlerType.LOGIN_RECONNECT;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnServerDisconnected() {
            HandlerType handlerType = HandlerType.LOGIN_DISCONNECT;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnLoginSuccess(String userIdForDomain, String userName) {
            HandlerType handlerType = HandlerType.LOGIN_SUCCESS;
            core.setLogingHandler(handlerType);
            core.setLoginSuccessUserInfo(userIdForDomain, userName);
            sendActivityMessage(HANDLE_INIT_BTN, null);
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnLoginFail(int i) {
            HandlerType handlerType = HandlerType.LOGIN_FAIL;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), i);
        }

        @Override
        public void OnLoginOut(String s) {
            HandlerType handlerType = HandlerType.LOGIN_OUT;
            core.setLogingHandler(handlerType);
            sendActivityMessage(handlerType.ordinal(), s);
        }

        @Override
        public void OnMcuTime(String s) {

        }

        @Override
        public void OnCenterServerState(boolean b) {
            core.setOpenCenterState(b);
            HandlerType handlerType;
            if (b) {
                handlerType = HandlerType.CENTER_SERVER_OPEN;
            } else {
                handlerType = HandlerType.CENTER_SERVER_CLOSE;
            }
            sendActivityMessage(handlerType.ordinal(), null);
        }

        @Override
        public void OnGetWebPort(int port) {
        }

        @Override
        public void OnCallRequest(UserInfo userInfo) {
            core.setP2pUser(true,userInfo);
            sendActivityMessage(HANDLE_P2P_CALL,null);
        }

        @Override
        public void OnCallHangup(final int i) {
            handlerActivity.post(new Runnable() {
                @Override
                public void run() {
                    if(i!= CodeType.CALL_HANGUP_MY){
                        Toast.makeText(getApplicationContext(), "对方已挂断", Toast.LENGTH_LONG).show();
                    }
                    onShowMainCategory();
                }
            });
        }

        @Override
        public void OnServerBackCall(Business business, String s, String s1) {

        }

        @Override
        public void OnUpdateCustom(UserInfo userInfo) {

        }

        @Override
        public void OnGetCustomList(List<UserInfo> list) {

        }

        @Override
        public void OnReceiveMsg(String s, String s1, String s2) {

        }

        @Override
        public void OnGetUserInfo(UserInfo userInfo) {
            String status=userInfo.getUserStatus();
            AvcLog.printI(TAG, "OnGetUserInfo", "status:"+status);
            String msg="用户状态异常";
            if(status!=null){
                if(status.equals("offline")){
                    msg="用户不在线";
                }else if(status.equals("busy")||status.equals("meeting")){
                    msg="用户忙线中";
                }else if(core.bWaitCall){
                    userInfo.setUserName(core.inputName);
                    core.setP2pUser(false,userInfo);
                    sendActivityMessage(HANDLE_P2P_CALL,null);
                    return;
                }
            }
            final String finalMsg = msg;
            handlerActivity.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private CallbackBusiness callbackBusiness = new CallbackBusiness() {

        @Override
        public void OnFail(int i) {

        }

        @Override
        public void OnUpdateing() {
            core.setBusinessCategoriyList(null);
            sendLayoutMessage(HandlerType.MSG_BUSINESS_UPDAING, null);
        }

        @Override
        public void OnGetItem(BusinessCategoriy businessCategoriy) {

        }

        @Override
        public void OnGetItemEnd() {

        }

        @Override
        public void OnGetAllBySort(List<BusinessCategoriy> list) {
            core.setBusinessCategoriyList(list);
            sendLayoutMessage(HandlerType.MSG_BUSINESS_FINISH, null);
        }
    };


/**************************************  Handler **************************************************/
    /**
     * 负责向子布局视图发送主线程消息
     */
    private Handler handlerLayout = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HandlerType handlerType = HandlerType.values()[what];
            if (handlerType == HandlerType.MSG_BUSINESS_UPDAING || handlerType == HandlerType.MSG_BUSINESS_FINISH) {
                if (mainCategory != null)
                    mainCategory.updataLayoutView(msg);
                return;
            }
            layoutMain.updataLayoutView(msg);
        }
    };
    /**
     * 接收本类中的消息处理
     */
    private Handler handlerActivity = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == HANDLE_INIT_BTN) {//显示初始布局的按钮
                onShowMainCategory();
                return;
            }else if(what == HANDLE_P2P_CALL){//主动发起P2P呼叫
                onShowMainCall(true);
                return;
            }
            if (what < 0 || what >= HandlerType.values().length) {
                return;
            }
            HandlerType handlerType = HandlerType.values()[what];
            Object obj = msg.obj;
            changeServerState(handlerType, obj);
        }

        private void changeServerState(HandlerType handlerType, Object obj) {
            boolean textShow = false;
            switch (handlerType) {
                case LOGIN_DISCONNECT:
                    textShow = true;
                    break;
                case LOGIN_CONNECTING:
                    textShow = true;
                    break;
                case LOGIN_CONNECTED:
                    textShow = true;
                    break;
                case LOGIN_CONNECT_BUSY:
                    textShow = true;
                    break;
                case LOGIN_CONNECT_FAIL:
                    textShow = true;
                    break;
                case LOGIN_RECONNECT:
                    textShow = false;
                    break;
                case LOGIN_SUCCESS:
                    textShow = false;
                    break;
                case LOGIN_OUT:
                    textShow = true;
                    break;
                case LOGIN_FAIL:
                    if (obj != null && obj instanceof Integer) {
                        int err = Integer.valueOf(obj.toString());
                        obj = core.getLoginFailMsg(err);
                    }
                    textShow = true;
                    break;
                case LOGIN_LOADING:
                    textShow = true;
                    break;
                case CENTER_SERVER_CLOSE:
                    textShow = true;
                    break;
                case CENTER_SERVER_OPEN:
                    textShow = false;
                    break;
                default:
                    break;
            }
            changeTextView(textShow, handlerType, obj);
        }

        private void changeTextView(boolean show, HandlerType handlerType, Object obj) {
            if (!show) {
                tvShow.setText("");
                tvShow.setVisibility(View.GONE);
                return;
            }
            String str = core.getHandlerTypeString(handlerType);
            if (obj != null) {
                str += ":" + obj;
            }
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setText(str);
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((layoutMain == null) || (!(layoutMain instanceof MainVideoCall))) {
                if(layoutMain!=null&&layoutMain instanceof MainBusiness){
                    onShowMainCategory();
                    return true;
                }
                ExitAppDialog dialog = new ExitAppDialog(this, R.style.Dialog);
                dialog.setExitAppListener(new ExitAppDialog.ExitAppListener() {
                    @Override
                    public void onClickExit() {
                        finish();
                    }
                });
                dialog.show();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth()); //设置宽度
                dialog.getWindow().setAttributes(lp);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
