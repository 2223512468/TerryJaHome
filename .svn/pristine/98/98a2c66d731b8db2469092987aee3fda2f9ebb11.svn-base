package com.jajahome.feature.set.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by ${Terry} on 2017/10/27.
 */
public class SetImageView extends ImageView {

    private Scroller mScroller;

    public SetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

}
