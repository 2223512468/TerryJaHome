package com.jajahome.feature.item.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.ItemListModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.PriceUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 单品列表adapter
 */
public class ItemListAdapter extends BaseRecyclerAdapter<ItemListModel.DataBean.ItemBean> {

    private String ImgUrl;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ItemListModel.DataBean.ItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ItemListModel.DataBean.ItemBean itemBean = mItemDataList.get(i);
        if (itemBean.getImage() != null) {
            String imgUrl = itemBean.getImage().getThumb();
            ImgUrl = imgUrl;
            //图片地址不为空
            if (!StringUtil.isEmpty(imgUrl)) {
                Picasso.with(getContext())
                        .load(imgUrl)
                        .placeholder(R.mipmap.newlogo_square)
                        .into(holder.itemBannerImg);
            } else {
                holder.itemBannerImg.setImageResource(R.mipmap.newlogo_square);
            }
        } else {
            holder.itemBannerImg.setImageResource(R.mipmap.newlogo_square);
        }

        int count = mItemDataList.get(i).getCount();
        if (count == 1) {
            holder.selectColor.setVisibility(View.INVISIBLE);
        } else if (count > 1) {
            holder.selectColor.setVisibility(View.VISIBLE);
            holder.selectColor.setText(mItemDataList.get(i).getCount() + "色可选");
        }
        holder.tvName.setText(itemBean.getName());
        String prices[] = PriceUtil.getItemPriceInList(itemBean.getPrice_discount(), itemBean.getPrice_basic());
        holder.tvPriceDiscount.setText(prices[0]);
        //几个价格
        if (prices[1] == null) {
            holder.tvPriceBasic.setVisibility(View.INVISIBLE);
        } else {
            holder.tvPriceBasic.setVisibility(View.VISIBLE);
            holder.tvPriceBasic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvPriceBasic.setText(prices[1]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemDetailAct.class);
                intent.putExtra(ItemDetailAct.ITEM_ID, mItemDataList.get(i).getId());
                intent.putExtra(ItemDetailAct.ITEM_IMG_URL, mItemDataList.get(i).getImage().getThumb());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_item_item_list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        TextView tvName, selectColor;
        TextView tvPriceBasic;
        TextView tvPriceDiscount;
        LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
            itemBannerImg = (ImageView) findView(R.id.item_img);
            ll = (LinearLayout) findView(R.id.item_ll);
            int dd = DensityUtil.dip2px(mContext, 16);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w - dd, w - dd);
            ll.setLayoutParams(params);
            itemBannerImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
            tvName = (TextView) findView(R.id.tv_name);
            tvPriceBasic = (TextView) findView(R.id.tv_price_basic);
            tvPriceDiscount = (TextView) findView(R.id.tv_price_discount);
            selectColor = (TextView) findView(R.id.select);
        }
    }
}

