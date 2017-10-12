package com.example.administrator.customchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Bmob.initialize(this, "9234dc949f7bf1742c6ea4eb91d9eb65");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
//        Bmob.initialize(this, "9234dc949f7bf1742c6ea4eb91d9eb65","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("9234dc949f7bf1742c6ea4eb91d9eb65")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }
}
