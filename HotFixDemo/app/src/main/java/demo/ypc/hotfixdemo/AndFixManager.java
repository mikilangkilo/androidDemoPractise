package demo.ypc.hotfixdemo;

import android.content.Context;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

/**
 * Created by yinpengcheng on 2017/8/22.
 */

public class AndFixManager {
    private static AndFixManager mInstance;
    private static PatchManager mPatchManager;
    private AndFixManager(){}
    public static AndFixManager getmInstance(){
        if (mInstance == null){
            synchronized (AndFixManager.class){
                if (mInstance == null){
                    mInstance = new AndFixManager();
                }
            }
        }
        return mInstance;
    }
    public void initAndFix(Context context){
        mPatchManager = new PatchManager(context);
        mPatchManager.init(getVersionName(context));
        mPatchManager.loadPatch();
    }

    public void addPatch(String patchPath){
        if (mPatchManager != null){
            try {
                mPatchManager.addPatch(patchPath);
            }catch (IOException e){
                LogUtils.e(e);
            }
        }
    }

    private String getVersionName(Context context){
        String appVersion = null;
        try {
             appVersion= context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }catch (PackageManager.NameNotFoundException e){
            LogUtils.e(e);
        }
        return appVersion;
    }

}
