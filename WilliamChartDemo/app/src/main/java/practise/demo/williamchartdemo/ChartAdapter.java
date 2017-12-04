package practise.demo.williamchartdemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yinpengcheng on 2017/12/1.
 */
class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {

    private final static int NUM_CHARTS = 1;
    private final Context mContext;

    ChartAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ChartAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(mContext).inflate(R.layout.chart, parent, false);
                break;
            default:
                v = LayoutInflater.from(mContext).inflate(R.layout.chart, parent, false);
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (position) {
            case 0:
                (new LineCardTwo(holder.cardView,mContext)).init();
                break;
            default:
                (new LineCardTwo(holder.cardView, mContext)).init();
                break;
        }
    }

    @Override
    public int getItemCount() {

        return NUM_CHARTS;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final CardView cardView;

        ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.chart_card);
        }
    }
}