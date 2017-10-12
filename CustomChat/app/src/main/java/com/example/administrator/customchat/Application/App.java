package com.example.administrator.customchat.Application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/10/10.
 */

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (!LeakCanary.isInAnalyzerProcess(this)){
            LeakCanary.install(this);
        }
    }
    public static Context getContext(){
        return mContext;
    }
}
