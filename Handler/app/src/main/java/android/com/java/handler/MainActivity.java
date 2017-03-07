package android.com.java.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button;
    private int change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.image);
        button = (Button)findViewById(R.id.button);
        change = 0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change++;
                if(change % 2 == 0){
                    thread.start();
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    imageView.setImageResource(R.mipmap.b1);
                    break;
                case 1:
                    imageView.setImageResource(R.mipmap.b2);
                    break;
                case 2:
                    imageView.setImageResource(R.mipmap.b3);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            int i =1;
            while (true){
                handler.sendEmptyMessage((i++)%3);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    });

}
