package highwho.com.okhttp3frompractise;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by yinpengcheng on 2017/11/7.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
