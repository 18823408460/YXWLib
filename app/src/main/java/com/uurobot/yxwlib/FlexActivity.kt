package com.uurobot.yxwlib


import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.ArrayMap
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.google.android.flexbox.FlexboxLayout
import com.uurobot.yxwlib.alarm.Logger

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Created by Administrator on 2017/12/13.
 */

class FlexActivity : Activity() {
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    Logger.e(TAG, "msg1==========start")
                    SystemClock.sleep(5000)
                    Logger.e(TAG, "msg1==========end")
                }
                2 -> Logger.e(TAG, "msg2==========")
                3 -> Logger.e(TAG, "msg3==========")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flexlayout)
        val flexLayout = findViewById<FlexboxLayout>(R.id.flexLayout)
        val childCount = flexLayout.childCount
        for (i in 0 until childCount) {
            val childAt = flexLayout.getChildAt(i) as TextView
            childAt.setOnClickListener { Toast.makeText(this@FlexActivity, "show" + childAt.text, Toast.LENGTH_SHORT).show() }
        }
        handler.sendEmptyMessageDelayed(1, 1000)
        handler.sendEmptyMessageDelayed(2, 1300)
        handler.sendEmptyMessageDelayed(3, 1300)
        handler.sendEmptyMessageDelayed(3, 1300)

        val sparseArray = SparseArray<String>()
        sparseArray.append(2, "hello")
        sparseArray.append(2, "hello2")

        for (i in 0 until sparseArray.size()) {
            val key = sparseArray.keyAt(i)
            Logger.e(TAG, "key=" + key + ", value=" + sparseArray.get(key))
        }


    }

    companion object {

        private val TAG = "FlexActivity"
    }
}
