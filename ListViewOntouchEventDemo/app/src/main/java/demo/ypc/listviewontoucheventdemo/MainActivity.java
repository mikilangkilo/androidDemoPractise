package demo.ypc.listviewontoucheventdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        index = 0;
        myAdapter = new MyAdapter(this, R.layout.item);
        listView.setAdapter(myAdapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.add(index + "");
                myAdapter.notifyDataSetChanged();
                index ++;
            }
        });
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
