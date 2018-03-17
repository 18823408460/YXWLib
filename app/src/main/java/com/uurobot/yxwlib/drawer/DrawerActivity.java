package com.uurobot.yxwlib.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/12/10.
 */

public class DrawerActivity extends Activity {

        private static final String TAG = "DrawerActivity";
        DrawerLayout drawerLayout ;
        ListView listView ;
        MenuAdapter menuAdapter ;
        LinearLayout frameLayout ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_drawer);

                drawerLayout = findViewById(R.id.drawerLayout);
                listView = findViewById(R.id.menuList);
                listView.setAdapter(new MenuAdapter(this));

                frameLayout = findViewById(R.id.mainLayout);
                Button button = new Button(this);
                button.setText("mainlayout");
                Button button1 = new Button(this);
                button1.setText("mainlayout1111");
                frameLayout.addView(button);
                frameLayout.addView(button1);

        }


        private class DrawerMenuToggle extends ActionBarDrawerToggle{

                public DrawerMenuToggle(Activity activity, DrawerLayout drawerLayout, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
                        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        Logger.e(TAG,"....onDrawerClosed....");
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        Logger.e(TAG,"....onDrawerOpened....");
                }
        }
 }
