package demo.ypc.popupwindowdemo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        View view = getLayoutInflater().inflate(R.layout.alert_success, null);
        final View rootview = getLayoutInflater().inflate(R.layout.activity_main, null);
        final PopupWindow popupWindow = new PopupWindow(view, getResources().getDimensionPixelOffset(R.dimen.width), getResources().getDimensionPixelOffset(R.dimen.width));
        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        final int width = windowManager.getDefaultDisplay().getWidth();
        final int height = windowManager.getDefaultDisplay().getHeight();
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(view, Gravity.CENTER,0 ,0);
            }
        });
    }
}
