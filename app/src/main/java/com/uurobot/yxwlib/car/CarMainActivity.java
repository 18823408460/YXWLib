package com.uurobot.yxwlib.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/3/16.
 */

public class CarMainActivity extends BaseActivity {

        private static final String TAG = "CarMainActivity";
        public static final String[] tabTitle = new String[]{"综艺1", "体育2", "新闻3", "热点4", "头条5", "军事6", "历史7", "科技8", "人文9"};

        @BindView(R.id.search_car)
        SearchView searchCar;

        @BindView(R.id.left)
        ImageView left;

        @BindView(R.id.right)
        ImageView right;

        @BindView(R.id.frameLayout)
        FrameLayout frameLayout;

        FirstFragment firstFragment;
        SecondeFragment secondeFragment;

        @BindView(R.id.bottom_car_1)
        TextView bottomCar1;

        @BindView(R.id.bottom_car_2)
        TextView bottomCar2;

        @BindView(R.id.bottom_car_3)
        TextView bottomCar3;

        @BindView(R.id.bottom_car_4)
        TextView bottomCar4;

        @BindView(R.id.bottom_car_5)
        TextView bottomCar5;
        FragmentManager supportFragmentManager;
        int index =1 ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_car);
                ButterKnife.bind(this);


                firstFragment = new FirstFragment();
                secondeFragment = new SecondeFragment();
                 supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, firstFragment).commit();
                bottomCar1.setPressed(true);
        }

        @OnClick(R.id.bottom_car_1)
        public void first(){
               if (index != 1 ){
                       FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.frameLayout, firstFragment).commit();
                       bottomCar1.setPressed(true);
                       bottomCar2.setPressed(false);
                       index =  1;
                       Log.e(TAG, "first:  press");
               }
        }

        @OnClick(R.id.bottom_car_2)
        public void second(){
                if (index != 2 ){
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, secondeFragment).commit();
                        bottomCar1.setPressed(false);
                        bottomCar2.setPressed(true);
                        index =  2 ;
                        Log.e(TAG, "second: press" );
                }
        }
}
