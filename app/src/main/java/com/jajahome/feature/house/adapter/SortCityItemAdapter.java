package com.jajahome.feature.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;
import com.jajahome.model.SortCityModel;

import java.util.List;

/**
 * Created by ${Terry} on 2017/10/18.
 */
public class SortCityItemAdapter extends BaseListAdapter<SortCityModel.DataBean.SortCitysBean.CitysBean> {

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SortCityItemAdapter(Context context, List<SortCityModel.DataBean.SortCitysBean.CitysBean> list) {
        super(context, list);
    }

    @Override
    public View initView(final SortCityModel.DataBean.SortCitysBean.CitysBean citysBean, View convertView, ViewGroup parent) {
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

        holder.ItemCity.setText(citysBean.getName());
        holder.ItemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(citysBean.getName(), citysBean.getId());
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
