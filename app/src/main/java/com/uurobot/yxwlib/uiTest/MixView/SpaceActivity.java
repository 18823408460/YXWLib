package com.uurobot.yxwlib.uiTest.MixView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.StackView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextSwitcher;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SpaceActivity extends Activity {

    private static final String TAG = "SpaceActivity";

    @BindView(R.id.button12)
    Button button12;

    @BindView(R.id.button13)
    Button button13;

    @BindView(R.id.space)
    Space space; // 纯粹是用在占用空间的场景下，

    @BindView(R.id.spinner)
    Spinner spinner; // 下拉列表

    @BindView(R.id.switch1)
    Switch switch1; //

    @BindView(R.id.togglebutton)
    ToggleButton togglebutton; //只是状态的改变，侧重在UI

    @BindView(R.id.iamgeSwitch)
    ImageSwitcher imageSwitcher; //（android4.0以后） 图片查看器，Gallery 在 安卓4.0版本已经 过时了，
    // 建议使用 ScrollView水平视图实现或者 GridView实现

    TextSwitcher textSwitcher; // 文本显示器，和Textview 类似，但是 TextSwitcher可以切换多个文本，并且有动画。
    // TextSwitcher 和 ImageSwitcher 都用到 Factory，在Factory中返回一个TextView，ImageView显示在控件上

    /**
     * FrameLayout
     * |
     * ViewAnimator
     * \
     * ViewSwitcher   ViewFlipper
     * |
     * ImageSwitcher      TextSwitcher
     *
     * @param savedInstanceState
     */

    /**
     * 有两种方式控制显示的View组件
     * <p>
     * 1.触摸操作拖走StackView中处于顶端的View,下一个View将会显示出来，将上一个View拖进StackView显示其View组件
     * <p>
     * 2.调用StackView的showPrevious(),showNext()方法控制显不上一个，下一个组件
     */
    StackView stackView; //StackView 堆叠视图


    Chronometer chronometer ; // 简单的计时器，通常在录像的时候用到，00:00样式
    TextClock textClock ;// 文本时中；

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space);
        ButterKnife.bind(this);
        initData();
        initSwitch();
    }

    private void initSwitch() {
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(TAG, "onclick=-=-=-=-=-=-=-=-=-=-" + switch1.isChecked());
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Logger.e(TAG, "onclick=-=-=-=-=-=-=-=-=-=onCheckedChangedonCheckedChanged-" + b);
            }
        });
    }

    private void initToggleButton() {
        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    private void initImageSwitch() {
        //imageSwitcher在xml中可以 设置 切换动画
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(SpaceActivity.this);
                return imageView;
            }
        });

        int[] lists = new int[]{};
        imageSwitcher.setImageResource(lists[0]);
    }


    private void initData() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add("hello" + i);
        }
        SpinnerAdater spinnerAdater = new SpinnerAdater(this, arrayList);

        spinner.setAdapter(spinnerAdater);
    }
}
