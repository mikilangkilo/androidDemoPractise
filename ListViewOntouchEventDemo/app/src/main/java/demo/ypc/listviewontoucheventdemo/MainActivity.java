package demo.ypc.listviewontoucheventdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listView)ListView listView;
    @BindView(R.id.add_button)
    Button addButton;
    int index;
    MyAdapter myAdapter;
    private float mFirstY, mCurrentY, mTouchSlop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        index = 0;
        myAdapter = new MyAdapter(this, R.layout.item);
        listView.setAdapter(myAdapter);
        Scroller scroller = new Scroller(this){
            @Override
            public boolean computeScrollOffset() {
                return super.computeScrollOffset();

            }

        };
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.add(index + "");
                myAdapter.notifyDataSetChanged();
//                ((View)addButton.getParent()).scrollBy(3,3);
//                ((View)addButton.getParent()).offsetLeftAndRight(3);
//                ((View)addButton.getParent()).offsetTopAndBottom(3);
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)addButton.getLayoutParams();
//                layoutParams.leftMargin = 3;
//                layoutParams.topMargin = 3;
//                addButton.setLayoutParams( layoutParams );
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)addButton.getLayoutParams();
                params.height = 100;
                params.width = 200;

                addButton.setLayoutParams(params);
                index ++;
            }
        });
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        LogUtils.e("actionDown");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        LogUtils.e("actionMove");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        LogUtils.e("actionUp");
//                        break;
//                    case MotionEvent.ACTION_BUTTON_PRESS:
//                        LogUtils.e("actionButtonPress");
//                        break;
//                    case MotionEvent.ACTION_BUTTON_RELEASE:
//                        LogUtils.e("actionButtonRelease");
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        LogUtils.e("actionCancel");
//                        break;
//                }
//                return false;
//            }
//        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i){
                    case SCROLL_STATE_IDLE:
                        LogUtils.e("scrollStateIdle");
                        break;
                    case SCROLL_STATE_FLING:
                        LogUtils.e("scrollStateFling");
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        LogUtils.e("scrollStateTouchScroll");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                LogUtils.e("firstVisibleItem : "+i);
                LogUtils.e("visibleItemCount : "+i1);
                LogUtils.e("totalItemCount : "+i2);
            }
        });

        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 400));
        listView.addHeaderView(header);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class MyAdapter extends ArrayAdapter{
        List<String> contentList = new ArrayList<>();
        private Context context;
        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            this.context = context;
        }

        @Override
        public void add(@Nullable Object object) {
            super.add(object);
            contentList.add(object.toString());
        }

        @Override
        public void clear() {
            super.clear();
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @Override
        public int getPosition(@Nullable Object item) {
            return super.getPosition(item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            return super.getView(position, convertView, parent);
            View rootView = LayoutInflater.from(context).inflate(R.layout.item, null);
            TextView textView = (TextView) rootView.findViewById(R.id.text);
            textView.setText(contentList.get(position));
            return rootView;
        }

    }
}
