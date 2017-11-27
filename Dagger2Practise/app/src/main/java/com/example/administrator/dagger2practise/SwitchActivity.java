package com.example.administrator.dagger2practise;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by yinpengcheng on 2017/11/21.
 */

public class SwitchActivity extends Activity {
    private final String TAG = getClass().getSimpleName().toString();
    @Inject
    Person person;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainComponent mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        mainComponent.inject(this);
        Log.i(TAG, "2 person = "+person.toString());
    }
}
