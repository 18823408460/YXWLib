package com.evcall.mobp2p.layout;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avcon.SDK;
import com.avcon.base.AvcCore;
import com.evcall.mobp2p.MainActivity;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.NetState;
import com.evcall.mobp2p.utils.StringUtil;
import com.uurobot.yxwlib.R;

import org.utils.view.annotation.ViewInject;

/**
 * Created by ljx on 2017/7/12.
 */
public class MainLogin extends LayoutMain{

    @ViewInject(value = R.id.radioGroup)
    RadioGroup radioGroup;
    @ViewInject(value = R.id.editTextIp)
    EditText editTextIp;
    @ViewInject(value = R.id.editTextUser)
    EditText editTextUser;
    @ViewInject(value = R.id.editTextPwd)
    EditText editTextPwd;
    @ViewInject(value = R.id.btnEnter)
    Button btnEnter;
    @ViewInject(value = R.id.tvVersion)
    TextView tvVersion;
    @ViewInject(value = R.id.llUser)
    LinearLayout llUser;
    @ViewInject(value = R.id.checkBox)
    CheckBox checkBox;

    private SharedPreferences spf;
    private String ip;
    private String userId;
    private String pwd;
    private int type = 0;

    public MainLogin(MainActivity mainActivity, MyCore core, SharedPreferences spf){
        super(mainActivity,core,R.layout.main_view_login);
        this.spf=spf;
        ip = spf.getString(MainActivity.IP, "");
        editTextIp.setText(ip);
        editTextUser.setText(spf.getString(MainActivity.USER_NAME, ""));
        editTextPwd.setText(spf.getString(MainActivity.USER_PWD, ""));
    }

    @Override
    protected void initData() {
        try {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            String version = info.versionName;
            tvVersion.setText("软件版本:" + version + " , SDK版本:" + AvcCore.MARKNAME + "." + SDK.VERSION_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editTextIp.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbm1:
                        btnEnter.setText(R.string.c_enter1);
                        llUser.setVisibility(View.GONE);
                        checkBox.setChecked(false);
                        break;
                    case R.id.rbm2:
                        btnEnter.setText(R.string.c_enter2);
                        llUser.setVisibility(View.VISIBLE);
                        userId = null;
                        pwd = null;
                        break;
                    default:
                        break;
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                core.setbServerLogin(isChecked);
            }
        });
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void updataLayoutView(Message msg) {

    }

    @Override
    public void updateNetState(NetState state) {

    }
    /**
     * 保存偏好设置
     */
    private void saveConfig(String key,String value) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /**
     * 登入按钮的点击事件
     *
     */
    @Override
    public void onClick(View v) {
        ip = editTextIp.getText().toString();
        if (ip == null || ip.trim().length() <= 0) {
            editTextIp.setError(activity.getResources().getString(R.string.s_validate));
            return;
        }
        if (!StringUtil.isIpAddr(ip)) {
            editTextIp.setError(activity.getResources().getString(R.string.s_ip_error));
            return;
        }
        saveConfig(activity.IP,ip);
        core.setIp(ip);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == R.id.rbm1) {//匿名登入
            userId = null;
            pwd = null;
            type = 1;
        } else if (id == R.id.rbm2) {//验证用户进入
            userId = editTextUser.getText().toString();
            pwd = editTextPwd.getText().toString();
            if (userId == null || userId.trim().length() < 1) {
                editTextUser.setError(activity.getResources().getString(
                        R.string.s_validate));
                return;
            }
            if (pwd == null || pwd.trim().length() < 1) {
                editTextPwd.setError(activity.getResources()
                        .getString(R.string.s_validate));
                return;
            }
            saveConfig(activity.USER_NAME,userId);
            saveConfig(activity.USER_PWD,pwd);
            type = 2;
            core.setLoginUser(userId,pwd);
        }else{
            return;
        }
        core.setLoginMode(type);
        activity.startLogin();
    }
}
