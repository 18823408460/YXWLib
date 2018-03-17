package com.evcall.mobp2p.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avcon.SDK;
import com.avcon.utils.AvcLog;
import com.evcall.mobp2p.adapter.ViewPageAdapter;
import com.evcall.mobp2p.base.App;
import com.evcall.mobp2p.constant.HandlerType;
import com.evcall.mobp2p.utils.StringUtil;
import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频通讯界面
 *
 * @author ljx
 */
public class CallVideoView extends LayoutView {

    private RelativeLayout rlParent;
    private FrameLayout flLocal, flRemote;
    private ImageView ivCallSpeaker, ivCallHandup, ivCallCamera, ivCallLocal, ivCallAudio;
    private ViewPager vpage;
    private ViewPageAdapter pageAdapter;
    private PageIndicator pagel;
    private RelativeLayout rel_pause_audio;
    private ImageView ivWarn;
    private TextView ivTip,tvTime;
    private RelativeLayout relShareImageView;
    private ImageView ivCloseShareImage;
    private MyImageView mIv_shareImage,mIv_max;
    private boolean pause;
    private boolean onlyAudio;
    private DisplayMetrics dm2;
    private boolean showLocationView;
    private final int autoMillisecond = 5000;
    private int locaLeftMargin, locaTopMargin, locaRightMargin, locaBottomMargin;
    private Bitmap shareBitmap;
    private GestureDetector gestureDetector;
    private int fatherWidth, fatherHeight, childWidth, childHeight;
    private View childView;
    private boolean normal;
    private int[] adBigIds;
    private Handler handlerTime=new Handler();;

    @Override
    public void onDestroy() {
        AvcLog.printI(TAG,"onDestroy","--->");
        handlerTime.removeCallbacks(runnTime);
        flLocal.getViewTreeObserver().removeOnGlobalLayoutListener(localGlobalLayoutListener);
        flRemote.getViewTreeObserver().removeOnGlobalLayoutListener(remoteGlobalLayoutListener);
        flLocal.removeAllViews();
        flRemote.removeAllViews();
        pagel.onDestroy();
        vpage = null;
        pageAdapter = null;
        pagel = null;
        ivCallSpeaker = null;
        ivCallHandup = null;
        ivCallCamera = null;
        ivWarn = null;
        ivTip = null;
        clickLayoutView = null;
        rel_pause_audio.removeAllViews();
        rel_pause_audio = null;
        clickLayoutView = null;
    }

    public CallVideoView(Context context, OnClickLayoutView clickLayoutView) {
        super(context, R.layout.layoutview_call_video, clickLayoutView);
        dm2 = getResources().getDisplayMetrics();
    }

    public void setVideoSize(SurfaceView locationView, View remoteView) {
        if (locationView != null) flLocal.addView(locationView);
        if (remoteView != null) flRemote.addView(remoteView);
        childView = remoteView;
        flRemote.getViewTreeObserver().addOnGlobalLayoutListener(remoteGlobalLayoutListener);
        flLocal.setLongClickable(true);
        flLocal.setOnTouchListener(onTouchListener);
        flLocal.getViewTreeObserver().addOnGlobalLayoutListener(localGlobalLayoutListener);
        gestureDetector = new GestureDetector(getContext(), onGestureListener);
        showLocationView = true;
        handlerTime.removeCallbacks(runnTime);
        handlerTime.postDelayed(runnTime,1000);
    }

    private ViewTreeObserver.OnGlobalLayoutListener localGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int localHeight = flLocal.getHeight();
            int localWidth = flLocal.getWidth();
            int preHeight = App.CORE.getPreviewHeight();
            int preWidth = App.CORE.getPreviewWidth();
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {//竖屏
                if (preHeight < preWidth) {
                    int t = preHeight;
                    preHeight = preWidth;
                    preWidth = t;
                }
            }
            float localScale = (float) localHeight / localWidth;
            float preScale = (float) preHeight / preWidth;
            float changeScale;
            if (localScale > preScale) {//以宽比例为准，调节高度
                changeScale = (float) localWidth / preWidth;
                localHeight = (int) (changeScale * preHeight);
            } else {//以高比例为准，调节宽度
                changeScale = (float) localHeight / preHeight;
                localWidth = (int) (changeScale * preWidth);
            }
            flLocal.getLayoutParams().height = localHeight;
            flLocal.getLayoutParams().width = localWidth;
            flLocal.requestLayout();
            flLocal.getViewTreeObserver().removeOnGlobalLayoutListener(localGlobalLayoutListener);
        }
    };
    private ViewTreeObserver.OnGlobalLayoutListener remoteGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            fatherHeight = flRemote.getHeight();
            fatherWidth = flRemote.getWidth();
            resetRemoteSize();
            flRemote.getViewTreeObserver().removeOnGlobalLayoutListener(remoteGlobalLayoutListener);
        }
    };

    @Override
    protected void initView() {
        rlParent = (RelativeLayout) findViewById(R.id.rlParent);
        flRemote = (FrameLayout) findViewById(R.id.flRemote);
        flLocal = (FrameLayout) findViewById(R.id.flLocal);
        ivCallSpeaker = (ImageView) findViewById(R.id.ivCallSpeaker);
        ivCallHandup = (ImageView) findViewById(R.id.ivCallHandup);
        ivCallCamera = (ImageView) findViewById(R.id.ivCallCamera);
        ivCallLocal = (ImageView) findViewById(R.id.ivCallLocal);
        ivCallAudio = (ImageView) findViewById(R.id.ivCallAudio);
        vpage = (ViewPager) findViewById(R.id.vpage);
        tvTime= (TextView) findViewById(R.id.tvTime);
        mIv_max= (MyImageView) findViewById(R.id.mIv_max);
        mIv_max.setOnClickListener(this);
        List<View> imageList = new ArrayList<>();
        int[] pageIds = {R.mipmap.img_small_ad1, R.mipmap.img_small_ad2, R.mipmap.img_small_ad3};
        adBigIds=new int[]{R.mipmap.img_big_ad1, R.mipmap.img_big_ad2, R.mipmap.img_big_ad3};
        for (int i = 0; i < pageIds.length; i++) {
            ImageView imageView = newImageView(pageIds[i]);
            final int showIndex = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("--->showIndex:" + showIndex);
                    mIv_max.setVisibility(VISIBLE);
                    mIv_max.setBackgroundResource(adBigIds[showIndex]);
                }
            });
            imageList.add(imageView);
        }
        pageAdapter = new ViewPageAdapter(imageList, true);
        pagel = (PageIndicator) findViewById(R.id.pagel);
        pagel.setViewPager(vpage, pageAdapter);
        pagel.startAutoSlide(autoMillisecond);
        rel_pause_audio = (RelativeLayout) findViewById(R.id.rel_pause_audio);
        ivWarn = (ImageView) findViewById(R.id.ivWarn);
        ivTip = (TextView) findViewById(R.id.ivTip);
        relShareImageView = (RelativeLayout) findViewById(R.id.relShareImageView);
        ivCloseShareImage = (ImageView) findViewById(R.id.ivCloseShareImage);
        mIv_shareImage = (MyImageView) findViewById(R.id.mIv_shareImage);

        ivCallSpeaker.setOnClickListener(this);
        ivCallHandup.setOnClickListener(this);
        ivCallCamera.setOnClickListener(this);
        ivCallLocal.setOnClickListener(this);
        ivCallAudio.setOnClickListener(this);
        ivCloseShareImage.setOnClickListener(this);
        clickLayoutView.onToggleSpeaker(true);
    }

    private Runnable runnTime=new Runnable() {
        int time=0;
        @Override
        public void run() {
            time++;
            System.out.println("------------------>[runnTime] "+time);
            String strTime= StringUtil.secToTime(time);
            tvTime.setText(strTime);
            handlerTime.postDelayed(runnTime,1000);
        }
    };
    private ImageView newImageView(int imageResourceId) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        MyImageView imageView = new MyImageView(getContext());
        imageView.setId(imageResourceId);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageResourceId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    private void changePauseAudio() {
        if (pause) {
            rel_pause_audio.setVisibility(VISIBLE);
            ivWarn.setVisibility(VISIBLE);
            ivTip.setText(R.string.s_server_pause);
            ivCallAudio.setEnabled(false);
            ivCallCamera.setEnabled(false);
            ivCallLocal.setEnabled(false);
            ivCallSpeaker.setEnabled(false);
        }else{
            ivCallAudio.setEnabled(true);
            ivCallCamera.setEnabled(true);
            ivCallLocal.setEnabled(true);
            ivCallSpeaker.setEnabled(true);
            if (onlyAudio) {
                rel_pause_audio.setVisibility(VISIBLE);
                ivWarn.setVisibility(GONE);
                ivTip.setText(R.string.s_only_audio);
            } else {
                rel_pause_audio.setVisibility(INVISIBLE);
            }
        }
        if (onlyAudio) {
            ivCallAudio.setBackgroundResource(R.mipmap.img_changeaudiopress);
        } else {
            ivCallAudio.setBackgroundResource(R.mipmap.img_changeaudionormal);
        }
    }

    /***
     * 改变远程视图的大小
     *
     * @param normal 坐席像是否正常
     * @param width  坐席宽度
     * @param height 坐席高度
     */
    private void changeRemoteSize(boolean normal, int width, int height) {
        childWidth = width;
        childHeight = height;
        this.normal = normal;
        resetRemoteSize();
    }

    private void resetRemoteSize() {
        if (fatherWidth <= 0 || fatherHeight <= 0 || childWidth <= 0 || childHeight <= 0)
            return;
        if (normal) {//远程像是竖屏，保证高度大于宽度tt
            if (childWidth > childHeight) {
                int temp = childWidth;
                childWidth = childHeight;
                childHeight = temp;
            }
        } else {//远程像是横屏，保证宽度大于高度
            if (childWidth < childHeight) {
                int temp = childWidth;
                childWidth = childHeight;
                childHeight = temp;
            }
        }
        float scaleW = (float) fatherWidth / childWidth;
        float scaleH = (float) fatherHeight / childHeight;
        int margin = 0;
        int leftMargin = 0;
        int rightMargin = 0;
        int topMargin = 0;
        int bottomMargin = 0;
        AvcLog.printI(TAG, "resetRemoteSize", "------>normal:" + normal + ",fatherWidth:" +
                fatherWidth + ",fatherHeight:" + fatherHeight + ",childWidth:" + childWidth +
                ",childHeight:" + childHeight + ",scaleW:" + scaleW + ",scaleH:" + scaleH);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        if (scaleW >= 1 && scaleH >= 1) {//子容器的宽高都小于父容器
            if (scaleW < scaleH) {//以高为基准，剪切宽
                childWidth = (int) (childWidth * scaleH);
                margin = (childWidth - fatherWidth) / 2;
                leftMargin = -margin;
                rightMargin = -margin;
                topMargin = 0;
                bottomMargin = 0;
            } else if (scaleW > scaleH) {//以宽为基准，剪切高
                childHeight = (int) (childHeight * scaleW);
                margin = (childHeight - fatherHeight) / 2;
                leftMargin = 0;
                rightMargin = 0;
                topMargin = -margin;
                bottomMargin = -margin;
            }
        } else if (scaleW < 1 && scaleH < 1) {//子容器宽高都大于父容器宽高
            if (scaleW < scaleH) {//以高为基准，剪切宽
                childWidth = (int) (childWidth * scaleH);
                margin = (childWidth - fatherWidth) / 2;
                leftMargin = -margin;
                rightMargin = -margin;
                topMargin = 0;
                bottomMargin = 0;
            } else if (scaleH < scaleW) {//以宽为基准，剪切高
                childHeight = (int) (childHeight * scaleW);
                margin = (childHeight - fatherHeight) / 2;
                leftMargin = 0;
                rightMargin = 0;
                topMargin = -margin;
                bottomMargin = -margin;
            }
        } else if (scaleW > 1 && scaleH < 1) {//子容器的宽小于父容器，子容器的高大于父容器
            childHeight = (int) (childHeight * scaleW);
            margin = (childHeight - fatherHeight) / 2;
            leftMargin = 0;
            rightMargin = 0;
            topMargin = -margin;
            bottomMargin = -margin;
        } else if (scaleW < 1 && scaleH > 1) {//子容器的宽大于父容器，子容器的高小于父容器
            childWidth = (int) (childWidth * scaleH);
            margin = (childWidth - fatherWidth) / 2;
            leftMargin = -margin;
            rightMargin = -margin;
            topMargin = 0;
            bottomMargin = 0;
        }
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        AvcLog.printI(TAG, "resetRemoteSize", "---->leftMargin:" + leftMargin + ",rightMargin:" + rightMargin + ",topMargin:" + topMargin + ",bottomMargin:" + bottomMargin);
        childView.setLayoutParams(params);
        childView.setTranslationX(-leftMargin);
        childView.setTranslationY(-topMargin);
    }

    @Override
    public void updateMessage(Message msg) {
        int what = msg.what;
        if (what < 0 || what >= HandlerType.values().length) {
            return;
        }
        HandlerType handlerType = HandlerType.values()[what];
        Bundle data = msg.getData();
        switch (handlerType) {
            case TALK_PAUSE_REVER: {
                pause = data.getBoolean("b");
                changePauseAudio();
                break;
            }
            case TALK_AUDIO_VIDEO: {
                onlyAudio = data.getBoolean("b");
                changePauseAudio();
                break;
            }
            case TALK_REMOTE_SIZE: {
                boolean normal = data.getBoolean("b");
                int w = data.getInt("w");
                int h = data.getInt("h");
                changeRemoteSize(normal, w, h);
                break;
            }
            case TALK_SHARE_RESOURCE_SHOW:
                relShareImageView.setVisibility(View.VISIBLE);
                break;
            case TALK_SHARE_RESOURCE_SUCCESS:
                shareBitmap = data.getParcelable("SHARE_IMAGE");
                mIv_shareImage.setImageBitmap(shareBitmap);
                break;
            case TALK_SHARE_RESOURCE_CLOSE:
                closeShareImage();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCallSpeaker: {
                Boolean b = toggleSpeaker();
                if (b == null) {
                    return;
                }
                if (b) {
                    ivCallSpeaker.setBackgroundResource(R.mipmap.img_handfree_press);
                } else {
                    ivCallSpeaker.setBackgroundResource(R.mipmap.img_handfree_normal);
                }
                break;
            }
            case R.id.ivCallHandup:
                hangup();
                break;
            case R.id.ivCallCamera:
                Boolean b = toggleCamera();
                if (b == null) {
                    return;
                }
                if (b) {
                    ivCallCamera.setBackgroundResource(R.mipmap.img_camera_normal);
                } else {
                    ivCallCamera.setBackgroundResource(R.mipmap.img_camera_press);
                }
                break;
            case R.id.ivCallLocal:
                showLocationView = !showLocationView;
                changeLocationView(showLocationView);
                break;
            case R.id.ivCallAudio:
                onlyAudio = !onlyAudio;
                SDK.onChangeAuido(onlyAudio);
                changePauseAudio();
                break;
            case R.id.ivCloseShareImage:
                closeShareImage();
                break;
            case R.id.mIv_max:
                mIv_max.setBackground(null);
                mIv_max.setVisibility(GONE);
                break;
        }
    }

    /**
     * 关闭图片共享
     */
    private void closeShareImage() {
        relShareImageView.setVisibility(View.GONE);
        if (shareBitmap != null) {
            shareBitmap.isRecycled();
            shareBitmap = null;
        }
    }

    /**
     * 显示或隐藏本地图像
     *
     * @param show
     */
    private void changeLocationView(boolean show) {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (show) {
            ivCallLocal.setBackgroundResource(R.mipmap.img_closelocalvideonormal);
            lp.setMargins(locaLeftMargin, locaTopMargin, locaRightMargin, locaBottomMargin);
        } else {
            int parentWidth = dm2.widthPixels;
            int parentHeight = rlParent.getHeight();
            locaLeftMargin = flLocal.getLeft();
            locaTopMargin = flLocal.getTop();
            int w = flLocal.getWidth();
            int h = flLocal.getHeight();
            locaRightMargin = parentWidth - locaLeftMargin - w;
            locaBottomMargin = parentHeight - locaTopMargin - h;
            ivCallLocal.setBackgroundResource(R.mipmap.img_closelocalvideopress);
            lp.setMargins(parentWidth, parentHeight, 0, 0);
        }
        flLocal.setLayoutParams(lp);
        flLocal.requestLayout();
    }

    /**
     * 本地视图拖动位置事件
     */
    private OnTouchListener onTouchListener = new OnTouchListener() {
        private int lastX, lastY, touchViewWidth, touchViewHeight, top, left, right, bottom;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int parentWidth = dm2.widthPixels;
            int parentHeight = rlParent.getHeight();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pagel.stopAutoSlide();
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    touchViewWidth = v.getWidth();
                    touchViewHeight = v.getHeight();
                    break;

                case MotionEvent.ACTION_MOVE:
                    int curX = (int) event.getRawX();
                    int curY = (int) event.getRawY();
                    int dx = curX - lastX;
                    int dy = curY - lastY;
                    top = v.getTop() + dy;
                    left = v.getLeft() + dx;

                    if (top <= 0) {
                        top = 0;
                    }
                    if (top >= parentHeight) {
                        top = parentHeight;
                    }
                    if (top >= parentHeight - touchViewHeight) {
                        top = parentHeight - touchViewHeight;
                    }
                    if (left <= 0) {
                        left = 0;
                    }
                    if (left >= parentWidth - touchViewWidth) {
                        left = parentWidth - touchViewWidth;
                    }
                    right = left + touchViewWidth;
                    bottom = top + touchViewHeight;
                    v.layout(left, top, right, bottom);
                    lastX = curX;
                    lastY = curY;
                    break;
                case MotionEvent.ACTION_UP:
                    locaLeftMargin = left;
                    locaTopMargin = top;
                    int w = flLocal.getWidth();
                    int h = flLocal.getHeight();
                    locaRightMargin = parentWidth - locaLeftMargin - w;
                    locaBottomMargin = parentHeight - locaTopMargin - h;
                    changeLocationView(true);
                    pagel.startAutoSlide(autoMillisecond);
                    break;
            }
            return false;
        }
    };

    /**
     * 手指在屏幕上移动的监听,实现图片共享中的上一张下一张的功能
     */
    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
            if (e1.getRawX() - e2.getRawX() > 200) {//下一张
                SDK.onImageResourceChange(false);
            }
            if (e2.getRawX() - e1.getRawX() > 200) {//上一张
                SDK.onImageResourceChange(true);
            }
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
