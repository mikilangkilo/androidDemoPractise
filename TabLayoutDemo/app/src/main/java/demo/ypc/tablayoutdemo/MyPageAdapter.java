package demo.ypc.tablayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by yinpengcheng on 2017/7/14.
 */

public class MyPageAdapter extends PagerAdapter {
    ArrayList<View> datas;
    ArrayList<String> titles;

    public MyPageAdapter() {
        super();
    }

    public void setData(ArrayList<View> datas){
        this.datas = datas;
    }
    public void setTitle(ArrayList<String> titles){
        this.titles = titles;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas == null? 0:datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null? null:titles.get(position);
    }
}
