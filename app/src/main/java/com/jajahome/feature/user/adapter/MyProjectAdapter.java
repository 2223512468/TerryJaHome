package com.jajahome.feature.user.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.feature.diy.DiyDetailAct;
import com.jajahome.model.DiyListModel;
import com.jajahome.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 我得方案列表adapter
 */
public class MyProjectAdapter extends BaseRecyclerAdapter<DiyListModel.DataBean.SetBean> {
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
        ViewHolder holder = (ViewHolder) viewHolder;
        DiyListModel.DataBean.SetBean itemBean = mItemDataList.get(i);
        String url = itemBean.getImage().getThumb();
        if (TextUtils.isEmpty(url)) {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_holder_big);
        } else {
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_holder_big)
                    .into(holder.itemBannerImg);
        }

        holder.tvName.setText(mItemDataList.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DiyDetailAct.class);
                intent.putExtra(DiyDetailAct.SET_ID, mItemDataList.get(i).getId());
                intent.putExtra(DiyDetailAct.TYPE, type);
                intent.putExtra(DiyDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                mContext.startActivity(intent);
            }
        });

    }

    /**
     * @return 适配器布局
     */
    @Override
    public int getListLayoutId() {
        return R.layout.item_show_list_collect;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            //先获取屏幕宽度 再设置图片的宽高为屏幕宽度一半
            int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
            itemBannerImg = (ImageView) findView(R.id.item_img);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, (int) (w/ Constant.SET_SCALE));
            itemBannerImg.setLayoutParams(params);
            tvName = (TextView) findView(R.id.tv_name);
        }
    }

}

