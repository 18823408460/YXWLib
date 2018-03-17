package com.evcall.mobp2p.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.evcall.mobp2p.adapter.ViewPageAdapter;
import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPage的动态指示器
 * 自动创建圆点、横条
 * @author ljx
 */
public class PageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ViewPageAdapter viewPagerAdapter;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private List<View> vList=new ArrayList<>();
    private int currentPostion=0;
    private int viewCount;
    private boolean loop;
    private int styleType=0;
    private int drawableDefault;
    private int drawableSelect;
    private boolean onePageHide;
    private Handler handler=new Handler();
    private boolean auto;
    private long millisecond;

    public PageIndicator(Context context) {
        super(context);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray c = getContext().obtainStyledAttributes(attrs, R.styleable.indicator);
        styleType=c.getInt(R.styleable.indicator_style_type,0);
        if(styleType==1){
            drawableDefault = R.drawable.shape_strip_default;
            drawableSelect = R.drawable.shape_strip_select;
        }else{
            drawableDefault = R.drawable.shape_circle_default;
            drawableSelect = R.drawable.shape_circle_select;
        }
        onePageHide=c.getBoolean(R.styleable.indicator_one_page_hide,false);
    }

    private void showIndicator(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        viewCount=viewPagerAdapter.getViewCount();
        if(viewCount<=1&&onePageHide){
            return;
        }
        this.vList.clear();
        this.removeAllViews();
        for (int i=0;i<viewCount;i++){
            View v=layoutInflater.inflate(R.layout.view_page_indicator,null);
            View v1=v.findViewById(R.id.tvShow);
            int widht=v1.getLayoutParams().width;
            int height;
            if(styleType==1){ height=widht/3;}
            else{height=widht;}
            v1.getLayoutParams().height=height;
            this.addView(v);
            this.vList.add(v);
            if(i==0) {currentPostion=i;selectView(v,true);}
            else selectView(v,false);
        }
    }

    private void selectPostion(int position,boolean select){
        if(viewCount<=1&&onePageHide){
            return;
        }
        if(loop)
            position %= viewCount;
        View v=vList.get(position);
        selectView(v,select);
    }

    private void selectView(View v,boolean select){
        View v1=v.findViewById(R.id.tvShow);
        Drawable drawable;
        if(select)drawable=getResources().getDrawable(drawableSelect);
        else drawable=getResources().getDrawable(drawableDefault);
        v1.setBackground(drawable);
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            setCurrentPostion(currentPostion+1);
            startAutoSlide(millisecond);
        }
    };
    private void changePageView(List<View> viewList){
        viewPagerAdapter.clearData();
        viewPagerAdapter=new ViewPageAdapter(viewList,this.loop);
        viewPager.setAdapter(viewPagerAdapter);
        showIndicator();
    }
    /**
     * 注入关联的视图及适配器
     * @param viewPager
     * @param viewPagerAdapter
     */
    public void setViewPager(ViewPager viewPager,ViewPageAdapter viewPagerAdapter){
        this.viewPager=viewPager;
        this.viewPagerAdapter=viewPagerAdapter;
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        this.loop=viewPagerAdapter.isLoop();
        showIndicator();
    }

    /**
     * 清除视图的显示数据
     */
    public void clearPageView(){
        changePageView(new ArrayList<View>());
    }
    /**
     * 更新视图的显示数据
     */
    public void updatePageView(List<View> viewList){
        changePageView(viewList);
    }

    /**
     * 显示当前的页码位置
     * @param postion
     */
    public void setCurrentPostion(int postion) {
        if(viewPager==null||viewPagerAdapter==null){
            throw new IllegalStateException("ViewPager or ViewPageAdapter is null");
        }
        viewPager.setCurrentItem(postion);
    }

    /**
     * 页码改变监听
     * @param listener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        if(viewPager==null||viewPagerAdapter==null){
            throw new IllegalStateException("ViewPager or ViewPageAdapter is null");
        }
        pageChangeListener=listener;
    }

    /**
     * 开启自动滑动
     * @param millisecond
     */
    public void startAutoSlide(long millisecond){
        this.auto=true;
        this.millisecond=millisecond;
        if(viewCount<2){return;}
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,millisecond);
    }

    /**
     * 停止自动滑动
     */
    public void stopAutoSlide(){
        this.auto=false;
        if(viewCount<2){return;}
        handler.removeCallbacks(runnable);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(pageChangeListener!=null){
            pageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }

    }

    @Override
    public void onPageSelected(int position) {
        if(pageChangeListener!=null){
            pageChangeListener.onPageSelected(position);
        }
        if(currentPostion!=position){
            selectPostion(currentPostion,false);
        }
        selectPostion(position,true);
        currentPostion=position;
        if(auto){startAutoSlide(millisecond);}
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(pageChangeListener!=null){
            pageChangeListener.onPageScrollStateChanged(state);
        }
    }
    /**
     * 必须调用，否则无法回收此对象
     */
    public void onDestroy(){
        viewPager.removeAllViews();
        viewPagerAdapter.clearData();
        handler.removeCallbacks(runnable);
        vList.clear();
        viewPager=null;
        viewPagerAdapter=null;
        pageChangeListener=null;
    }
}
