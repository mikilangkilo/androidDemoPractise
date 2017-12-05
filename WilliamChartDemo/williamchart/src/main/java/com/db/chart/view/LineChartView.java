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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Log;

import com.db.chart.util.Tools;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.williamchart.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


/**
 * Implements a line chart extending {@link ChartView}
 */
public class LineChartView extends ChartView {

    private static final float SMOOTH_FACTOR = 0.15f;

    /**
     * Style applied to line chart
     */
    private final Style mStyle;
    private Timer timer;
    /**
     * Radius clickable region
     */
    private float mClickableRadius;
    private int mOffset = 0;
    private boolean mIsSizeStart = false;

    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    //波纹view
    private WaveView mWaveView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_RIPPLE:
                    timer = new Timer(true);
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    addSize();
                                }
                            }, 0, 200);
                    break;
                case SIZE_ADD:

                    break;
                default:
                    break;
            }
        }
    };
    private boolean mIsRefresh;
    private int mSize = 0;
    private static final int REFRESH_RIPPLE= 0;
    private static final int SIZE_ADD = 1;
    public LineChartView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setOrientation(Orientation.VERTICAL);
        mStyle = new Style(
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChartAttrs, 0, 0));
        mClickableRadius = context.getResources().getDimension(R.dimen.dot_region_radius);
        mWaveView = new WaveView(context);
        addView(mWaveView);
    }
    public void addSize(){
        mSize = mSize+1;
        mIsSizeStart = true;
    }

    public LineChartView(Context context) {

        super(context);

        setOrientation(Orientation.VERTICAL);
        mStyle = new Style();
        mClickableRadius = context.getResources().getDimension(R.dimen.dot_region_radius);
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
//                while (true){
//                Log.d("gg","refresh");
                drawPoints(canvas, lineSet, mSize);
//                if (!mHandler.hasMessages(REFRESH_RIPPLE) && !mIsSizeStart){
//                    mHandler.sendEmptyMessage(REFRESH_RIPPLE);
//                }
//                Point dot = (Point) lineSet.getEntry(0);
//                mWaveView.layout(dot.getX(),dot.getY(),dot.getRadius(),);
                postInvalidateDelayed(200);
            }
//        }else {
//            for (ChartSet set : data) {
//
//                final LineSet lineSet = (LineSet) set;
//
//                if (lineSet.isVisible()) {
//
//                    mStyle.mLinePaint.setColor(lineSet.getColor());
//                    int[] colors = new int[]{0x000000ff, Color.BLUE};
//                    float[] position = new float[]{0.1f,0.5f};
//                    LinearGradient lg=new LinearGradient(0,1/2*canvas.getHeight(),canvas.getWidth(),1/2*canvas.getHeight(),colors, position,Shader.TileMode.MIRROR);
//                    mStyle.mLinePaint.setShader(lg);
//                    mStyle.mLinePaint.setStrokeWidth(lineSet.getThickness());
//                    applyShadow(mStyle.mLinePaint, lineSet.getAlpha(), lineSet.getShadowDx(), lineSet
//                            .getShadowDy(), lineSet.getShadowRadius(), lineSet.getShadowColor());
//
//                    if (lineSet.isDashed()) mStyle.mLinePaint.setPathEffect(
//                            new DashPathEffect(lineSet.getDashedIntervals(), lineSet.getDashedPhase()));
//                    else mStyle.mLinePaint.setPathEffect(null);
//
//                    if (!lineSet.isSmooth()) linePath = createLinePath(lineSet);
//                    else linePath = createSmoothLinePath(lineSet);
//
//                    //Draw background
//                    if (lineSet.hasFill() || lineSet.hasGradientFill())
//                        canvas.drawPath(createBackgroundPath(new Path(linePath), lineSet), mStyle.mFillPaint);
//
//                    //Draw line
//                    canvas.drawPath(linePath, mStyle.mLinePaint);
//                    //Draw points
////                while (true){
//                    Log.d("gg","refresh");
////                mOffset = mOffset%10+3;
//                    drawPoints(canvas, lineSet, mOffset+0f);
//                    if (mIsRefresh){
//                        mOffset = mOffset%5+1;
//                        postInvalidateDelayed(1000);
//                    }else {
//                        mOffset = 0;
//                    }
////                }
////                final Runnable runnable;
////                runnable = new Runnable() {
////                    @Override
////                    public void run() {
////                        drawPoints(canvas, lineSet, System.currentTimeMillis()%3);
////                        postInvalidate();
////                        Log.d("line","refresh");
////                        postDelayed(this,500);
////                    }
////                };
////                post(runnable);
//
//                }
//            }
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
    private void drawPoints(Canvas canvas, LineSet set, int offset) {

        int begin = set.getBegin();
        int end = set.getEnd();
        Point dot;
        for (int i = begin; i < end; i++) {

            dot = (Point) set.getEntry(i);

            if (dot.isVisible()) {

                // Style dot
                mStyle.mDotsPaint.setColor(dot.getColor());

                mStyle.mDotsPaint.setAlpha((int) (set.getAlpha() * style.FULL_ALPHA));
                applyShadow(mStyle.mDotsPaint, set.getAlpha(), dot.getShadowDx(), dot
                        .getShadowDy(), dot.getShadowRadius(), dot.getShadowColor());

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
