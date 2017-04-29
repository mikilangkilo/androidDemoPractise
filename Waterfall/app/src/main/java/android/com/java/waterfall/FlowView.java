package android.com.java.waterfall;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/26.
 */
public class FlowView extends ImageView implements View.OnClickListener,View.OnLongClickListener {
    private Context mContext;
    private String TAG = "FlowView";
    private FlowTag mFlowTag;
    private int mColumnIndex;
    private int mRowIndex;
    private Handler mViewHandler;
    public Bitmap mBitmap;


    public FlowView(Context context){
        super(context);
        this.mContext = context;
        init();
    }
    public FlowView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.mContext = context;
        init();
    }
    public FlowView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        this.mContext = context;
        init();
    }
    private void init(){
        setOnClickListener(this);
        setOnLongClickListener(this);
        setAdjustViewBounds(true);
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG,"onLongClick");
        Toast.makeText(mContext, "onLongClick:" + this.mFlowTag.getFlowId(), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"onClick");
        Toast.makeText(mContext, "onClick:"+this.mFlowTag.getFlowId(), Toast.LENGTH_LONG).show();
    }
    public void LoadImage(){
        if (getFlowTag() != null){
            new LoadImageThread().start();
        }
    }
    public void Reload(){
        if (mBitmap == null && getFlowTag() != null){
            new LoadImageThread().start();
        }
    }
    public void Recycle(){
        setImageBitmap(null);
        if (mBitmap == null || mBitmap.isRecycled()){
            return;
        }
        mBitmap.recycle();
        mBitmap = null;
    }


    public void setFlowTag(FlowTag flowTag){
        mFlowTag = flowTag;
    }
    public FlowTag getFlowTag(){
        return mFlowTag;
    }
    public void setColumnIndex(int columnIndex){
        mColumnIndex = columnIndex;
    }
    public int getColumnIndex(){
        return mColumnIndex;
    }
    public void setRowIndex(int rowIndex){
        mRowIndex = rowIndex;
    }
    public int getRowIndex(){
        return mRowIndex;
    }
    public void setViewHandler(Handler handler){
        mViewHandler = handler;
    }
    public Handler getViewHandler(){
        return mViewHandler;
    }
    class LoadImageThread extends Thread {
        LoadImageThread() {
        }

        public void run() {

            if (mFlowTag != null) {

                BufferedInputStream buf;
                try {
                    buf = new BufferedInputStream(mFlowTag.getAssetManager()
                            .open(mFlowTag.getFileName()));
                    mBitmap = BitmapFactory.decodeStream(buf);

                } catch (IOException e) {

                    e.printStackTrace();
                }

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        if (mBitmap != null) {// 此处在线程过多时可能为null
                            int width = mBitmap.getWidth();// 获取真实宽高
                            int height = mBitmap.getHeight();

                            ViewGroup.LayoutParams lp = getLayoutParams();
                            lp.height = (height * mFlowTag.getItemWidth())
                                    / width;// 调整高度
                            setLayoutParams(lp);

                            setImageBitmap(mBitmap);// 将引用指定到同一个对象，方便销毁
                            Handler h = getViewHandler();
                            Message m = h.obtainMessage(mFlowTag.mWhat, width,
                                    height, FlowView.this);
                            h.sendMessage(m);
                        }
                    }
                });

            }

        }
    }

}
