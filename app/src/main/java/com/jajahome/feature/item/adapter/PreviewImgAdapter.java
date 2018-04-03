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

import java.util.ArrayList;
import java.util.List;

/**
 * 小的预览图列表adapter
 */
public class PreviewImgAdapter extends BaseRecyclerAdapter<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> {
    private ItemModel model;

    public void setModel(ItemModel model) {
        this.model = model;
    }

    private OnPreviewImageChange mListener;
    /**
     * 当前位置
     */
    private int mIndex;

    public String getDefaultFabricId() {
        return defaultFabricId;
    }

    public String getDefaultMaterialId() {
        return defaultMaterialId;
    }

    public void setDefaultFabricId(String defaultFabricId) {
        this.defaultFabricId = defaultFabricId;
    }

    /**
     * 默认材质id
     */
    private String defaultFabricId;

    public void setDefaultMaterialId(String defaultMaterialId) {
        this.defaultMaterialId = defaultMaterialId;
    }

    /**
     * 默认面料id
     */
    private String defaultMaterialId;


    public void setmListener(OnPreviewImageChange mListener) {
        this.mListener = mListener;
    }

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ItemModel.DataBean.ItemBean.PreviewBean.ImageBean bean = mItemDataList.get(i);
        String img = bean.getSmall();
        if (!StringUtil.isEmpty(img)) {
            Picasso.with(getContext())
                    .load(img)
                    .into(holder.itemBannerImg);
        }
        if (i == mIndex) {
            mIndex = i;
            holder.frameLayout.setBackgroundResource(R.drawable.select_btn_gray);
            holder.frameLayout.getBackground().setAlpha(15);
        } else {
            holder.frameLayout.setBackgroundResource(R.drawable.select_btn_white);
            holder.frameLayout.getBackground().setAlpha(15);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != mIndex) {
                    mIndex = i;
                    notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.onPreviewImageChange(i);
                    }
                }
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_preview_int_item;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public void setmIndex(int position) {
        mIndex = position;
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        LinearLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
//			int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 6;
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, w);
            frameLayout = (LinearLayout) findView(R.id.frame_layout);
//			frameLayout.setLayoutParams(params);
            itemBannerImg = (ImageView) findView(R.id.item_img);
        }
    }

    /**
     * 设置单品详情小的预览图
     *
     * @param model
     */
    public void setItemPreviewImgs(ItemModel model) {
        for (ItemModel.DataBean.ItemBean.PreviewBean bean : model.getData().getItem().getPreview()) {
            if (bean.getIs_default() == 1)
                defaultFabricId = bean.getFabric();
            defaultMaterialId = bean.getMaterial();
            resetItems(bean.getImage());
        }

    }

    /**
     * 设置单品详情小的预览图 (不管是否默认)
     *
     * @param model
     */
    public void setItemPreviewImgsNotDefault(ItemModel model) {
        this.model=model;
        if (model != null)
            resetItems(model.getData().getItem().getPreview().get(0).getImage());
    }

    /**
     * 设置单品详情小的预览图
     *
     * @param model
     */
    public void setItemPreviewImgsByFabric(ItemModel model, String fabricId) {
        defaultFabricId = fabricId;
        change();
    }

    /**
     * 设置单品详情小的预览图
     *
     * @param model
     */
    public void setItemPreviewImgsByMaterial(ItemModel model, String materialId) {
        defaultMaterialId = materialId;
        change();
    }

    public void change() {
        List<ItemModel.DataBean.ItemBean.PreviewBean.ImageBean> list = new ArrayList<>();
        if (!StringUtil.isEmpty(defaultFabricId) && !StringUtil.isEmpty(defaultMaterialId)) {
            for (ItemModel.DataBean.ItemBean.PreviewBean bean : model.getData().getItem().getPreview()) {
                if (defaultFabricId.equals(bean.getFabric()) && bean.getMaterial().equals(defaultMaterialId)) {
                    list = bean.getImage();
                }
            }
        }else  if (StringUtil.isEmpty(defaultFabricId) && !StringUtil.isEmpty(defaultMaterialId)) {
            for (ItemModel.DataBean.ItemBean.PreviewBean bean : model.getData().getItem().getPreview()) {
                if (bean.getMaterial().equals(defaultMaterialId)) {
                    list = bean.getImage();
                }
            }
        }else  if (!StringUtil.isEmpty(defaultFabricId) && StringUtil.isEmpty(defaultMaterialId)) {
            for (ItemModel.DataBean.ItemBean.PreviewBean bean : model.getData().getItem().getPreview()) {
                if (defaultFabricId.equals(bean.getFabric())) {
                    list = bean.getImage();
                }
            }
        }
        if(list!=null&&list.size()>0){
            resetItems(list);
        }
    }

    public interface OnPreviewImageChange {
        void onPreviewImageChange(int index);
    }
}
