package com.evcall.mobp2p.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends PagerAdapter{
    final String TAG=getClass().getSimpleName();

    private List<View> viewList=new ArrayList<>();
    private boolean loop;

    /**
     * 页面滑动适配器
     * @param viewList
     * @param loop 是否循環滾動
     */
    public ViewPageAdapter(List<View> viewList, boolean loop) {
        super();
        if(viewList==null){return;}
        this.viewList.addAll(viewList);
        this.loop=loop;
    }

    /**
     * 清除显示数据
     */
    public void clearData(){
        this.viewList.clear();
    }
    /**
     * 获取视图总数
     * @return
     */
    public int getViewCount(){
        return this.viewList.size();
    }

    /**
     * 是否循环播放
     */
    public boolean isLoop(){return this.loop;}
    @Override
    public int getCount() {
        if(loop){return Integer.MAX_VALUE;}
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(loop){return;}
        if(position>=viewList.size()){return;}
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(loop)
            position %= (viewList.size());
        View v=viewList.get(position);
        if(loop) {
            ViewParent viewParent = v.getParent();
            if (viewParent != null) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(v);
            }
        }
        container.addView(v);
        return v;
    }
}
