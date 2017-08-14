package demo.ypc.nanohttpd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            nano nano = new nano(8020);
            nano.start();

        }catch (IOException e){
            LogUtils.e(e);
        }
    }
}
