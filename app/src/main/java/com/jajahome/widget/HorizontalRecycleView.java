package com.jajahome.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by lhz on 2016/7/20.
 */
public class HorizontalRecycleView extends RecyclerView {
	public HorizontalRecycleView(Context context) {
		super(context);
		init();
	}

	public HorizontalRecycleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init(){
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		setLayoutManager(linearLayoutManager);
		setHasFixedSize(true);
	}
}
