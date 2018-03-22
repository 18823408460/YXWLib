package com.uurobot.yxwlib;

import android.app.Activity;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class TtActivity extends Activity {

        private static final String TAG = "TtActivity";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_tt);

//                findViewById(R.id.msg).setVisibility(View.GONE);
//                findViewById(R.id.left).setVisibility(View.GONE);
//                findViewById(R.id.right).setVisibility(View.GONE);
//
//                TextView viewById = findViewById(R.id.tt);
//                viewById.setVisibility(View.VISIBLE);
//                viewById.setText("ss");
                dataSetObservable.registerObserver(dataSetObserver);
                dataSetObservable.notifyChanged();

        }

        private DataSetObserver dataSetObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                        super.onChanged();
                        Log.e(TAG, "onChanged: ");
                }

                @Override
                public void onInvalidated() {
                        super.onInvalidated();
                        Log.e(TAG, "onInvalidated: ");
                }
        };

        private ArrayList<DataSetObserver> arrayList = new ArrayList<>();

        private DataSetObservable dataSetObservable = new DataSetObservable() {

                @Override
                public void notifyChanged() {
                        super.notifyChanged();
                        synchronized (mObservers) {
                                for (DataSetObserver d : mObservers
                                        ) {
                                        d.onChanged();
                                }
                        }
                        Log.e(TAG, "notifyChanged: ");
                }

                @Override
                public void notifyInvalidated() {
                        super.notifyInvalidated();
                        Log.e(TAG, "notifyInvalidated: ");
                        for (DataSetObserver d : mObservers
                                ) {
                                d.onInvalidated();
                        }
                }

                @Override
                public void registerObserver(DataSetObserver observer) {
                        super.registerObserver(observer);
                        if (!arrayList.contains(observer))
                                arrayList.add(observer);
                        Log.e(TAG, "registerObserver: ");
                }

                @Override
                public void unregisterObserver(DataSetObserver observer) {
                        super.unregisterObserver(observer);
                        Log.e(TAG, "unregisterObserver: ");
                        if (arrayList.contains(observer)) {
                                arrayList.remove(observer);
                        }
                }
        };

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                return super.onTouchEvent(event);
        }


}
