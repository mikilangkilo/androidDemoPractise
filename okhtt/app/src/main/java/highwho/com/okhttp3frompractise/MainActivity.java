package highwho.com.okhttp3frompractise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.send);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String filename = "/storage/emulated/0/temp/1510023122643.jpg";
                            File file = new File(filename);
                            LogUtils.e(file.exists());
//                            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
//                            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"picture\"; filename=\""+file.getName().toString()+"\"\r\nContent-Type: image/jpeg\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
//                            Request request = new Request.Builder()
//                                    .url("http://192.168.20.11:8080/rest/v1/food/pictures")
//                                    .post(body)
//                                    .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
//                                    .addHeader("access_token", "abc123")
//                                    .build();

                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("picture", file.getName(),
                                            RequestBody.create(MediaType.parse("image/jpeg"), file))
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://192.168.20.11:8080/rest/v1/food/pictures")
                                    .post(requestBody)
                                    .header("access_token", "abc123")
                                    .build();

                            LogUtils.e(request);
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()){

                                LogUtils.e(response.body().string());
                            }
                        }catch (IOException e){
                            LogUtils.e(e);
                        }
                    }
                }).start();

            }
        });
    }
}
