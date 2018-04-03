package com.jajahome.feature.user.adapter;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.activity.IntChangeAct;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.ActionModel;
import com.jajahome.model.ExModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.IntegReq;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ExAdapter extends BaseRecyclerAdapter<ExModel.DataBean.ItemBean> {

    private Subscription mSubscription;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ExModel.DataBean.ItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String small = mItemDataList.get(i).getImage().getSmall();
        if (!StringUtil.isEmpty(small)) {
            Picasso.with(getContext()).load(small).placeholder(R.mipmap.newlogo_square).into(holder.imgDesc);
        }
        holder.titleTv.setText(mItemDataList.get(i).getName());
        holder.priceTv.setText("￥" + mItemDataList.get(i).getPrice());
        holder.integerTv.setText("兑换金币" + mItemDataList.get(i).getIntegral());
        holder.timeTv.setText(mItemDataList.get(i).getTime());

        if (mItemDataList.get(i).getFreeShipping().equals("0")) {
            holder.itemLogisticsTv.setText("到付");
        }

        if (mItemDataList.get(i).getTotal().equals("0")) {
            holder.itemNumTv.setText("无限量");
        } else {
            holder.itemNumTv.setText("剩余" + mItemDataList.get(i).getLast_total() + "件");
        }
        holder.termTv.setText(mItemDataList.get(i).getTitle());

        final String discount = mItemDataList.get(i).getDiscount_id();

        holder.itemActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
                builder.setMessage("兑换需要消耗" + mItemDataList.get(i).getIntegral() + "金币");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (StringUtil.isEmpty(address)) {
                            Toast.makeText(getContext(), "收货地址为空，请编辑地址", Toast.LENGTH_SHORT).show();
                        } else {
                            getExAction(discount);
                        }
                    }
                });
                builder.create().show();

            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_ex_integer;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView imgDesc;
        TextView titleTv, priceTv, integerTv, termTv, timeTv, itemNumTv, itemActionTv, itemLogisticsTv;
        //  FreeShipView itemLogisticsTv;

        public ViewHolder(View itemView) {
            super(itemView);

            imgDesc = (ImageView) itemView.findViewById(R.id.ex_img);
            titleTv = (TextView) itemView.findViewById(R.id.ex_title);
            priceTv = (TextView) itemView.findViewById(R.id.ex_item_price);
            integerTv = (TextView) itemView.findViewById(R.id.ex_integer);
            termTv = (TextView) itemView.findViewById(R.id.ex_term);
            timeTv = (TextView) itemView.findViewById(R.id.ex_time);
            itemNumTv = (TextView) itemView.findViewById(R.id.ex_item_num);
            itemActionTv = (TextView) itemView.findViewById(R.id.ex_item_action);
            itemLogisticsTv = (TextView) itemView.findViewById(R.id.ex_item_logistics);

        }
    }

    private int address_id;
    private String address;


    public void setAddressId(int address_id) {
        this.address_id = address_id;
        this.notifyDataSetChanged();
    }


    public void setLocalAddress(String address) {
        this.address = address;
        this.notifyDataSetChanged();
    }


    public void getExAction(String discountId) {

        IntegReq.ContentBean bean = new IntegReq.ContentBean();
        bean.setDiscount_id(Integer.parseInt(discountId));
        bean.setAddress_id(address_id);
        bean.setNumber(1);
        IntegReq req = new IntegReq();
        req.setContentBean(bean);
        req.setCmd(Constant.INTEGRAL_EXCHANGE);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().integral_exchange(requestBody, HttpUtil.getSession(getContext())).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ActionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "此件物品兑换已达上限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ActionModel model) {
                        if (model.getStatus() == 0) {
                            Toast.makeText(getContext(), "兑换成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new EventMessage(IntChangeAct.IntChange, ""));
                        } else {

                        }
                    }
                });
    }

    public void stopSubscription() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
