package demo.ypc.customlistviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Context context;
    ArrayList<stuc> arraylist = new ArrayList<stuc>();
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        arraylist.add(new stuc("11",1));
        arraylist.add(new stuc("22",2));
        arraylist.add(new stuc("33",3));
        arraylist.add(new stuc("44",4));
        listView = (ListView)findViewById(R.id.myListView);
        myAdapter = new MyAdapter(context, R.layout.item, arraylist);
        listView.setAdapter(myAdapter);

    }
    private class MyAdapter extends ArrayAdapter{
        int resourceId;
        ArrayList<stuc> record;
        Context context;
        public MyAdapter(Context context, int resourceId, ArrayList<stuc> record){
            super(context, resourceId);
            this.resourceId = resourceId;
            this.record = record;
            this.context = context;
        }

        @Nullable
        @Override
        public stuc getItem(int position) {
            return record.get(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getCount() {
            return record.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View rootview = LayoutInflater.from(context).inflate(resourceId,null);
            TextView content = (TextView)rootview.findViewById(R.id.content);
            TextView count = (TextView)rootview.findViewById(R.id.count);
            content.setText(getItem(position).getContent());
            count.setText(getItem(position).getCount()+"");
            return rootview;
        }
    }

}
