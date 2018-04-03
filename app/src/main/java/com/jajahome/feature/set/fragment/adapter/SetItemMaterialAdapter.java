package com.jajahome.feature.set.fragment.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.SetItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 套装详情中单品预览列表adapter
 */
public class SetItemMaterialAdapter extends BaseRecyclerAdapter<SetItemModel.DataBean.ListBean.ItemBean.MaterialBean> {

	private SetItemModel setItemModel;
	private SetItemModel.DataBean.ListBean.ItemBean item;
	private int mIndexList;//当前选中list
	private OnSetItemMaterialListener listener;
	public void setListener(OnSetItemMaterialListener listener) {
		this.listener = listener;
	}
	public String getCurrentItemName(){
		if(item.getMaterial()==null)return "";
		SetItemModel.DataBean.ListBean.ItemBean.MaterialBean bean = item.getMaterial().get(mIndexList);
		if(bean!=null){
			String name=bean.getName();
			if(StringUtil.isEmpty(name)){
				return "";
			}else {
				return  bean.getName();
			}
		}
		return "";
	}
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetItemModel.DataBean.ListBean.ItemBean.MaterialBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		final SetItemModel.DataBean.ListBean.ItemBean.MaterialBean bean = mItemDataList.get(i);
		String url = bean.getImage().getUrl();
		if (!StringUtil.isEmpty(bean.getImage().getSmall())) {
			Picasso.with(getContext())
					.load(url)
					.into(holder.itemBannerImg);
		}
		if (i == mIndexList) {
			holder.frameLayout.setBackgroundResource(R.drawable.bg_img_selected);
		} else {
			holder.frameLayout.setBackgroundResource(R.drawable.select_btn_white);
		}
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mIndexList = i;
				item.setIndexMaterial(mIndexList);
				notifyDataSetChanged();
				if (listener != null) {
					listener.onMaterialChanged(i,bean.getId(), bean.getName());
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
			frameLayout = (LinearLayout) findView(R.id.frame_layout);
			itemBannerImg = (ImageView) findView(R.id.item_img);
		}
	}

	/**
	 * 设置套装单品预览图
	 */
	public void setItemImgs(SetItemModel model) {
		this.setItemModel=model;
		SetItemModel.DataBean data=model.getData();
		SetItemModel.DataBean.ListBean itemBean = data.getList().get(data.getIndex());
		item = itemBean.getItem();
		mIndexList=item.getIndexMaterial();
		resetItems(item.getMaterial());
	}
	/**
	 * 面料改变的监听
	 */
	public interface OnSetItemMaterialListener {
		void onMaterialChanged(int index, String materialId, String name);
	}
}
