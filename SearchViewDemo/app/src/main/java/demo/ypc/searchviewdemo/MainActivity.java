package demo.ypc.searchviewdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    SearchView sv = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SearchView) this.findViewById(R.id.sv);

        sv.setIconifiedByDefault(false);

        sv.setSubmitButtonEnabled(true);

        sv.setQueryHint("查询");

        //通过反射，修改默认的样式，可以从android的search_view.xml中找到需要的组件

        try {
            Field field = sv.getClass().getDeclaredField("mSubmitButton");

            field.setAccessible(true);

            ImageView iv = (ImageView) field.get(sv);

            iv.setImageDrawable(this.getResources().getDrawable(
                    R.mipmap.ic_launcher));


        } catch (Exception e) {

            e.printStackTrace();
        }

        Cursor cursor = this.getTestCursor();

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.my_textview, cursor, new String[] { "tb_name" },
                new int[] { R.id.textview });

        sv.setSuggestionsAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String str) {

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String str) {

                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

                return false;
            }

        });

    }

    //添加suggestion需要的数据
    public Cursor getTestCursor() {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                this.getFilesDir() + "/my.db3", null);

        Cursor cursor = null;
        try {

            String insertSql = "insert into tb_test values (null,?,?)";

            db.execSQL(insertSql, new Object[] { "aa", 1 });

            db.execSQL(insertSql, new Object[] { "ab", 2 });

            db.execSQL(insertSql, new Object[] { "ac", 3 });

            db.execSQL(insertSql, new Object[] { "ad", 4 });

            db.execSQL(insertSql, new Object[] { "ae", 5 });

            String querySql = "select * from tb_test";

            cursor = db.rawQuery(querySql, null);

        } catch (Exception e) {

            String sql = "create table tb_test (_id integer primary key autoincrement,tb_name varchar(20),tb_age integer)";

            db.execSQL(sql);

            String insertSql = "insert into tb_test values (null,?,?)";

            db.execSQL(insertSql, new Object[] { "aa", 1 });

            db.execSQL(insertSql, new Object[] { "ab", 2 });

            db.execSQL(insertSql, new Object[] { "ac", 3 });

            db.execSQL(insertSql, new Object[] { "ad", 4 });

            db.execSQL(insertSql, new Object[] { "ae", 5 });

            String querySql = "select * from tb_test";

            cursor = db.rawQuery(querySql, null);
        }

        return cursor;
    }

}
