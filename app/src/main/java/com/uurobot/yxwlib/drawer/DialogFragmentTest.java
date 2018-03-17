package com.uurobot.yxwlib.drawer;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class DialogFragmentTest extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.dialog_test);




        }

        public void onClick(View view) {
                MyDialog myDialog = new MyDialog();

                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                myDialog.show(fragmentManager,"");
        }

        @Override
        protected void onPause() {
                super.onPause();

        }

        public static class MyDialog extends DialogFragment{
                public MyDialog() {

                }

                @Nullable
                @Override
                public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
                        View inflate = inflater.inflate(R.layout.dialog_fragment, container, false);
                        Button button = inflate.findViewById(R.id.button4);
                        button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        getDialog().dismiss();
                                }
                        });
                        return inflate;
                }
        }

}



