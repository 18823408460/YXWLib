package com.uurobot.yxwlib;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.uurobot.yxwlib.alarm.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

// 1. listView 局部刷新  2.Adapter刷新的三种方法的区别  3. 上拉加载怎么实现

public class SwipeRefreshActivity extends Activity {

        private static final String TAG = "SwipeRefreshActivity";

        private ListView listView;

        private List<String> list = new ArrayList<>();

        private SwipeRefreshLayout swipeRefreshLayout;

        private Handler handler = new Handler();

        private ArrayAdapter<String> arrayAdapter ;
         int prev = -1;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_swipe);

                listView = findViewById(R.id.listView);
                initData();

                arrayAdapter = new ArrayAdapter<String>(this,R.layout.item,list){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                Logger.e(TAG,"---------position="+position);
                                return super.getView(position, convertView, parent);
                        }
                };
                listView.setAdapter(arrayAdapter);
                swipeRefreshLayout = findViewById(R.id.swipeLayout);

                swipeRefreshLayout.setColorSchemeColors(getColorOwn(R.color.colorAccent), getColorOwn(R.color.colorPrimary), getColorOwn(R.color.colorPrimaryDark));
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                list.add("ssss"+Math.random());
                                                int pistion = (int) (Math.random()*20);
                                                if (prev==-1){
                                                        prev = pistion ;
                                                }else {
                                                        View childAt = listView.getChildAt(prev);
                                                        childAt.setBackgroundColor(getResources().getColor(R.color.colorN));
                                                }
                                                prev = pistion ;
                                                View childAt = listView.getChildAt(pistion);
                                                childAt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                                arrayAdapter.getView(prev,childAt,listView);
//                                                arrayAdapter.notifyDataSetInvalidated();
                                                swipeRefreshLayout.setRefreshing(false);
                                        }
                                }, 3000);
                                Logger.e(TAG, "------------onRefresh-------------");
                        }
                });
        }

        private int getColorOwn(int id) {
                return getResources().getColor(id);
        }

        private void initData(){
                list = new ArrayList<>();
                list.add("1111");
                list.add("1111222");
                list.add("1111333");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
                list.add("1111444");
        }
}
