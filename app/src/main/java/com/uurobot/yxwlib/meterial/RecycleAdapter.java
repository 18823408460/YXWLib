package com.uurobot.yxwlib.meterial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class RecycleAdapter extends RecyclerView.Adapter {

        private static final String TAG = "RecycleAdapter";
        LayoutInflater layoutInflater ;
        private List<String> list ;
        public RecycleAdapter(Context context) {
                layoutInflater = LayoutInflater.from(context);
                list = new ArrayList<>();
                list.add("111");
                list.add("222");
                list.add("333");
                list.add("444");
                Logger.e(TAG,"RecycleAdapter");
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = layoutInflater.inflate(R.layout.item,null);
                Logger.e(TAG,"onCreateViewHolder");
                return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyHolder myHolder = (MyHolder) holder;
                myHolder.textView.setText(list.get(position));
                Logger.e(TAG,"onBindViewHolder");

        }

        @Override
        public int getItemCount() {
                return list.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder{
                public TextView textView ;
                public MyHolder(View itemView) {
                        super(itemView);
                        textView = itemView.findViewById(R.id.item_tv);
                }

        }
}
