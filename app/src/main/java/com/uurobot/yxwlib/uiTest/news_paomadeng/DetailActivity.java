package com.uurobot.yxwlib.uiTest.news_paomadeng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

/**
 * Created by chenpengfei on 2016/11/10.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((TextView) findViewById(R.id.detail_tv)).setText(getIntent().getStringExtra("content"));
    }
}
