package android.com.java.fragmentpractise;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.ArrayAdapter;

public class MainActivity extends LauncherActivity {
    String[] names = {"设置程序参数", "查看星际兵种"};
    Class<?>[] clazzs = {PreferenceActivity.class, ExpandableListActivityTest.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
    }

    @Override
    protected Intent intentForPosition(int position) {
//        return super.intentForPosition(position);
        return new Intent(MainActivity.this,clazzs[position]);
    }
}
