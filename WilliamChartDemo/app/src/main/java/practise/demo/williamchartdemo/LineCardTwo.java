package practise.demo.williamchartdemo;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

/**
 * Created by yinpengcheng on 2017/12/1.
 */

public class LineCardTwo extends CardController {


    private final LineChartView mChart;

    private final String[] mLabels =
            {"START", "2", "3", "4", "5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "FINISH"};

    private final float[][] mValues =
            {{35f, 37f, 47f, 49f, 43f, 46f, 80f, 83f, 65f, 68f, 28f, 55f, 58f, 50f, 53f, 53f, 57f,
                    48f, 50f, 53f, 54f, 25f, 27f, 35f, 37f, 35f, 80f, 82f, 55f, 59f, 85f, 82f, 60f,
                    55f, 63f, 65f, 58f, 60f, 63f, 60f},
                    {85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f,
                            85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f,
                            85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f}};

    private Tooltip mTip;
    private Context mContext;
    public LineCardTwo(CardView card, Context context) {

        super(card);

        mChart = (LineChartView) card.findViewById(R.id.chart);
        mContext = context;
    }


    @Override
    public void show(Runnable action) {

        super.show(action);
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

        ((TextView) mTip.findViewById(R.id.value)).setTypeface(
                Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }
        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset
                .setColor(Color.WHITE)
//                .setGradientFill(colors,positions)
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(40);
//        for (int i = 0; i < mLabels.length; i += 5) {
//            Point point = (Point) dataset.getEntry(i);
//            point.setColor(Color.parseColor("#ffffff"));
//            point.setStrokeColor(Color.parseColor("#0290c3"));
////            if (i == 30 || i == 10) point.setRadius(Tools.fromDpToPx(10));
//        }
        Point point = (Point)dataset.getEntry(mLabels.length - 1);
        point.setColor(Color.parseColor("#ffffff"));
        point.setStrokeColor(Color.parseColor("#0290c3"));
        mChart.addData(dataset);

//        Paint thresPaint = new Paint();
//        thresPaint.setColor(Color.parseColor("#6fbcff"));
//        thresPaint.setStyle(Paint.Style.STROKE);
//        thresPaint.setAntiAlias(true);
//        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
//        thresPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
        gridPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        mChart.setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.WHITE)
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setGrid(5, 7, gridPaint)
                .setTooltips(mTip)
                .setAxisColor(0xb9ffffff)
//                .setValueThreshold(80f, 80f, thresPaint)
                .setAxisBorderValues(0, 100,20)


//                .show(new Animation().fromXY(0, .5f).withEndAction(action))
        .show();
//        mChart.setBackgroundColor(0xff000000);
        ;
    }


    @Override
    public void update() {

        super.update();

        if (firstStage) {
            mChart.updateValues(0, mValues[1]);
        } else {
            mChart.updateValues(0, mValues[0]);
        }
        mChart.notifyDataUpdate();
    }


    @Override
    public void dismiss(Runnable action) {

        super.dismiss(action);

//        mChart.dismiss(new Animation().fromXY(1, .5f).withEndAction(action));
    }

}
