package demo.ypc.webdownloaddemos;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    URL url;
    AsyncHttpClient asyncHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String path = "http://192.168.20.40:8000/c1.mp4";
//                    url = new URL(path);
//                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//                    InputStream inputStream = urlConnection.getInputStream();
//                    String SDPath = Environment.getExternalStorageDirectory() + "/";
//                    String dirName = "/webscreen/resource";
//                    String fileName = "practise";
//                    File dir = new File(SDPath + dirName);
//                    dir.mkdirs();
//                    File file = new File(SDPath + dir + "/" + fileName);
//                    file.createNewFile();
//                    OutputStream outputStream = new FileOutputStream(file);
//                    byte[] buffer = new byte[1*1024];
//                    int downloadNum = 0;
//                    while ((downloadNum = inputStream.read(buffer)) != -1){
//                        outputStream.write(buffer, 0, downloadNum);
//                    }
//                    outputStream.flush();
//                }catch (Exception e){
//                    LogUtils.e(e);
//                }
//            }
//        }).start();
        asyncHttpClient = AsyncHttpClient.getDefaultInstance();
        AsyncHttpRequest asyncHttpRequest = new AsyncHttpGet("http://192.168.20.40:8000/c1.mp4");
        asyncHttpClient.executeFile(asyncHttpRequest, Environment.getExternalStorageState() + "/webscreen/resource" + "/" + "practise.mp4", new AsyncHttpClient.FileCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
                if (e!=null){
                    LogUtils.e(e);
                }
            }
        });



    }
}
