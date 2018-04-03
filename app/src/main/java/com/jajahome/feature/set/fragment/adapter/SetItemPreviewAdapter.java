package com.jajahome.feature.set.fragment.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.PublishConstant;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.ImageModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.widget.horizontalrecycle.AutoMoveRecycleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 套装详情中单品预览列表adapter
 */
public class SetItemPreviewAdapter extends BaseRecyclerAdapter<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> {
    private SetItemModel setItemModel;
    private int mIndexList;//当前选中list
    private OnSetItemChangeListener listener;

    public void setmIndexList(int mIndexList) {
        this.mIndexList = mIndexList;
    }

    public AutoMoveRecycleView getmAutoMoveRecycleView() {
        return mAutoMoveRecycleView;
    }

    public void setmAutoMoveRecycleView(AutoMoveRecycleView mAutoMoveRecycleView) {
        this.mAutoMoveRecycleView = mAutoMoveRecycleView;
    }

    AutoMoveRecycleView mAutoMoveRecycleView;

    public OnSetItemChangeListener getListener() {
        return listener;
    }

    public void setListener(OnSetItemChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean.ImageBean> bean = mItemDataList.get(i).getImage();
        if (bean!=null&&!StringUtil.isEmpty(bean.get(0).getSmall())) {
            Picasso.with(getContext())
                    .load(bean.get(0).getSmall())
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
                    int published = setItemModel.getData().getList().get(i).getItem().getPublished();
                    if (published == PublishConstant.UP) {
                        Intent intent = new Intent(mContext, ItemDetailAct.class);
                        intent.putExtra(ItemDetailAct.ITEM_ID, setItemModel.getData().getList().get(i).getItem().getId());
                        mContext.startActivity(intent);
                    } else {
                        T.showShort(mContext, PublishConstant.getState(published));
                    }
                }
                notifyItemChanged(mIndexList);
                mIndexList = i;
                notifyItemChanged(i);
                if (listener != null) {
                    listener.onSetItemChangeed(i);
                    SetItemModel.DataBean.ListBean listBean = setItemModel.getData().getList().get(i);
                    if (mList.get(i).getSet_image() == null) {
                        if (mList.get(i).getBaseUrl() != null) {
                            Object object = mList.get(i).getBaseUrl();
                            try {
                                Gson gson = new Gson();
                                ImageModel model = gson.fromJson(gson.toJson(object), ImageModel.class);
                                listener.onSetImgageChanged(listBean.getId(), getListBean(mList.get(i), listBean), model.getUrl(), listBean.getItem().getId());
                            } catch (JsonSyntaxException e) {

                            }

                        }
                    } else {
                        listener.onSetImgageChanged(listBean.getId(), getListBean(mList.get(i), listBean), mList.get(i).getSet_image().getUrl(), listBean.getItem().getId());
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

    public int getIndex() {
        return mIndexList;
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
    public void setItemImgs(SetItemModel model, String id) {
        this.setItemModel = model;
        this.mIndexList = 0;
        if (model == null) {
            return;
        }
        List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> mList = new ArrayList<>();
        List<SetItemModel.DataBean.ListBean> mListBeans = model.getData().getList();
        int size = mListBeans.size();
        for (int i = 0; i < size; i++) {
            SetItemModel.DataBean.ListBean bean = mListBeans.get(i);
            mList.add(getDefaultPreview(bean));
            if (bean.getInfo().getId().equals(id)) {
                this.mIndexList = i;
            }
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
            if (previewBean != null && previewBean.getMaterial().equals(materialId)) {
                mList.set(mIndexList, previewBean);
                notifyItemChanged(mIndexList);
                if (listener != null) {
                    //单品材质变化
                    SetItemModel.DataBean.ListBean listBean = setItemModel.getData().getList().get(mIndexList);
                    if(previewBean.getSet_image()!=null){
                        listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), previewBean.getSet_image().getUrl(), "");
                    }else {
                        listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), "", "");
                    }
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
        SetItemModel.DataBean.ListBean listReture = new SetItemModel.DataBean.ListBean();
        listReture.setItem(listBean.getItem());
        Gson gson = new Gson();
        String json = gson.toJson(listBean.getInfo());
        SetItemModel.DataBean.ListBean.InfoBean infoBean = gson.fromJson(json, SetItemModel.DataBean.ListBean.InfoBean.class);
        String jsonImg = gson.toJson(previewBean.getSet_image());
        infoBean.setImage(gson.fromJson(jsonImg, Object.class));
        infoBean.setPrice_basic(previewBean.getPrice_basic());
        if (!StringUtil.isEmpty(previewBean.getFabric())) {
            infoBean.setFabric(Integer.valueOf(previewBean.getFabric()));
        } else {
            infoBean.setFabric(0);
        }
        if (!StringUtil.isEmpty(previewBean.getMaterial())) {
            infoBean.setMaterial(Integer.valueOf(previewBean.getMaterial()));
        } else {
            infoBean.setMaterial(0);
        }

        infoBean.setPrice_discount(previewBean.getPrice_discount());
        listReture.setInfo(infoBean);
        listReture.setId(listBean.getId());
        return listReture;
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
                    if (previewBean.getSet_image() == null) {
                        if (previewBean.getBaseUrl() != null) {
                            Object object = previewBean.getBaseUrl();
                            try {
                                Gson gson = new Gson();
                                ImageModel model = gson.fromJson(gson.toJson(object), ImageModel.class);
                                listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), model.getUrl(), "");
                            } catch (JsonSyntaxException e) {

                            }
                        }
                    } else {
                        listener.onSetImgageChanged(listBean.getId(), getListBean(previewBean, listBean), previewBean.getSet_image().getUrl(), "");
                    }
                }
            }
        }
    }

    private SetItemModel.DataBean.ListBean.ItemBean.PreviewBean getDefaultPreview(SetItemModel.DataBean.ListBean bean) {
        List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> previews = bean.getItem().getPreview();
        for (SetItemModel.DataBean.ListBean.ItemBean.PreviewBean previewBean : previews) {
            if (previewBean.getIs_default() == 1) {
                if (previewBean.getSet_image() == null) {
                    previewBean.setBaseUrl(bean.getInfo().getImage());
                }
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
        void onSetImgageChanged(String itemId, final SetItemModel.DataBean.ListBean bean, String url, String id);
    }
}
