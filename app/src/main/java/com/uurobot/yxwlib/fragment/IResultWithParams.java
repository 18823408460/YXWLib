package com.uurobot.yxwlib.fragment;

/**
 * Created by Administrator on 2017/10/24.
 */

public abstract class IResultWithParams<T,D> {
        public static final String TAG =  IResultWithParams.class.getName();
        public  abstract T function(D d);
}
