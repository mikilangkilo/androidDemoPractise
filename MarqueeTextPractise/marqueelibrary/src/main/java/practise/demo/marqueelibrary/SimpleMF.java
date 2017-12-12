package practise.demo.marqueelibrary;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by yinpengcheng on 2017/12/7.
 */

public class SimpleMF<E extends CharSequence> extends MarqueeFactory<TextView, E> {
    public SimpleMF(Context mContext) {
        super(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(E data) {
        TextView mView = new TextView(mContext);
        mView.setText(data);
        return mView;
    }
}