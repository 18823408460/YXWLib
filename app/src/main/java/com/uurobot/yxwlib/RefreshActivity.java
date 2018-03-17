package com.uurobot.yxwlib;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;

import java.util.Formatter;

/**
 * Created by Administrator on 2017/10/13.
 */

public class RefreshActivity extends Activity {

        private static final String TAG = "RefreshActivity";
        XRefreshView refreshView ;
        Handler mhandler = new Handler();
        TextView textView ;
        String data ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_refresh);


//                android.text.format.Formatter.formatFileSize();
//                DateUtils.formatDateTime();




                refreshView = (XRefreshView) findViewById(R.id.custom_view);
                textView = (TextView) findViewById(R.id.listView);
                // 设置是否可以下拉刷新
                refreshView.setPullRefreshEnable(true);
                // 设置是否可以上拉加载
                refreshView.setPullLoadEnable(true);
                //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
                refreshView.setMoveHeadWhenDisablePullRefresh(true);
                // 设置时候可以自动刷新
                refreshView.setAutoRefresh(false);
                refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
                        @Override
                        public void onRefresh(boolean isPullDown) {
                                Log.e(TAG, "onRefresh: >>>>>>>>>>>>>>>>>>>>>>>>>>");
                                mhandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                refreshView.stopRefresh();
                                                data += "sdfdsfsdfsdfsfsd\n\n";
                                                textView.setText(data);
                                        }
                                },2000);
                        }

                        @Override
                        public void onLoadMore(boolean isSilence) {
                                Log.e(TAG, "onLoadMore: >>>>>>>>>>>>>>" );
                                mhandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                              refreshView.stopLoadMore();
                                                data += "sdfdsfsdfsdfsfsd\n\n";
                                                textView.setText(data);
                                        }
                                },2000);
                        }

                        @Override
                        public void onRelease(float direction) {
                                super.onRelease(direction);
                        }
                });
        }


        @Override
        protected void onStop() {
                super.onStop();
                refreshView.setXRefreshViewListener(null);
        }
}
