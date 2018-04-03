package com.jajahome.feature.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;
import com.jajahome.feature.search.SearchShowAct;

import java.util.Arrays;

/**
 * Created by liuhaizhu on 2017/4/29.
 */

public class AdapterPopSelect extends BaseListAdapter<String> {
    private final String[] filter_item=new String[]{
            "单品","标题","厂家编号","类别"
    };
    private final String[] filter_set=new String[]{
            "套装","标题","厂家编号","类别"
    };
    public static final String[] filter_show=new String[]{
            "大赛","原创","美图","美文"
    };
    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mOnItemSelectListener = onItemSelectListener;
    }

    private OnItemSelectListener mOnItemSelectListener;

    public AdapterPopSelect(Context context,String type) {
        super(context, null);
        switch (type){
            case SearchShowAct.TYPE_ITEM:
                list= Arrays.asList(filter_item);
                break;
            case SearchShowAct.TYPE_SET:
                list= Arrays.asList(filter_set);
                break;
            case SearchShowAct.TYPE_SHOW:
                list= Arrays.asList(filter_show);
                break;
        }
    }

    public String getIndexStr(int position) {
        return list.get(position);
    }

    @Override
    public View initView(final String o, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_search_filter, null);
            holder = new ViewHolder();
            holder.tvCountry = (TextView) convertView.findViewById(R.id.hot_tv_country);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCountry.setText(o);
        holder.tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemSelectListener.onItemSelected(list.indexOf(o));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvCountry;
    }
    public interface OnItemSelectListener {
        void onItemSelected(int index);
    }

}
