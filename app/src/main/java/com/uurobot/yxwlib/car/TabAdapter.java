package com.uurobot.yxwlib.car;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class TabAdapter extends FragmentPagerAdapter {


        private final List<Fragment> fragments;

        public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
                super(fm);
                this.fragments= fragments ;
        }

        @Override
        public Fragment getItem(int position) {
                return fragments.get(position);
        }

        @Override
        public int getCount() {
                return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return CarMainActivity.tabTitle[position];
        }
}
