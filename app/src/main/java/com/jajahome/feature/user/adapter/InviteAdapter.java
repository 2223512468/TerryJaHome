package com.jajahome.feature.user.adapter;

import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.InviteModel;

import java.util.List;

/**
 * Created by laotang on 2016/8/1.
 */
public class InviteAdapter extends BaseRecyclerAdapter<InviteModel.DataBean.InvitesBean> {
    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<InviteModel.DataBean.InvitesBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv_phone.setText(mItemDataList.get(i).getPhone());
        String time = mItemDataList.get(i).getTime();
        String[] split = time.split(" ");
        holder.tv_time.setText(split[0]);
        holder.tv_much.setText(mItemDataList.get(i).getPay_amount()+"元");
        holder.tv_person.setText(mItemDataList.get(i).getOne_friends()+"人");
        holder.tv_much_two.setText(mItemDataList.get(i).getOne_payAmounts()+"元");

    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_invite_list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        TextView tv_phone, tv_time,tv_much,tv_person,tv_much_two;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_much = (TextView) itemView.findViewById(R.id.tv_much);
            tv_person = (TextView) itemView.findViewById(R.id.tv_person);
            tv_much_two = (TextView) itemView.findViewById(R.id.tv_much_two);
        }
    }
}
