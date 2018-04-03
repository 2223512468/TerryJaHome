package com.jajahome.feature.item.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *  相关单品 adapter
 */
public class RelatedImgAdapter extends BaseRecyclerAdapter<ItemModel.DataBean.ItemBean.RelatedBean> {
	/**
	 * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
	 * @param i             位置
	 * @param mItemDataList 数据集合
	 */
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemModel.DataBean.ItemBean.RelatedBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		final ItemModel.DataBean.ItemBean.RelatedBean bean = mItemDataList.get(i);
		if (!StringUtil.isEmpty(bean.getImage().getSmall())) {
			Picasso.with(getContext())
					.load(bean.getImage().getSmall())
					.into(holder.itemBannerImg);
		}

		holder.frameLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ItemDetailAct.class);
				intent.putExtra(ItemDetailAct.ITEM_ID, bean.getId());
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public int getListLayoutId() {
		return R.layout.item_preview_img;
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
		LinearLayout frameLayout;

		public ViewHolder(View itemView) {
			super(itemView);
			itemBannerImg = (ImageView) findView(R.id.item_img);
			frameLayout = (LinearLayout) findView(R.id.frame_layout);
		}
	}

}
