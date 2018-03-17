package com.uurobot.yxwlib.mvp1;

/**
 * Created by Administrator on 2017/11/22.
 */

public class PresentImpl implements IPresent {
        private IView  iView  ;

        public PresentImpl(IView iView) {
                this.iView = iView ;
        }

        @Override
        public void login(String name, String age) {
                if ("aa".equals(name)){
                        iView.showLoginState(true);
                }else {
                        iView.showLoginState(false);
                }
        }
}
