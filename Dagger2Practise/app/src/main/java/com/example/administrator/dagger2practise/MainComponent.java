package com.example.administrator.dagger2practise;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by yinpengcheng on 2017/11/21.
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
    void inject(SwitchActivity activity);
}
