package com.jajahome.feature.user.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.alipay.PayResult;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.user.adapter.MyOrderListAdapter;
import com.jajahome.feature.user.adapter.OrderListAdapter;
import com.jajahome.feature.user.fragment.OrderFrag;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.feature.view.LoginKnownDialog;
import com.jajahome.model.AlipayModel;
import com.jajahome.model.ExitMoneyModel;
import com.jajahome.model.JWeichatPayModel;
import com.jajahome.model.OrderDetailModel;
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
import com.jajahome.wxapi.WXPayEntryActivity;
import com.jajahome.wxapi.WxPayApi;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单详情
 */
public class OrderDetailAct extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    public final static String ORDER_ID = "ORDER_ID";
    public static final String ORDER_NUM = "ORDER_NUM";

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_order_pay)
    TextView tvOrderTotalPrice;
    @BindView(R.id.tv_order_pay_yuan)
    TextView tvOrderTotalPriceNum;
    @BindView(R.id.tv_order_tv)
    TextView tvOrderPrice;


    @BindView(R.id.bottom_view)
    RelativeLayout bottomView;

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;

    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_mobile)
    TextView tvAddressMobile;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;

    @BindView(R.id.tv_yun_fei)
    TextView tvYunFei;
    @BindView(R.id.tv_fun_feil)
    TextView tvYunFeil;


    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.list_view)
    AutoListView listView;
    @BindView(R.id.btn_01)
    Button btn01;
    @BindView(R.id.btn_02)
    Button btn02;
    @BindView(R.id.view_address)
    LinearLayout viewAddress;
    @BindView(R.id.view_select_pay_way)
    LinearLayout viewSelectPayWay;
    @BindView(R.id.cb_weichat)
    CheckBox cbWeichat;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.tv_num)
    TextView tvNuM;
    @BindView(R.id.second_tv)
    TextView secPayTv;
    @BindView(R.id.count_num)
    LinearLayout count_num;


    @BindView(R.id.cb_whole)
    CheckBox wholeBox;
    @BindView(R.id.cb_second_pay)
    CheckBox secondBox;
    @BindView(R.id.second_pay_detail)
    LinearLayout secondPayDetail;

    @BindView(R.id.current_pay)
    RelativeLayout curRL;
    @BindView(R.id.current_pay_tv)
    TextView tv;

    @BindView(R.id.container_ll)
    LinearLayout ll;
    OrderListAdapter adapter;
    private ExitMoneyModel exitMoneyModel;

    @BindView(R.id.second_pay_ll)
    LinearLayout second_pay_LL;

    @BindView(R.id.whole_lin)
    LinearLayout wholeLin;

    @BindView(R.id.buy_known)
    RelativeLayout buyKnownRL;

    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;

    @BindView(R.id.cb_xian)
    CheckBox xianBox;

    private boolean isAgree = true;
    private int flag;

    /**
     * 订单id
     */
    private String mOrderId;
    private String totalNum;
    private String substring;
    private String cityName;
    /**
     * 获取到的订单详情数据
     */
    private OrderDetailModel mOrderDetailModel;

    private OrderDetailModel.DataBean.OrderBean orderBean;

    private int mPayWay = -1;//支付方式 0微信 1支付宝,-1未选择,2线下

    private IWXAPI mIwxapi;      //微信api

    private int mPayMethod = -1;//支付次数 一次性，分次


    private int payCount = 1;

    @Override
    protected int getViewId() {
        return R.layout.act_order_detail;
    }

    @Override
    protected void initEvent() {
        textView2.setText(R.string.order);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvXieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                H5Act.gotoH5(mContext, Constant.ITEM_NOTICE, getString(R.string.buy_known));
            }
        });
        bottomView.setVisibility(View.GONE);
        initViewController(scrollView);
        showLoading(true, "获取订单详情");
        mOrderId = getIntent().getStringExtra(ORDER_ID);
        totalNum = getIntent().getStringExtra(ORDER_NUM);

        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);


        cbXieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree = isChecked;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetail() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.ORDER);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(mOrderId);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().order(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderDetailModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                        showLoading(false, "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        showLoading(false, "");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OrderDetailModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            mOrderDetailModel = model;
                            setData();
                            setListener();
                        }
                    }
                });
    }

    private void setData() {
        orderBean = mOrderDetailModel.getData().getOrder();
        //订单编号
        String fromat = getResources().getString(R.string.order_num);
        tvOrderId.setText(String.format(fromat, orderBean.getOrder_id()));
        //订单状态
        setOrderState(orderBean.getStatus());

        if (orderBean.getStatus().equals("8")) {
            payCount = 2;
        } else {
            payCount = 1;
        }

        //地址
        OrderDetailModel.DataBean.OrderBean.AdressBean addressBean = orderBean.getAdress();
        tvAddressDetail.setText(addressBean.getDetail_address());
        tvAddressName.setText(addressBean.getName());
        tvAddressMobile.setText(addressBean.getMobile());
        //总价格
        bottomView.setVisibility(View.VISIBLE);

        String price = orderBean.getPrice();
        int position = price.indexOf(".");

        substring = price;

        if (orderBean.getStatus().equals("1") || orderBean.getStatus().equals("7")) {
            tvOrderTotalPriceNum.setVisibility(View.GONE);
            tvOrderPrice.setText("订单合计:￥" + substring);
        }


        //｜ 售后承担 （0->平台  1->客户）
        if (0 == orderBean.getSales_service()) {
            tvYunFei.setText("含服务（仓储、物流、安装、配送、一年售后）");
        } else if (1 == orderBean.getSales_service()) {
            tvYunFei.setText("服务自理");
        }

        count_num.setVisibility(View.VISIBLE);
        tvOrderTotalPriceNum.setText(R.string.current_pay);
        tvOrderPrice.setText("￥" + substring);

        adapter = new OrderListAdapter(mContext, orderBean.getOrder_list());
        listView.setAdapter(adapter);
        tvNuM.setText(getTotalNums(orderBean.getOrder_list()) + "");
    }

    private int getTotalNums(List<OrderDetailModel.DataBean.OrderBean.OrderListBean> listorder_list) {
        int num = 0;
        for (OrderDetailModel.DataBean.OrderBean.OrderListBean bean : listorder_list) {
            num = num + bean.getNumber();
        }
        return num;
    }


    private void getPay() {
        BigDecimal bd = new BigDecimal((Double.parseDouble(substring) / 2) + "");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (mPayMethod == 1) {
            count_num.setVisibility(View.VISIBLE);
            tvOrderTotalPriceNum.setText(R.string.current_pay);
            tvOrderPrice.setText("￥" + substring);
        } else if (mPayMethod == 0) {
            count_num.setVisibility(View.VISIBLE);
            tvOrderTotalPriceNum.setText(R.string.current_pay);
            tvOrderPrice.setText("￥" + bd.toString());
        }
    }

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
    private void setOrderState(String status) {
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
         * 8 订金已付
         */
        switch (status) {
            case "0":
                tvOrderStatus.setText("等待买家付款");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.PAY);
                /*btn02.setText(MyOrderListAdapter.CLOSE_ORDER);*/
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.VISIBLE);

                tvOrderTotalPriceNum.setVisibility(View.VISIBLE);
                tvOrderPrice.setVisibility(View.VISIBLE);
                buyKnownRL.setVisibility(View.VISIBLE);

                break;
            case "1":
                tvOrderStatus.setText("等待发货");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                btn02.setText(MyOrderListAdapter.EXIT_MONEY);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.VISIBLE);
                viewSelectPayWay.setVisibility(View.GONE);

                //支付方式对话框隐藏
                ll.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.GONE);

                //底部bottom 件数与价格隐藏
                tvOrderTotalPriceNum.setVisibility(View.VISIBLE);
                tvOrderPrice.setVisibility(View.VISIBLE);
                count_num.setVisibility(View.VISIBLE);
                //购买须知
                buyKnownRL.setVisibility(View.GONE);


                break;
            case "2":
                tvOrderStatus.setText("交易关闭");
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
                viewSelectPayWay.setVisibility(View.GONE);

                //支付方式对话框隐藏
                ll.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.GONE);

                //底部bottom 件数与价格隐藏
                tvOrderTotalPriceNum.setVisibility(View.GONE);
                tvOrderPrice.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);

                //购买须知
                buyKnownRL.setVisibility(View.GONE);

                break;
            case "3":
                tvOrderStatus.setText("交易完成");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
                viewSelectPayWay.setVisibility(View.GONE);
                break;
            case "4":
                tvOrderStatus.setText("退款中");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
                viewSelectPayWay.setVisibility(View.GONE);

                //支付方式对话框隐藏
                ll.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.GONE);


                break;
            case "5":
                tvOrderStatus.setText("交易关闭");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
                viewSelectPayWay.setVisibility(View.GONE);
                break;
            case "6":
                tvOrderStatus.setText("等待卖家修改价格");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.CONTACT_SELLER);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
                viewSelectPayWay.setVisibility(View.GONE);
                break;
            case "7":
                tvOrderStatus.setText("待收货");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.SEARCH_STATE);
                // btn02.setText(MyOrderListAdapter.SEARCH_STATE);
                //设置按钮是否显示
                btn01.setVisibility(View.VISIBLE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.VISIBLE);
                viewSelectPayWay.setVisibility(View.GONE);

                //支付方式对话框隐藏
                ll.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.GONE);

                //底部bottom 件数与价格隐藏
                tvOrderTotalPriceNum.setVisibility(View.VISIBLE);
                tvOrderPrice.setVisibility(View.VISIBLE);
                count_num.setVisibility(View.VISIBLE);

                break;

            case "8":
                tvOrderStatus.setText("订金已付");
                second_pay_LL.setVisibility(View.VISIBLE);
                wholeLin.setVisibility(View.GONE);
                btn02.setVisibility(View.GONE);
                btn01.setText("支付订单");
                tvOrderTotalPriceNum.setVisibility(View.VISIBLE);
                tvOrderTotalPrice.setVisibility(View.VISIBLE);
                tvOrderPrice.setVisibility(View.VISIBLE);
                buyKnownRL.setVisibility(View.VISIBLE);

                break;

            case "9":
                tvOrderStatus.setText("转帐审核中");
                //设置显示的文字
                btn01.setText(MyOrderListAdapter.SEARCH_STATE);
                // btn02.setText(MyOrderListAdapter.SEARCH_STATE);
                //设置按钮是否显示
                btn01.setVisibility(View.GONE);
                btn02.setVisibility(View.GONE);
                viewAddress.setVisibility(View.VISIBLE);
                viewSelectPayWay.setVisibility(View.GONE);

                //支付方式对话框隐藏
                ll.setVisibility(View.GONE);
                count_num.setVisibility(View.GONE);
                tvOrderTotalPrice.setVisibility(View.GONE);

                //底部bottom 件数与价格隐藏
                tvOrderTotalPriceNum.setVisibility(View.VISIBLE);
                tvOrderPrice.setVisibility(View.VISIBLE);
                count_num.setVisibility(View.VISIBLE);
                buyKnownRL.setVisibility(View.GONE);
                break;
        }
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(((Button) v).getText());
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick(((Button) v).getText());
            }
        });

    }

    /**
     * 操作按钮被点击
     *
     * @param text ： 按钮上的文字 （通过文字判断动作）
     */
    private void onActionClick(CharSequence text) {
        if (MyOrderListAdapter.PAY.equals(text) || MyOrderListAdapter.PAY_ORDER.equals(text)) {
            //付款

            if (wholeBox.isChecked()) {
            } else if (secondBox.isChecked()) {
            } else {
                showToast(R.string.pay_count);
                return;
            }
            if (cbWeichat.isChecked()) {
                getWeichatPayInfo();
            } else if (cbAlipay.isChecked()) {
                getAlipayInfo();
            } else if (xianBox.isChecked()) {
                Intent intent = new Intent(this, CommitAct.class);
                intent.putExtra(CommitAct.ORDERID, mOrderId);
                intent.putExtra(CommitAct.PAYCOUNT, payCount);
                String price = orderBean.getPrice();
                if (payCount == 1) {
                    if (mPayMethod == 0) {
                        intent.putExtra(CommitAct.ORDERPRICE, Double.parseDouble(price) / 2);
                    } else if (mPayMethod == 1) {
                        intent.putExtra(CommitAct.ORDERPRICE, Double.parseDouble(price));
                    }

                } else if (payCount == 2) {
                    intent.putExtra(CommitAct.ORDERPRICE, Double.parseDouble(price) / 2);
                }

                startActivity(intent);
            } else {
                showToast(R.string.select_way_pay);
            }

        } else if (MyOrderListAdapter.CONTACT_SELLER.equals(text)) {
            //联系卖家
            if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
            } else {
                cityName = "";
            }
            String about_us = Constant.ABOUT_US + "&city=" + cityName;
            H5Act.gotoH5(mContext, about_us, MyOrderListAdapter.CONTACT_SELLER);
        } else if (MyOrderListAdapter.ORDER_RECIVE.equals(text)) {
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
                    orderRecive(mOrderId);
                }
            });
            builder.create().show();

        } else if (MyOrderListAdapter.CLOSE_ORDER.equals(text)) {
            //取消订单
            LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
            builder.setMessage("是否取消订单?");
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
                    closeOrder();
                }
            });
            builder.create().show();
        } else if (MyOrderListAdapter.EXIT_MONEY.equals(text)) {
            //确认退款
            exitMoney(orderBean.getOrder_id());

        } else if (MyOrderListAdapter.SEARCH_STATE.equals(text)) {
            Intent intent = new Intent(this, YunOrderAct.class);
            intent.putExtra(YunOrderAct.ORDER_ID, mOrderId);
            startActivity(intent);
        }
    }

    /*
     * 申请退款
     */

    public void exitMoney(final String id) {
        DetailReq req = new DetailReq();
        req.setCmd(MyOrderListAdapter.order_refund_apply);
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
                                        confirmExitOrder(id, flag);
                                    }
                                });
                                builder.setPositiveButton("确认退款", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        flag = 1;
                                        confirmExitOrder(id, flag);
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
        req.setCmd(MyOrderListAdapter.order_refund_confirm);
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
                            getOrderDetail();
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));

                        }
                    }
                });
    }


    /**
     * 获取微信支付预处理信息
     */
    private void getWeichatPayInfo() {

        if (!isAgree) {
            showToast(R.string.buy_agree);
        } else {

            ReqPage baseReq = new ReqPage();
            baseReq.setCmd(Constant.PAY_WEIXIN);
            ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
            contentBean.setOrder_id(mOrderId);

            if (mPayMethod == 1) {//微信全额支付
                float order_price = Float.parseFloat(substring);
                int pay_count = 1;

                if (orderBean.getStatus().equals("0")) {
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                }

            } else if (mPayMethod == 0) {//微信多次支付

                float order_price = Float.parseFloat(substring) / 2;

                if (orderBean.getStatus().equals("0")) {
                    int pay_count = 1;
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                } else if (orderBean.getStatus().equals("8")) {
                    int pay_count = 2;
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                }

            }

            baseReq.setContent(contentBean);
            Gson gson = new Gson();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
            showProgressDialog("获取微信支付信息");
            mSubscription = ApiImp.get().weixinPay(requestBody, HttpUtil.getSession(mContext))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JWeichatPayModel>() {
                        @Override
                        public void onCompleted() {
                            showLoading(false, "");
                            dissmisProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            showLoading(false, "");
                            dissmisProgressDialog();
                            obs = Observable.just(error)
                                    .subscribeOn(Schedulers.io());
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(JWeichatPayModel model) {
                            if (model.getStatus() == HttpCode.SUCCESS) {
                                showProgressDialog("进入微信支付");
                                mIwxapi.sendReq(WxPayApi.genPayReq(model));
                                getOrderDetail();
                            }
                        }
                    });

        }
    }

    private Observable obs;

    /**
     * 获取支付宝预处理
     */
    private void getAlipayInfo() {
        if (!isAgree) {
            showToast(R.string.buy_agree);
        } else {

            ReqPage baseReq = new ReqPage();
            baseReq.setCmd(Constant.PAY_ALIPAY);
            ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
            contentBean.setOrder_id(mOrderId);
            contentBean.setOrder_price(Float.parseFloat(substring));

            if (mPayMethod == 1) {//微信全额支付
                float order_price = Float.parseFloat(substring);
                int pay_count = 1;

                if (orderBean.getStatus().equals("0")) {
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                }

            } else if (mPayMethod == 0) {//微信多次支付

                float order_price = Float.parseFloat(substring) / 2;

                if (orderBean.getStatus().equals("0")) {
                    int pay_count = 1;
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                } else if (orderBean.getStatus().equals("8")) {
                    int pay_count = 2;
                    contentBean.setOrder_price(order_price);
                    contentBean.setPay_count(pay_count);
                }

            }

            baseReq.setContent(contentBean);
            Gson gson = new Gson();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
            showProgressDialog("获取支付信息");
            mSubscription = ApiImp.get().alipay(requestBody, HttpUtil.getSession(mContext))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<AlipayModel>() {
                        @Override
                        public void onCompleted() {
                            dissmisProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dissmisProgressDialog();
                            obs = Observable.just(error)
                                    .subscribeOn(Schedulers.io());
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(AlipayModel model) {
                            if (model.getStatus() == HttpCode.SUCCESS) {
                                startAlipay(model.getData().getOrder_string());
                                getOrderDetail();
                            }
                        }
                    });

        }
    }


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


    private void setListener() {
        cbWeichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 0) {
                    mPayWay = 0;
                    cbWeichat.setChecked(true);
                    cbAlipay.setChecked(false);
                    xianBox.setChecked(false);
                } else {
                    mPayWay = -1;
                }
            }
        });
        cbAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 1) {
                    mPayWay = 1;
                    cbWeichat.setChecked(false);
                    cbAlipay.setChecked(true);
                    xianBox.setChecked(false);
                } else {
                    mPayWay = -1;
                }
            }
        });

        xianBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPayWay != 2) {
                    mPayWay = 2;
                    cbWeichat.setChecked(false);
                    cbAlipay.setChecked(false);
                    xianBox.setChecked(true);
                } else {
                    mPayWay = -1;
                }
            }
        });

        wholeBox.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPayMethod != 1) {
                    //全额付款
                    mPayMethod = 1;
                    getPay();
                    secondBox.setChecked(false);
                    wholeBox.setChecked(true);
                    curRL.setVisibility(View.VISIBLE);
                    tv.setText("￥" + substring);
                    secondPayDetail.removeAllViews();
                } else {
                    mPayMethod = -1;
                    curRL.setVisibility(View.GONE);
                }
            }
        });

        secondBox.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                BigDecimal bd = new BigDecimal((Double.parseDouble(substring) / 2) + "");
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

                if (mPayMethod != 0) {
                    //分次付款
                    mPayMethod = 0;
                    getPay();
                    wholeBox.setChecked(false);
                    secondBox.setChecked(true);

                    secondPayDetail.setOrientation(LinearLayout.VERTICAL);
                    secondPayDetail.removeAllViews();

                    for (int i = 0; i < 3; i++) {

                        LinearLayout layout = new LinearLayout(OrderDetailAct.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        TextView tv = new TextView(OrderDetailAct.this);
                        TextView tv1 = new TextView(OrderDetailAct.this);

                        LinearLayout.LayoutParams paramTv = new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);

                        paramTv.setMargins(secPayTv.getLeft() + 20, 10, 0, 0);
                        tv.setLayoutParams(paramTv);

                        LinearLayout.LayoutParams paramTv1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        int Right = getWidth();
                        paramTv1.setMargins(Right - 700, 10, 0, 0);
                        tv1.setLayoutParams(paramTv1);


                        if (i == 0) {
                            tv.setText("订单总价");
                            tv1.setText("￥" + substring);
                            layout.addView(tv);
                            layout.addView(tv1);
                        } else if (i == 1) {
                            if (orderBean.getStatus().equals("0")) {
                                tv.setText("本次支付");
                                tv.setTextColor(getResources().getColor(R.color.colorAccent));
                                tv1.setText("￥" + bd);
                                tv1.setTextColor(getResources().getColor(R.color.colorAccent));

                            } else if (orderBean.getStatus().equals("8")) {
                                tv.setText("已支付");
                                tv1.setText("￥" + bd);
                            }

                            layout.addView(tv);
                            layout.addView(tv1);
                        } else if (i == 2) {
                            if (orderBean.getStatus().equals("0")) {
                                tv.setText("剩余");
                                tv1.setText("￥" + bd);
                            } else if (orderBean.getStatus().equals("8")) {
                                tv.setText("本次支付");
                                tv1.setText("￥" + bd);
                                tv.setTextColor(getResources().getColor(R.color.colorAccent));
                                tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                            }

                            layout.addView(tv);
                            layout.addView(tv1);
                        }
                        secondPayDetail.addView(layout);
                    }

                } else {
                    mPayMethod = -1;
                    secondPayDetail.removeAllViews();
                }
            }
        });

    }


    /**
     * 获取手机屏幕宽高
     *
     * @param
     */

    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenWidth;
    }

    private void startAlipay(final String alipayInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailAct.this);
                Map<String, String> result = alipay.payV2(alipayInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 支付宝支付结果在此回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailAct.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功 通知 我的订单处刷新列表
                        EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailAct.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    getOrderDetail();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private String error = "订单中有下架商品，无法支付";
    private Observable obs2;

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == WXPayEntryActivity.WEICAT_PAY_RESULT_ACTION) {
            dissmisProgressDialog();
            //微信支付结果
            int code = Integer.valueOf(event.msg);
            if (code == BaseResp.ErrCode.ERR_USER_CANCEL) {
                showToast("已取消支付");
            } else if (code == BaseResp.ErrCode.ERR_OK) {
                showToast("支付成功");
                getOrderDetail();
                //支付成功 通知 我的订单处刷新列表
                EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
            }
        } else if (event.action == Constant.ERROR) {
            Log.i("print", "订单中有下架商品，无法支付");
            obs2 = Observable.just(error)
                    .subscribeOn(AndroidSchedulers.mainThread());

            Observable.merge(obs, obs2)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {
                            Toast.makeText(OrderDetailAct.this, "订单中有下架商品，无法支付", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    /**
     * 取消订单
     */
    public void closeOrder() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.ORDER_CLOSE);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(mOrderId);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("正在取消订单");
        mSubscription = ApiImp.get().closeOrder(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        showToast(model.getMessage());
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            getOrderDetail();
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                        }
                    }
                });
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
        showProgressDialog("正在确认收货");
        mSubscription = ApiImp.get().closeOrder(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        showToast("确认收货成功");
                        getOrderDetail();
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            EventBus.getDefault().post(new EventMessage(OrderFrag.STATUS_CHANGE_ACTION, ""));
                        }
                    }
                });
    }

}
