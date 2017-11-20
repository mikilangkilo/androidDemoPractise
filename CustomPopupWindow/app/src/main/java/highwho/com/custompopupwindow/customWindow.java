package highwho.com.custompopupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by yinpengcheng on 2017/11/14.
 */

public class customWindow {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mRootView;
    private ImageView mPopupIcon;
    private TextView mPopupText;
    private Drawable mSuccess;
    private Drawable mFailure;
    public final static int SUCCESS = 1;
    public final static int FAILURE = 2;
    public final static int WORD = 3;
    public customWindow(Context context){
        mContext = context;
        init();
    }
    private void init(){
        mPopupWindow = new PopupWindow(mContext);
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.pop_window, null);
        mPopupIcon = (ImageView) mRootView.findViewById(R.id.pop_img);
        mPopupText = (TextView)mRootView.findViewById(R.id.pop_text);
        mSuccess = mContext.getResources().getDrawable(R.drawable.pop_success);
        mFailure = mContext.getResources().getDrawable(R.drawable.pop_failure);

    }
    public customWindow setType(int type){
        switch (type){
            case SUCCESS:
                mPopupIcon.setImageDrawable(mSuccess);
                return this;
            case FAILURE:
                mPopupIcon.setImageDrawable(mFailure);
                return this;
            case WORD:
                mPopupIcon.setVisibility(View.GONE);
                return this;
            default:
                mPopupIcon.setVisibility(View.GONE);
                return this;
        }
    }
    public customWindow setText(String text){
        mPopupText.setText(text);
        return this;
    }
    public void show(View rootview, Handler handler){
        mPopupWindow.setContentView(mRootView);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setWindowLayoutMode(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0,0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
            }
        },500);
    }

}
