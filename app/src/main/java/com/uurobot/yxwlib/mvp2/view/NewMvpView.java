package com.uurobot.yxwlib.mvp2.view;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public interface NewMvpView extends BaseView {
        void showMessage(String msg);
        void updateListItem(List<String> data);
}
