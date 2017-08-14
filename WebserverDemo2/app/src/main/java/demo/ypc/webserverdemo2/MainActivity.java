package demo.ypc.webserverdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;
import com.zy.mocknet.MockNet;
import com.zy.mocknet.application.MockConnection;
import com.zy.mocknet.application.MockConnectionFactory;

public class MainActivity extends AppCompatActivity {
    SimpleWebServer simpleWebServer;
    private MockNet mockNet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        simpleWebServer = new SimpleWebServer(8011, getAssets());
//        simpleWebServer.start();
//        simpleWebServer.run("mnt/internal_sd/webscreen/mp4/c4.mp4");
//        LogUtils.e(simpleWebServer.getPort());
        initServer();
    }
    private void initServer(){
        mockNet = MockNet.create().addConnection(MockConnectionFactory.getInstance().createGeneralConnection("/*", "Hello, world!"))
                .addConnection(MockConnectionFactory.getInstance().createGeneralConnection(MockConnection.GET, "/api","{\"status\": \"success\"}"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mockNet.start(8001);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mockNet.stop();
    }
}
