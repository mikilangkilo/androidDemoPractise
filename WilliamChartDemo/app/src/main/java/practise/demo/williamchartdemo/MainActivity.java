package practise.demo.williamchartdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChartView mChart;
    private final String[] mLabels = {"", "", "", "", "", "", "", "", ""};

    private final float[][] mValues = {{0f, 2f, 1.4f, 4.f, 3.5f, 4.3f, 2f, 4f, 6.f},
            {1.5f, 2.5f, 1.5f, 5f, 4f, 5f, 4.3f, 2.1f, 1.4f}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new ChartsFragment()).commit();
    }

    private void initView() {
        mChart = (LineChartView) findViewById(R.id.chart);

        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#53c1bd"))
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(new int[] {Color.parseColor("#364d5a"), Color.parseColor("#3f7178")},
                        null);
        mChart.addData(dataset);

        mChart.setBorderSpacing(1)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE);

        //Animation anim = new Animation().setEndAction(action);

        mChart.show();
    }
}
