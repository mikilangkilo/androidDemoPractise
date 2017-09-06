package demo.ypc.viewpagerclicktomove;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by yinpengcheng on 2017/9/4.
 */

public class FixSpeedScroller extends Scroller {
    private int mDuration = 1500;

    public FixSpeedScroller(Context context) {
        super(context);
    }

    public FixSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
    public void setDuration(int time){
        mDuration = time;
    }
    public int getmDuration(){
        return mDuration;
    }
}
