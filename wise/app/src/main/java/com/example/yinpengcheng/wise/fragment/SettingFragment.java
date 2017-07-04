package com.example.yinpengcheng.wise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yinpengcheng.wise.R;

/**
 * Created by yinpengcheng on 2017/7/4.
 */

public class SettingFragment extends PreferenceFragment {
    private static final String TAG = "SettingFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null){
            view.setBackgroundColor(Color.WHITE);
        }
        return view;
    }
}
