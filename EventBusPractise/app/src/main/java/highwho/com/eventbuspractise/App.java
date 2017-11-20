package highwho.com.eventbuspractise;

import android.app.Application;
import android.content.Context;

/**
 * Created by yinpengcheng on 2017/11/17.
 */

public class App extends Application {
    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }
    public static Context getInstance(){
        return instance;
    }
}
