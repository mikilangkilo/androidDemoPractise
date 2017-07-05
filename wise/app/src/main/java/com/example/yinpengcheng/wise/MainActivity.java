package com.example.yinpengcheng.wise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.yinpengcheng.wise.fragment.SettingFragment;
import com.example.yinpengcheng.wise.util.Logger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Toolbar mToolbar = null;
    private Context mContext = null;
    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.i(TAG,"onCreate");
        mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mContext = this;
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initToolbar(mContext,mToolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void initToolbar(Context context,Toolbar toolbar){
        toolbar.setTitle(context.getResources().getString(R.string.app_name));
        toolbar.setTitleTextAppearance(mContext, R.style.toolbar_title);
        toolbar.setBackgroundColor(context.getResources().getColor(R.color.toolbar_background));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.toolbar_export_data:
                        Logger.i(TAG, "toolbar_export_data");
                        break;
                    case R.id.toolbar_nfc_bind:
                        Logger.i(TAG, "toolbar_nfc_bind");
                        break;
                    case R.id.toolbar_user_login:
                        Logger.i(TAG, "toolbar_user_login");
                        loginServer();
                        break;
                    case R.id.toolbar_upload_sickbed_data:
                        Logger.i(TAG, "toolbar_upload_sickbed_data");
                        break;
                    case R.id.toolbar_setting:
                        Logger.i(TAG, "toolbar_setting");
                        getFragmentManager().beginTransaction()
                                .add(R.id.sickbend_info_fragment, new SettingFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        return MainActivity.super.onOptionsItemSelected(item);
                }
                return MainActivity.super.onOptionsItemSelected(item);
            }
        });
    }
    private void loginServer(){
        Logger.i(TAG, "loginServer");
        String login = mContext.getResources().getString(R.string.login);
        String cancel = mContext.getResources().getString(R.string.cancel);
        final View view = getLayoutInflater().inflate(R.layout.alert_login, null);
        EditText username = (EditText)view.findViewById(R.id.alert_login_username);
        EditText userPassword = (EditText)view.findViewById(R.id.alert_login_userpassword);
        final AlertView alertView = new AlertView(login,null, cancel, null, new String[]{login}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    Logger.i(TAG, "click login");
                    String userName = ((EditText) view.findViewById(R.id.alert_login_username)).getText().toString();
                    String userPassword = ((EditText) view.findViewById(R.id.alert_login_userpassword)).getText().toString();
                    App.authenticManager(mContext).loginByDefault(userName, userPassword);
                }else {
                    Logger.i(TAG, "click cancel");
                    IBinder usernameIb = ((EditText)view.findViewById(R.id.alert_login_username)).getWindowToken();
                    mInputMethodManager.hideSoftInputFromWindow(usernameIb, 0);
                }
            }
        }).setCancelable(false);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                boolean isOpen = mInputMethodManager.isActive();
                alertView.setMarginBottom(isOpen && isFocus ? 200:0);
            }
        });
        userPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                boolean isOpen = mInputMethodManager.isActive();
                alertView.setMarginBottom(isOpen && isFocus ? 250:0);
            }
        });
        alertView.addExtView(view);
        alertView.show();
        
    }
}
