package demo.ypc.arraylistpractise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(3);
//        list.add(5);
//        list.add(99);
//        list.add(-1);
//        list.add(0);
//        LogUtils.e(list);
//        Set<Integer> set = new HashSet<Integer>();
//        set.add(2);
//        set.add(3);
//        set.add(5);
//        set.add(1);
//        set.add(-1);
//        set.add(-55);
//        LogUtils.e(set);
//        Set<Integer> treeSet = new TreeSet<Integer>();
//        treeSet.add(3);
//        treeSet.add(-2);
//        treeSet.add(1);
//        treeSet.add(99);
//        treeSet.add(-34);
//        LogUtils.e(treeSet);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(5);
        Iterator iterator = list.iterator();
        LogUtils.e(iterator);
        LogUtils.e(iterator.next());
        LogUtils.e(iterator);
        iterator.remove();
        LogUtils.e(iterator);

    }
}
