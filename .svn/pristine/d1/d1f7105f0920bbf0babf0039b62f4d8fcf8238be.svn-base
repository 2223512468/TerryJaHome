package com.jajahome.feature.user.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.show.ShowH5DetailAct;
import com.jajahome.model.UserLogModel;
import com.jajahome.util.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 收藏的单品adapter
 */
public class UserLogAdapter extends BaseRecyclerAdapter<UserLogModel.DataBean.ItemBean> {
    public static String Tag = "ItemCollectAdapter";
    private View.OnLayoutChangeListener mListener;
    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<UserLogModel.DataBean.ItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        UserLogModel.DataBean.ItemBean itemBean = mItemDataList.get(i);
        String day = TimeUtil.getTime(Long.parseLong(itemBean.getTime()));
        if (i == 0) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(day);
        } else {
            String dayLast = TimeUtil.getTime(Long.parseLong(mItemDataList.get(i - 1).getTime()));
            if (day.equals(dayLast)) {
                holder.tvTime.setVisibility(View.GONE);
            } else {
                holder.tvTime.setText(day);
                holder.tvTime.setVisibility(View.VISIBLE);
            }
        }
        if (TextUtils.isEmpty(itemBean.getImage().getThumb())) {
            holder.img.setImageResource(R.mipmap.ic_holder_big);
        } else {
            Picasso.with(getContext())
                    .load(itemBean.getImage().getThumb())
                    .placeholder(R.mipmap.ic_holder_big)
                    .into(holder.img);
        }
        if (itemBean.getAction().equals("set") || itemBean.getAction().equals("item")) {
            if (itemBean.getPrice().equals("0.00")) {
                holder.tvPrice.setText("请询价");
            } else
                holder.tvPrice.setText(Constant.YUAN+itemBean.getPrice());
        } else {
            holder.tvPrice.setText("");
        }
        holder.tvName.setText(itemBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = mItemDataList.get(i).getAction();
                String actionId = mItemDataList.get(i).getAction_id();
                if (action.equals("set")) {
                    //进入套装
                    Intent intent = new Intent(mContext, SetDetailAct.class);
                    intent.putExtra(SetDetailAct.SET_ID, actionId);
                    intent.putExtra(SetDetailAct.SET_URL, mItemDataList.get(i).getImage().getThumb());
                    mContext.startActivity(intent);
                } else if (action.equals("item")) {
                    //进入单品
                    Intent intent = new Intent(mContext, ItemDetailAct.class);
                    intent.putExtra(ItemDetailAct.ITEM_ID, actionId);
                    mContext.startActivity(intent);
                } else if (action.equals("show")) {
                    //进入秀家
                    Intent intent = new Intent(mContext, ShowH5DetailAct.class);
                    intent.putExtra(ShowH5DetailAct.SHOW_ID, actionId);
                    mContext.startActivity(intent);
                }
                ;
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_user_log;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvTime;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) findView(R.id.item_order_img);
            tvName = (TextView) findView(R.id.item_name);
            tvTime = (TextView) findView(R.id.item_time);
            tvPrice = (TextView) findView(R.id.item_other);
        }
    }
}
