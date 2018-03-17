package com.evcall.mobp2p.layout;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.avcon.bean.Business;
import com.avcon.bean.BusinessCategoriy;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.MainActivity;
import com.evcall.mobp2p.adapter.BusinessAdapter;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.NetState;
import com.uurobot.yxwlib.R;

import org.utils.view.annotation.ViewInject;

/**
 * 主界面之 业务视图
 *
 * @author ljx
 */
public class MainBusiness extends LayoutMain{

    @ViewInject(value = R.id.lvBusiness)
    private ListView lvBusiness;
    @ViewInject(value = R.id.iv_back)
    private ImageView iv_back;

    private BusinessCategoriy categoriy;
    private BusinessAdapter businessAdapter;

    /**
     * 构造对象，在对象不再使用时，调用onDestroy方法体，加快垃圾回收
     *
     * @param mainActivity
     * @param myCore
     */
    public MainBusiness(MainActivity mainActivity, MyCore myCore) {
        super(mainActivity, myCore,R.layout.main_business);
    }
    @Override
    protected void initData() {
        core.setbCalling(false);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        categoriy = core.getClickCategoriy();
        businessAdapter = new BusinessAdapter(activity, categoriy);
        lvBusiness.setAdapter(businessAdapter);
        businessAdapter.setOnClickItemButtonListener(new BusinessAdapter.OnClickItemButtonListener() {
            @Override
            public void onClickBtn(Business business, View v) {
                core.setClickBusiness(business);
                activity.onShowMainCall(false);
            }
        });
    }

    @Override
    public void updataLayoutView(Message msg) {

    }

    @Override
    public void updateNetState(NetState state) {

    }

    @Override
    public void onDestroy() {
        AvcLog.printI(TAG,"onDestroy","===>");
        super.onDestroy();
        categoriy = null;
        businessAdapter = null;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                activity.onShowMainCategory();
                break;
        }
    }
}
