package com.jajahome.feature.user.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.RebateInfoModel;
import com.jajahome.util.StringUtil;
import com.jajahome.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 我得订单列表 adapter
 */
public class RebateListAdapter extends BaseRecyclerAdapter<RebateInfoModel.DataBean.RebateInfoBean.RebateListBean> {


    private String partten = "yyyy年mm月dd日 HH:mm:ss";

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, List<RebateInfoModel.DataBean.RebateInfoBean.RebateListBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        String time = mItemDataList.get(i).getTime();
        if (!StringUtil.isEmpty(time)) {
            String dayLast = TimeUtil.getTimeDesc(Long.parseLong(time));
            holder.timeDesc.setText(dayLast);
        }
        int type = mItemDataList.get(i).getType();
        if (type == 0) {
            holder.titleDesc.setText("购买奖励");
            holder.moneyDesc.setText("+" + mItemDataList.get(i).getVariable_amount());
        } else if (type == 1) {
            holder.titleDesc.setText("提现");
            holder.moneyDesc.setText(mItemDataList.get(i).getVariable_amount() + "");
            holder.moneyDesc.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            holder.titleTv.setText("提现");
            holder.titleTv.setBackgroundColor(getContext().getResources().getColor(R.color.background_color));
        }
    }


    @Override
    public int getListLayoutId() {
        return R.layout.act_rebate_item;
    }

    public String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
        }
        return re_time;
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
