package com.jajahome.feature.item.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/24.
 */
public class RelatedSetImgAdapter extends BaseRecyclerAdapter<ItemModel.DataBean.ItemBean.RelatedSetBean> {

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<ItemModel.DataBean.ItemBean.RelatedSetBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final ItemModel.DataBean.ItemBean.RelatedSetBean bean = mItemDataList.get(i);
        if (!StringUtil.isEmpty(bean.getImage().getSmall())) {
            Picasso.with(getContext())
                    .load(bean.getImage().getSmall())
                    .into(holder.itemBannerImg);
        }

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, bean.getId());
                mContext.startActivity(intent);
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
}
