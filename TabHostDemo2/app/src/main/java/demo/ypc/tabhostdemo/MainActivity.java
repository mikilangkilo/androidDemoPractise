package demo.ypc.tabhostdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SimpleFragmentAdapter simpleFragmentAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleFragmentAdapter = new SimpleFragmentAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(simpleFragmentAdapter);
        tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }
}
