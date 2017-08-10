package demo.ypc.mp4showdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";
    SurfaceView surfaceView;
    Button player, pause, stop;
    MediaPlayer mediaPlayer;
    FileInputStream fileInputStream;
    private Context context;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/index.html");
    }



}
