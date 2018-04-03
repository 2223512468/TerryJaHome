package com.jajahome.feature.diy.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.PublishConstant;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.SetItemModel;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.widget.horizontalrecycle.AutoMoveRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 套装详情中单品预览列表adapter
 */
public class DiyItemPreviewAdapter extends BaseRecyclerAdapter<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> {
	private SetItemModel setItemModel;
	/**
	 * 当前选中list
	 */
	private int mIndexList;
	/**
	 * 所选单品变化监听
	 */
	private OnSetItemChangeListener listener;

	AutoMoveRecycleView mAutoMoveRecycleView;

	public OnSetItemChangeListener getListener() {
		return listener;
	}

	public void setListener(OnSetItemChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
	 * @param i             位置
	 * @param mItemDataList 数据集合
	 */
	@Override
	public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> mItemDataList) {
		ViewHolder holder = (ViewHolder) viewHolder;
		String imgUrl = mItemDataList.get(i).getImage().get(0).getSmall();
		if (!StringUtil.isEmpty(imgUrl)) {
			Glide.with(getContext())
					.load(imgUrl)
					.placeholder(R.mipmap.ic_holder_big)
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
				if (mIndexList == i) {
					int published=setItemModel.getData().getList().get(i).getItem().getPublished();
					if(published== PublishConstant.UP) {
						Intent intent = new Intent(mContext, ItemDetailAct.class);
						intent.putExtra(ItemDetailAct.ITEM_ID, setItemModel.getData().getList().get(i).getItem().getId());
						mContext.startActivity(intent);
					}else{
						T.showShort(mContext,PublishConstant.getState(published));
					}
				}
				notifyItemChanged(mIndexList);
				mIndexList = i;
				notifyItemChanged(i);
				if (listener != null) {
					listener.onSetItemChangeed(i);
					SetItemModel.DataBean.ListBean listBean = setItemModel.getData().getList().get(i);
					if(mList.get(i).getSet_image()==null){
						listener.onSetImgageChanged(listBean.getId(), getListBean(mList.get(i), listBean), mList.get(i).getImage().get(0).getUrl());
					}else {
						listener.onSetImgageChanged(listBean.getId(), getListBean(mList.get(i), listBean), mList.get(i).getSet_image().getUrl());
					}

				}
				if (mAutoMoveRecycleView != null) {
					mAutoMoveRecycleView.checkAutoAdjust(i);
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

		public ViewHolder(View view) {
			super(view);
			frameLayout = (LinearLayout) findView(R.id.frame_layout);
			itemBannerImg = (ImageView) findView(R.id.item_img);
		}
	}


	/**
	 * 设置套装单品预览图
	 */
	public void setItemImgs(SetItemModel model) {
		this.setItemModel = model;
		this.mIndexList = model.getData().getIndex();
		List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> mList = new ArrayList<>();
		for (SetItemModel.DataBean.ListBean bean : model.getData().getList()) {
			mList.add(getDefaultPreview(bean));
		}
		resetItems(mList);
	}

	public void changePreviewImageByMaterial(String materialId) {
		if (setItemModel == null) {
			return;
		}
		//预览图
		List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> previews = setItemModel.getData().getList().get(mIndexList).getItem().getPreview();

		for (SetItemModel.DataBean.ListBean.ItemBean.PreviewBean previewBean : previews) {
			if (previewBean.getMaterial().equals(materialId)) {
				mList.set(mIndexList, previewBean);
				notifyItemChanged(mIndexList);
				if (listener != null) {
					//单品材质变化
					SetItemModel.DataBean.ListBean listBean = setItemModel.getData().getList().get(mIndexList);
					listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), previewBean.getSet_image().getUrl());
				}
			}
		}
	}

	/**
	 * 更换组装套装的单品信息（透视图 和价格信息）
	 *
	 * @param previewBean ：选择的
	 * @param listBean    ：要转化的单品信息
	 * @return 化的单品信息
	 */
	private SetItemModel.DataBean.ListBean getListBean(SetItemModel.DataBean.ListBean.ItemBean.PreviewBean previewBean, SetItemModel.DataBean.ListBean listBean) {
		Gson gson = new Gson();
		String jsonImg = gson.toJson(previewBean.getSet_image());
		listBean.getInfo().setImage(gson.fromJson(jsonImg, Object.class));
		listBean.getInfo().setPrice_basic(previewBean.getPrice_basic());
		listBean.getInfo().setPrice_discount(previewBean.getPrice_discount());
		return listBean;
	}

	/**
	 * 单品的面料变化
	 *
	 * @param fbricId
	 */
	public void changePreviewImageByFabric(String fbricId) {
		if (setItemModel == null) {
			return;
		}
		List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> previews = setItemModel.getData().getList().get(mIndexList).getItem().getPreview();
		for (SetItemModel.DataBean.ListBean.ItemBean.PreviewBean previewBean : previews) {
			if (previewBean.getFabric().equals(fbricId)) {
				mList.set(mIndexList, previewBean);
				notifyItemChanged(mIndexList);
				if (listener != null) {
					SetItemModel.DataBean.ListBean listBean = setItemModel.getData().getList().get(mIndexList);
					listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), previewBean.getSet_image().getUrl());
				}
			}
		}
	}

	private SetItemModel.DataBean.ListBean.ItemBean.PreviewBean getDefaultPreview(SetItemModel.DataBean.ListBean bean) {
		List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> previews = bean.getItem().getPreview();
		for (SetItemModel.DataBean.ListBean.ItemBean.PreviewBean previewBean : previews) {
			if (previewBean.getIs_default() == 1) {
				return previewBean;
			}
		}
		return null;
	}

	/**
	 * 单品种类变化监听
	 */
	public interface OnSetItemChangeListener {
		void onSetItemChangeed(int index);

		/**
		 * 单品样式 等变化
		 *
		 * @param itemId ：单品id
		 * @param bean   :变化单品位置 透视图相关
		 * @param url    ：透视图地址
		 */
		void onSetImgageChanged(String itemId, final SetItemModel.DataBean.ListBean bean, String url);
	}
}
