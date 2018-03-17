package com.uurobot.yxwlib.fragment;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/24.
 */

public class FragmentMgr {
        private static  FragmentMgr instance = null ;
        private HashMap<String,INoResutlNoParams>  hashMapNoResultNoParams ;
        private HashMap<String,IResultWithParams>  hashMapResultWithParams ;

        private FragmentMgr() {
                hashMapNoResultNoParams = new HashMap<>();
                hashMapResultWithParams = new HashMap<>();
        }

        public static FragmentMgr getInstance(){
                if (instance == null){
                        synchronized (FragmentMgr.class){
                                if (instance == null) {
                                        instance  = new FragmentMgr() ;
                                }
                        }
                }
                return  instance;
        }

        public void addLisenter(String tag,INoResutlNoParams iNoResutlNoParams){
                hashMapNoResultNoParams.put(tag,iNoResutlNoParams);
        }

        public void addResultWithParamsLisenter(String tag, IResultWithParams iResutlWithParams){
                hashMapResultWithParams.put(tag,iResutlWithParams);
        }

        public void invoke(String tag){
                if (TextUtils.isEmpty(tag)){
                        return;
                }
                INoResutlNoParams iNoResutlNoParams = hashMapNoResultNoParams.get(tag);
                if (iNoResutlNoParams!=null){
                        iNoResutlNoParams.function();
                }
        }

        public String invokeResultParams(String tag,Integer params){
                if (TextUtils.isEmpty(tag)){
                        return null;
                }
                IResultWithParams<String,Integer> iResutlParams = hashMapResultWithParams.get(tag);
                if (iResutlParams!=null){
                        return  iResutlParams.function(params);
                }
                return  null;
        }




}
