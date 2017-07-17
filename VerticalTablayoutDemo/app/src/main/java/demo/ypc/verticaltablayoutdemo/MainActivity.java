package demo.ypc.verticaltablayoutdemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    VerticalTabLayout verticalTabLayout;
    ArrayList<Struct> arrayList = new ArrayList<Struct>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        verticalTabLayout = (VerticalTabLayout)findViewById(R.id.tablayout);
        View view1 = getLayoutInflater().inflate(R.layout.a, null);
        View view2 = getLayoutInflater().inflate(R.layout.b, null);
        View view3 = getLayoutInflater().inflate(R.layout.c, null);
        arrayList.add(new Struct(view1, "1"));
        arrayList.add(new Struct(view2, "2"));
        arrayList.add(new Struct(view3, "3"));
        viewPager.setAdapter(pagerAdapter);
        verticalTabLayout.setTabMode(VerticalTabLayout.TAB_MODE_FIXED);
        verticalTabLayout.setupWithViewPager(viewPager);
    }

    public class Struct{
        private View view;
        private String title;
        public Struct(View view, String title){
            this.view = view;
            this.title = title;
        }
        public View getView(){
            return view;
        }
        public String getTitle(){
            return title;
        }
    }
    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(arrayList.get(position).getView());
            return arrayList.get(position).getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(arrayList.get(position).getView());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position).getTitle();
        }
    };

}
