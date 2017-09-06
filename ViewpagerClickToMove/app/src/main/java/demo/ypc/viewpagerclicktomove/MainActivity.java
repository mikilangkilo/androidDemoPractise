package demo.ypc.viewpagerclicktomove;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.left)Button left;
    @BindView(R.id.right)Button right;
    @BindView(R.id.viewpager)ViewPager viewPager;

    private static final int PAGER_NUM = 10;
    private int mCurrentViewId = 0;
    private int mMyDuration = 100;
    private FixSpeedScroller mScroller;
    private List<View> mListViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = new GridView(this);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,)

    }

}
