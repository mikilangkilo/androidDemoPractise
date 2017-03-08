package android.com.java.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int Images[] = {R.mipmap.b1,R.mipmap.b2,R.mipmap.b3};
    private int index = 0;
    private ImageView imageView;
    private TextView textView;
    private Handler handler1 = new Handler();
    private MyRunnable myRunnable = new MyRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.image);
        textView = (TextView)findViewById(R.id.textView);
        handler1.postDelayed(myRunnable,1000);//跑一个runnable
        new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("100");
//                        }
//                    });
                    Message msg = new Message();
                    msg.arg1 = 88;
                    handler2.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler2 = new Handler(){//处理接收到的信息
        @Override
        public void handleMessage(Message msg) {
            textView.setText(""+msg.arg1);
        }
    };

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            index ++ ;
            index = index % 3;
            imageView.setImageResource(Images[index]);
            handler1.postDelayed(myRunnable,1000);//循环的跑下去
        }
    };


}