package com.jajahome.feature.diy.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.SetItemModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.PriceUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 单品列表adapter （DIY 选单品时用）
 */
public class DiySelectItemListAdapter extends BaseRecyclerAdapter<SetItemModel.DataBean.ListBean> {


	public void setmSelectedList(List<SetItemModel.DataBean.ListBean> mSelectedList) {
		this.mSelectedList = mSelectedList;
	}

	private List<SetItemModel.DataBean.ListBean> mSelectedList;

	public List<SetItemModel.DataBean.ListBean> getSelectList() {

		return mSelectedList;
	}

	/**
	 * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
	 * @param i             位置
	 * @param mItemDataList 数据集合
	 */
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetItemModel.DataBean.ListBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		SetItemModel.DataBean.ListBean listBean=mItemDataList.get(i);
		SetItemModel.DataBean.ListBean.InfoBean itemBean =listBean.getInfo();
		String img=getDefaultImg(listBean);
		if(!StringUtil.isEmpty(img)) {
			Picasso.with(getContext())
					.load(img)
					.placeholder(R.mipmap.ic_holder_big)
					.into(holder.itemBannerImg);
		}else {
			holder.itemBannerImg.setImageResource(R.mipmap.ic_holder_big);
		}

		holder.tvName.setText(itemBean.getName());
		String prices[]= PriceUtil.getItemPrice(itemBean.getPrice_discount(),itemBean.getPrice_basic());
		holder.tvPriceDiscount.setText(prices[0]);
		if (prices[1]==null) {
			holder.tvPriceBasic.setVisibility(View.INVISIBLE);
		}else {
			holder.tvPriceBasic.setVisibility(View.VISIBLE);
			holder.tvPriceBasic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.tvPriceBasic.setText(prices[1]);
		}


		if (isSelected(itemBean.getId())) {
			//未选中状态
			holder.checkBox.setChecked(true);
			holder.itemSelected.setVisibility(View.VISIBLE);
		} else {
			//未选中状态
			holder.checkBox.setChecked(false);
			holder.itemSelected.setVisibility(View.GONE);
		}

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selected(i);
			}
		});

	}

	/**
	 * 选中或未选中状态设置
	 * @param  ：index
	 */
	private boolean isSelected(String id) {
		if (mSelectedList == null || mSelectedList.size() == 0) {
			return false;
		} else {
			for (SetItemModel.DataBean.ListBean listBean : mSelectedList) {
				if (listBean.getInfo().getId().equals(id)) {
					return true;
				}
			}
			return false;
		}
	}


	private void selected(int p) {
		if (isSelected(mList.get(p).getInfo().getId())) {
			String id=mList.get(p).getInfo().getId();
			for(int i=0;i<mSelectedList.size();i++){
				if(mSelectedList.get(i).getInfo().getId().equals(id)){
					mSelectedList.remove(i);
				}
			}
		} else {
			if (mSelectedList == null) mSelectedList = new ArrayList<>();
			mSelectedList.add(mList.get(p));
		}
		notifyDataSetChanged();
	}


	@Override
	public int getListLayoutId() {
		return R.layout.item_item_list;
	}

	/**
	 * @param view item_frag_home_drag 的view
	 * @return
	 */
	@Override
	public BaseRecyclerViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}

	class ViewHolder extends BaseRecyclerViewHolder {
		ImageView itemBannerImg;
		TextView tvName;
		TextView tvPriceBasic;
		TextView tvPriceDiscount;
		CheckBox checkBox;
		RelativeLayout ll;
		ImageView itemSelected;
		public ViewHolder(View itemView) {
			super(itemView);
			int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
			ll = (RelativeLayout) findView(R.id.item_ll);
			itemBannerImg = (ImageView) findView(R.id.item_img);
			checkBox = (CheckBox) findView(R.id.checkbox);
			int dd=DensityUtil.dip2px(mContext,16);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w-dd, w-dd);
			ll.setLayoutParams(params);
			itemBannerImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
			tvName = (TextView) findView(R.id.tv_name);
			tvPriceBasic = (TextView) findView(R.id.tv_price_basic);
			tvPriceDiscount = (TextView) findView(R.id.tv_price_discount);
			itemSelected = (ImageView) findView(R.id.diy_select_iv);
			checkBox.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取默认图
	 * @param listBean ：集合
	 * @return 默认图
	 */
	public String getDefaultImg(SetItemModel.DataBean.ListBean listBean) {
		for (SetItemModel.DataBean.ListBean.ItemBean.PreviewBean bean : listBean.getItem().getPreview()) {
			if (bean.getIs_default() == 1)
			return bean.getImage().get(0).getSmall();
		}
		return "";
	}
}

