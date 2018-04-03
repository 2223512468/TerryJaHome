package com.jajahome.feature.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;

import java.util.List;

/**
 * Created by liuhaizhu on 2016/12/20.
 */

public class SearchTipsFlowAdapter extends BaseListAdapter<String> {

    public SearchTipsFlowAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View initView(String s, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_search_tips_flow, null);
            holder = new ViewHolder();

            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(s);
        return convertView;
    }

    class ViewHolder {
        TextView mTextView;
    }
}
