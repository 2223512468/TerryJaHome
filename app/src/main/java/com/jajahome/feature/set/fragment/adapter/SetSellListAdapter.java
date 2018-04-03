package com.jajahome.feature.set.fragment.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.model.SellItemListModel;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 销售清单adapter
 */
public class SetSellListAdapter extends BaseRecyclerAdapter<SellItemListModel.DataBean.ItemListBean> {

    public void setListItems(List<SetDiyPriceModel.ItemsBean> listItems) {
        this.listItems = listItems;
    }

    List<SetDiyPriceModel.ItemsBean> listItems;
    Gson gson = new Gson();


    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SellItemListModel.DataBean.ItemListBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        SellItemListModel.DataBean.ItemListBean.ItemBean.PreviewBean bean = getPreviewBean(i, mItemDataList.get(i));
        SellItemListModel.DataBean.ItemListBean.InfoBean info = mItemDataList.get(i).getInfo();
        Picasso.with(getContext())
                .load(bean.getImage().get(0).getSmall())
                .placeholder(R.mipmap.newlogo_square)
                .error(R.mipmap.newlogo_square)
                .into(holder.imgView);
        holder.tvName.setText(info.getName());
        holder.tvNums.setText(String.valueOf(info.getCount()));

        //这个字段中0：非卖品、1：销售品、2：赠品。
        if (info.getPay_state() == 1) {
            holder.tvPrice.setText(Constant.YUAN + info.getPrice_basic().getMax());
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvTypeNotSell.setVisibility(View.INVISIBLE);
        } else if (info.getPay_state() == 2) {
            holder.tvPrice.setVisibility(View.GONE);
            holder.tvPriceNotSell.setText(Constant.YUAN + info.getPrice_basic().getMax());
            holder.tvPriceNotSell.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTypeNotSell.setVisibility(View.VISIBLE);
        } else {
            holder.tvPrice.setVisibility(View.VISIBLE);
        }

    }

    private SellItemListModel.DataBean.ItemListBean.ItemBean.PreviewBean getPreviewBean(int i, SellItemListModel.DataBean.ItemListBean itemListBean) {
        SetDiyPriceModel.ItemsBean itemBean = listItems.get(i);
        int fId = itemBean.getFab_id();
        int mId = itemBean.getMat_id();

        List<SellItemListModel.DataBean.ItemListBean.ItemBean.PreviewBean> listPre = itemListBean.getItem().getPreview();
        for (SellItemListModel.DataBean.ItemListBean.ItemBean.PreviewBean pre : listPre) {
            int ffId = 0;
            int mmId = 0;
            if (!StringUtil.isEmpty(pre.getFabric())) {
                ffId = Integer.valueOf(pre.getFabric());
            }
            if (!StringUtil.isEmpty(pre.getMaterial())) {
                mmId = Integer.valueOf(pre.getMaterial());
            }
            if (ffId == fId && mmId == mId) return pre;
        }
        return listPre.get(0);
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_sell_item;
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
        ImageView imgView;
        TextView tvName;
        TextView tvPrice;
        TextView tvPriceNotSell;
        TextView tvTypeNotSell;
        TextView tvFabric;
        TextView tvNums;


        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findView(R.id.item_order_name);
            tvPrice = (TextView) findView(R.id.item_order_price);
            tvPriceNotSell = (TextView) findView(R.id.item_price_not_sell);
            tvTypeNotSell = (TextView) findView(R.id.item_type);
            tvFabric = (TextView) findView(R.id.item_order_fabric);
            tvNums = (TextView) findView(R.id.item_order_nums);
            imgView = (ImageView) findView(R.id.item_order_img);

        }
    }
}
