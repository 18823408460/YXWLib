package com.uurobot.yxwlib.uiTest.MixView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SpinnerAdater extends BaseAdapter {

        private static final String TAG = "SpinnerAdater";
        private ArrayList<String> arrayList;

        private Context context;

        private LayoutInflater layoutinflater;

        public SpinnerAdater(Context context, ArrayList<String> arrayList) {
                this.arrayList = arrayList;
                this.context = context;
                layoutinflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
                return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
                return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                MyHolder myHolder;
                if (convertView == null) {
                        myHolder = new MyHolder();
                        convertView = layoutinflater.inflate(R.layout.spinner_item, null);
                        myHolder.textView = convertView.findViewById(R.id.spinner_item_tv);
                        convertView.setTag(myHolder);
                }
                else {
                        myHolder = (MyHolder) convertView.getTag();
                }
                Logger.e(TAG,"getview==="+position);
                myHolder.textView.setText(arrayList.get(position));
                return convertView;
        }

        private class MyHolder {

                public TextView textView;
        }
}
