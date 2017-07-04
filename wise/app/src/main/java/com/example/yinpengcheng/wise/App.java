package com.example.yinpengcheng.wise;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.yinpengcheng.wise.Authentic.AuthenticManager;

/**
 * Created by yinpengcheng on 2017/7/4.
 */

public class App {

    private static volatile App mInstance;
    private final Context mContext;
    private RequestQueue mRequestQueue;
    private AuthenticManager mAuthenticManager;
    private PreferenceReader mPreferenceReader;


    private App(Context context){
        mContext = context;
    }

    private static App getInstance(Context context){
        if (mInstance == null){
            synchronized (App.class){
                if (mInstance == null)
                    mInstance = new App(context).init();
            }
        }
        return mInstance;
    }

    public static PreferenceReader preferenceReader(Context context){
        return getInstance(context).getPreferenceReader();
    }

    public static AuthenticManager authenticManager(Context context){
        return getInstance(context).getAuthenticManager();
    }

    public static RequestQueue wiseRequestQueue(Context context){
        return getInstance(context).getWiseRequestQueue();
    }

    private App init(){
        mPreferenceReader = new PreferenceReader();
        mPreferenceReader.initPreference();
        initWiseRequestQueue(this);
        initAuthenticManager(this);
        return this;
    }

    private synchronized void initWiseRequestQueue(App app){
        mRequestQueue = Volley.newRequestQueue(app.getContext().getApplicationContext());
    }

    private synchronized void initAuthenticManager(App app){
        mAuthenticManager = new AuthenticManager(mRequestQueue, app);
    }

    public Context getContext(){
        return mContext;
    }


    public AuthenticManager getAuthenticManager(){
        return mAuthenticManager;
    }

    public PreferenceReader getPreferenceReader(){
        return mPreferenceReader;
    }

    public RequestQueue getWiseRequestQueue(){
        return mRequestQueue;
    }

    public class PreferenceReader{
        private String apiHostKey;
        private String apiProtoclKey;
        private SharedPreferences defaultPreference;

        public void initPreference(){
            apiHostKey = "api_host";
            apiProtoclKey = "api_protocol";
            defaultPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        public String getScheme(){
            return defaultPreference.getString(apiProtoclKey, "");
        }

        public String getApiHostKey(){
            return defaultPreference.getString(apiHostKey, "");
        }

        public String getApiUri(String path){
            return String.format("%s://%s/wise/rest/v1%s", getScheme(), getApiHostKey(), path);
        }
    }
}
