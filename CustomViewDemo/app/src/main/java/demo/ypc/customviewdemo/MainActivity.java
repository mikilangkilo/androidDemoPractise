package demo.ypc.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    RocketView rocketView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rocketView = (RocketView) findViewById(R.id.rocketview);
        rocketView.setOvalRect(0,0, 500, 100);
    }
}
