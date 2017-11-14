package highwho.com.custompopupwindow;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Handler handler = new Handler();
    private View mRootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mRootView);
        button = (Button)findViewById(R.id.button);
        final customWindow customWindow = new customWindow(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWindow.setText("gggg")
                        .setType(highwho.com.custompopupwindow.customWindow.SUCCESS)
                        .show(mRootView, handler);
            }
        });
    }
}
