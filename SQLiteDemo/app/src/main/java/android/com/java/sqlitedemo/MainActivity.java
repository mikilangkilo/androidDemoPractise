package android.com.java.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private Button button;
    private Button add;
    private Button update;
    private Button delete;
    private Button query;
    private Button replace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        button = (Button)findViewById(R.id.button);
        add = (Button)findViewById(R.id.add);
        update = (Button)findViewById(R.id.update);
        delete = (Button)findViewById(R.id.delete);
        query = (Button)findViewById(R.id.query);
        replace = (Button)findViewById(R.id.replace);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getWritableDatabase();//getWritableDatabase()在第一次运行时会检测是否存在该表，不存在则创建（即调用MyDatabaseHelper的oncreate）
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","The da vinci code");
                values.put("author", "sample");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book",null,values);
                values.put("name","the lost symbols");
                values.put("author", "sample");
                values.put("pages", 500);
                values.put("price", 500);
                db.insert("Book",null,values);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"name = ?",new String[]{
                        "The da vinci code"
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[]{
                        "500"
                });
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e("ypc","name = "+name);
                        Log.e("ypc","author = "+author);
                        Log.e("ypc","pages = "+pages);
                        Log.e("ypc","prices = "+prices);

                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.beginTransaction();
                try{
                    db.delete("Book",null,null);
//                    if(true){
//                        throw new NullPointerException();
//                    }
                    db.execSQL("insert into Book (name, author, pages, price) values (?,?,?,?)",
                            new String[] { "game of thrones","gorge martin","720","20.85"});
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
    }
}
