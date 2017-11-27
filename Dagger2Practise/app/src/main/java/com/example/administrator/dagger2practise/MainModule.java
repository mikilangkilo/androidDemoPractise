package com.example.administrator.dagger2practise;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yinpengcheng on 2017/11/21.
 */

@Module
public class MainModule {
    @Provides
        // 关键字，标明该方法提供依赖对象
    @Singleton
    Person providerPerson(){
        //提供Person对象
        return new Person();
    }
}
