package com.example.yinpengcheng.annotationdemo;

import android.app.Activity;
import android.view.View;

/**
 * Created by yinpengcheng on 2018/2/10.
 */

public class ViewFinder {
    private Activity activity;

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }
    public View findViewById(int id){
        return activity.findViewById(id);
    }
    public View findViewById(int id, int pid){
        View pView = null;
        if (pid > 0){
            pView = this.findViewById(pid);
        }
        View view = null;
        if (pView != null){
            view = pView.findViewById(id);
        }else {
            view = this.findViewById(id);
        }
        return view;
    }
}
