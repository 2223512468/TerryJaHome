package com.jajahome.feature.item.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 单品中材质列表adapter
 */
public class FabricAdapter extends BaseRecyclerAdapter<ItemModel.DataBean.ItemBean.FabricBean> {
	public String getDefaultName() {
		return defaultName;
	}

	private String defaultName="";
	private int mIndex;//当前选中

	public void setListener(OnFabricChangeListener listener) {
		this.listener = listener;
	}

	private OnFabricChangeListener listener;

	/**
	 * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
	 * @param i             位置
	 * @param mItemDataList 数据集合
	 */
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemModel.DataBean.ItemBean.FabricBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		ItemModel.DataBean.ItemBean.FabricBean bean = mItemDataList.get(i);
		//判断图片地址
		if (!StringUtil.isEmpty(bean.getImage().getSmall())) {
			Picasso.with(getContext())
					.load(bean.getImage().getSmall())
					.into(holder.itemBannerImg);
		}else {
			holder.itemBannerImg.setImageResource(R.mipmap.ic_holder_big);
		}
		if (mIndex==i) {
			holder.frameLayout.setBackgroundResource(R.drawable.bg_img_selected);
		} else {
			holder.frameLayout.setBackgroundResource(R.drawable.select_btn_white);
		}
		holder.frameLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(i!=mIndex){
					mIndex = i;
					notifyDataSetChanged();
					if(listener!=null){
						listener.onFabricChanged(mList.get(i).getId(),mList.get(i).getName());
					}
				}
			}
		});
	}

	@Override
	public int getListLayoutId() {
		return R.layout.item_preview_img;
	}

	@Override
	public BaseRecyclerViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}

	class ViewHolder extends BaseRecyclerViewHolder {
		ImageView itemBannerImg;
		LinearLayout frameLayout;

		public ViewHolder(View itemView) {
			super(itemView);
			itemBannerImg = (ImageView) findView(R.id.item_img);
			frameLayout = (LinearLayout) findView(R.id.frame_layout);
		}
	}
	/**
	 * 设置单品 面料
	 *
	 * @param list
	 */
	public void setFabricList(List<ItemModel.DataBean.ItemBean.FabricBean> list, String fabricId) {
		if (StringUtil.isEmpty(fabricId)) {
			mIndex = 0;
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId().equals(fabricId)) {
					defaultName=list.get(i).getName();
					mIndex = i;
				}
			}
		}
		resetItems(list);
		notifyDataSetChanged();
	}

	public interface  OnFabricChangeListener{
		void onFabricChanged(String id, String name);
	}
}
