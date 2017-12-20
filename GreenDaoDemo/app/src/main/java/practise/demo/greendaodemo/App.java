package practise.demo.greendaodemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by yinpengcheng on 2017/12/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho
                .newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this)).build());
    }
}
