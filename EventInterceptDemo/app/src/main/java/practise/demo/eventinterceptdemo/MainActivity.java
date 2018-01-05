package practise.demo.eventinterceptdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,200);
        ViewA viewA = new ViewA(this);
        ViewB viewB = new ViewB(this);
        final ViewChildC viewChildC = new ViewChildC(this);
        addContentView(viewA, layoutParams);
        viewA.addView(viewB);
        viewB.addView(viewChildC);
//        Button button = new Button(this);
//        layoutParams.setMarginStart(500);
//        addContentView(button,layoutParams);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewChildC.performClick();
//            }
//        });
    }
}
