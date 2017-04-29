package android.com.java.waterfall;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Display mDisplay;
    private int mItemWidth;
    private int mColumnCount = 4;
    private int mPageCount = 15;
    private int mCurrentPage = 0;
    private int mLoadCount = 0;
    private AssetManager mAssetManager;
    private int[] mTopIndex;
    private int[] mBottomIndex;
    private int[] mColumnHeight;
    private Context mContext;
    private HashMap<Integer, FlowView> mViewMap;
    private HashMap<Integer, String > mPinMap;
    private HashMap<Integer, Integer>mPinMarkMap;

    private LazyScrollView mWaterFallScroll;
    private LinearLayout mWaterFallContainer;
    private ArrayList<LinearLayout> mWaterFallItems;

    private Handler mHandler;
    private List<String> mImageFilenameList;
    private final String mImagePath = "images";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplay = getWindowManager().getDefaultDisplay();
        mItemWidth = mDisplay.getWidth()/mColumnCount;
        mAssetManager = getAssets();
        mTopIndex = new int[mColumnCount];
        mBottomIndex = new int[mColumnCount];
        mColumnHeight = new int[mColumnCount];
        mContext = this.getApplicationContext();
        mViewMap = new HashMap<Integer, FlowView>();
        mPinMap = new HashMap<Integer, String>();
        mPinMarkMap = new HashMap<Integer, Integer>();
        initLayout();
    }
    public void initLayout(){
        mWaterFallScroll = (LazyScrollView)findViewById(R.id.waterfall_scroll);
        mWaterFallScroll.getView();
        mWaterFallScroll.setOnScrollListener(new LazyScrollView.OnScrollListener() {
            @Override
            public void onBottom() {
                AddItemToContainer(++mCurrentPage, mPageCount);
            }

            @Override
            public void onTop() {

            }

            @Override
            public void onScroll() {

            }

            @Override
            public void onAutoScroll() {
                Rect bounds = new Rect();
                Rect scrollBounds = new Rect(mWaterFallScroll.getScrollX(),mWaterFallScroll.getScrollY(),
                        mWaterFallScroll.getScrollX()+mWaterFallScroll.getWidth(),mWaterFallScroll.getScrollY()+mWaterFallScroll.getHeight());
                for (int i = 0; i <mLoadCount; i++ ){
                    FlowView v = mViewMap.get(i);
                    if (v != null){
                        v.getHitRect(bounds);
                        if (Rect.intersects(scrollBounds,bounds)){
                            if (v.mBitmap == null){
                                v.Reload();
                            }
                        }else {
                            v.Recycle();
                        }
                    }
                }
            }
        });
        mWaterFallContainer = (LinearLayout)findViewById(R.id.waterfall_container);
        mHandler = new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
            }

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        FlowView view = (FlowView)msg.obj;
                        int w = msg.arg1;
                        int h = msg.arg2;
                        String f = view.getFlowTag().getFileName();
                        int coulumIndex = GetMinValue(mColumnHeight);
                        view.setColumnIndex(coulumIndex);
                        mColumnHeight[coulumIndex] += h;
                        mPinMap.put(view.getId(),f);
                        mViewMap.put(view.getId(),view);
                        break;
                }

            }

            @Override
            public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
                return super.sendMessageAtTime(msg, uptimeMillis);
            }
        };
        mWaterFallItems = new ArrayList<LinearLayout>();
        for (int i = 0; i< mColumnCount;i++){
            LinearLayout itemLayout = new LinearLayout(this);
            LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(mItemWidth, WindowManager.LayoutParams.WRAP_CONTENT);
            itemLayout.setPadding(2,2,2,2);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setLayoutParams(itemParam);
            mWaterFallItems.add(itemLayout);
            mWaterFallContainer.addView(itemLayout);
        }
        try{
            mImageFilenameList = Arrays.asList(mAssetManager.list(mImagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        AddItemToContainer(mCurrentPage,mPageCount);

    }
    private int GetMinValue(int[] array) {
        int m = 0;
        int length = array.length;
        for (int i = 0; i < length; ++i) {

            if (array[i] < array[m]) {
                m = i;
            }
        }
        return m;
    }
    private void AddItemToContainer(int pageIndex, int pageCount){
        int currentIndex = pageIndex*pageCount;
        int j = currentIndex % mColumnCount;
        int imageCount = mImageFilenameList.size();
        for (int i = currentIndex; i < pageCount * (pageIndex + 1) && i < imageCount ; i++ ){
            mLoadCount ++ ;
            j = j>= mColumnCount ? j =0 : j;
            AddImage(mImageFilenameList.get(i),j++,(int)Math.ceil(mLoadCount / (double)mColumnCount),mLoadCount);
        }
    }
    private void AddImage(String filename, int columnIndex, int rowIndex,int id){
        FlowView item = (FlowView) LayoutInflater.from(this).inflate(R.layout.waterfallitem,null);
        item.setColumnIndex(columnIndex);
        item.setRowIndex(rowIndex);
        item.setId(id);
        item.setViewHandler(mHandler);
        FlowTag param = new FlowTag();
        param.setmFlowId(id);
        param.setAssetManager(mAssetManager);
        param.setFileName(mImagePath + "/"+filename);
        param.setItemWidth(mItemWidth);
        item.setFlowTag(param);
        item.LoadImage();
        mWaterFallItems.get(columnIndex).addView(item);
    }
}
