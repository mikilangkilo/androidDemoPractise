package demo.ypc.expandablelistviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> groupArray;
    private List<List<String>>childArray;
    private ExpandableListView expandableListView;
    private ExpandableAdapter expandableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupArray = new ArrayList<String>();
        childArray = new ArrayList<List<String>>();
        groupArray.add("第一行");
        groupArray.add("dierhang");
        List<String> tempArray = new ArrayList<String>();
        tempArray.add("1");
        tempArray.add("2");
        tempArray.add("3");
        for (int index = 0; index < groupArray.size(); index++){
            childArray.add(tempArray);
        }
        expandableListView = (ExpandableListView)findViewById(R.id.expandable_listview);
        expandableAdapter = new ExpandableAdapter(this);
        expandableListView.setAdapter(expandableAdapter);

    }
    public class ExpandableAdapter extends BaseExpandableListAdapter{
        Context context;
        public ExpandableAdapter(Context context){
            this.context = context;
        }

        @Override
        public Object getChild(int i, int i1) {
            return childArray.get(i).get(i1);
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public int getChildrenCount(int i) {
            return childArray.get(i).size();
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            String string = childArray.get(i).get(i1);
            return getGenericView(string);
        }

        @Override
        public Object getGroup(int i) {
            return groupArray.get(i);
        }

        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            String string = groupArray.get(i);
            return getGenericView(string);
        }

        public TextView getGenericView(String string){
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
            textView.setPadding(36, 0, 0, 0);
            textView.setText(string);
            return textView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }
}
