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
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.model.SetListModel;
import com.jajahome.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 收藏的套装adapter
 */
public class SetCollectAdapter extends BaseRecyclerAdapter<SetListModel.DataBean.SetBean> {

    public static String Tag = "SetCollectAdapter";

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<SetListModel.DataBean.SetBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        SetListModel.DataBean.SetBean setBean = mItemDataList.get(i);
        if (TextUtils.isEmpty(setBean.getImage().getThumb())) {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_holder_big);
        } else {
            Picasso.with(getContext())
                    .load(setBean.getImage().getThumb())
                    .placeholder(R.mipmap.ic_holder_big)
                    .into(holder.itemBannerImg);
        }

        holder.tvName.setText(setBean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, mItemDataList.get(i).getId());
                intent.putExtra(SetDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                intent.putExtra(SetDetailAct.SET_COLLECT, Tag);
                intent.putExtra(SetDetailAct.SET_DELETECOLLECT,i+"");
                mContext.startActivity(intent);
            }
        });

    }

    /**
     * @return
     */
    @Override
    public int getListLayoutId() {
        return R.layout.item_show_list_collect;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            int w = (int) DensityUtil.getDisplayWidthDp(mContext) / 2;
            itemBannerImg = (ImageView) findView(R.id.item_img);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, (int) (w / Constant.SET_SCALE));
            itemBannerImg.setLayoutParams(params);
            tvName = (TextView) findView(R.id.tv_name);
        }
    }
}
