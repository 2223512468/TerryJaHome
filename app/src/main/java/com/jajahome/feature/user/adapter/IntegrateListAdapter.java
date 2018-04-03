package com.jajahome.feature.user.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.IntegralModel;
import com.jajahome.util.TimeUtil;

import java.util.List;

/**
 * 我得订单列表 adapter
 */
public class IntegrateListAdapter extends BaseRecyclerAdapter<IntegralModel.DataBean.IntegralInfoBean.IntegralListBean> {


    private String partten = "yyyy年mm月dd日 HH:mm:ss";

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, List<IntegralModel.DataBean.IntegralInfoBean.IntegralListBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        String dayLast = TimeUtil.getTimeDesc(Long.parseLong(mItemDataList.get(i).getTime()));
        holder.timeDesc.setText(dayLast);

        String type = mItemDataList.get(i).getType();
        if (type.equals("1")) {
            holder.titleDesc.setText("购买金币");
            holder.moneyDesc.setText("+" + mItemDataList.get(i).getVariable_amount());
            holder.titleTv.setText("购买金币");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.buy));
            holder.moneyDesc.setTextColor(getContext().getResources().getColor(R.color.text_gray));
        } else if (type.equals("2")) {
            holder.titleDesc.setText("签到金币");
            holder.moneyDesc.setText("+" + mItemDataList.get(i).getVariable_amount());
            holder.titleTv.setText("签到金币");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.rebate_qiandao));
            holder.moneyDesc.setTextColor(getContext().getResources().getColor(R.color.text_gray));
        } else if (type.equals("100")) {
            holder.titleDesc.setText("兑换金币");
            holder.moneyDesc.setText("" + mItemDataList.get(i).getVariable_amount());
            holder.moneyDesc.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            holder.titleTv.setText("兑换金币");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.background_color));
        } else if (type.equals("3")) {
            holder.titleDesc.setText("注册金币");
            holder.moneyDesc.setText("+" + mItemDataList.get(i).getVariable_amount());
            holder.titleTv.setText("注册金币");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.regist_gold));
        } else if (type.equals("4")) {
            holder.titleDesc.setText("消费金币");
            holder.moneyDesc.setText("" + mItemDataList.get(i).getVariable_amount());
            holder.moneyDesc.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            holder.titleTv.setText("消费金币");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.background_color));
        }
    }


    @Override
    public int getListLayoutId() {
        return R.layout.act_integrate_item;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {

        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        private ImageView img;
        private TextView timeDesc, moneyDesc, titleDesc, titleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            timeDesc = (TextView) itemView.findViewById(R.id.time_desc);
            moneyDesc = (TextView) itemView.findViewById(R.id.money_desc);
            titleDesc = (TextView) itemView.findViewById(R.id.title_desc);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
        }
    }
}
