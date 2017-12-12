package practise.demo.marqueetextpractise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import practise.demo.marqueelibrary.MarqueeFactory;
import practise.demo.marqueelibrary.SimpleMF;
import practise.demo.marqueelibrary.SimpleMarqueeView;

public class MainActivity extends AppCompatActivity {

    private List<String> datas = new ArrayList<>();
    private SimpleMarqueeView marqueeView1;
    private Button btn;
    private Animation inAnimation, outAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeView1 = (SimpleMarqueeView)findViewById(R.id.marqueeView);
        btn = (Button)findViewById(R.id.btn);
        datas.add("1111");
        initMarqueeView1();
    }
    private void initMarqueeView1() {
        final SimpleMF<String> marqueeFactory = new SimpleMF(MainActivity.this);
        marqueeFactory.setData(datas);
        marqueeView1.setAnimateFirstView(true);
        marqueeView1.setMarqueeFactory(marqueeFactory);
//        marqueeView1.startFlipping();
        marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(MainActivity.this, holder.data, Toast.LENGTH_SHORT).show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marqueeFactory.addData("ggg");
                marqueeView1.showNextData();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
//        marqueeView1.stopFlipping();
//        marqueeView2.stopFlipping();
//        marqueeView3.stopFlipping();
    }
}
