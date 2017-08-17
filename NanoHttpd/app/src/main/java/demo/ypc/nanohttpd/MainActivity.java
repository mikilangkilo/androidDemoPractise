package demo.ypc.nanohttpd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;

public class MainActivity extends AppCompatActivity {
    NanoHTTPD nanoHTTPD;
    int port = 8080;
    File wwwroot;
    String hostaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        wwwroot = new File("/mnt/internal_sd/webscreen");
//        nanoHTTPD = new NanoHTTPD(port, wwwroot);
        try {
            nano nano = new nano(8020);
            nano.start();

        }catch (IOException e){
            LogUtils.e(e);
        }
//        try {
//
//            webServer.start();
//        }catch (Exception e){
//            LogUtils.e(e);
//        }
    }
}
