package android.com.java.broadcastpractise;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/2/18.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
