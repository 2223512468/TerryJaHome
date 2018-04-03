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
 * Created by lhz on 2016/7/19.
 */
public class SingleFilterAdapter extends BaseAdapter {
    public OnItemClick getListener() {
        return listener;
    }

    public void setListener(OnItemClick listener) {
        this.listener = listener;
    }

    private OnItemClick listener;
    //set
    public static final int ACTION_SET_ROOM = 1; //空间
    public static final int ACTION_SET_STYLE = 2; //风格
    public static final int ACTION_SET_COLOR = 3; //色系
    public static final int ACTION_SET_PRICE = 4; //价格
    //item
    public static final int ACTION_ITEM_STYLE = 6; //风格
    public static final int ACTION_ITEM_COLOR = 7; //色系
    public static final int ACTION_ITEM_PRICE = 8; //价格
    List<String> mList;
    private ConfigModel mConfigModel;
    private int mAction;
    private int[] bitmap = new int[]{R.mipmap.ic_filter_blue, R.mipmap.ic_filter_cai, R.mipmap.ic_filter_coffee,
            R.mipmap.ic_filter_gold, R.mipmap.ic_filter_gray, R.mipmap.ic_filter_green,
            R.mipmap.ic_filter_mi, R.mipmap.ic_filter_zi, R.mipmap.ic_filter_orange,
            R.mipmap.ic_filter_red, R.mipmap.ic_filter_white_or_black, R.mipmap.ic_filter_yellow,
            R.mipmap.ic_nocolor
    };

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
    private final String FLAG = "-";


    public SingleFilterAdapter(Context context, ConfigModel configModel, int action) {
        this.mAction = action;
        this.mConfigModel = configModel;
        this.mContext = context;
        mColorBlack = ContextCompat.getColor(context, R.color.text_black);
        mColorYellow = ContextCompat.getColor(context, R.color.orange_ll);

        setConfigList();

    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else
            return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (mAction == ACTION_SET_COLOR || mAction == ACTION_ITEM_COLOR) {
                LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
                convertView = mInflater.inflate(R.layout.item_filter_color, null);
                holder = new ViewHolder();
                holder.tvFilter = (TextView) convertView.findViewById(R.id.tvFilter);
                holder.ivFilter = (ImageView) convertView.findViewById(R.id.ivFilter);
                convertView.setTag(holder);
            } else {
                LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
                convertView = mInflater.inflate(R.layout.item_filter, null);
                holder = new ViewHolder();
                holder.tvFilter = (TextView) convertView.findViewById(R.id.tvFilter);
                convertView.setTag(holder);
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mAction == ACTION_SET_COLOR && holder.ivFilter != null || mAction == ACTION_ITEM_COLOR) {
            colorShow(position, holder.ivFilter);
            holder.tvFilter.setText(mList.get(position));
        } else {
            holder.tvFilter.setText(mList.get(position));
        }

        if (position == mCurrentIndex) {
            holder.tvFilter.setTextColor(mColorYellow);

        } else {
            holder.tvFilter.setTextColor(mColorBlack);

        }

        holder.tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = position;
                notifyDataSetChanged();
                if (listener != null)
                    listener.onItemClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvFilter;
        ImageView ivFilter;
    }

    public interface OnItemClick {
        void onItemClick(int p);
    }

    private void setConfigList() {
        mList = new ArrayList<>();
        mList.clear();
        mList.add("全部");
        switch (mAction) {
            case ACTION_SET_ROOM:
                for (ConfigModel.DataBean.ConfigBean.SetRoomBean bean : mConfigModel.getData().getConfig().getSet_room()) {
                    mList.add(bean.getText());
                }
                break;
            case ACTION_SET_STYLE:
                for (ConfigModel.DataBean.ConfigBean.SetStyleBean bean : mConfigModel.getData().getConfig().getSet_style()) {
                    mList.add(bean.getText());
                }
                break;
            case ACTION_SET_COLOR:
                for (ConfigModel.DataBean.ConfigBean.SetColorBean bean : mConfigModel.getData().getConfig().getSet_color()) {
                    mList.add(bean.getText());
                }
                break;
            case ACTION_SET_PRICE:
                for (ConfigModel.DataBean.ConfigBean.SetPriceBean bean : mConfigModel.getData().getConfig().getSet_price()) {
                    mList.add(bean.getMin() + FLAG + bean.getMax());
                }
                break;
            case ACTION_ITEM_STYLE:
                for (ConfigModel.DataBean.ConfigBean.SetStyleBean bean : mConfigModel.getData().getConfig().getSet_style()) {
                    mList.add(bean.getText());
                }
                break;
            case ACTION_ITEM_COLOR:
                for (ConfigModel.DataBean.ConfigBean.ItemColorBean bean : mConfigModel.getData().getConfig().getItem_color()) {
                    mList.add(bean.getText());
                }
                break;
            case ACTION_ITEM_PRICE:
                for (ConfigModel.DataBean.ConfigBean.ItemPriceBean bean : mConfigModel.getData().getConfig().getItem_price()) {
                    mList.add(bean.getMin() + FLAG + bean.getMax());
                }
                break;
        }
    }

/*色块*/

    public void colorShow(int position, ImageView colorIv) {
        String color = mList.get(position);

        if (color.equals("全部") && position == 0) {
            colorIv.setVisibility(View.INVISIBLE);
        } else if (color.equals("蓝色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[0]);
        } else if (color.equals("彩色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[1]);
        } else if (color.equals("咖啡色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[2]);
        } else if (color.equals("金属色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[3]);
        } else if (color.equals("灰色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[4]);
        } else if (color.equals("绿色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[5]);
        } else if (color.equals("米色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[6]);
        } else if (color.equals("紫色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[7]);
        } else if (color.equals("橙色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[8]);
        } else if (color.equals("红色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[9]);
        } else if (color.equals("黑白")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[10]);
        } else if (color.equals("黄色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[11]);
        } else if (color.equals("透明色")) {
            colorIv.setVisibility(View.VISIBLE);
            colorIv.setImageResource(bitmap[12]);
        } else {
            colorIv.setVisibility(View.INVISIBLE);
        }
    }
}

