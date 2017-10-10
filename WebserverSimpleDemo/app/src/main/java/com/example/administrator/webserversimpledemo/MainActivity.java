package com.example.administrator.webserversimpledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Thread(new SimpleWebServer(8000,getAssets())).start();
//        new SimpleWebServer(8000, getAssets()).start();
        new SimpleWebServer(8000, getAssets()).run();
    }
}
