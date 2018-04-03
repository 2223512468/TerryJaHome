package com.jajahome.feature.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.ExAdapter;
import com.jajahome.model.ExModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.ExChangeReq;
import com.jajahome.network.HttpUtil;
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
 * Created by Administrator on 2017/8/15.
 */
public class IntChangeAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener {

    public static final int IntChange = 0x111;
    public static final String JIFEN = "JIFEN";
    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;
    @BindView(R.id.ibtn_back)
    ImageButton imgBack;
    @BindView(R.id.my_rebate)
    TextView tvRebate;
    @BindView(R.id.tv_address_name)
    TextView adressName;
    @BindView(R.id.tv_address_mobile)
    TextView mobileName;
    @BindView(R.id.tv_address_detail)
    TextView detailAddress;
    @BindView(R.id.add_btn)
    ImageView addIMV;
    @BindView(R.id.rl_address)
    RelativeLayout addRL;
    @BindView(R.id.default_tv)
    TextView defaultTv;

    private ExAdapter exAdapter;
    private int offset = 1;
    private boolean isFirst = true;
    private String Integer;

    @Override
    protected int getViewId() {
        return R.layout.act_change_integ;
    }


    @Override
    protected void initEvent() {
        initViewController(recycleView);
        Integer = getIntent().getStringExtra(JIFEN);
        if (!StringUtil.isEmpty(Integer)) {
            tvRebate.setText("您有" + Integer + "金币，活动期间限量兑换");
        }

        exAdapter = new ExAdapter();
        recycleView.setAdapter(exAdapter);
        recycleView.setOnMutilRecyclerViewListener(this);
        showLoading(true, "");
        getExChange();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String address = detailAddress.getText().toString();
        exAdapter.setLocalAddress(address);
    }

    private void getExChange() {

        ExChangeReq.ContentBean.Pagination pagination = new ExChangeReq.ContentBean.Pagination();
        pagination.setLimit(Constant.PAGE_LIMIT);
        pagination.setOffset(offset);
        ExChangeReq.ContentBean bean = new ExChangeReq.ContentBean();
        bean.setPagination(pagination);
        ExChangeReq req = new ExChangeReq();
        req.setContentBean(bean);
        req.setCmd(Constant.ITEM_EXCHANGE_LIST);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().item_exchange_List(requestBody, HttpUtil.getSession(this)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ExModel>() {
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
                    public void onNext(ExModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<ExModel.DataBean.ItemBean> item = model.getData().getItem();
                            if (item == null || item.size() == 0) {
                                showToast("未找到可兑换物品");
                                exAdapter.clear();
                            } else {
                                exAdapter.resetItems(item);
                            }
                        } else {
                            exAdapter.addItems(model.getData().getItem());
                        }

                        ExModel.DataBean.AddressBean address = model.getData().getAddress();
                        if (StringUtil.isEmpty(address.getName())) {
                            defaultTv.setVisibility(View.VISIBLE);
                            defaultTv.setText("您还没有收货地址，赶快去添加吧");

                        } else {
                            adressName.setText("收货人:" + address.getName());
                            mobileName.setText(address.getMobile());
                            detailAddress.setText("收货地址:" + address.getArea() + address.getDetail_address());
                            exAdapter.setAddressId(java.lang.Integer.parseInt(address.getId()));
                            defaultTv.setVisibility(View.GONE);
                            String ress = detailAddress.getText().toString();
                            exAdapter.setLocalAddress(ress);
                        }
                    }
                });
    }

    private int requCode = 1;

    public void setListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IntChangeAct.this, DefaultAct.class);
                startActivityForResult(intent, requCode);
            }
        });

    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == IntChange) {
            getExChange();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == requCode) {
                String name = data.getStringExtra("name");
                String mobile = data.getStringExtra("mobile");
                String address = data.getStringExtra("address");
                String area = data.getStringExtra("area");
                defaultTv.setVisibility(View.GONE);
                adressName.setText("收货人:" + name);
                mobileName.setText(mobile);
                detailAddress.setText("收货地址:" + area + " " + address);

            }
        }
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getExChange();
    }

    @Override
    public void onLoadMore() {
        offset = offset + 1;
        getExChange();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exAdapter != null) {
            exAdapter.stopSubscription();
        }
    }
}
