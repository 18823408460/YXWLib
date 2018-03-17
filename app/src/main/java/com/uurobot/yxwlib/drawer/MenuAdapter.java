package com.uurobot.yxwlib.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */

public class MenuAdapter extends BaseAdapter {

        private final Context context;

        private List<String> lists  ;
        private LayoutInflater layoutInflater  ;
        public MenuAdapter(Context context) {
                this.context = context ;
                layoutInflater = LayoutInflater.from(context);
                lists = new ArrayList<>();
                lists.add("menu1");
                lists.add("menu2");
                lists.add("menu3");
                lists.add("menu4");
        }

        @Override
        public int getCount() {
                return lists.size();
        }

        @Override
        public Object getItem(int position) {
                return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView ;
                if (view == null){
                        view = layoutInflater.inflate(R.layout.item,null);
                        TextView textview = view.findViewById(R.id.item_tv);
                        textview.setText(lists.get(position));
                }
                return view;
        }
}
