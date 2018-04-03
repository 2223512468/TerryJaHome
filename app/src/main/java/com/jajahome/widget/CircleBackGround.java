package com.jajahome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/7/6.
 */
public class CircleBackGround extends View {

    private Paint mPaint;
    private int radius = 43;

    public CircleBackGround(Context context) {
        this(context, null);
    }

    public CircleBackGround(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBackGround(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }
}
