package com.jajahome.feature.show.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.show.ShowH5DetailAct;
import com.jajahome.feature.show.ShowImgDetailAct;
import com.jajahome.model.ShowListModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 秀家列表的adapter
 */
public class ShowListAdapter extends BaseRecyclerAdapter<ShowListModel.DataBean.ItemBean> {
    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ShowListModel.DataBean.ItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final String img = mItemDataList.get(i).getImage().getSmall();
        if (!StringUtil.isEmpty(img)) {
            Picasso.with(getContext())
                    .load(img)
                    .placeholder(R.mipmap.ic_bottom_background)
                    .into(holder.itemBannerImg);

        } else {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_bottom_background);
        }

        String type = mItemDataList.get(i).getType();

        switch (type) {
            case "0":
                holder.leftImg.setImageResource(R.mipmap.ic_beautiful_pic);
                break;
            case "1":
                holder.leftImg.setImageResource(R.mipmap.ic_small_essay);
                break;
            case "2":
                holder.leftImg.setImageResource(R.mipmap.ic_home_match_icon);
                break;
            case "3":
                holder.leftImg.setImageResource(R.mipmap.ic_home_original_icon);
                break;
        }

        holder.textView.setText(mItemDataList.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //美图
                if (mItemDataList.get(i).getType().equals("0")) {
                    Intent intent = new Intent(mContext, ShowImgDetailAct.class);
                    intent.putExtra(ShowH5DetailAct.SHOW_ID, mItemDataList.get(i).getId());
                    intent.putExtra(ShowImgDetailAct.SHOW_URI, img);
                    intent.putExtra(ShowImgDetailAct.SHOW_TEXT, mItemDataList.get(i).getName() + "");
                    mContext.startActivity(intent);
                } else {
                    //进入显示H5
                    Intent intent = new Intent(mContext, ShowH5DetailAct.class);
                    intent.putExtra(ShowH5DetailAct.TYPE, mItemDataList.get(i).getType());
                    intent.putExtra(ShowH5DetailAct.SHOW_ID, mItemDataList.get(i).getId());
                    intent.putExtra(ShowH5DetailAct.SHOW_URI_H5, img);
                    intent.putExtra(ShowH5DetailAct.SHOW_TEXT_H5, mItemDataList.get(i).getName() + "");
                    mContext.startActivity(intent);
                }

            }
        });
    }

    /**
     * @return
     */
    @Override
    public int getListLayoutId() {
        return R.layout.item_home_list;
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
        ImageView itemBannerImg, leftImg;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBannerImg = (ImageView) findView(R.id.item_img);
            textView = (TextView) findView(R.id.textView);
            leftImg = (ImageView) findView(R.id.img);
        }
    }
}

