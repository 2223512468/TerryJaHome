package com.jajahome.feature.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.feature.user.activity.MaterialsAct;
import com.jajahome.model.YunOrderModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class YunOrderDetailAdapter extends BaseAdapter {

    private Context mCotnext;
    private List<YunOrderModel.DataBean.WaybillBean.OrderListBean> order_list;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private String priceName, desc, orderId, mOrderId;
    private int number, count;

    public YunOrderDetailAdapter(Context mContext, List<YunOrderModel.DataBean.WaybillBean.OrderListBean> order_list, String orderId, String mOrderId) {
        this.mCotnext = mContext;
        this.order_list = order_list;
        this.inflater = LayoutInflater.from(mContext);

        this.orderId = orderId;
        this.mOrderId = mOrderId;

    }


    @Override
    public int getCount() {
        return order_list.size();
    }

    @Override
    public Object getItem(int i) {
        return order_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        if (convertview == null) {
            viewHolder = new ViewHolder();
            convertview = inflater.inflate(R.layout.item_order_list1, null);
            viewHolder.itemImg = (ImageView) convertview.findViewById(R.id.item_order_img);
            viewHolder.itemOrderName = (TextView) convertview.findViewById(R.id.item_order_name);
            viewHolder.itemOrderPrice = (TextView) convertview.findViewById(R.id.item_order_price);
            viewHolder.itemOrderFabric = (TextView) convertview.findViewById(R.id.item_order_fabric);
            viewHolder.textNum = (TextView) convertview.findViewById(R.id.num_text);
            viewHolder.footView = (RelativeLayout) convertview.findViewById(R.id.foot_view);
            viewHolder.totalNum = (TextView) convertview.findViewById(R.id.tv_num);
            viewHolder.searchState = (Button) convertview.findViewById(R.id.search_mater);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        final String imgUrl = order_list.get(position).getImage().getSmall();
        if (!StringUtil.isEmpty(imgUrl)) {
            Picasso.with(mCotnext)
                    .load(imgUrl)
                    .into(viewHolder.itemImg);
        }

        String price = order_list.get(position).getDesc();
        int pos = price.indexOf("\n");
        for (int i = 0; i < price.length(); i++) {

            desc = price.substring(0, pos);
            priceName = price.substring(pos + 1, price.length() - 1);
        }


        desc = desc.replace(",", "");
        viewHolder.itemOrderName.setText(order_list.get(position).getName());
        viewHolder.itemOrderPrice.setText(priceName);
        viewHolder.itemOrderFabric.setText(desc);


        number = order_list.get(position).getNumber();
        viewHolder.textNum.setText("X" + number + "");
        count = count + number;

        if (position == order_list.size() - 1) {
            viewHolder.footView.setVisibility(View.VISIBLE);

            viewHolder.totalNum.setText("共" + count / 2 + "件");
        }


        viewHolder.searchState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchState(count / 2, imgUrl);
            }
        });


        return convertview;
    }

    /**
     * 查看物流
     */

    private void searchState(int number, String Url) {
        Intent intent = new Intent(mCotnext, MaterialsAct.class);
        intent.putExtra(MaterialsAct.ORDER_ID, orderId);
        intent.putExtra(MaterialsAct.MORDER_ID, mOrderId);
        intent.putExtra(MaterialsAct.NUMBER, number + "");
        intent.putExtra(MaterialsAct.URL, Url);
        mCotnext.startActivity(intent);
    }


    class ViewHolder {

        ImageView itemImg;
        TextView itemOrderName;
        TextView itemOrderPrice;
        TextView itemOrderFabric;
        RelativeLayout footView;
        TextView textNum;
        Button searchState;
        TextView totalNum;
    }
}
