package demo.ypc.hotfixdemo;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.apkfuns.logutils.LogUtils;

import java.io.File;

/**
 * Created by yinpengcheng on 2017/8/22.
 */

public class App extends Application {
    AndFixManager andFixManager;
    @Override
    public void onCreate() {
        super.onCreate();
        andFixManager = AndFixManager.getmInstance();
        andFixManager.initAndFix(this);
        File file = new File("/mnt/sdcard/out.apatch");
        LogUtils.e(file.exists());
        if (file.exists()){
            andFixManager.addPatch("/mnt/sdcard/out.apatch");
        }

    }
}
