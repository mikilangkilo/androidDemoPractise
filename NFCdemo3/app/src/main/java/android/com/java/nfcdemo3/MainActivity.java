package android.com.java.nfcdemo3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    Request request;
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = client.newCall(request).execute();
                    }catch (IOException e){

                }
                }}).start();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
