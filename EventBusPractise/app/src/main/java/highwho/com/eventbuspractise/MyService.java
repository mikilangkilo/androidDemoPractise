package highwho.com.eventbuspractise;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by yinpengcheng on 2017/11/17.
 */

public class MyService extends Service {
    private final String TAG = "MyService";
    public MyService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }
    public class MyBinder extends Binder{
        public MyService onCall(){

            Log.d(TAG,"onCall");
            return MyService.this;
        }
    }
    public void Call(){
        Log.d(TAG, "call");
    }
    // 当一个Message Event提交的时候这个方法会被调用
    @Subscribe
    public void onMessageEvent(MessageEvent event){
        Toast.makeText(App.getInstance(), event.message, Toast.LENGTH_SHORT).show();
    }

    // 当一个SomeOtherEvent被提交的时候这个方法被调用。
    @Subscribe
    public void handleSomethingElse(SomeOtherEvent event){
        Toast.makeText(App.getInstance(), event.message, Toast.LENGTH_SHORT).show();
    }

}
