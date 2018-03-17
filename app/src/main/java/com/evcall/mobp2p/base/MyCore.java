package com.evcall.mobp2p.base;

import android.content.Context;
import android.hardware.Camera;

import com.avcon.SDK;
import com.avcon.bean.Business;
import com.avcon.bean.BusinessCategoriy;
import com.avcon.bean.UserInfo;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.constant.LoginState;
import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

public class MyCore {
    final String TAG = "MyCore";

    private Context context;

    protected MyCore(Context context) {
        this.context = context;
    }

    private int previewWidth=640,previewHeight=480;
    private boolean isServer = false;//默认网点用户类型
    private LoginState loginState = LoginState.INITSTATE;
    private boolean userOnline;            //用户是否在线
    private boolean isAnonymityLogin;
    private String inputLoginUserId;    //录入的登录用户
    private String inputLoginPwd;        //录入的登录密码
    private String loginUserIdForDomain;//登录成功后回调的用户
    private String loginUserName;        //登录成功后回调的用户名称
    private boolean openCenterState = false;//排队服务是否开启
    private List<BusinessCategoriy> businessCategoriyList = new ArrayList<BusinessCategoriy>();//业务类别列表

    private BusinessCategoriy clickCategoriy;
    private String mIp;
    private int mLoginMode;
    private boolean bServerLogin;
    private boolean bCallIn;
    private UserInfo p2pUser;
    private boolean bCalling;
    public  String inputName;
    public boolean bWaitCall;

    public boolean isCalling(){
        return bCalling;
    }
    public void setbCalling(boolean isCalling){
        bCalling=isCalling;
    }

    public void setbWaitCall(boolean waitCall,String inputName){
        this.inputName=inputName;
        bWaitCall=waitCall;
    }
    /**
     * 是否为呼入请求
     * @return
     */
    public boolean isbCallIn(){return bCallIn;}
    public UserInfo getP2pUser() {
        return p2pUser;
    }
    /**
     * 设置呼叫用户
     * @param callIn 呼入请求
     * @param p2pUser 用户
     */
    public void setP2pUser(boolean callIn,UserInfo p2pUser) {
        this.bCallIn=callIn;
        this.p2pUser = p2pUser;
    }

    public boolean isbServerLogin() {
        return bServerLogin;
    }

    public void setbServerLogin(boolean bServerLogin) {
        this.bServerLogin = bServerLogin;
    }

    public void setIp(String ip) {
        this.mIp = ip;
    }

    public void setLoginMode(int loginMode) {
        this.mLoginMode = loginMode;
    }

    public void setLoginUser(String userId,String pwd){
        inputLoginUserId=userId;
        inputLoginPwd=pwd;
    }
    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewSize(Camera.Size size) {
        this.previewWidth = size.width;
        this.previewHeight = size.height;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    /**
     * 匿名登录
     */
    private void loginForAnonymity() {
        if (isUserOnline()) {
            SDK.onLogout();
        }
        isAnonymityLogin = true;
        inputLoginUserId = null;
        inputLoginPwd = null;
        loginUserIdForDomain = null;
        loginUserName = null;
        SDK.onLoginForAnonymity();
    }

    /**
     * 输入用户名和密码登录
     */
    private void loginForUser() {
        if (isUserOnline()) {
            SDK.onLogout();
        }
        isAnonymityLogin = false;
        loginUserIdForDomain = null;
        loginUserName = null;
        SDK.onLogin(inputLoginUserId, inputLoginPwd, bServerLogin);
    }

    /**
     * 登录，自动区分匿名和非匿名
     */
    public boolean onLogin(){
//        if (isUserOnline()) return false;
        if (mIp == null || mIp.trim().length() <= 4)  return false;
        SDK.setHostAddr(mIp);
        if(mLoginMode==1){//匿名
            loginForAnonymity();
        }else if(mLoginMode==2){//实名
            if(inputLoginUserId==null||inputLoginPwd==null)
                return false;
            loginForUser();
        }
        return true;
    }
    /**
     * 脱机
     */
    public void onLoginOut() {
        if (isUserOnline()) {
            SDK.onLogout();
        }
        loginState = LoginState.LOGIN_OUT;
        inputLoginUserId = null;
        inputLoginPwd = null;
        loginUserIdForDomain = null;
        loginUserName = null;
    }

    /**
     * 设置登录成功后回馈的用户信息
     *
     * @param userIdForDomain
     * @param userName
     */
    public void setLoginSuccessUserInfo(String userIdForDomain, String userName) {
        loginUserIdForDomain = userIdForDomain;
        loginUserName = userName;
    }

    /**
     * 用户是否登录成功 在线
     *
     * @return
     */
    public boolean isUserOnline() {
        return userOnline;
    }

    /**
     * 获取登录服务器状态
     *
     * @return
     */
    public LoginState getLoginState() {
        return loginState;
    }

    /**
     * 设置登录相关的handler
     *
     * @param logingHandler
     */
    public void setLogingHandler(HandlerType logingHandler) {
        switch (logingHandler) {
            case LOGIN_DISCONNECT:
                loginState = LoginState.DISCONNECT;
                userOnline = false;
                break;
            case LOGIN_CONNECTING:
                loginState = LoginState.CONNECTING;
                userOnline = false;
                break;
            case LOGIN_CONNECTED:
                loginState = LoginState.CONNECTED;
                break;
            case LOGIN_CONNECT_BUSY:
                loginState = LoginState.CONNECT_BUSY;
                break;
            case LOGIN_CONNECT_FAIL:
                loginState = LoginState.CONNECT_FAIL;
                userOnline = false;
                break;
            case LOGIN_RECONNECT:
                if (loginState != LoginState.LOGIN_SUCCESS) {
                    loginState = LoginState.CONNECTED;
                }
                break;
            case LOGIN_SUCCESS:
                loginState = LoginState.LOGIN_SUCCESS;
                userOnline = true;
                break;
            case LOGIN_OUT:
                loginState = LoginState.LOGIN_OUT;
                userOnline = false;
                break;
            case LOGIN_FAIL:
                loginState = LoginState.LOGIN_FAIL;
                userOnline = false;
                break;
            case LOGIN_LOADING:
                loginState = LoginState.LOGINING;
                userOnline = false;
                break;
            default:
                break;
        }
    }

    /**
     * 获取文字提示
     *
     * @param handlerType
     * @return
     */
    public String getHandlerTypeString(HandlerType handlerType) {
        String str = "";
        switch (handlerType) {
            case LOGIN_DISCONNECT:
                str = context.getString(R.string.n_server_disconnect);
                break;
            case LOGIN_CONNECTING:
                str = context.getString(R.string.n_server_conneting);
                break;
            case LOGIN_CONNECTED:
                str = context.getString(R.string.n_server_connected);
                break;
            case LOGIN_CONNECT_BUSY:
                str = context.getString(R.string.n_server_busy);
                break;
            case LOGIN_CONNECT_FAIL:
                str = context.getString(R.string.n_server_fail);
                break;
            case LOGIN_RECONNECT:
                str = context.getString(R.string.n_server_reconnected);
                break;
            case LOGIN_SUCCESS:
                str = context.getString(R.string.n_server_login_success);
                break;
            case LOGIN_OUT:
                str = context.getString(R.string.n_server_login_out);
                break;
            case LOGIN_FAIL:
                str = context.getString(R.string.n_server_login_false);
                break;
            case LOGIN_LOADING:
                str = context.getString(R.string.n_server_logining);
                break;
            case CENTER_SERVER_CLOSE:
                str = context.getString(R.string.n_server_center_close);
                break;
            case CENTER_SERVER_OPEN:
                str = context.getString(R.string.n_server_center_open);
                break;
            default:

                break;
        }
        return str;
    }

    /**
     * 获取登录失败提示
     *
     * @param errcode
     * @return
     */
    public String getLoginFailMsg(int errcode) {
        String msg = "" + errcode;
        if (errcode == 20001) {
            msg = context.getString(R.string.error_user);
        } else if (errcode == 20002) {
            msg = context.getString(R.string.error_pwd);
        } else if (errcode == 20003) {
            msg = context.getString(R.string.error_online);
        } else if (errcode == 20004) {
            msg = context.getString(R.string.error_relogin);
        } else if (errcode == 20021) {
            msg = context.getString(R.string.error_usertype);
        }
        return msg;
    }

    /**
     * 设置排队服务状态
     *
     * @param open
     */
    public void setOpenCenterState(boolean open) {
        this.openCenterState = open;
    }

    /**
     * 获取排队服务状态
     *
     * @return true:已开启
     */
    public boolean isOpenCenterState() {
        return this.openCenterState;
    }

    /**
     * 注入业务类别列表
     *
     * @param businessCategoriyList
     */
    public void setBusinessCategoriyList(List<BusinessCategoriy> businessCategoriyList) {
        this.businessCategoriyList.clear();
        if(businessCategoriyList==null){return;}
        this.businessCategoriyList.addAll(businessCategoriyList);
    }

    /**
     * 获取业务类别列表
     *
     * @return
     */
    public List<BusinessCategoriy> getBusinessCategoriyList() {
        return this.businessCategoriyList;
    }

    /**
     * 记录当前点击的类别
     *
     * @param clickCategoriy
     */
    public void setClickCategoriy(BusinessCategoriy clickCategoriy) {
        this.clickCategoriy = clickCategoriy;
    }

    /**
     * 获取当前点击的业务类别
     *
     * @return
     */
    public BusinessCategoriy getClickCategoriy() {
        return this.clickCategoriy;
    }

    /****************************************
     * 业务、呼叫相关数据
     ********************************************/
    private Business clickBusiness;//当前点击的业务

    /**
     * 设置点击的业务
     *
     * @param business
     */
    public void setClickBusiness(Business business) {
        this.clickBusiness = business;
    }

    /**
     * 获取呼叫的业务
     *
     * @return
     */
    public Business getClickBusiness() {
        return this.clickBusiness;
    }

}
