package com.uurobot.yxwlib.meterial;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uurobot.yxwlib.R;


/**
 * Created by Administrator on 2017/12/11.
 */

public class CoordinatorActivity extends AppCompatActivity {
        FloatingActionButton floatingActionButton ;
        RecyclerView recyclerView ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_coordination);
                //initActionBar();
                initVIew();
        }

        private void initActionBar(){
                android.support.v7.app.ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("helloTitle");
                actionBar.setLogo(R.mipmap.ic_launcher);

                //下面两个都要打开，才可以显示出来。。
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
        }

        private void initVIew() {
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);

                recyclerView.setAdapter(new RecycleAdapter(this));
                floatingActionButton = (FloatingActionButton) findViewById(R.id.floatBtn);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Snackbar.make(v,"hello2222",Snackbar.LENGTH_LONG).setAction("hide2222", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                }).show();
                        }
                });
        }
}
