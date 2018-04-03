package com.jajahome.feature.user.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.WebAct;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.user.fragment.CouFrag;
import com.jajahome.feature.view.LoginTelDialog;
import com.jajahome.model.GetCouModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.TransModel;
import com.jajahome.model.ValueListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.ConPonReq;
import com.jajahome.network.CouReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.pop.adapter.PopCode;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
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
 * Created by ${Terry} on 2017/9/12.
 */
public class ValueListAdapter extends BaseRecyclerAdapter<ValueListModel.DataBean.CouponItemBean> {

    private int mState;
    private String userType;
    private Subscription mSubscription;

    public ValueListAdapter(int mState, Context mContext) {
        this.mState = mState;
        LoginModle info = LoginUtil.getInfo(mContext);
        if (info != null) {
            userType = info.getData().getUser().getType();
        }
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<ValueListModel.DataBean.CouponItemBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String imgUrl = mItemDataList.get(i).getImage().getUrl();
        if (!StringUtil.isEmpty(imgUrl)) {
            Picasso.with(getContext()).load(imgUrl).placeholder(R.mipmap.newlogo02).into(holder.imgDesc);
        }

        holder.titleTv.setText(mItemDataList.get(i).getName());
        String couponType = mItemDataList.get(i).getCoupon_type();
        if (couponType.equals("D")) {
            holder.priceTv.setText("特惠 " + StringUtil.spilStr(mItemDataList.get(i).getPrice() * 10 + "") + "折");
        } else if (couponType.equals("M")) {
            holder.priceTv.setText("满减 " + Constant.YUAN + StringUtil.spilStr(mItemDataList.get(i).getPrice() + ""));
        } else if (couponType.equals("S")) {
            holder.priceTv.setText("立减 " + Constant.YUAN + StringUtil.spilStr(mItemDataList.get(i).getPrice() + ""));
        } else if (couponType.equals("N")) {
            holder.priceTv.setText("每满减 " + Constant.YUAN + StringUtil.spilStr(mItemDataList.get(i).getPrice() + ""));
        } else {
            holder.priceTv.setText(Constant.YUAN + StringUtil.spilStr(mItemDataList.get(i).getPrice() + ""));
        }

        holder.termTv.setText(mItemDataList.get(i).getTitle());
        holder.timeTv.setText(mItemDataList.get(i).getTime());
        int status = mItemDataList.get(i).getStatus();
        setCanUse(status, holder);
        int lastcount = mItemDataList.get(i).getLastcount();
        if (mState == 0 && (lastcount == -1 || lastcount > 0) && status == 3) {
            p.setColor(getContext().getResources().getColor(R.color.orange_user));
            holder.stateTv.setText("领取");
        }

        int isFree = mItemDataList.get(i).getFreeShipping();
        if (isFree == 0) {
            holder.itemLogisticsTv.setText("到付");
        } else {
            holder.itemLogisticsTv.setText("包邮");
        }

        final int id = mItemDataList.get(i).getId();
        final String code = mItemDataList.get(i).getCode();
        final String actionId = mItemDataList.get(i).getActionId();
        final String action = mItemDataList.get(i).getAction();
        final String setUrl = mItemDataList.get(i).getLinkUrl();

       /* if (userType.equals("3") || userType.equals("4")) {
            if (action.equals("all")) {
                holder.transTv.setVisibility(View.VISIBLE);
            } else {
                holder.transTv.setVisibility(View.GONE);
            }
        } else {
            holder.transTv.setVisibility(View.GONE);
        }
*/

        int transfer = mItemDataList.get(i).getIs_transfer();
        if (transfer == 1 && mState == 1) {
            holder.transTv.setVisibility(View.VISIBLE);
        } else {
            holder.transTv.setVisibility(View.GONE);
        }

        holder.stateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick((TextView) v, ((TextView) v).getText(), id, code, action, actionId);
            }
        });

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(setUrl)) {
                    WebAct.gotoH5(mContext, setUrl);
                }
            }
        });

        holder.transTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action.equals("all")) {
                    final LoginTelDialog.Builder builder = new LoginTelDialog.Builder(mContext);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String msg = builder.getMessage();

                            if (!StringUtil.isEmpty(msg)) {
                                dialog.dismiss();
                                transfer_conpon(msg, mItemDataList.get(i).getId());
                            } else {
                                Toast.makeText(mContext, R.string.input_phone, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }


    private GradientDrawable p, p1;

    private void setCanUse(int status, ViewHolder holder) {
        p = (GradientDrawable) holder.stateTv.getBackground();
        p1 = (GradientDrawable) holder.transTv.getBackground();
        switch (status) {
            case 0:
                p.setColor(getContext().getResources().getColor(R.color.bg_light_gray));
                p1.setColor(getContext().getResources().getColor(R.color.orange_user));
                holder.stateTv.setText("已过期");
                break;
            case 1:
                p.setColor(getContext().getResources().getColor(R.color.orange_user));
                p1.setColor(getContext().getResources().getColor(R.color.orange_user));
                holder.stateTv.setText("去使用");
                break;
            case 2:
                p.setColor(getContext().getResources().getColor(R.color.bg_light_gray));
                p1.setColor(getContext().getResources().getColor(R.color.orange_user));
                holder.stateTv.setText("已使用");
                break;
            case 3:
                p.setColor(getContext().getResources().getColor(R.color.bg_light_gray));
                p1.setColor(getContext().getResources().getColor(R.color.orange_user));
                holder.stateTv.setText("已领取");
                break;
            case 4:
                p.setColor(getContext().getResources().getColor(R.color.orange_user));
                p1.setColor(getContext().getResources().getColor(R.color.orange_user));
                holder.stateTv.setText("领取");
                break;
        }
    }

    private static String GET = "领取";
    private static String USE = "去使用";
    private PopCode mPopupWindow;

    private void onActionClick(TextView v, CharSequence s, int id, String code, String action, String actionId) {
        if (GET.equals(s)) {
            if (StringUtil.isEmpty(code)) {
                okBuy(id, code);
                return;
            }
            mPopupWindow = new PopCode(v, mContext, id, code);
            mPopupWindow.show();
        } else if (USE.equals(s)) {
            if (action.equals("set")) {
                Intent intent = new Intent();
                if (actionId.equals("0")) {
                    intent.setClass(mContext, MainAty.class);
                    intent.putExtra(MainAty.ACTION_CLASS, "set");
                    mContext.startActivity(intent);
                    return;
                }
                intent.setClass(mContext, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, actionId);
                mContext.startActivity(intent);
            } else if (action.equals("item")) {
                Intent intent = new Intent(mContext, ItemDetailAct.class);
                intent.putExtra(ItemDetailAct.ITEM_ID, actionId);
                mContext.startActivity(intent);
            } else if (action.equals("brand") || action.equals("class") || action.equals("all")) {
                Intent intent = new Intent(mContext, MainAty.class);
                intent.putExtra(MainAty.ACTION, actionId);
                intent.putExtra(MainAty.ACTION_CLASS, action);
                mContext.startActivity(intent);
            }

        }
    }

    private void okBuy(int id, String code) {
        CouReq.ContentBean bean = new CouReq.ContentBean();
        bean.setCode(code);
        bean.setId(id);
        CouReq req = new CouReq();
        req.setContent(bean);
        req.setCmd(Constant.GETCOUPON);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().get_coupon(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCouModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        T.showShort(mContext, "领取异常");
                    }

                    @Override
                    public void onNext(GetCouModel model) {
                        T.showShort(mContext, "领取成功");
                        EventBus.getDefault().post(new EventMessage(CouFrag.REFRESH, ""));
                    }
                });
    }


    @Override
    public int getListLayoutId() {
        return R.layout.item_value_integer;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView imgDesc;
        TextView titleTv, priceTv, termTv, timeTv, itemActionTv, itemLogisticsTv, stateTv, transTv;
        LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);

            imgDesc = (ImageView) itemView.findViewById(R.id.ex_img);
            titleTv = (TextView) itemView.findViewById(R.id.ex_title);
            priceTv = (TextView) itemView.findViewById(R.id.ex_item_price);
            termTv = (TextView) itemView.findViewById(R.id.ex_term);
            timeTv = (TextView) itemView.findViewById(R.id.ex_time);
            itemActionTv = (TextView) itemView.findViewById(R.id.ex_item_action);
            itemLogisticsTv = (TextView) itemView.findViewById(R.id.ex_item_logistics);
            stateTv = (TextView) itemView.findViewById(R.id.ex_item_action);
            transTv = (TextView) itemView.findViewById(R.id.ex_trans);
            ll = (LinearLayout) findView(R.id.container);

        }
    }

    private Gson gson = new Gson();

    private void transfer_conpon(String phone, int id) {

        ConPonReq req = new ConPonReq();
        ConPonReq.Content content = new ConPonReq.Content();
        content.setCon_id(id);
        content.setTel(phone);
        req.setContent(content);
        req.setCmd(Constant.transfer_conpon);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));

        ApiImp.get().transfer_conpon(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TransModel model) {
                        if (model.getStatus() == 0) {
                            EventBus.getDefault().post(new EventMessage(CouFrag.REFRESH, ""));
                            Toast.makeText(mContext, "转让成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, model.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void destory() {
        if (mSubscription != null) {
            mSubscription.isUnsubscribed();
            mSubscription = null;
        }
    }
}
