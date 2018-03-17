package com.uurobot.yxwlib.mvp1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2017/11/22.
 */

public class MVP1Activity extends Activity implements IView {

        @Override
        public void showLoginState(boolean state) {
                Toast.makeText(this,Boolean.toString(state),Toast.LENGTH_SHORT).show();
        }



        private EditText editText ;
        private IPresent iPresent ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_mvp1);

                editText = findViewById(R.id.input);
                iPresent = new PresentImpl(this);

        }

        public void onClick(View v){
                String s = editText.getText().toString();
                iPresent.login(s,"");
        }
}
