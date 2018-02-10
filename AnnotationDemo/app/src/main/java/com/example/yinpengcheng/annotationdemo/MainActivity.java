package com.example.yinpengcheng.annotationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by yinpengcheng on 2018/2/10.
 */

public class MainActivity extends Activity {
    @ViewInject(R.id.text)
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        textView.setText("hehehehehe");
    }
}
