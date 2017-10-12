package com.example.administrator.customchat.Application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/10/10.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!LeakCanary.isInAnalyzerProcess(this)){
            LeakCanary.install(this);
        }

    }
}
