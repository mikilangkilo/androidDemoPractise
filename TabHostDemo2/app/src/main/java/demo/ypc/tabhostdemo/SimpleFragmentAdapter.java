package demo.ypc.tabhostdemo;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yinpengcheng on 2017/7/13.
 */

public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"tab1", "tab2", "tab3", "tab4"};
    private Context context;
    public SimpleFragmentAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return PagerFragment.newInstance(position+1);
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
