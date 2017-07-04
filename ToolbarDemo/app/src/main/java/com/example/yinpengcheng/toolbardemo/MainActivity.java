package com.example.yinpengcheng.toolbardemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()){
                case R.id.export_data:
                    msg += "Click export_data";
                    break;
                case R.id.nfc_bind:
                    msg += "Click nfc_bind";
                    break;
                case R.id.login:
                    loginInServer();
                    break;
            }
            if (!msg.equals("")){
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("WISE");
        mToolbar.setBackgroundColor(0xff3366ff);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void loginInServer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录");
        final View view = getLayoutInflater().inflate(R.layout.login, null);
        builder.setView(view);

        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name =
                        ((EditText) view.findViewById(R.id.edit_login_username))
                                .getText().toString();

                String password =
                        ((EditText) view.findViewById(R.id.edit_login_password))
                                .getText().toString();

            }
        }).setNegativeButton("取消", null);
        builder.show();
    }
}
