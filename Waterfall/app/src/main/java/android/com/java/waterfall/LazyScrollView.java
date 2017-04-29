package android.com.java.waterfall;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/4/26.
 */
public class LazyScrollView extends ScrollView {
    private Handler mHandler;
    private View mView;
    private OnScrollListener mOnScrollListener;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mOnScrollListener.onAutoScroll();
    }

    public LazyScrollView(Context context) {
        super(context);
    }

    public LazyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LazyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LazyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int computerVerticalScrollRange(){
        return super.computeHorizontalScrollRange();
    }
    public int computerVerticalScrollOffset(){
        return  super.computeVerticalScrollOffset();
    }
    private void init(){
        this.setOnTouchListener(mOnTouchListener);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        if (mView.getMeasuredHeight() - 20 <= getScrollY() + getHeight()){
                            if (mOnScrollListener != null){
                                mOnScrollListener.onBottom();
                            }
                        }else if (getScrollY() == 0){
                            if (mOnScrollListener != null){
                                mOnScrollListener.onTop();
                            }
                        }else {
                            if (mOnScrollListener != null){
                                mOnScrollListener.onScroll();
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }
    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if (mView != null && mOnScrollListener != null){
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(1),200);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    public void getView(){
        this.mView = getChildAt(0);
        if (mView != null){
            init();
        }
    }

    public interface OnScrollListener{
        void onBottom();

        void onTop();

        void onScroll();

        void onAutoScroll();
    }


    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

}
