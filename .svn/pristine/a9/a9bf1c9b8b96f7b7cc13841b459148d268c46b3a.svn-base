package com.jajahome.feature.user.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.feature.show.ShowH5DetailAct;
import com.jajahome.feature.show.ShowImgDetailAct;
import com.jajahome.model.ShowModel;
import com.jajahome.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by laotang on 2016/7/22.
 */
public class ShowCollectAdapter extends BaseRecyclerAdapter<ShowModel.DataBean.ShowBean> {
    public static String Tag = "ShowCollectAdapter";

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ShowModel.DataBean.ShowBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(mItemDataList.get(i).getName());
        Log.e("TAG", "showData: " + mItemDataList.get(i).getImage().getThumb());
        if (TextUtils.isEmpty(mItemDataList.get(i).getImage().getThumb())) {
            Picasso.with(mContext).load(R.mipmap.ic_holder_big).into(holder.itemBannerImg);
        } else {
            Picasso.with(mContext).load(mItemDataList.get(i).getImage().getThumb()).placeholder(R.mipmap.ic_holder_big).into(holder.itemBannerImg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemDataList.get(i).getType().endsWith("0")) {
                    Intent intent = new Intent(mContext, ShowImgDetailAct.class);
                    intent.putExtra(ShowH5DetailAct.SHOW_ID, mItemDataList.get(i).getId());
                    intent.putExtra(ShowImgDetailAct.SHOW_COLLECT, Tag);
                    intent.putExtra(ShowImgDetailAct.SHOW_DELETECOLLECT, i + "");
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, ShowH5DetailAct.class);
                    intent.putExtra(ShowH5DetailAct.TYPE, mItemDataList.get(i).getType());
                    intent.putExtra(ShowH5DetailAct.SHOW_ID, mItemDataList.get(i).getId());
                    intent.putExtra(ShowH5DetailAct.SHOW_COLLECT, Tag);
                    intent.putExtra(ShowH5DetailAct.SHOW_DELETECOLLECT, i + "");
                    mContext.startActivity(intent);
                }

            }
        });

    }

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
