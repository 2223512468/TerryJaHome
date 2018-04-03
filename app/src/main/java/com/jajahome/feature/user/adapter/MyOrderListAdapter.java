package com.jajahome.feature.user.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.user.activity.OrderDetailAct;
import com.jajahome.feature.user.activity.YunOrderAct;
import com.jajahome.feature.user.fragment.OrderFrag;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.feature.view.LoginKnownDialog;
import com.jajahome.model.ExitMoneyModel;
import com.jajahome.model.OrderDetailModel;
import com.jajahome.model.OrderListModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.DetailReq;
import com.jajahome.network.ExitOrderReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.AutoListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我得订单列表 adapter
 */
public class MyOrderListAdapter extends BaseRecyclerAdapter<OrderListModel.DataBean.ListBean> {

    private Subscription mSubscription;
    public final static String CONTACT_SELLER = "联系卖家";
    public final static String PAY = "付款";
    public final static String CLOSE_ORDER = "取消订单";
    public final static String ORDER_RECIVE = "确认收货";
    public final static String EXIT_MONEY = "申请退款";
    public final static String order_refund_apply = "order_refund_apply";
    public final static String order_refund_confirm = "order_refund_confirm";
    public final static String SEARCH_STATE = "查看物流";
    public final static String GOON_PAY = "继续付款";
    public final static String PAY_ORDER = "支付订单";
    public final static String DELETE_ORDER = "删除订单";
    private String cityName;


    String fromatNums; //订单编号
    String fromatTotalNum; //件数
    String fromatPayPrice; //实付
    String substring;
    private ExitMoneyModel exitMoneyModel;
    private int flag;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, List<OrderListModel.DataBean.ListBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        OrderListModel.DataBean.ListBean orderListBean = mItemDataList.get(i);
        holder.listView.setAdapter(new OrderListAdapter(mContext, orderListBean.getOrder_list()));
        //订单编号
        holder.tvOrderNums.setText(String.format(fromatNums, orderListBean.getOrder_id()));
        //件数
        holder.tvTotalNums.setText(String.format(fromatTotalNum, getTotalNums(orderListBean.getOrder_list())));


        //实付

        String price = orderListBean.getPrice();
        if (price.contains(".")) {
            int position = price.indexOf(".");
            for (int j = 0; j < price.length(); j++) {
                substring = price.substring(0, position);
            }
        } else {
            substring = price;
        }

        holder.tvPayPrice.setText(String.format(fromatPayPrice, substring));
        /**
         * 设置订单状态
         * 0 - 待处理 ,
         * 1 - 已处理(用户付款成功后,通过此表生成对应物流表中数据) ,
         * 2 - 订单取消(交易关闭)
         * 3 交易完成 ,
         * 4 退款中
         * 5 退款成功(交易关闭)
         * 6 请询价(必须客服改价后才能处理)
         * 7 已发货(待收货)
         */
        switch (orderListBean.getStatus()) {
            case "0":
                holder.tvStatus.setText("等待买家付款");
                //设置显示的文字
                holder.btn01.setText(PAY);
                holder.btn02.setText(CLOSE_ORDER);
                holder.btn03.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.VISIBLE);
                holder.btn03.setVisibility(View.VISIBLE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn03.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                break;

            case "1":
                holder.tvStatus.setText("等待发货");
                //设置显示的文字
                holder.btn01.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.GONE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.VISIBLE);

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));


                break;
            case "2":
                holder.tvStatus.setText("交易关闭");
                holder.btn01.setText(CONTACT_SELLER);
                holder.btn02.setText(DELETE_ORDER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.VISIBLE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                break;
            case "3":
                holder.tvStatus.setText("交易完成");
                //设置显示的文字
                holder.btn01.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.GONE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                break;
            case "4":
                holder.tvStatus.setText("退款中");
                //设置显示的文字
                holder.btn01.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.GONE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.GONE);
                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                break;
            case "5":
                holder.tvStatus.setText("退款成功");
                //设置显示的文字
                holder.btn01.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.GONE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                break;
            case "6":
                holder.tvStatus.setText("等待卖家修改价格");
                //设置显示的文字
                holder.btn01.setText(CONTACT_SELLER);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.VISIBLE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));

                break;
            case "7":
                holder.tvStatus.setText("待收货");
                //设置显示的文字
                holder.btn01.setText(ORDER_RECIVE);
                holder.btn03.setText(CONTACT_SELLER);
                holder.btn02.setText(SEARCH_STATE);
                //设置按钮是否显示
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.VISIBLE);
                holder.btn03.setVisibility(View.VISIBLE);
                holder.btn04.setVisibility(View.GONE);

                //设置BUTTON颜色
                holder.btn02.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));
                //设置BUTTON颜色
                holder.btn03.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));


                break;

            case "8":
                holder.tvStatus.setText("订金已付");
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.VISIBLE);
                holder.btn03.setVisibility(View.VISIBLE);
                holder.btn01.setText(GOON_PAY);
                holder.btn02.setText(EXIT_MONEY);
                holder.btn03.setText(CONTACT_SELLER);
                //设置BUTTON颜色
                holder.btn03.setTextColor(getContext().getResources().getColor(R.color.text_gray_dark));
                //设置BUTTON颜色
                holder.btn01.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                break;
            case "9":
                holder.tvStatus.setText("转帐审核中");
                holder.btn01.setVisibility(View.VISIBLE);
                holder.btn02.setVisibility(View.GONE);
                holder.btn03.setVisibility(View.GONE);
                holder.btn01.setText(CONTACT_SELLER);
                break;


        }
        holder.btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(i, ((TextView) v).getText());
            }
        });
        holder.btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(i, ((TextView) v).getText());
            }
        });
        holder.btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(i, ((TextView) v).getText());
            }
        });
        holder.btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(i, ((TextView) v).getText());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderDetailAct.class);
                intent.putExtra(OrderDetailAct.ORDER_ID, mList.get(i).getOrder_id());
                getContext().startActivity(intent);
            }
        });

        holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long pos) {
                Intent intent = new Intent(getContext(), OrderDetailAct.class);
                intent.putExtra(OrderDetailAct.ORDER_ID, mList.get(i).getOrder_id());
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * 操作按钮被点击
     *
     * @param i    ：第几个条目;
     * @param text ： 按钮上的文字 （通过文字判断动作）
     */
    private void onActionClick(final int i, CharSequence text) {
        if (PAY.equals(text)) {
            //付款
            Intent intent = new Intent(getContext(), OrderDetailAct.class);
            intent.putExtra(OrderDetailAct.ORDER_ID, mList.get(i).getOrder_id());
            intent.putExtra(OrderDetailAct.ORDER_NUM, getTotalNums(mList.get(i).getOrder_list()) + "");

            getContext().startActivity(intent);
        } else if (GOON_PAY.equals(text)) {

            Intent intent = new Intent(getContext(), OrderDetailAct.class);
            intent.putExtra(OrderDetailAct.ORDER_ID, mList.get(i).getOrder_id());
            intent.putExtra(OrderDetailAct.ORDER_NUM, getTotalNums(mList.get(i).getOrder_list()) + "");
            getContext().startActivity(intent);

        } else if (CONTACT_SELLER.equals(text)) {
            //联系卖家
            if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
            } else {
                cityName = "";
            }
            String about_us = Constant.ABOUT_US + "&city=" + cityName;
            H5Act.gotoH5(mContext, about_us, CONTACT_SELLER);

        } else if (ORDER_RECIVE.equals(text)) {
            //确认订单
            LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
            builder.setMessage("是否确认收货?");
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
                    orderRecive(mList.get(i).getOrder_id());
                }
            });
            builder.create().show();

        } else if (CLOSE_ORDER.equals(text)) {
            //取消订单
            showPopWindow(mList.get(i).getOrder_id());

        } else if (EXIT_MONEY.equals(text)) {
            //确认退款
            exitMoney(mList.get(i).getOrder_id(), i);

        } else if (SEARCH_STATE.equals(text)) {
            Intent intent = new Intent(getContext(), YunOrderAct.class);
            intent.putExtra(YunOrderAct.ORDER_ID, mList.get(i).getOrder_id());
            getContext().startActivity(intent);

        } else if (DELETE_ORDER.equals(text)) {

            //删除订单
            LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
            builder.setMessage("是否删除订单?");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String id = mList.get(i).getOrder_id();
                    getDeleteOrder(id);
                }
            });
            builder.create().show();
        }
    }


    private int state;

    private void showPopWindow(final String id) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_cancel_order_pop, null);
        TextView reasonOne = (TextView) rootView.findViewById(R.id.reasonone);
        TextView reasonTwo = (TextView) rootView.findViewById(R.id.reasontwo);
        TextView reasonThree = (TextView) rootView.findViewById(R.id.reasonthree);
        Button disBtn = (Button) rootView.findViewById(R.id.btn_dismiss);
        final PopupWindow popupWindow = new PopupWindow(rootView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_transparent));
        popupWindow.setAnimationStyle(R.style.order_cancel_anim_style);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        reasonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 1;
                popupWindow.dismiss();
                closeOrder(id);
            }
        });
        reasonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 2;
                popupWindow.dismiss();
                closeOrder(id);
            }
        });
        reasonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 3;
                popupWindow.dismiss();
                closeOrder(id);
            }
        });

        disBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


    }


    /**
     * 删除订单
     */
    /**
     * 获取订单详情
     */
    public void getDeleteOrder(String id) {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.ORDER_DELETE);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(id);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().order(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderDetailModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OrderDetailModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                        }
                    }
                });
    }


    private int getTotalNums(List<OrderDetailModel.DataBean.OrderBean.OrderListBean> listorder_list) {
        int num = 0;
        for (OrderDetailModel.DataBean.OrderBean.OrderListBean bean : listorder_list) {
            num = num + bean.getNumber();
        }
        return num;
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_my_order_list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        fromatNums = mContext.getResources().getString(R.string.order_num);
        fromatTotalNum = mContext.getResources().getString(R.string.order_total_num);
        fromatPayPrice = mContext.getResources().getString(R.string.pay_price);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        AutoListView listView;
        TextView tvStatus;
        TextView tvOrderNums;
        TextView tvTotalNums;
        TextView tvPayPrice;
        Button btn01;
        Button btn02;
        Button btn03;
        Button btn04;

        public ViewHolder(View itemView) {
            super(itemView);
            listView = (AutoListView) findView(R.id.list_view);
            tvStatus = (TextView) findView(R.id.tv_order_status);
            tvOrderNums = (TextView) findView(R.id.tv_order_id);
            tvTotalNums = (TextView) findView(R.id.tv_total_num);
            tvPayPrice = (TextView) findView(R.id.tv_pay_price);
            btn03 = (Button) findView(R.id.btn_03);
            btn02 = (Button) findView(R.id.btn_02);
            btn01 = (Button) findView(R.id.btn_01);
            btn04 = (Button) findView(R.id.btn_04);
        }
    }

    /**
     * 确认收货
     *
     * @param order_id ;订单id
     */
    private void orderRecive(String order_id) {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.ORDER_RECIVE);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(order_id);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        ((BaseActivity) mContext).showProgressDialog("正在确认收货");
        mSubscription = ApiImp.get().closeOrder(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        Toast.makeText(mContext, "确认收货成功", Toast.LENGTH_SHORT).show();
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                        }
                    }
                });
    }

    /**
     * 取消订单
     */
    public void closeOrder(String orderId) {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.ORDER_CLOSE);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(orderId);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        ((BaseActivity) mContext).showProgressDialog("正在取消订单");
        mSubscription = ApiImp.get().closeOrder(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        Toast.makeText(mContext, R.string.cancel_order_success, Toast.LENGTH_SHORT).show();
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                        }
                    }
                });
    }

    /**
     * 申请退款
     */

    public void exitMoney(final String id, final int i) {
        DetailReq req = new DetailReq();
        req.setCmd(order_refund_apply);
        final DetailReq.ContentBean bean = new DetailReq.ContentBean();
        bean.setId(id);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ((BaseActivity) mContext).showProgressDialog("正在退款");
        mSubscription = ApiImp.get().exitMoney(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExitMoneyModel>() {
                    @Override
                    public void onCompleted() {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ExitMoneyModel model) {
                        exitMoneyModel = model;
                        int code = model.getData().getStatus_time();
                        String msg = model.getData().getOrder_desc();

                        LoginKnownDialog.Builder builder = new LoginKnownDialog.Builder(mContext);

                        switch (code) {

                            case 0:
                            case 1:
                            case 2:
                                builder.setTitle("是否继续退款?");
                                builder.setMessage(msg);
                                builder.setNegativeButton("取消退款", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        flag = 0;
                                        confirmExitOrder(mList.get(i).getOrder_id(), flag);
                                    }
                                });
                                builder.setPositiveButton("确认退款", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        flag = 1;
                                        confirmExitOrder(mList.get(i).getOrder_id(), flag);
                                    }
                                });
                                builder.create().show();
                                break;
                            case 3:
                                builder.setMessage(msg);
                                builder.setBottomButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                builder.create().show();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }


    /**
     * 确认退款
     */

    public void confirmExitOrder(String id, int status_refund) {

        ExitOrderReq req = new ExitOrderReq();
        req.setCmd(order_refund_confirm);
        ExitOrderReq.ContentBean bean = new ExitOrderReq.ContentBean();
        bean.setId(id);
        bean.setStatus_refund(status_refund);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));

        mSubscription = ApiImp.get().confirmExitMoney(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderDetailModel.DataBean.OrderBean>() {
                    @Override
                    public void onCompleted() {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) mContext).dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OrderDetailModel.DataBean.OrderBean model) {

                        if (model.getStatus().equals("0")) {
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));

                        }
                    }
                });
    }

    /**
     * 销毁网络访问
     */
    public void destory() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
