package demo.ypc.tablayoutdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    MyPageAdapter myPageAdapter;
    AFragment aFragment;
    BFragment bFragment;
    CFragment cFragment;
    ArrayList<View> datas = new ArrayList<View>();
    ArrayList<String> titles = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPageAdapter = new MyPageAdapter();
        aFragment = new AFragment();
        bFragment = new BFragment();
        cFragment = new CFragment();
        View view1 = getLayoutInflater().inflate(R.layout.a, null);
        View view2 = getLayoutInflater().inflate(R.layout.b, null);
        View view3 = getLayoutInflater().inflate(R.layout.c, null);
        datas.add(view1);
        datas.add(view2);
        datas.add(view3);
        titles.add("a");
        titles.add("b");
        titles.add("c");
        myPageAdapter.setData(datas);
        myPageAdapter.setTitle(titles);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
