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
 * 单品种类的选择框adapter
 */
public class ItemBrandFilterAdapter extends BaseAdapter {
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
    public int mIndex2 = -1; //z
    public int mIndex3 = -1; //第三级 选择的item  index

    List<ConfigModel.DataBean.ConfigBean.ItemBrandBean> mList1;
    List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean> mList2;
    List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans> mList3;
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


    public ItemBrandFilterAdapter(Context context, ConfigModel configModel) {
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
            case 3:
                if (mList3 == null) return 0;
                else return mList3.size();
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
                } else {
                    StringBuilder sb = new StringBuilder();
                    mList2 = mList1.get(position).getItems();

                    for (int i = 0; i < mList2.size(); i++) {
                        String name = mList2.get(i).getText();
                        if (name.equals(mList1.get(position).getText())) {
                        } else {
                            sb.append(name + " ");
                        }
                    }
                    holder.tvFilter.setText(mList1.get(position).getText());
                    holder.tvDetail.setText(sb);
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
              /*  String sub_item = mList2.get(position).getSub_item();
                if (!StringUtil.isEmpty(sub_item)) {
                    holder.tvDetail.setText(sub_item);
                }*/
                StringBuilder sb = new StringBuilder();
                if (mList2 != null && mList2.size() > 0) {
                    List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans> items = mList2.get(position).getItems();

                    if (items != null && items.size() > 0) {
                        for (int i = 0; i < items.size(); i++) {
                            String text = items.get(i).getText();
                            sb.append(text);
                        }
                    }
                }
                holder.tvDetail.setText(sb);

                break;
            case 3:
                if (position == mIndex3) {
                    holder.tvFilter.setTextColor(mColorYellow);
                } else {
                    holder.tvFilter.setTextColor(mColorBlack);
                }
                holder.tvFilter.setText(mList3.get(position).getText());
                if (position == 0) {
                    holder.tvDetail.setText(mList2.get(mIndex2).getSub_item());
                } else {
                    holder.tvDetail.setText("");
                }

                break;
        }
        if (position == 0 || !hasNextLevel(position)) {
            holder.imgFlag.setVisibility(View.INVISIBLE);
        } else {
            holder.imgFlag.setVisibility(View.VISIBLE);
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
     * 该条目是否还有下一级
     *
     * @param index index
     * @return 是否
     */
    private boolean hasNextLevel(int index) {
        switch (mCurrentLevel) {
            case 1:
                List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean> list2 = mList1.get(index).getItems();
                return list2 != null && list2.size() > 0;
            case 2:
                if (mList2.size() <= index) {
                    return false;
                }
                List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans> list3 = mList2.get(index).getItems();
                return list3 != null && list3.size() > 0;
            case 3:
                return false;
        }
        return false;
    }

    /**
     * 弹窗消失  数据回到第一层
     */

    public void clear() {
        mCurrentLevel = 1;
        mIndex2 = -1;
        mIndex3 = -1;
        notifyDataSetChanged();
    }

    private void itemClicked(final int position) {
        switch (mCurrentLevel) {
            case 1:

                if (position == 0) {
                    mIndex1 = 0;
                    listener.onItemClick(-1, "全部");
                } else if (hasNextLevel(position)) {
                    mIndex1 = position;
                    mCurrentLevel = 2;
                    setConfigList();
                    notifyDataSetChanged();
                } else {
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean bean = mList1.get(mIndex1);
                    listener.onItemClick(bean.getId(), bean.getText());
                }
                break;
            case 2:

                mIndex2 = position;
                if (position == 0) {
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean bean = mList1.get(mIndex1);
                    listener.onItemClick(bean.getId(), bean.getText());

                } else if (hasNextLevel(position)) {
                    mIndex2 = position;
                    mCurrentLevel = 3;
                    setConfigList();
                    notifyDataSetChanged();
                } else {
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean itemsBean = mList2.get(mIndex2);
                    listener.onItemClick(itemsBean.getId(), itemsBean.getText());
                }

                break;
            case 3:
                //第三层 被点击
                if (position == 0) {
                    //第一个（全部3级）
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean bean = mList2.get(mIndex2);
                    listener.onItemClick(bean.getId(), bean.getText());
                } else {
                    mIndex3 = position;
                    if (mList3.size() > position) {
                        ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans bean = mList3.get(position);
                        listener.onItemClick(bean.getId(), bean.getText());
                    }
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
                mList1 = mConfigModel.getData().getConfig().getItem_brand();
                ConfigModel.DataBean.ConfigBean.ItemBrandBean bean = new ConfigModel.DataBean.ConfigBean.ItemBrandBean();
                bean.setText("全部");
                mList1.add(0, bean);
                break;
            case 2:
                mList2 = mList1.get(mIndex1).getItems();
                if (mList2 != null && mList2.size() > 0 && mList2.get(0).getId() != -1) {
                    //未曾添加过“全部"
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean itemsBean = new ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean();
                    itemsBean.setText(mList1.get(mIndex1).getText());
                    itemsBean.setId(-1);
                    mList2.add(0, itemsBean);
                }
                break;
            case 3:
                if (mList2 == null || mList2.size() == 0) {
                    return;
                }
                mList3 = mList2.get(mIndex2).getItems();
                if (mList3 != null && mList3.get(0).getId() != -1) {
                    ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans itemsBean = new ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans();
                    itemsBean.setText(mList2.get(mIndex2).getText());
                    itemsBean.setId(-1);
                    mList3.add(0, itemsBean);
                }

                break;
        }
    }

}

