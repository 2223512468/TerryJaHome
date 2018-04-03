package com.jajahome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jajahome.R;

/**
 * Created by Administrator on 2017/8/17.
 */
public class FreeShipView extends View {


    private Paint mPaint;
    private String msg = "包 邮";

    public FreeShipView(Context context) {
        this(context, null);
    }

    public FreeShipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreeShipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.free_ship));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(25);
    }

    private int mWidth, mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect = new RectF(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2);
        canvas.drawRoundRect(rect, 10, 10, mPaint);
        mPaint.setStrokeWidth(2);
        canvas.drawText(msg, 0, mPaint.descent(), mPaint);

    }

}
