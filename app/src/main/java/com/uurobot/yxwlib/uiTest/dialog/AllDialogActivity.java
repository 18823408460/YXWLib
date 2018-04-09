package com.uurobot.yxwlib.uiTest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loonggg.bottomsheetpopupdialoglib.ShareBottomPopupDialog;
import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.util.ToastUtil;

import org.utils.common.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23.
 */

public class AllDialogActivity extends Activity {

        private static final String TAG = "AllDialogActivity";

        @BindView(R.id.button6)
        Button button6;

        @BindView(R.id.allLayout)
        ConstraintLayout allLayout;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_all_dialog);
                ButterKnife.bind(this);
        }

        @OnClick(R.id.button6)
        public void setButton6() {
//                choiceDialog();
                testMyDialog();
        }


        // 自定义dialog 的大小只能通过 代码来控制，

        private void testMyDialog(){
//                DialogFragment dialogFragment = new DialogFragment();
                Dialog dialog = new Dialog(this,R.style.mydialog);
                dialog.setContentView(R.layout.mydialog);
                dialog.show();
                Window window = dialog.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                attributes.width = (int) (displayMetrics.widthPixels*0.5);
                attributes.height = (int) (displayMetrics.heightPixels*0.2);

//                window.setGravity(Gravity.BOTTOM);
//                attributes.x =
//                attributes.y =  位置设置

                window.setAttributes(attributes);
        }


        private void testBottomDialog() {
                LayoutInflater inflater = LayoutInflater.from(this);
                View inflate = inflater.inflate(R.layout.dialog_bottom_test, null);
                ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this,inflate);
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int heightPixels = displayMetrics.heightPixels;
                shareBottomPopupDialog.getmPopupWindow().setHeight(heightPixels/2);
                shareBottomPopupDialog.showPopup(allLayout);
        }


        private void baseDialog() {
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .content(R.string.content)
                        .positiveText(R.string.comfirm)
                        .negativeText(R.string.cancel)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                        new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        ToastUtil.getToastUtil().show("cancel");
                                                }
                                        }).start();

                                }
                        })
                        .show();
        }

        private void baseDialogIcon() {
                //title 旁边有个 icon
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .content(R.string.content)
                        .positiveText(R.string.comfirm)
                        .negativeText(R.string.cancel)
                        .iconRes(R.drawable.icon_lawyer)
                        .neutralText(R.string.more)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                        new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        ToastUtil.getToastUtil().show("cancel"); }
                                        }).start();

                                }
                        })
                        .show();
        }


        private void choiceDialog() {
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .items(R.array.items)
                        .show();
        }

}
