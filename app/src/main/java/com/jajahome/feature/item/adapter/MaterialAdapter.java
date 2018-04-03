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
public class MaterialAdapter extends BaseRecyclerAdapter<ItemModel.DataBean.ItemBean.MaterialBean> {
	private int mIndex;//当前选中

	/**
	 * 获取名称
	 * @return 名称
	 */
	public String getDefaultName() {
		return defaultName;
	}
	private String defaultName="";
	public void setListener(OnMaterialChangeListener listener) {
		this.listener = listener;
	}

	private OnMaterialChangeListener listener;

	/**
	 * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
	 * @param i             位置
	 * @param mItemDataList 数据集合
	 */
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemModel.DataBean.ItemBean.MaterialBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		ItemModel.DataBean.ItemBean.MaterialBean bean = mItemDataList.get(i);
		String imgUrl=bean.getImage().getSmall();
		if (!StringUtil.isEmpty(imgUrl)) {
			Picasso.with(getContext())
					.load(imgUrl)
					.into(holder.itemBannerImg);
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
						listener.onMaterialChanged(mList.get(i).getId(),mList.get(i).getName());
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
	public void setMaterialList(List<ItemModel.DataBean.ItemBean.MaterialBean> list, String fabricId) {
		if (StringUtil.isEmpty(fabricId)) {
			mIndex = 0;
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId().equals(fabricId)) {
					mIndex = i;
					defaultName=list.get(i).getName();
				}
			}
		}
		resetItems(list);
		notifyDataSetChanged();
	}

	public interface  OnMaterialChangeListener{
		void onMaterialChanged(String id, String name);
	}
}
