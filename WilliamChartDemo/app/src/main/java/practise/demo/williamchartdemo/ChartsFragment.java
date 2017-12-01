package practise.demo.williamchartdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yinpengcheng on 2017/12/1.
 */

public class ChartsFragment extends Fragment {

    private final static int FULL_SPAN = 3;
    private final static int MULTI_SPAN = 2;
    private final static int SINGLE_SPAN = 1;

    private ChartAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.charts, container, false);


        RecyclerView mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), FULL_SPAN);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (mAdapter.getItemViewType(position)) {
                    case 0:
                        return FULL_SPAN;
                    default:
                        return SINGLE_SPAN;
                }
            }
        });

        mAdapter = new ChartAdapter(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

}
