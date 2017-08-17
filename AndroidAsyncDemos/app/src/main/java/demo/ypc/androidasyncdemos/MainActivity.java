package demo.ypc.androidasyncdemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncHttpServer server = new AsyncHttpServer();
        server.get("/\\w*.mp4", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                LogUtils.e("in");
                LogUtils.e(request.getPath().replace("/",""));
                String fileName = request.getPath().replace("/","");
                File file = new File("/mnt/internal_sd/webscreen/mp4/"+fileName);
                if (file.exists()){
                    response.sendFile(file);
                }

            }
        });
        server.listen(5000);
    }
}
