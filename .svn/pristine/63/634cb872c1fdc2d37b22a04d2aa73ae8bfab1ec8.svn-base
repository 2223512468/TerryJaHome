package com.jajahome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jajahome.util.StringUtil;

/**
 * Created by Administrator on 2017/8/8.
 */
public class SellerImg extends ImageView {


    private Paint mPaint;
    private String url;
    private int viewWidth, viewHeight;
    private String centerMsg = "上传转帐凭证";
    private Context mContext;


    public SellerImg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public SellerImg(Context context) {
        this(context, null);
    }

    public SellerImg(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(5f);
        mPaint.setTextSize(25f);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (StringUtil.isEmpty(url)) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.RED);
            canvas.drawText(centerMsg, viewWidth / 2, viewHeight / 2, mPaint);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        RectF rect = new RectF(0, 0, viewWidth, viewHeight);
        canvas.drawRoundRect(rect, 10, 10, mPaint);
        Glide.with(mContext).load(url).into(this);

    }


    public void setUrl(String url) {
        this.url = url;
        invalidate();
    }

}
