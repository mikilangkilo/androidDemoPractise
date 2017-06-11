package android.com.java.netdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView show;
    Bitmap mBitmap;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123){
                show.setImageBitmap(mBitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (ImageView)findViewById(R.id.image_view);
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://img.sootuu.com/vector/2007-07-01/066/1/479.gif");
                    InputStream is = url.openStream();//解析url
                    mBitmap = BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(0x123);//更新组件
                    OutputStream os = openFileOutput("crazyit.png",MODE_PRIVATE);
                    byte [] buff = new byte[1024];
                    int hasRead = 0;
                    while((hasRead = is.read(buff))>0){
                        os.write(buff,0,hasRead);
                    }
                    is.close();
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
