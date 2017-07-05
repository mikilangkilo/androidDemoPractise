package com.example.yinpengcheng.wise.Authentic;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yinpengcheng.wise.App;
import com.example.yinpengcheng.wise.R;
import com.example.yinpengcheng.wise.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yinpengcheng on 2017/7/4.
 */

public class AuthenticManager {
    private static final String PREFERENCE_AUTHENTIC_NAME = "AUTHENTIC";
    private static final String PREFERENCE_TOKEN_NAME = "AUTHENTIC_TOKEN";
    private static final String TAG = "AuthenticManager";

    private RequestQueue mRequestQueue;
    private App mApp;
    private SharedPreferences mSharedPreferences;

    public interface LoginCallback extends Response.ErrorListener, Response.Listener<JSONObject> {

    }

    public AuthenticManager(RequestQueue requestQueue, App app) {
        this.mRequestQueue = requestQueue;
        this.mApp = app;
        mSharedPreferences = app.getContext().getApplicationContext().getSharedPreferences(PREFERENCE_AUTHENTIC_NAME, Context.MODE_PRIVATE);
    }

    public void login(String userName, String password, LoginCallback callback) {
        Logger.i(TAG, "login");
        String url = mApp.getPreferenceReader().getApiUri(String.format("/token?username=%s&password=%s",
                userName.trim(), password.trim()));
        Logger.d(TAG, url);
        Request<JSONObject> request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                callback,
                callback
        );
        mRequestQueue.add(request);
    }
    public void loginByDefault(final String userName, String password){
        Logger.i(TAG, "loginByDefault");
        LoginCallback loginCallback = new LoginCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String loginFailuer = mApp.getContext().getApplicationContext().getResources().getString(R.string.login_failure);
                Logger.i(TAG, loginFailuer);
                Toast.makeText(mApp.getContext().getApplicationContext(), loginFailuer, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                String name = mApp.getContext().getApplicationContext().getResources().getString(R.string.user) + userName + "";
                String loginSuccess = mApp.getContext().getApplicationContext().getString(R.string.login_success);
                try {
                    String token = response.getString("token");
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(PREFERENCE_TOKEN_NAME, token);
                    if (!editor.commit()){
                        Logger.i(TAG,"cookie error");
                    }
                }catch (JSONException e){
                    Logger.e(TAG, "crash e = "+e);
                }
                Toast.makeText(mApp.getContext().getApplicationContext(), name + loginSuccess,Toast.LENGTH_LONG).show();

            }
        };
        this.login(userName, password, loginCallback);
    }
}
