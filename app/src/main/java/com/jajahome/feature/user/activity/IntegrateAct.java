package com.jajahome.feature.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.IntegrateListAdapter;
import com.jajahome.model.IntegralModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.RebateReq;
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
 * Created by Administrator on 2017/5/9.
 */
public class IntegrateAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener {


    @BindView(R.id.tv_total_price_num)
    TextView totalPriceNum;
    @BindView(R.id.tv_get_money)
    TextView totalGetMoney;
    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;
    @BindView(R.id.ibtn_back)
    ImageButton imgBack;
    @BindView(R.id.tv_change)
    TextView tvChange;

    private int offset = 1;
    private boolean isFirst = true;
    private IntegrateListAdapter mAdapter;
    private String JIFEN;

    @Override
    protected int getViewId() {
        return R.layout.act_integrate;
    }

    @Override
    protected void initEvent() {
        initViewController(recycleView);
        mAdapter = new IntegrateListAdapter();
        recycleView.setAdapter(mAdapter);
        recycleView.setOnMutilRecyclerViewListener(this);
        showLoading(true, "");
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
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
        req.setCmd(Constant.INTEGRAL_DETAILS);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().integral_details(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<IntegralModel>() {
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
                    public void onNext(IntegralModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<IntegralModel.DataBean.IntegralInfoBean.IntegralListBean> list = model.getData().getIntegralInfo().getIntegral_list();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "暂无相关记录", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "暂无相关记录", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(model.getData().getIntegralInfo().getIntegral_list());
                        }

                        String available = model.getData().getIntegralInfo().getAvailable();
                        if (!StringUtil.isEmpty(available)) {
                            totalPriceNum.setText(available + "");
                            JIFEN = available + "";
                        }

                    }
                });
    }

    private void setListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegrateAct.this, IntChangeAct.class);
                if (!StringUtil.isEmpty(JIFEN)) {
                    intent.putExtra(IntChangeAct.JIFEN, JIFEN);
                }
                startActivity(intent);
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
