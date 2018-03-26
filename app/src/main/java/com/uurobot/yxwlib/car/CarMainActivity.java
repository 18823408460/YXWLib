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
        private static final int page_one = 1 ;
        private static final int page_two = 2 ;
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


        int index = 1;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_car);
                ButterKnife.bind(this);


                supportFragmentManager = getSupportFragmentManager();
                showFragment(page_one);
                bottomCar1.setPressed(true);
        }

        @OnClick(R.id.bottom_car_1)
        public void first() {
                if (index != 1) {
                        showFragment(page_one);
                        bottomCar1.setPressed(true);
                        bottomCar2.setPressed(false);
                        index = 1;
                        Log.e(TAG, "first:  press");
                }
        }

        @OnClick(R.id.bottom_car_2)
        public void second() {
                if (index != 2) {
                        showFragment(page_two);
                        bottomCar1.setPressed(false);
                        bottomCar2.setPressed(true);
                        index = 2;
                        Log.e(TAG, "second: press");
                }
        }

        private void showFragment(int index){
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                if (firstFragment!=null){
                        fragmentTransaction.hide(firstFragment);
                }
                if (secondeFragment!=null){
                        fragmentTransaction.hide(secondeFragment);
                }
                this.index = index ;
                switch (index){
                        case  page_one:
                                if (firstFragment == null){
                                        firstFragment = new FirstFragment();
                                        fragmentTransaction.add(R.id.frameLayout,firstFragment);
                                }else {
                                        fragmentTransaction.show(firstFragment);
                                }
                                break;
                        case page_two :
                                if (secondeFragment == null){
                                        secondeFragment = new SecondeFragment();
                                        fragmentTransaction.add(R.id.frameLayout,secondeFragment);
                                }else {
                                        fragmentTransaction.show(secondeFragment);
                                }
                                break;
                }

                // fragment 提交的四个方法的区别？？？？？？
                fragmentTransaction.commitAllowingStateLoss();
        }
}
