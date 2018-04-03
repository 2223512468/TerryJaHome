package com.jajahome.feature.search.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.home.adapter.HomeListAdapter;
import com.jajahome.feature.search.SearchShowAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.set.view.SetImageView;
import com.jajahome.model.SearchResultModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.PriceUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单品列表adapter
 */
public class SearchListAdapter extends BaseRecyclerAdapter<SearchResultModel.DataBean.ItemBean> {

    public SearchListAdapter(String type) {
        this.type = type;
    }

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    private String ImgUrl;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SearchResultModel.DataBean.ItemBean> mItemDataList) {
        final SearchResultModel.DataBean.ItemBean itemBean = mItemDataList.get(i);
        if (type.equals(SearchShowAct.TYPE_ITEM)) {
            ViewHolderItem holder = (ViewHolderItem) viewHolder;
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
            holder.tvName.setText(itemBean.getTitle());
            //holder.tvPriceDiscount.setText(itemBean.getPrice());
            //几个价格
            // holder.tvPriceBasic.setVisibility(View.INVISIBLE);
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
                    HomeListAdapter.jumpToActByAction(mContext, itemBean.getAction(), itemBean.getAction_id(), itemBean.getUrl(), itemBean.getTitle());
                }
            });
        } else if (type.equals(SearchShowAct.TYPE_SET)) {
            final ViewHolderSet holder = (ViewHolderSet) viewHolder;
            SearchResultModel.DataBean.ItemBean itemSetBean = mItemDataList.get(i);
            final String url = itemSetBean.getImage().getThumb();
            if (!StringUtil.isEmpty(url)) {
                Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_bottom_background).into(holder.itemBannerImg);
            } else {
                holder.itemBannerImg.setImageResource(R.mipmap.ic_bottom_background);
            }
            if (setImageViewMap != null && !setImageViewMap.containsKey(i)) {
                setImageViewMap.put(i, holder.itemBannerImg);
            }

            holder.categoryTv.setText(mItemDataList.get(i).getCategory());
            holder.nameTv.setText(mItemDataList.get(i).getTitle());
            holder.tipTv.setText(mItemDataList.get(i).getTips());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SetDetailAct.class);
                    intent.putExtra(SetDetailAct.SET_ID, mItemDataList.get(i).getId());
                    intent.putExtra(SetDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                    intent.putExtra(SetDetailAct.SET_IMG, url);
                    intent.putExtra(SetDetailAct.SET_TIP, mItemDataList.get(i).getTips());
                    mContext.startActivity(intent);
                }
            });

        } else if (type.equals(SearchShowAct.TYPE_SHOW)) {
            ViewHolderShow holder = (ViewHolderShow) viewHolder;
            final String img = itemBean.getImage().getSmall();
            if (!StringUtil.isEmpty(img)) {
                Picasso.with(getContext())
                        .load(img)
                        .placeholder(R.mipmap.ic_bottom_background)
                        .into(holder.itemBannerImg);

            } else {
                holder.itemBannerImg.setImageResource(R.mipmap.newlogo02);
            }
            String type = mItemDataList.get(i).getType();

            switch (type) {
                case "0":
                    holder.leftImg.setImageResource(R.mipmap.ic_beautiful_pic);
                    break;
                case "1":
                    holder.leftImg.setImageResource(R.mipmap.ic_small_essay);
                    break;
                case "2":
                    holder.leftImg.setImageResource(R.mipmap.ic_home_match_icon);
                    break;
                case "3":
                    holder.leftImg.setImageResource(R.mipmap.ic_home_original_icon);
                    break;
            }
            holder.textView.setText(itemBean.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeListAdapter.jumpToActByAction(mContext, itemBean.getAction(), itemBean.getAction_id(), itemBean.getUrl(), itemBean.getTitle());
                }
            });
        }

    }

    private Map<Integer, SetImageView> setImageViewMap = new HashMap<>();

    public Map<Integer, SetImageView> getSetImageViewMap() {
        return setImageViewMap;
    }

    @Override
    public int getListLayoutId() {
        switch (type) {
            case SearchShowAct.TYPE_ITEM:
                return R.layout.item_item_item_list;
            case SearchShowAct.TYPE_SET:
                return R.layout.act_set_item_frag;
            case SearchShowAct.TYPE_SHOW:
                return R.layout.item_home_list;
        }
        return R.layout.item_item_item_list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        switch (type) {
            case SearchShowAct.TYPE_ITEM:
                return new ViewHolderItem(view);
            case SearchShowAct.TYPE_SET:
                return new ViewHolderSet(view);
            case SearchShowAct.TYPE_SHOW:
                return new ViewHolderShow(view);
        }
        return new ViewHolderItem(view);

    }

    /**
     * ViewHolder
     */
    class ViewHolderItem extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        TextView tvName, selectColor;
        TextView tvPriceBasic;
        TextView tvPriceDiscount;
        LinearLayout ll;

        public ViewHolderItem(View itemView) {
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

    class ViewHolderShow extends BaseRecyclerViewHolder {
        ImageView itemBannerImg, leftImg;
        TextView textView;

        public ViewHolderShow(View itemView) {
            super(itemView);
            itemBannerImg = (ImageView) findView(R.id.item_img);
            textView = (TextView) findView(R.id.textView);
            leftImg = (ImageView) findView(R.id.img);
        }
    }

    private int w;

    class ViewHolderSet extends BaseRecyclerViewHolder {
        SetImageView itemBannerImg;
        TextView tipTv, categoryTv, nameTv;

        public ViewHolderSet(View itemView) {
            super(itemView);
            w = (int) DensityUtil.getDisplayWidthDp(mContext);
            itemBannerImg = (SetImageView) findView(R.id.item_img);
            tipTv = (TextView) findView(R.id.tips);
            categoryTv = (TextView) findView(R.id.category);
            nameTv = (TextView) findView(R.id.name);
            itemBannerImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemBannerImg.setImageResource(R.mipmap.ic_bottom_background);
        }
      /*  ImageView itemBannerImg;

        public ViewHolderSet(View itemView) {
            super(itemView);
            int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
            itemBannerImg = (ImageView) findView(R.id.item_img);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, w);
            itemBannerImg.setLayoutParams(params);
        }*/
    }
}

