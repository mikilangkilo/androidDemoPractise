package demo.ypc.downloadpictures;

import android.graphics.Bitmap;

/**
 * Created by yinpengcheng on 2017/9/1.
 */

public interface ImageCache {
    public Bitmap getBitmap(String url);
    public void putBitmap(String url, Bitmap bitmap);
}
