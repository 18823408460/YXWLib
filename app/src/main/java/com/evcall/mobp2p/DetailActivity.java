package com.evcall.mobp2p;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

import org.utils.x;

public class DetailActivity extends AppCompatActivity {

    private ImageView iv_back;
    private TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        setContentView(R.layout.activity_detail);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_back.setVisibility(View.VISIBLE);
        tvVersion= (TextView) findViewById(R.id.tvVersion);
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            String versionStr=tvVersion.getText().toString();
            tvVersion.setText("Version:" + version +versionStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
