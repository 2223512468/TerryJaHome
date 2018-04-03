package com.jajahome.feature.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.YunOrderModel;
import com.jajahome.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class YunOrderAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<YunOrderModel.DataBean.WaybillBean> waybill;
    private String mOrderId;

    /**
     * 运单号集合
     *
     * @param mContext
     * @param waybill
     */

    private HashMap<Integer, String> orderId = new HashMap<Integer, String>();


    public YunOrderAdapter(Context mContext, List<YunOrderModel.DataBean.WaybillBean> waybill, String mOrderId) {

        this.mContext = mContext;
        this.mOrderId = mOrderId;
        this.waybill = new ArrayList<>();
        for (YunOrderModel.DataBean.WaybillBean bean : waybill) {
            this.waybill.add(bean);
        }
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return waybill == null ? 0 : waybill.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        if (waybill == null) return null;
        return waybill.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup parent) {
        TextView searchTextView = null;
        TextView yunOrderTextView = null;

        if (view == null) {
            view = inflater.inflate(R.layout.act_yunorder_item_1, parent, false);
        }
        yunOrderTextView = (TextView) view.findViewById(R.id.yun_order);
        yunOrderTextView.setText("运单号:" + waybill.get(i).getWaybill_number());

        if (!orderId.containsKey(i)) {
            orderId.put(i, waybill.get(i).getWaybill_number() + "");
        }

        return view;
    }

    @Override
    public View getChildView(int grouposition, int childposition, boolean b, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.act_yunorder_item_2, null);
        }

        ListView recycleView = (ListView) convertView.findViewById(R.id.recyclerView);
        recycleView.setDivider(null);
        recycleView.setDividerHeight(0);
        YunOrderDetailAdapter adapter = new YunOrderDetailAdapter(mContext, waybill.get(grouposition).getOrder_list(), orderId.get(grouposition), mOrderId);
        recycleView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(recycleView);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
