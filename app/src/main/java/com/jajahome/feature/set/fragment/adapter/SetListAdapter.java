package com.jajahome.feature.set.fragment.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.set.view.SetImageView;
import com.jajahome.model.SetListModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单品列表adapter
 */
public class SetListAdapter extends BaseRecyclerAdapter<SetListModel.DataBean.SetBean> {

    private String Url;
    private int w;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetListModel.DataBean.SetBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        SetListModel.DataBean.SetBean itemBean = mItemDataList.get(i);
        String url = itemBean.getImage().getThumb();
        Url = url;
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
                Intent intent = new Intent(mContext, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, mItemDataList.get(i).getId());
                intent.putExtra(SetDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                intent.putExtra(SetDetailAct.SET_IMG, Url);
                intent.putExtra(SetDetailAct.SET_TIP, mItemDataList.get(i).getTips());
                mContext.startActivity(intent);
            }
        });

    }

    private Map<Integer, SetImageView> setImageViewMap = new HashMap<>();

    public Map<Integer, SetImageView> getSetImageViewMap() {
        return setImageViewMap;
    }

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

