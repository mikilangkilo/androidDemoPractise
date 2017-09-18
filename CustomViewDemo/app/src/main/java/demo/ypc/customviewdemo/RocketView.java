package demo.ypc.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by yinpengcheng on 2017/9/6.
 */

public class RocketView extends View {
    private Paint mOvalPaint;
    private int mStrokeWidth = 2;
    private int padding = 3;

    private int mOval_l;
    private int mOval_t;
    private int mOval_r;
    private int mOval_b;
    public RocketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRocketView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RocketView);
        mOval_b = a.getDimensionPixelOffset(R.styleable.RocketView_ovalBottom, 100);
        mOval_t = a.getDimensionPixelOffset(R.styleable.RocketView_ovalTop, padding);
        mOval_l = a.getDimensionPixelOffset(R.styleable.RocketView_ovalLeft, padding);
        mOval_r = a.getDimensionPixelOffset(R.styleable.RocketView_ovalRight, 100);
        a.recycle();
    }

    private void initRocketView(){
        mOvalPaint = new Paint();
        mOvalPaint.setAntiAlias(true);
        mOvalPaint.setColor(Color.BLUE);
        mOvalPaint.setStyle(Paint.Style.STROKE);
        mOvalPaint.setStrokeWidth(mStrokeWidth);
        setPadding(padding, padding, padding, padding);
    }
    public void setOvalRect(int l, int t, int r, int b){
        mOval_l = l + padding;
        mOval_t = t + padding;
        mOval_r = r;
        mOval_b = b;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        RectF rectF = new RectF(mOval_l, mOval_t, mOval_r, mOval_b);
        canvas.drawOval(rectF, mOvalPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));//设置宽高
    }
    private int measureWidth(int measureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);//获得类型
        int specSize = MeasureSpec.getSize(measureSpec);//获得尺寸
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = mOval_r + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    public boolean hasFocus() {
        return super.hasFocus();
    }


    private int measureHeight(int measureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = mOval_b + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        return super.onKeyDown(keyCode, event);
    }
}
