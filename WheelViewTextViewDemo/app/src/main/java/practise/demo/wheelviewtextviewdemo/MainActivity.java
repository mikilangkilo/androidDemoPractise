package practise.demo.wheelviewtextviewdemo;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    LinearLayout layout;
    Context context;
    Animation in_Bottom, out_Top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btn = (Button)findViewById(R.id.btn);
        layout = (LinearLayout)findViewById(R.id.layout);
        in_Bottom = AnimationUtils.loadAnimation(this, R.anim.in_bottom);
        out_Top = AnimationUtils.loadAnimation(this, R.anim.out_top);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("childcount",layout.getChildCount()+"");
                if (layout.getChildCount() == 0){
                    TextView textView = createTextView(context, "childcount = 0");
                    layout.addView(textView);
//                    textView.setAnimation(in_Bottom);
                    textView.startAnimation(in_Bottom);
                }else {
                    final TextView textView = (TextView)layout.getChildAt(0);
                    final TextView secondText = createTextView(context, "reclick");
//                    secondText.setAnimation(in_Bottom);
                    out_Top.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layout.removeView(textView);
                            layout.addView(secondText);
                            secondText.startAnimation(in_Bottom);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
//                    textView.setAnimation(out_Top);
                    textView.startAnimation(out_Top);


                }
            }
        });
    }
    public TextView createTextView(Context context,String text){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(fromDpToPx(16));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
    public static float fromDpToPx(float dp) {

        try {
            return dp * Resources.getSystem().getDisplayMetrics().density;
        } catch (Exception e) {
            return dp;
        }
    }
}
