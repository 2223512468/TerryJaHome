package com.jajahome.feature.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;
import com.jajahome.model.BuildListModel;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/11.
 */
public class SortBuildItemAdapter extends BaseListAdapter<BuildListModel.DataBean.SortBuildingsBean.BuildingsBean> {

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SortBuildItemAdapter(Context context, List<BuildListModel.DataBean.SortBuildingsBean.BuildingsBean> list) {
        super(context, list);
    }

    @Override
    public View initView(final BuildListModel.DataBean.SortBuildingsBean.BuildingsBean buildingsBean, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.act_sort_city_item_item, null);
            holder = new ViewHolder();
            holder.ItemCity = (TextView) convertView.findViewById(R.id.cityItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ItemCity.setText(buildingsBean.getName());
        holder.ItemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(buildingsBean.getName(), buildingsBean.getId());
                }
            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView ItemCity;
    }

    interface OnItemClickListener {
        void OnItemClick(String text, String id);
    }

}
