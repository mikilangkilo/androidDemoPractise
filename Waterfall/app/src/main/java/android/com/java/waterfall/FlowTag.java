package android.com.java.waterfall;

import android.content.res.AssetManager;

/**
 * Created by Administrator on 2017/4/26.
 */
public class FlowTag {
    private int mFlowId;
    private String mFileName;
    private AssetManager mAssetManager;
    private int mItemWidth;
    public int mWhat = 1;
    public int getFlowId(){
        return mFlowId;
    }
    public void setmFlowId(int flowId){
        mFlowId = flowId;
    }
    public String getFileName(){
        return mFileName;
    }
    public void setFileName(String fileName){
        mFileName = fileName;
    }
    public AssetManager getAssetManager(){
        return mAssetManager;
    }
    public void setAssetManager(AssetManager assetManager){
        mAssetManager = assetManager;
    }
    public int getItemWidth(){
        return mItemWidth;
    }
    public void setItemWidth(int itemWidth){
        mItemWidth = itemWidth;
    }



}
