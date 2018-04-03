package com.jajahome.pop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.ConfigModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐种类的选择框adapter
 */
public class SetBrandFilterAdapter extends BaseAdapter {
    public OnItemClick getListener() {
        return listener;
    }

    public void setListener(OnItemClick listener) {
        this.listener = listener;
    }

    private OnItemClick listener;
    //item
    public int mIndex = 0; //当前选择
    List<ConfigModel.DataBean.ConfigBean.SetBrandBean> mList1;
    private ConfigModel mConfigModel;
    private int mColorBlack;
    private int mColorYellow;
    private Context mContext;

    public SetBrandFilterAdapter(Context context, ConfigModel configModel) {
        this.mConfigModel = configModel;
        this.mContext = context;
        mColorBlack = ContextCompat.getColor(context, R.color.text_black);
        mColorYellow = ContextCompat.getColor(context, R.color.orange_ll);
        setConfigList();
    }

    @Override
    public int getCount() {
        if (mList1 == null) return 0;
        else return mList1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_filter_muti, null);
            holder = new ViewHolder();
            holder.tvFilter = (TextView) convertView.findViewById(R.id.tv_filter);
            holder.imgFlag = (ImageView) convertView.findViewById(R.id.img_filter);
            holder.imgFlag.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvFilter.setText(mList1.get(position).getText());
        if (position == mIndex) {
            holder.tvFilter.setTextColor(mColorYellow);
        } else {
            holder.tvFilter.setTextColor(mColorBlack);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked(position);
            }
        });
        return convertView;
    }

    /**
     * 分类列表被点击
     *
     * @param position
     */
    private void itemClicked(final int position) {
        mIndex=position;
        if (position != 0) {
            listener.onItemClick(mList1.get(mIndex).getId(), mList1.get(mIndex).getText());
        } else {
            listener.onItemClick(-1, "全部");
        }
    }

    class ViewHolder {
        TextView tvFilter;
        ImageView imgFlag;
    }

    public interface OnItemClick {
        void onItemClick(int id, String text);
    }

    /**
     * 填充数据
     */
    private void setConfigList() {
        mList1=new ArrayList<>();
        ConfigModel.DataBean.ConfigBean.SetBrandBean bean = new ConfigModel.DataBean.ConfigBean.SetBrandBean();
        bean.setText("全部");
        mList1.add(0, bean);
        for(ConfigModel.DataBean.ConfigBean.SetBrandBean brandBean: mConfigModel.getData().getConfig().getSet_brand()){
            mList1.add(brandBean);
        }
    }

}

