package com.uurobot.yxwlib.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;

import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2018/3/16.
 */

public class CarMainActivity extends BaseActivity {
        public static final String[] tabTitle = new String[]{"综艺1","体育2","新闻3","热点4","头条5","军事6","历史7","科技8","人文9"};
        @BindView(R.id.left)
        ImageView left;

        @BindView(R.id.right)
        ImageView right;

        @BindView(R.id.tablayout_car)
        TabLayout tablayoutCar;

        @BindView(R.id.viewPager_car)
        ViewPager viewPagerCar;

        @BindView(R.id.search_car)
        SearchView searchCar;

        TabAdapter tabAdapter ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_car);
                ButterKnife.bind(this);

                List<Fragment> fragments = new ArrayList<>();
                for (int i=0,len = tabTitle.length; i<len ; i++){
                        fragments.add(TabLayoutFragment.newInstance(i));
                }
                tabAdapter = new TabAdapter(getSupportFragmentManager(),fragments);

                viewPagerCar.setAdapter(tabAdapter);
                tablayoutCar.setupWithViewPager(viewPagerCar);
                tablayoutCar.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
}
