package demo.ypc.okhttpdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final OkHttpClient client = new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url("http://www.glhospital.com:82//webapinew/api/UnifiedData/GetExpertInfo").build();
                    Response response = client.newCall(request).execute();
                    LogUtils.e(response);
                }catch (IOException e){
                    Log.e("main", e+"");
                }
            }
        }).start();


    }
}
