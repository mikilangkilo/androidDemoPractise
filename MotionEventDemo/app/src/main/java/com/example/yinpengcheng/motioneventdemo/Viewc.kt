package com.example.yinpengcheng.motioneventdemo

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * Created by yinpengcheng on 2018/1/13.
 */
class Viewc (context: Context) : RelativeLayout(context) {
    val tag:String = "viewc"
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> Log.d(tag,"action_down")
            MotionEvent.ACTION_UP -> Log.d(tag,"action_up")
            MotionEvent.ACTION_MOVE -> Log.d(tag,"action_move")
            MotionEvent.ACTION_CANCEL ->Log.d(tag,"action_cancel")
            MotionEvent.ACTION_OUTSIDE -> Log.d(tag,"action_outside")
            MotionEvent.ACTION_POINTER_DOWN -> Log.d(tag,"action_pointer_down")
            MotionEvent.ACTION_POINTER_UP -> Log.d(tag,"action_pointer_up")
            else -> Log.d(tag,"nothing")

        }

        return super.onTouchEvent(event)
    }
}