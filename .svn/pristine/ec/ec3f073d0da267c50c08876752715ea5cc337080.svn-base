package com.jajahome.feature.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.activity.OrderDetailAct;
import com.jajahome.model.OrderDetailModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 订单详情中的订单列表
 */
public class OrderListAdapter extends BaseListAdapter<OrderDetailModel.DataBean.OrderBean.OrderListBean> {
    public String orderNum;


    public OrderListAdapter(Context context, List<OrderDetailModel.DataBean.OrderBean.OrderListBean> list) {
        super(context, list);
    }

    @Override
    public View initView(OrderDetailModel.DataBean.OrderBean.OrderListBean orderListBean, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_order_list, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.item_order_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.item_order_price);
            holder.tvFabric = (TextView) convertView.findViewById(R.id.item_order_fabric);
            holder.tvUnit = (TextView) convertView.findViewById(R.id.item_order_unit);
            holder.tvNums = (TextView) convertView.findViewById(R.id.item_order_nums);
            holder.imgView = (ImageView) convertView.findViewById(R.id.item_order_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //面料，材质，颜色，规格\n价格
        String data[] = orderListBean.getDesc().split(",");
        String data2[] = data[3].split("\n");
        String fabric = null;
        if (!StringUtil.isEmpty(data[0])) {
            fabric = "面料：" + data[0];
        }
        if (StringUtil.isEmpty(fabric)) {
            if (!StringUtil.isEmpty(data[1]))
                fabric = "材质：" + data[1];
        } else {
            if (!StringUtil.isEmpty(data[1]))
                fabric = fabric + "  材质：" + data[1];
        }
        if (!StringUtil.isEmpty(fabric)) {
            holder.tvFabric.setText(fabric);
        } else {
            holder.tvFabric.setVisibility(View.GONE);
        }
        //颜色 规格
        String unitAndColor = null;
        if (!StringUtil.isEmpty(data2[0])) {
            unitAndColor = "规格：" + data2[0];
        }
        if (StringUtil.isEmpty(unitAndColor)) {
            if (!StringUtil.isEmpty(data[2]))
                unitAndColor = "颜色：" + data[2];
        } else {
            if (!StringUtil.isEmpty(data[2]))
                unitAndColor = unitAndColor + "  颜色" + data[2];
        }
        if (!StringUtil.isEmpty(unitAndColor)) {
            holder.tvUnit.setText(unitAndColor);
        } else {
            holder.tvUnit.setVisibility(View.GONE);
        }

        holder.tvPrice.setText(data2[1]);
        holder.tvName.setText(orderListBean.getName());
        holder.tvNums.setText("×" + orderListBean.getNumber());
        orderNum = orderListBean.getNumber() + "";


        if (!StringUtil.isEmpty(orderListBean.getImage().getSmall())) {
            Picasso.with(context).load(orderListBean.getImage().getSmall()).into(holder.imgView);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imgView;
        TextView tvName;
        TextView tvPrice;
        TextView tvFabric;
        TextView tvUnit;
        TextView tvNums;
    }
}
