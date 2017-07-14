package demo.ypc.viewpager_viewdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<View> viewArrayList = new ArrayList<View>();
    ArrayList<String> titles = new ArrayList<String>();
    ViewPager viewPager;
    TabLayout tabLayout;
    TabLayout.Tab tab;
    private static final String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.indicator);
        View view1 = getLayoutInflater().inflate(R.layout.a, null);
        View view2 = getLayoutInflater().inflate(R.layout.b, null);
        View view3 = getLayoutInflater().inflate(R.layout.c, null);
        viewArrayList.add(view1);
        viewArrayList.add(view2);
        viewArrayList.add(view3);
        titles.add("a");
        titles.add("b");
        titles.add("c");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewArrayList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewArrayList.get(position));
            return viewArrayList.get(position);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    };


}
