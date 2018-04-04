package com.uurobot.yxwlib.uiTest.TextLayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4.
 */

/**
 * TextInputLayout的特点：  提供一个显示在EditText上方的浮动标签
 * <p>
 * 1. TextInputLayout不能单独使用，必须和EditText一起使用
 * 2. 一个TextInputLayout里面只能放一个 EditText
 * <p>
 * 3.TextInputLayout中的EditText，既能将hint显示在EditText上面，还能降error显示在下面
 */


public class TextLayoutActivity extends Activity {

        @BindView(R.id.textInput_layout)
        TextInputLayout textInputLayout; // 他的浮动标签会占用空间（它的浮动标签其实就是textview）

        @BindView(R.id.textInput_layout2)
        TextInputLayout textInputLayout2;

        @BindView(R.id.button11)
        Button button11;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.textlayout);
                ButterKnife.bind(this);

                // textInputLayout.setError(getString(R.string.phone_error)); // 当输入验证错误是调用
                textInputLayout.setHint(getString(R.string.phone_input));

                // textInputLayout2.setError(getString(R.string.password_error));
                textInputLayout2.setHint(getString(R.string.password_input));

                button11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EditText editText = textInputLayout.getEditText();
                                String s = editText.getText().toString();
                                if ("1".equals(s)){
                                        textInputLayout.setError(getString(R.string.phone_error));
                                }else {
                                        textInputLayout.setError(null);
                                }
                        }
                });
        }
}
