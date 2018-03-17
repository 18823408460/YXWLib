package com.uurobot.yxwlib.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/16.
 */

public class TabLayoutFragment extends Fragment {

        private static final String TAG = "TabLayoutFragment";
        private static String key = "TAB";

        @BindView(R.id.frament_tv_car)
        TextView framentTvCar;

        Unbinder bind;

        private int type;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                // 这里只能传递false，否则报错 ？？？？？？？？？？？？？？？？？
                View inflate = inflater.inflate(R.layout.fragment_car, container, false);
                bind = ButterKnife.bind(this, inflate);
                initView(inflate);
                return inflate;
        }

        private void initView(View inflate) {
                Log.e(TAG, "initView: "+type );
//                TextView textView = inflate.findViewById(R.id.frament_tv_car);
//                textView.setText(CarMainActivity.tabTitle[type]);
                 framentTvCar.setText("这是="+CarMainActivity.tabTitle[type]);
        }


        public static TabLayoutFragment newInstance(int type) {
                TabLayoutFragment tabLayoutFragment = new TabLayoutFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(key, type);
                tabLayoutFragment.setArguments(bundle);
                return tabLayoutFragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Bundle arguments = getArguments();
                if (arguments != null) {
                        type = (int) arguments.getSerializable(key);
                        Log.e(TAG, "onCreate: "+type  );
                }else {
                        Log.e(TAG, "onCreate: arguments is null =="+type  );
                }

        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                bind.unbind();
        }
}
