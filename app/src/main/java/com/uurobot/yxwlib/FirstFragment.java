package com.uurobot.yxwlib;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/10/30.
 */

public class FirstFragment extends Fragment {

        private static final String TAG = "FirstFragment";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Log.e(TAG, "onCreate: " );
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                Log.e(TAG, "onCreateView: " );
                return inflater.inflate(R.layout.fragment1, container, false);
        }

        @Override
        public void onAttach(Context context) {
                super.onAttach(context);
                Log.e(TAG, "onAttach: " );
        }

        @Override
        public void onAttachFragment(Fragment childFragment) {
                super.onAttachFragment(childFragment);
                Log.e(TAG, "onAttachFragment: ");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                Log.e(TAG, "onActivityCreated: ");
        }

        @Override
        public void onPause() {
                super.onPause();
                Log.e(TAG, "onPause: " );
        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                Log.e(TAG, "onDestroyView: " );
        }

        @Override
        public void onDetach() {
                super.onDetach();
                Log.e(TAG, "onDetach: " );
        }

        @Override
        public void onDestroy() {
                super.onDestroy();
                Log.e(TAG, "onDestroy: " );
        }
}
