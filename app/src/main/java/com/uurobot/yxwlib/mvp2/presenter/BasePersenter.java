package com.uurobot.yxwlib.mvp2.presenter;

/**
 * Created by Administrator on 2017/11/22.
 */

public abstract class  BasePersenter<T> {
        T view ;

        public void attachView(T view){
                this.view = view ;
        }

        public void detachView(){
                this.view = null;
        }
}
