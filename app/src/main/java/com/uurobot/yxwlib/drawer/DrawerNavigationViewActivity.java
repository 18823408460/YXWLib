package com.uurobot.yxwlib.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/12/10.
 */

public class DrawerNavigationViewActivity extends Activity {

        private static final String TAG = "DrawerActivity";
        DrawerLayout drawerLayout ;
                NavigationView navigationView ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_drawer_navagation);

                navigationView = findViewById(R.id.nav);

                View headerView = navigationView.getHeaderView(0);
                headerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Toast.makeText(DrawerNavigationViewActivity.this,"onClick",Toast.LENGTH_SHORT).show();
                        }
                });
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                Toast.makeText(DrawerNavigationViewActivity.this,""+item.getTitle(),Toast.LENGTH_SHORT).show();
                                return false;
                        }
                });
        }



 }
