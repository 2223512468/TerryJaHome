package com.jajahome.feature.user.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.ItemShowModel;
import com.jajahome.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 收藏的单品adapter
 */
public class ItemCollectAdapter extends BaseRecyclerAdapter<ItemShowModel.DataBean.ItemBean> {
    public static String Tag = "ItemCollectAdapter";
    private ItemShowModel.DataBean.ItemBean itemBean;

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemShowModel.DataBean.ItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        itemBean = mItemDataList.get(i);
        if (TextUtils.isEmpty(itemBean.getImage().getThumb())) {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_holder_big);
        } else {
            Picasso.with(getContext())
                    .load(itemBean.getImage().getThumb())
                    .placeholder(R.mipmap.ic_holder_big)
                    .into(holder.itemBannerImg);
        }
        holder.tvName.setText(itemBean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemDetailAct.class);
                intent.putExtra(ItemDetailAct.ITEM_ID, mItemDataList.get(i).getId());
                intent.putExtra(ItemDetailAct.ITEM_COLLECT, Tag);
                intent.putExtra(ItemDetailAct.ITEM_DELETECOLLECT,i+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_set_collect;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        LinearLayout LinearLayout;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
            itemBannerImg = (ImageView) findView(R.id.item_img);
            LinearLayout = (LinearLayout) findView(R.id.ll_view);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w-DensityUtil.dip2px(mContext,1), w);
            LinearLayout.setLayoutParams(params);
            tvName = (TextView) findView(R.id.tv_name);
        }
    }
}
