package com.jajahome.feature.diy.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.diy.DiySellItemListAct;
import com.jajahome.model.SellItemListModel;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/29.
 */
public class DiySellListAdapter extends BaseRecyclerAdapter<SellItemListModel.DataBean.ItemListBean> {


    List<SetDiyPriceModel.ItemsBean> listItems;
    List<SellItemListModel.DataBean.ItemListBean> list;
    Gson gson = new Gson();

    public void setListItems(List<SetDiyPriceModel.ItemsBean> listItems) {
        this.listItems = listItems;
    }

    private Map<Integer, Integer> addMap = new HashMap<>();

    public void addListItems(List<SellItemListModel.DataBean.ItemListBean> list) {
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            addMap.put(i, 1);
        }
    }

    public List<SellItemListModel.DataBean.ItemListBean> getListItem() {
        return list;
    }

    public List<SetDiyPriceModel.ItemsBean> getItems() {
        if (list == null) {
            return null;
        }
        List<SetDiyPriceModel.ItemsBean> setDiyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SellItemListModel.DataBean.ItemListBean itemInfoBean = list.get(i);
            SellItemListModel.DataBean.ItemListBean.InfoBean infoBean = itemInfoBean.getInfo();
            if (infoBean.getPay_state() == 1) {

                SetDiyPriceModel.ItemsBean itembean = new SetDiyPriceModel.ItemsBean();
                itembean.setId(infoBean.getId());
                itembean.setFab_id(infoBean.getFabric());
                itembean.setMat_id(infoBean.getMaterial());
                setDiyList.add(itembean);
            }
        }
        return setDiyList;
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SellItemListModel.DataBean.ItemListBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        SellItemListModel.DataBean.ItemListBean.ItemBean.PreviewBean bean = getPreviewBean(i, mItemDataList.get(i));
        final SellItemListModel.DataBean.ItemListBean.InfoBean info = mItemDataList.get(i).getInfo();
        Picasso.with(getContext())
                .load(bean.getImage().get(0).getSmall())
                .placeholder(R.mipmap.newlogo_square)
                .error(R.mipmap.newlogo_square)
                .into(holder.imgView);
        holder.tvName.setText(info.getName());
        holder.tvNums.setText(1 + "");

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

        if (addMap.get(i) != null && addMap.get(i) == 1) {
            holder.selectItem.setVisibility(View.VISIBLE);
        } else if (addMap.get(i) != null && addMap.get(i) == 0) {
            holder.selectItem.setVisibility(View.INVISIBLE);
        }


        holder.sellLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSelItem(i, mItemDataList);
            }
        });

    }


    private void addSelItem(int pos, List<SellItemListModel.DataBean.ItemListBean> mItemDataList) {
        int index = addMap.get(pos);
        if (index == 1) {
            addMap.put(pos, 0);
            list.remove(mItemDataList.get(pos));

        } else if (index == 0) {
            addMap.put(pos, 1);
            list.add(mItemDataList.get(pos));
        }

        EventBus.getDefault().post(new EventMessage(DiySellItemListAct.DIY_SELL0, ""));
        notifyDataSetChanged();

    }


    @Override
    public int getListLayoutId() {
        return R.layout.item_diy_sell_item;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
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

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView imgView;
        TextView tvName;
        TextView tvPrice;
        TextView tvPriceNotSell;
        TextView tvTypeNotSell;
        TextView tvFabric;
        TextView tvNums;
        ImageView selectItem, unselectItem;
        LinearLayout sellLinearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findView(R.id.item_order_name);
            tvPrice = (TextView) findView(R.id.item_order_price);
            tvPriceNotSell = (TextView) findView(R.id.item_price_not_sell);
            tvTypeNotSell = (TextView) findView(R.id.item_type);
            tvFabric = (TextView) findView(R.id.item_order_fabric);
            tvNums = (TextView) findView(R.id.item_order_nums);
            imgView = (ImageView) findView(R.id.item_order_img);
            selectItem = (ImageView) findView(R.id.select_item);
            unselectItem = (ImageView) findView(R.id.unselect_item);
            sellLinearLayout = (LinearLayout) findView(R.id.diy_sell_ll);

        }
    }
}
