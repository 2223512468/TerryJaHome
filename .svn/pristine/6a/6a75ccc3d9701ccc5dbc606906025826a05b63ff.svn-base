package com.jajahome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 类描述：
 * 创建人：lhz
 * 创建时间：2016/7/4 10:29
 * 修改时间：2016/7/4 10:29
 * 修改备注：
 */
public class AutoGridView extends GridView {
    public AutoGridView(Context context) {
        super(context);
    }

    public AutoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
