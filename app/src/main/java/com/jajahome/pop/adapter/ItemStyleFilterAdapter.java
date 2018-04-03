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

import java.util.List;

/**
 * 单品风格的选择框adapter
 */
public class ItemStyleFilterAdapter extends BaseAdapter {
    public OnItemClick getListener() {
        return listener;
    }

    public int getmCurrentLevel() {
        return mCurrentLevel;
    }

    public void setmCurrentLevel(int mCurrentLevel) {
        this.mCurrentLevel = mCurrentLevel;
    }

    public int mCurrentLevel = 1;//当前分类层次 ，默认1

    public void setListener(OnItemClick listener) {
        this.listener = listener;
    }

    private OnItemClick listener;
    //item
    public int mIndex1 = 0; //
    public int mIndex2 = -1; //
    List<ConfigModel.DataBean.ConfigBean.ItemStyleBean> mList1;
    List<ConfigModel.DataBean.ConfigBean.ItemStyleBean.ItemsBean> mList2;
    private ConfigModel mConfigModel;

    public int getmCurrentIndex() {
        return mCurrentIndex;
    }

    public void setmCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
    }

    private int mCurrentIndex;
    private int mColorBlack;
    private int mColorYellow;
    private Context mContext;

    public ItemStyleFilterAdapter(Context context, ConfigModel configModel) {
        this.mConfigModel = configModel;
        this.mContext = context;
        mColorBlack = ContextCompat.getColor(context, R.color.text_black);
        mColorYellow = ContextCompat.getColor(context, R.color.orange_ll);
        setConfigList();
    }

    @Override
    public int getCount() {
        switch (mCurrentLevel) {
            case 1:
                if (mList1 == null) return 0;
                else return mList1.size();
            case 2:
                if (mList2 == null) return 0;
                else return mList2.size();
        }
        return 0;
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
            holder.tvDetail = (TextView) convertView.findViewById(R.id.tv_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (mCurrentLevel) {
            case 1:
                if (position == 0) {
                    holder.tvFilter.setText(mList1.get(position).getText());
                    holder.tvDetail.setText("");
                    holder.imgFlag.setVisibility(View.INVISIBLE);
                } else {

                    StringBuilder sb = new StringBuilder();
                    mList2 = mList1.get(position).getItems();
                    if (mList2 != null && mList2.size() > 0) {
                        for (int i = 0; i < mList2.size(); i++) {

                            String name = mList2.get(i).getText();
                            if (name.equals(mList1.get(position).getText())) {

                            } else {
                                sb.append(name + " ");
                            }

                        }

                        holder.tvFilter.setText(mList1.get(position).getText());
                        holder.tvDetail.setText(sb);
                        holder.imgFlag.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvDetail.setText("");
                        holder.imgFlag.setVisibility(View.INVISIBLE);
                    }
                }
                if (position == mIndex1) {
                    holder.tvFilter.setTextColor(mColorYellow);
                } else {
                    holder.tvFilter.setTextColor(mColorBlack);
                }
                holder.tvFilter.setText(mList1.get(position).getText());
                break;
            case 2:
                if (position == mIndex2) {
                    holder.tvFilter.setTextColor(mColorYellow);
                } else {
                    holder.tvFilter.setTextColor(mColorBlack);
                }
                holder.tvFilter.setText(mList2.get(position).getText());
                holder.tvDetail.setText("");
                holder.imgFlag.setVisibility(View.INVISIBLE);
                break;
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
     * 弹窗消失  数据回到第一层
     */

    public void clear() {
        mCurrentLevel = 1;
        mIndex2 = -1;
        notifyDataSetChanged();
    }

    private void itemClicked(final int position) {
        switch (mCurrentLevel) {
            case 1:
                if (position != 0) {
                    mIndex1 = position;
                    if (mList1.get(position).getItems() == null) {
                        //没有二级的数据
                        listener.onItemClick(mList1.get(position).getId(), mList1.get(position).getText());
                    } else {
                        mCurrentLevel = 2;
                        setConfigList();
                    }
                    notifyDataSetChanged();
                } else {
                    listener.onItemClick(-1, "全部");
                }
                break;
            case 2:
                mIndex2 = position;
                if (position == 0) {
                    ConfigModel.DataBean.ConfigBean.ItemStyleBean bean = mList1.get(mIndex1);
                    listener.onItemClick(bean.getId(), bean.getText());
                } else {
                    ConfigModel.DataBean.ConfigBean.ItemStyleBean.ItemsBean itemsBean = mList2.get(mIndex2);
                    listener.onItemClick(itemsBean.getId(), itemsBean.getText());
                }
                break;
        }
    }

    public void pressBack() {
        mCurrentLevel = mCurrentLevel - 1;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tvFilter, tvDetail;
        ImageView imgFlag;
    }

    public interface OnItemClick {
        void onItemClick(int id, String text);
    }

    /**
     * 填充数据
     */
    private void setConfigList() {
        switch (mCurrentLevel) {
            case 1:
                mList1 = mConfigModel.getData().getConfig().getItem_style();
                ConfigModel.DataBean.ConfigBean.ItemStyleBean bean = new ConfigModel.DataBean.ConfigBean.ItemStyleBean();
                bean.setText("全部");
                mList1.add(0, bean);
                break;
            case 2:
                mList2 = mList1.get(mIndex1).getItems();
                if (mList2 != null && mList2.size() > 0 && mList2.get(0).getId() != -1) {
                    //未曾添加过“全部"
                    ConfigModel.DataBean.ConfigBean.ItemStyleBean.ItemsBean itemsBean = new ConfigModel.DataBean.ConfigBean.ItemStyleBean.ItemsBean();
                    itemsBean.setText(mList1.get(mIndex1).getText());
                    itemsBean.setId(-1);
                    mList2.add(0, itemsBean);

                }
                break;
        }
    }

}

