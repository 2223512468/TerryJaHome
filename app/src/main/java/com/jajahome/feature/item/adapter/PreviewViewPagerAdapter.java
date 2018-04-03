package com.jajahome.feature.item.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jajahome.feature.PagerActivity;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 *  顶部预览图viewpager
 */
public class PreviewViewPagerAdapter extends PagerAdapter {

	Context mContext;
	/**
	 * 预览图数据
	 */
	List<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> mList;

	public PreviewViewPagerAdapter(Context mContext, List<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	/**
	 * @return 数量
	 */
	@Override
	public int getCount() {
		if (mList == null) return 0;
		return mList.size();
	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView iv = new ImageView(mContext);
		//增加占位图
		String url=mList.get(position).getUrl();
		if(!StringUtil.isEmpty(url)){
			Picasso.with(mContext).load(url).into(iv);
		}
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PagerActivity.class);
				intent.putStringArrayListExtra(PagerActivity.DATA, getImageList());
				intent.putExtra(PagerActivity.INDEX, position);
				mContext.startActivity(intent);
			}
		});
		container.addView(iv);
		return iv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ImageView imageView = (ImageView) object;
		container.removeView(imageView);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/**
	 * 设置单品详情小的预览图
	 *
	 * @param list
	 */
	public void setItemPreviewImgs(List<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	/**
	 * 获取连接地址
	 * @return ：图片地址集合
	 */
	private ArrayList<String> getImageList() {
		ArrayList<String> list = new ArrayList<>();
		for (ItemModel.DataBean.ItemBean.PreviewBean.ImageBean bean : mList) {
			list.add(bean.getUrl());
		}
		return list;
	}
}
