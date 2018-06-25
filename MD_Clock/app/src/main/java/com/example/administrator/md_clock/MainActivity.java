package com.example.administrator.md_clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;

import static com.example.administrator.md_clock.Utils.BottomNavigationUtil.disableShiftMode;

public class MainActivity extends BaseActivity {
    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_world_clock:
                    mTextMessage.setText(R.string.navigation_world_clock);
                    return true;
                case R.id.navigation_alarm_clock:
                    mTextMessage.setText(R.string.navigation_alarm_clock);
                    return true;
                case R.id.navigation_sleep:
                    mTextMessage.setText(R.string.navigation_sleep);
                    return true;
                case R.id.navigation_second_clock:
                    mTextMessage.setText(R.string.navigation_second_clock);
                    return true;
                case R.id.navigation_time_counter:
                    mTextMessage.setText(R.string.navigation_time_counter);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(){
        mTextMessage = (TextView) findViewById(R.id.message);
        disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
