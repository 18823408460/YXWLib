package com.evcall.mobp2p.layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avcon.SDK;
import com.avcon.bean.Business;
import com.avcon.bean.BusinessCategoriy;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.DetailActivity;
import com.evcall.mobp2p.MainActivity;
import com.evcall.mobp2p.adapter.ViewPageAdapter;
import com.evcall.mobp2p.base.MyCore;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.constant.NetState;
import com.evcall.mobp2p.views.MyImageView;
import com.evcall.mobp2p.views.PageIndicator;
import com.uurobot.yxwlib.R;

import org.utils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面之 类别视图
 *
 * @author ljx
 */
public class MainCategory extends LayoutMain {

    @ViewInject(value = R.id.vpage_adver)
    ViewPager vpage_adver;
    @ViewInject(value = R.id.vpage_category)
    ViewPager vpage_category;
    @ViewInject(value = R.id.pagel_adver)
    PageIndicator pagel_adver;
    @ViewInject(value = R.id.pagel_category)
    PageIndicator pagel_category;

    private ViewPageAdapter adverPageAdapter;
    private ViewPageAdapter categoryPageAdapter;
    private List<View> viewList = new ArrayList<>();

    public MainCategory(MainActivity mainActivity, MyCore myCore) {
        super(mainActivity, myCore,R.layout.main_category);
    }

    @Override
    protected void initData() {
        core.setbCalling(false);
        List<View> imageList = new ArrayList<>();
        imageList.add(newImageView(R.mipmap.img_main_ad1));
        imageList.add(newImageView(R.mipmap.img_main_ad2));
        imageList.add(newImageView(R.mipmap.img_main_ad3));
        imageList.add(newImageView(R.mipmap.img_main_ad4));
        imageList.add(newImageView(R.mipmap.img_main_ad5));
        adverPageAdapter = new ViewPageAdapter(imageList, true);
        pagel_adver.setViewPager(vpage_adver, adverPageAdapter);
        pagel_adver.startAutoSlide(3000);
        viewList = getCategoryList();
        categoryPageAdapter = new ViewPageAdapter(viewList, false);
        pagel_category.setViewPager(vpage_category, categoryPageAdapter);
    }

    @Override
    public void updataLayoutView(Message msg) {
        final int what = msg.what;
        Log.w(TAG, "--->handler:" + what);
        if (what == HandlerType.MSG_BUSINESS_UPDAING.ordinal() || what == HandlerType.LOGIN_CONNECT_FAIL.ordinal()) {
            viewList.clear();
            pagel_category.clearPageView();
        } else if (what == HandlerType.MSG_BUSINESS_FINISH.ordinal()) {
            viewList = getCategoryList();
            pagel_category.updatePageView(viewList);
        }
    }

    @Override
    public void updateNetState(NetState state) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AvcLog.printI(TAG,"onDestroy","===>");
    }

    private ImageView newImageView(int imageResourceId) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        MyImageView imageView = new MyImageView(activity);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageResourceId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    private List<View> getCategoryList() {
        List<View> views = new ArrayList<>();
        List<BusinessCategoriy> categoriyList = new ArrayList<>();
        categoriyList.addAll(core.getBusinessCategoriyList());
//        if (categoriyList == null) return views;
        //加入点对点呼叫类别
        BusinessCategoriy p2pCategoriy=new BusinessCategoriy();
        p2pCategoriy.setId(-1);
        p2pCategoriy.setName("点对点呼叫");
        categoriyList.add(p2pCategoriy);
        int categorySize = categoriyList.size();

        final int showNum = 4;//每页显示4条
        boolean zero = false;
        if (categorySize > 0) {
            zero = categorySize % showNum == 0 ? true : false;
        }
        int pageCount = categorySize / showNum + (zero ? 0 : 1);
        int index;
        for (int i = 0; i < pageCount; i++) {
            index = i * showNum;
            View view = layoutInflater.inflate(R.layout.layout_category, null);
            Button btnBus00 = (Button) view.findViewById(R.id.btnBus00);
            Button btnBus01 = (Button) view.findViewById(R.id.btnBus01);
            Button btnBus10 = (Button) view.findViewById(R.id.btnBus10);
            Button btnBus11 = (Button) view.findViewById(R.id.btnBus11);
            for (int n = 0; n < showNum; n++) {
                String showText = null;
                CategoryListener listener = null;
                if (index < categorySize) {
                    BusinessCategoriy categoriy = categoriyList.get(index);
                    showText = categoriy.getName();
                    listener = new CategoryListener(categoriy);
                }
                switch (n) {
                    case 0:
                        if (showText == null) {
                            btnBus00.setText(activity.getResources().getString(R.string.s_no_business1));
                        } else {
                            btnBus00.setText(showText);
                            btnBus00.setOnClickListener(listener);
                        }
                        break;
                    case 1:
                        if (showText == null) {
                            btnBus01.setText(activity.getResources().getString(R.string.s_no_business1));
                        } else {
                            btnBus01.setText(showText);
                            btnBus01.setOnClickListener(listener);
                        }
                        break;
                    case 2:
                        if (showText == null) {
                            btnBus10.setText(activity.getResources().getString(R.string.s_no_business1));
                        } else {
                            btnBus10.setText(showText);
                            btnBus10.setOnClickListener(listener);
                        }

                        break;
                    case 3:
                        if (showText == null) {
                            btnBus11.setText(activity.getResources().getString(R.string.s_no_business1));
                        } else {
                            btnBus11.setText(showText);
                            btnBus11.setOnClickListener(listener);
                        }
                        break;
                }
                index++;
            }
            views.add(view);
        }
        return views;
    }

    @Override
    public void onClick(View v) {

    }

    class CategoryListener implements OnClickListener {

        private BusinessCategoriy categoriy;

        public CategoryListener(BusinessCategoriy categoriy) {
            this.categoriy = categoriy;
        }

        @Override
        public void onClick(View v) {
            List<Business> businessList = categoriy.getBusinessInforList();
            if (businessList.size() <= 0) {
                if(categoriy.getName().trim().equals("关于我们")){
                    activity.startActivity(new Intent(activity,DetailActivity.class));
                    return;
                }else if(categoriy.getId()==-1){//点对点呼叫
                    final EditText et = new EditText(activity);
                    new AlertDialog.Builder(activity).setTitle("请输入用户id")
                            .setIcon(android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("呼叫",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = et.getText().toString();
                                    if (!input.equals("")) {
                                        core.setbWaitCall(true,input);
                                        SDK.getUserInfo(input);
                                    }else{
                                        Toast.makeText(activity, "不能为空！" + input, Toast.LENGTH_LONG).show();
                                    }
                            }
                        }).setNegativeButton("取消", null).show();
                    return;
                }
                Toast.makeText(activity, R.string.s_no_business, Toast.LENGTH_SHORT).show();
            } else {
                core.setClickCategoriy(categoriy);
                activity.onShowMainBsiness();
            }
        }
    }
}
