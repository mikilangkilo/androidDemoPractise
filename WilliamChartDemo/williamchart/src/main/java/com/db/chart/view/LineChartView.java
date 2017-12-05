/*
 * Copyright 2015 Diogo Bernardino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.db.chart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.db.chart.util.Tools;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.williamchart.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


/**
 * Implements a line chart extending {@link ChartView}
 */
public class LineChartView extends ChartView {
    // 初始波纹半径
    private float mMiniRadius = 7f;
    //最大波纹半径
    private float mMaxRadius = 24f;
    //波纹持续时间
    private long mWaveDuration = 5000;
    //波纹创建时间间隔
    private long mSpeed = 1000;
    //波纹画笔
    private Paint mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //波纹动画效果
    private Interpolator mInterpolator = new AccelerateInterpolator();
    //所有的水波纹
    private List<ValueAnimator> mAnimatorList = new ArrayList<ValueAnimator>();
    //是否开启水波纹
    private boolean mIsRuning = false;

    private static final float SMOOTH_FACTOR = 0.15f;

    /**
     * Style applied to line chart
     */
    private final Style mStyle;
    /**
     * Radius clickable region
     */
    private float mClickableRadius;
    private Runnable mWaveRunable = new Runnable() {
        @Override
        public void run() {
            if (mIsRuning) {
                newWaveAnimator();
                invalidate();
                postDelayed(mWaveRunable, mSpeed);
            }
        }
    };

    //开启水波纹
    public void start(){
        if (!mIsRuning){
            mIsRuning = true;
            mWaveRunable.run();
        }
    }
    public LineChartView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setOrientation(Orientation.VERTICAL);
        mStyle = new Style(
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChartAttrs, 0, 0));
        mClickableRadius = context.getResources().getDimension(R.dimen.dot_region_radius);
    }
    public float getMiniRadius(){
        return mMiniRadius;
    }

    private ValueAnimator newWaveAnimator() {
        final ValueAnimator mWaveAnimator = new ValueAnimator();
        mWaveAnimator.setFloatValues(mMiniRadius, mMaxRadius);
        mWaveAnimator.setDuration(mWaveDuration);
        mWaveAnimator.setRepeatCount(0);
        mWaveAnimator.setInterpolator(mInterpolator);
        mWaveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                (Float) animation.getAnimatedValue();
            }
        });
        mAnimatorList.add(mWaveAnimator);
        mWaveAnimator.start();
        return mWaveAnimator;
    }
    public LineChartView(Context context) {

        super(context);

        setOrientation(Orientation.VERTICAL);
        mStyle = new Style();
        mClickableRadius = context.getResources().getDimension(R.dimen.dot_region_radius);


//        mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.center);

    }


    /**
     * Credits: http://www.jayway.com/author/andersericsson/
     * Given an index in points, it will make sure the the returned index is
     * within the array.
     */
    private static int si(int setSize, int i) {

        if (i > setSize - 1) return setSize - 1;
        else if (i < 0) return 0;
        return i;
    }

    @Override
    public void onAttachedToWindow() {

        super.onAttachedToWindow();
        mStyle.init();
    }

    @Override
    public void onDetachedFromWindow() {

        super.onDetachedFromWindow();
        mStyle.clean();
    }


    @Override
    public void onDrawChart(final Canvas canvas, ArrayList<ChartSet> data) {

//        final LineSet lineSet;
        Path linePath;
//        Log.d("gg",data.size()+"");
        if (data.size() == 1){
            final LineSet lineSet = (LineSet) data.get(0);
            if (lineSet.isVisible()) {

                mStyle.mLinePaint.setColor(lineSet.getColor());
                int[] colors = new int[]{0x000000ff, Color.BLUE};
                float[] position = new float[]{0.1f,0.5f};
                LinearGradient lg=new LinearGradient(0,1/2*canvas.getHeight(),canvas.getWidth(),1/2*canvas.getHeight(),colors, position,Shader.TileMode.MIRROR);
                mStyle.mLinePaint.setShader(lg);
                mStyle.mLinePaint.setStrokeWidth(lineSet.getThickness());
                applyShadow(mStyle.mLinePaint, lineSet.getAlpha(), lineSet.getShadowDx(), lineSet
                        .getShadowDy(), lineSet.getShadowRadius(), lineSet.getShadowColor());

                if (lineSet.isDashed()) mStyle.mLinePaint.setPathEffect(
                        new DashPathEffect(lineSet.getDashedIntervals(), lineSet.getDashedPhase()));
                else mStyle.mLinePaint.setPathEffect(null);

                if (!lineSet.isSmooth()) linePath = createLinePath(lineSet);
                else linePath = createSmoothLinePath(lineSet);

                //Draw background
                if (lineSet.hasFill() || lineSet.hasGradientFill())
                    canvas.drawPath(createBackgroundPath(new Path(linePath), lineSet), mStyle.mFillPaint);

                //Draw line
                canvas.drawPath(linePath, mStyle.mLinePaint);
                //Draw points
                drawPoints(canvas, lineSet);
                postInvalidateDelayed(200);
            }
        }

    }

    @Override
    void defineRegions(ArrayList<ArrayList<Region>>
                               regions, ArrayList<ChartSet> data) {

        float x;
        float y;
        int dataSize = data.size();
        int setSize;
        for (int i = 0; i < dataSize; i++) {

            setSize = data.get(0).size();
            for (int j = 0; j < setSize; j++) {

                x = data.get(i).getEntry(j).getX();
                y = data.get(i).getEntry(j).getY();
                regions.get(i)
                        .get(j)
                        .set((int) (x - mClickableRadius), (int) (y - mClickableRadius),
                                (int) (x + mClickableRadius), (int) (y + mClickableRadius));
            }
        }
    }


    /**
     * Responsible for drawing points
     */
    private void drawPoints(Canvas canvas, LineSet set) {

        int begin = set.getBegin();
        int end = set.getEnd();
        Point dot;
        for (int i = begin; i < end; i++) {

            dot = (Point) set.getEntry(i);

            if (dot.isVisible()) {

                // Style dot
                mStyle.mDotsPaint.setColor(dot.getColor());

                mStyle.mDotsPaint.setAlpha((int) (set.getAlpha() * style.FULL_ALPHA));
                mWavePaint.setStrokeWidth(1f);
                mWavePaint.setColor(0xffff0000);
                mWavePaint.setDither(true);
                mWavePaint.setStyle(Paint.Style.FILL);
                applyShadow(mStyle.mDotsPaint, set.getAlpha(), dot.getShadowDx(), dot
                        .getShadowDy(), dot.getShadowRadius(), dot.getShadowColor());
                Iterator<ValueAnimator> iterator = mAnimatorList.iterator();
                while (iterator.hasNext()){
                    ValueAnimator valueAnimator = iterator.next();
                    if (!valueAnimator.getAnimatedValue().equals(mMaxRadius)){
                        //设置透明度
                        mWavePaint.setAlpha(getAlpha((Float) valueAnimator.getAnimatedValue()));
                        //画水波纹
                        canvas.drawCircle(dot.getX(),dot.getY(), (Float) valueAnimator.getAnimatedValue(),mWavePaint);
                    }else{
                        valueAnimator.cancel();
                        iterator.remove();
                    }
                }

                if (mAnimatorList.size() > 0){
                    postInvalidateDelayed(10);
                }
                start();
                // Draw dot
//                if (offset<= 5){
//                    for (int j = offset; j >0; j--){
//                        mStyle.mDotsPaint.setAlpha((int)(255*(1- j/5.0)));
//                        Log.d("gg",(int)(255*(1- j/5))+"");
//                        canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius()+j, mStyle.mDotsPaint);//add ripple here
//                    }
//                }else {
//                    int weight = offset % 5;
//                    for (int j = weight; j >0; j--){
//                        mStyle.mDotsPaint.setAlpha((int)(255*(1- j/5.0)));
////                        Log.d("gg",(int)(255*(1- j/5))+"");
//                        Log.d("gg",dot.getRadius()+"");
//                        canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius()+j, mStyle.mDotsPaint);//add ripple here
//                        canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius()+j+5, mStyle.mDotsPaint);
//                    }
//                }
//                Log.d("gg",dot.getX()+"x");//1367
//                Log.d("gg", dot.getY()+"y");//380
//                mWaveView.layout((int)dot.getX(),(int)dot.getY(),(int)dot.getX()+mWaveView.getWidth(),(int)dot.getY()+mWaveView.getHeight());

                //Draw dots stroke
//                if (dot.hasStroke()) {
//                    // Style stroke
//                    mStyle.mDotsStrokePaint.setStrokeWidth(dot.getStrokeThickness());
//                    mStyle.mDotsStrokePaint.setColor(dot.getStrokeColor());
//                    mStyle.mDotsStrokePaint.setAlpha((int) (set.getAlpha() * style.FULL_ALPHA));
//                    applyShadow(mStyle.mDotsStrokePaint, set.getAlpha(), dot.getShadowDx(), dot
//                            .getShadowDy(), dot.getShadowRadius(), dot.getShadowColor());
//                    canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius(), mStyle.mDotsStrokePaint);
//                }

                // Draw drawable
                if (dot.getDrawable() != null) {
                    Bitmap dotsBitmap = Tools.drawableToBitmap(dot.getDrawable());
                    canvas.drawBitmap(dotsBitmap, dot.getX() - dotsBitmap.getWidth() / 2,
                            dot.getY() - dotsBitmap.getHeight() / 2, mStyle.mDotsPaint);
                }
            }
        }

    }
    private int getAlpha(float mRadius){
        int alpha = 1;
        if (mMaxRadius > 0){
            alpha = (int)((1 - (mRadius - mMiniRadius)/(mMaxRadius - mMiniRadius)) * 255);
        }
//        Log.e("alpha",alpha + "");
        return alpha;
    }

    /**
     * Responsible for drawing a (non smooth) line.
     *
     * @param set {@link LineSet} object
     * @return {@link Path} object containing line
     */
    Path createLinePath(LineSet set) {

        Path res = new Path();

        int begin = set.getBegin();
        int end = set.getEnd();
        for (int i = begin; i < end; i++) {
            if (i == begin) res.moveTo(set.getEntry(i).getX(), set.getEntry(i).getY());
            else res.lineTo(set.getEntry(i).getX(), set.getEntry(i).getY());
        }

        return res;
    }


    /**
     * Credits: http://www.jayway.com/author/andersericsson/
     * Method responsible to draw a smooth line with the parsed screen points.
     *
     * @param set {@link LineSet} object.
     * @return {@link Path} object containing smooth line
     */
    Path createSmoothLinePath(LineSet set) {

        float thisPointX;
        float thisPointY;
        float nextPointX;
        float nextPointY;
        float startDiffX;
        float startDiffY;
        float endDiffX;
        float endDiffY;
        float firstControlX;
        float firstControlY;
        float secondControlX;
        float secondControlY;

        Path res = new Path();
        res.moveTo(set.getEntry(set.getBegin()).getX(), set.getEntry(set.getBegin()).getY());

        int begin = set.getBegin();
        int end = set.getEnd();
        for (int i = begin; i < end - 1; i++) {

            thisPointX = set.getEntry(i).getX();
            thisPointY = set.getEntry(i).getY();

            nextPointX = set.getEntry(i + 1).getX();
            nextPointY = set.getEntry(i + 1).getY();

            startDiffX = (nextPointX - set.getEntry(si(set.size(), i - 1)).getX());
            startDiffY = (nextPointY - set.getEntry(si(set.size(), i - 1)).getY());

            endDiffX = (set.getEntry(si(set.size(), i + 2)).getX() - thisPointX);
            endDiffY = (set.getEntry(si(set.size(), i + 2)).getY() - thisPointY);

            firstControlX = thisPointX + (SMOOTH_FACTOR * startDiffX);
            firstControlY = thisPointY + (SMOOTH_FACTOR * startDiffY);

            secondControlX = nextPointX - (SMOOTH_FACTOR * endDiffX);
            secondControlY = nextPointY - (SMOOTH_FACTOR * endDiffY);

            res.cubicTo(firstControlX, firstControlY, secondControlX, secondControlY, nextPointX,
                    nextPointY);
        }

        return res;

    }


    /**
     * Responsible for drawing line background
     *
     * @param path {@link Path} object containing line path
     * @param set  {@link LineSet} object.
     * @return {@link Path} object containing background
     */
    private Path createBackgroundPath(Path path, LineSet set) {

        mStyle.mFillPaint.setAlpha((int) (set.getAlpha() * style.FULL_ALPHA));

        if (set.hasFill()) mStyle.mFillPaint.setColor(set.getFillColor());
        if (set.hasGradientFill()) mStyle.mFillPaint.setShader(
                new LinearGradient(super.getInnerChartLeft(), super.getInnerChartTop(),
                        super.getInnerChartLeft(), super.getInnerChartBottom(),
                        set.getGradientColors(), set.getGradientPositions(), Shader.TileMode.MIRROR));

        path.lineTo(set.getEntry(set.getEnd() - 1).getX(), super.getInnerChartBottom());
        path.lineTo(set.getEntry(set.getBegin()).getX(), super.getInnerChartBottom());
        path.close();

        return path;
    }


    /**
     * @param radius Point's radius where touch event will be detected
     * @return {@link com.db.chart.view.LineChartView} self-reference.
     */
    public LineChartView setClickablePointRadius(@FloatRange(from = 0.f) float radius) {

        mClickableRadius = radius;
        return this;
    }


    /**
     * Class responsible to style the LineChart!
     * Can be instantiated with or without attributes.
     */
    class Style {

        static final int FULL_ALPHA = 255;

        /**
         * Paint variables
         */
        private Paint mDotsPaint;

        private Paint mDotsStrokePaint;

        private Paint mLinePaint;

        private Paint mFillPaint;


        Style() {
        }


        Style(TypedArray attrs) {
        }

        private void init() {

            mDotsPaint = new Paint();
            mDotsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mDotsPaint.setAntiAlias(true);

            mDotsStrokePaint = new Paint();
            mDotsStrokePaint.setStyle(Paint.Style.STROKE);
            mDotsStrokePaint.setAntiAlias(true);

            mLinePaint = new Paint();
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setAntiAlias(true);

            mFillPaint = new Paint();
            mFillPaint.setStyle(Paint.Style.FILL);
        }

        private void clean() {

            mLinePaint = null;
            mFillPaint = null;
            mDotsPaint = null;
        }

    }

}
