package com.uurobot.yxwlib.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/16.
 */

public class SecondeFragment extends Fragment {

        private static final String TAG = "TabLayoutFragment";

        private static String key = "TAB";


        Unbinder bind;



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                // 这里只能传递false，否则报错 ？？？？？？？？？？？？？？？？？
                View inflate = inflater.inflate(R.layout.car_second_fragment, container, false);
                bind = ButterKnife.bind(this, inflate);
                initView(inflate);
                return inflate;
        }

        private void initView(View inflate) {
//                TextView textView = inflate.findViewById(R.id.frament_tv_car);
//                textView.setText(CarMainActivity.tabTitle[type]);
        }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Bundle arguments = getArguments();


        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                bind.unbind();
        }
}
