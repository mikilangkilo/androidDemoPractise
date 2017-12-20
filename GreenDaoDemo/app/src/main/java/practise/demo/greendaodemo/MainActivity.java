package practise.demo.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import practise.demo.greendaodemo.R;

public class MainActivity extends AppCompatActivity {
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    private StudentDao getStudentDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dao.db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getStudentDao();
    }
}
