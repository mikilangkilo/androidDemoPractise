package demo.ypc.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private Button mbutton01;
    private Button mbutton02;
    private Button mbutton03;
    private Button mbutton04;
    private PopupWindow mPopupWindow;
    // 屏幕的width
    private int mScreenWidth;
    // 屏幕的height
    private int mScreenHeight;
    // PopupWindow的width
    private int mPopupWindowWidth;
    // PopupWindow的height
    private int mPopupWindowHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbutton01 = (Button) findViewById(R.id.button01);
        mbutton02 = (Button) findViewById(R.id.button02);
        mbutton03 = (Button) findViewById(R.id.button03);
        mbutton04 = (Button) findViewById(R.id.button04);

        mbutton01.setOnClickListener(this);
        mbutton02.setOnClickListener(this);
        mbutton03.setOnClickListener(this);
        mbutton04.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 相对某个控件的位置（正左下方），无偏移
            case R.id.button01:
                getPopupWindowInstance();
                mPopupWindow.showAsDropDown(v);
                break;

            // 相对某个控件的位置（正左下方），有偏移
            case R.id.button02:
                getPopupWindowInstance();
                mPopupWindow.showAsDropDown(v, 50, 50);// X、Y方向各偏移50
                break;

            // 相对于父控件的位置，无偏移
            case R.id.button03:
                getPopupWindowInstance();
                mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;

            // 相对于父控件的位置，有偏移
            case R.id.button04:
                getPopupWindowInstance();
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 50);
                break;

            default:
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mPopupWindow.dismiss();
    }

    /*
     * 获取PopupWindow实例
     */
    private void getPopupWindowInstance() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /*
     * 创建PopupWindow
     */
    private void initPopuptWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popupWindow = layoutInflater.inflate(R.layout.popup_window, null);
        RadioGroup radioGroup = (RadioGroup) popupWindow.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        // 参数2：width 指定PopupWindow的width
        // 参数3：height 指定PopupWindow的height
        mPopupWindow = new PopupWindow(popupWindow, 100, 130);

        // 获取屏幕和PopupWindow的width和height
        mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
        mPopupWindowWidth = mPopupWindow.getWidth();
        mPopupWindowHeight = mPopupWindow.getHeight();
    }
}
