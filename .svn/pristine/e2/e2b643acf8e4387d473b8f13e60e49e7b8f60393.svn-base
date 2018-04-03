package com.jajahome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/19.
 */
public class GiftImageView extends ImageView {
    public GiftImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GiftImageView(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_UP:
                return false;

        }
        return super.onTouchEvent(event);
    }
}
