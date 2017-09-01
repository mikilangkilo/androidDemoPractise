package demo.ypc.downloadpictures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.apkfuns.logutils.LogUtils;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    ImageView imageView;
    NetworkImageView networkImageView;
    String imgPath = "http://www.glhospital.com:82/glyy/UploadFiles/Photo/facb4ffc-e3c4-46c5-81ab-925c982b7132.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        imageView = (ImageView)findViewById(R.id.image);
        networkImageView = (NetworkImageView)findViewById(R.id.networkImage);
//        ImageRequest imageRequest = new ImageRequest(imgPath,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(toRoundBitmap(response));
//                    }
//                }, 600, 200, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                LogUtils.e(error);
//            }
//        });
//        requestQueue.add(imageRequest);
        LruImageCache lruImageCache = LruImageCache.instance();
        ImageLoader imageLoader = new ImageLoader(requestQueue, lruImageCache);
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl(imgPath, imageLoader);
        networkImageView.setImageBitmap(toRoundBitmap(drawable2Bitmap(networkImageView.getDrawable())));


    }
    public Bitmap toRoundBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_right, dst_top, dst_bottom;
        if (width <= height){
            roundPx = width/2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }else {
            roundPx = height / 2;
            float clip = (width - height)/2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    public Bitmap drawable2Bitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
