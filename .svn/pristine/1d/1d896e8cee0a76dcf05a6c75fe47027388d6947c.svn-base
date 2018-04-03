package com.jajahome.feature.user.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.RebateListAdapter;
import com.jajahome.model.RebateInfoModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.RebateReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/8.
 */
public class RebateInfoAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener {

    public static final int TOTALPRICENUM = 0x000;
    public static final int TOTALGETMONEY = 0x001;


    @BindView(R.id.tv_total_price_num)
    TextView totalPriceNum;
    @BindView(R.id.tv_get_money)
    TextView totalGetMoney;
    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;
    @BindView(R.id.ibtn_back)
    ImageButton imgBack;
    @BindView(R.id.cash)
    TextView cashTv;

    private int offset = 1;
    private boolean isFirst = true;
    private RebateListAdapter mAdapter;
    private Double totalPrice;

    @Override
    protected int getViewId() {
        return R.layout.act_rebate;
    }

    @Override
    protected void initEvent() {
        initViewController(recycleView);
        mAdapter = new RebateListAdapter();
        recycleView.setAdapter(mAdapter);
        recycleView.setOnMutilRecyclerViewListener(this);
        showLoading(true, "");
        setListener();
    }

    private void getList() {
        RebateReq.ContentBean.PaginationBean paginationBean = new RebateReq.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        RebateReq.ContentBean contentBean = new RebateReq.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setType(0);
        RebateReq req = new RebateReq();
        req.setContent(contentBean);
        req.setCmd(Constant.REBATE_DETAILS);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().rebate_details(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<RebateInfoModel>() {
                    @Override
                    public void onCompleted() {
                        recycleView.stopRefresh();
                        recycleView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(RebateInfoModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<RebateInfoModel.DataBean.RebateInfoBean.RebateListBean> list = model.getData().getRebateInfo().getRebate_list();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "暂无相关记录", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "暂无相关记录", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(model.getData().getRebateInfo().getRebate_list());
                        }

                        Double available = model.getData().getRebateInfo().getAvailable();
                        if (!StringUtil.isEmpty(available + "")) {
                            totalPriceNum.setText("￥" + available + "");
                            totalPrice = available;
                        }

                        String cashback = model.getData().getRebateInfo().getCashback();
                        if (!StringUtil.isEmpty(cashback)) {
                            totalGetMoney.setText("累计取现: ￥" + cashback);
                        } else {
                            totalGetMoney.setText("累计取现: ￥" + 0);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void setListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtil.isLogin(RebateInfoAct.this)) {
                    Intent intent = new Intent(RebateInfoAct.this, CashAct.class);
                    if (!StringUtil.isEmpty(totalPrice + "") && totalPrice > 0) {
                        intent.putExtra(CashAct.CASHACT, totalPrice + "");
                        startActivity(intent);
                    } else {
                        showToast("无可提现余额");
                    }
                } else {
                    gotoNewAty(LoginAct.class);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    @Override
    public void onLoadMore() {
        offset = 1 + offset;
        getList();
    }
}

