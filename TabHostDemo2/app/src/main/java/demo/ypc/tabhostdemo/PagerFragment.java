package demo.ypc.tabhostdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by yinpengcheng on 2017/7/13.
 */

public class PagerFragment extends Fragment {
    private int mPage;
    private static final String ARG_PAGE = "ARG_PAGE";
    public static PagerFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PagerFragment pagerFragment = new PagerFragment();
        pagerFragment.setArguments(args);
        return pagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView)view;
        textView.setText("Fragment #"+mPage);
        return view;
    }
}
