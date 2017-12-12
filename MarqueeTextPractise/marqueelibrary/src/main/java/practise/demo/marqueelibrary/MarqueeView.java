package practise.demo.marqueelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import practise.demo.marqueelibrary.util.AnimationListenerAdapter;

import static java.security.AccessController.getContext;

/**
 * Created by yinpengcheng on 2017/12/7.
 */

public class MarqueeView extends ViewFlipper implements Observer {
    protected MarqueeFactory factory;
    private final int DEFAULT_ANIM_RES_IN = R.anim.in_bottom;
    private final int DEFAULT_ANIM_RES_OUT = R.anim.out_top;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (getInAnimation() == null || getOutAnimation() == null) {
            setInAnimation(getContext(), DEFAULT_ANIM_RES_IN);
            setOutAnimation(getContext(), DEFAULT_ANIM_RES_OUT);
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        if (a.hasValue(R.styleable.MarqueeView_marqueeAnimDuration)) {
            int animDuration = a.getInt(R.styleable.MarqueeView_marqueeAnimDuration, -1);
            getInAnimation().setDuration(animDuration);
//            getInAnimation().setRepeatCount(0);
            getOutAnimation().setDuration(animDuration);
//            getOutAnimation().setRepeatCount(0);
        }
        a.recycle();
    }

    public void setAnimDuration(long animDuration) {
        if (getInAnimation() != null) {
            getInAnimation().setDuration(animDuration);
        }
        if (getOutAnimation() != null) {
            getOutAnimation().setDuration(animDuration);
        }
    }

    public void setInAndOutAnim(Animation inAnimation, Animation outAnimation) {
        setInAnimation(inAnimation);
        setOutAnimation(outAnimation);
    }

    public void setInAndOutAnim(@AnimRes int inResId, @AnimRes int outResId) {
        setInAnimation(getContext(), inResId);
        setOutAnimation(getContext(), outResId);
    }

    public void setMarqueeFactory(MarqueeFactory factory) {
//        Log.d("gg","setmarqueeFactory");
        this.factory = factory;
        factory.attachedToMarqueeView(this);
        refreshChildViews();
    }

    public void refreshChildViews() {
//        startFlipping();
//        Log.d("gg","refreshchildviews");
        if (getChildCount() > 0) {
            removeAllViews();
        }
        List<View> mViews = factory.getMarqueeViews();
        for (int i = 0; i < mViews.size(); i++) {
            addView(mViews.get(i));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
//        Log.d("gg","update");
        if (arg == null) return;
        if (MarqueeFactory.COMMAND_UPDATE_DATA.equals(arg.toString())) {
            Animation animation = getInAnimation();
            if (animation != null && animation.hasStarted()) {
                animation.setAnimationListener(new AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        refreshChildViews();
                        if (animation != null) {
                            animation.setAnimationListener(null);
                        }
                    }
                });
            } else {
                refreshChildViews();
            }
        }
    }
    public void showNextData(){
        Animation outAnimation = getOutAnimation();
        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeAllViews();
                List<View> mViews = factory.getMarqueeViews();
                addView(mViews.get(1));
                Animation inAnimation = getInAnimation();
                MarqueeView.this.getChildAt(0).startAnimation(inAnimation);
//                removeViewAt(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        getChildAt(0).startAnimation(outAnimation);
    }

    @Override
    public void startFlipping() {
        super.startFlipping();
//        Log.d("gg","startFlipping");
        stopFlipping();
    }

    @Override
    public void stopFlipping() {
        super.stopFlipping();
//        Log.d("gg","stopFlipping");
    }
}
