package com.jajahome.feature.diy.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.diy.DiyDetailAct;
import com.jajahome.feature.set.view.SetImageView;
import com.jajahome.model.DiyListModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DIY列表adapter
 */
public class DiyListAdapter extends BaseRecyclerAdapter<DiyListModel.DataBean.SetBean> {
    private int w;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<DiyListModel.DataBean.SetBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        DiyListModel.DataBean.SetBean itemBean = mItemDataList.get(i);
        String url = itemBean.getImage().getThumb();
        if (!StringUtil.isEmpty(url)) {
            Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_bottom_background).into(holder.itemBannerImg);
        } else {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_bottom_background);
        }
        if (setImageViewMap != null && !setImageViewMap.containsKey(i)) {
            setImageViewMap.put(i, holder.itemBannerImg);
        }
        holder.categoryTv.setText(mItemDataList.get(i).getCategory());
        holder.nameTv.setText(mItemDataList.get(i).getName());
        holder.tipTv.setText(mItemDataList.get(i).getTips());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DiyDetailAct.class);
                intent.putExtra(DiyDetailAct.SET_ID, mItemDataList.get(i).getId());
                intent.putExtra(DiyDetailAct.TYPE, type);
                intent.putExtra(DiyDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                intent.putExtra(DiyDetailAct.DIY_TIP, mItemDataList.get(i).getTips());
                mContext.startActivity(intent);
            }
        });

    }

    private Map<Integer, SetImageView> setImageViewMap = new HashMap<>();

    public Map<Integer, SetImageView> getSetImageViewMap() {
        return setImageViewMap;
    }

    /**
     * @return 适配器布局
     */
    @Override
    public int getListLayoutId() {
        return R.layout.act_set_item_frag;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        SetImageView itemBannerImg;
        TextView tipTv, categoryTv, nameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            w = (int) DensityUtil.getDisplayWidthDp(mContext);
            itemBannerImg = (SetImageView) findView(R.id.item_img);
            tipTv = (TextView) findView(R.id.tips);
            categoryTv = (TextView) findView(R.id.category);
            nameTv = (TextView) findView(R.id.name);

        }
    }

}

