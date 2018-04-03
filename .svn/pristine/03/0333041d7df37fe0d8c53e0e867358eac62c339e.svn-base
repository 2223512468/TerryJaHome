package com.jajahome.feature.user.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.YunOrderAdapter;
import com.jajahome.model.YunOrderModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.YunOrderId;
import com.jajahome.util.LoginUtil;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/15.
 */
public class YunOrderAct extends BaseActivity implements View.OnClickListener {

    public final static String ORDER_ID = "ORDER_ID";
    @BindView(R.id.listview)
    ExpandableListView listview;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.order_title)
    RelativeLayout titleRL;

    @BindView(R.id.ibtn_back)
    ImageButton imgBack;


    private YunOrderAdapter adapter;
    private String mOrderId;
    private YunOrderModel yunOrderModel;


    @Override
    protected int getViewId() {
        return R.layout.act_yunorder;
    }

    @Override
    protected void initEvent() {
        titleRL.setBackgroundColor(getResources().getColor(R.color.theme_bg));
        textView2.setText("我的订单");
        initViewController(listview);
        mOrderId = getIntent().getStringExtra(ORDER_ID);
        showLoading(true, "");
        getOrder(mOrderId);
        setListener();
    }

    private void getOrder(String order_id) {
        if (LoginUtil.getInfo(mContext) == null) return;
        YunOrderId.ContentBean bean = new YunOrderId.ContentBean();
        bean.setOrder_id(order_id);
        YunOrderId req = new YunOrderId();
        req.setContent(bean);
        req.setCmd(Constant.order_Waybill_query);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().order_Waybill_query(requestBody, requestBody1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<YunOrderModel>() {

            @Override
            public void onCompleted() {
                showLoading(false, "");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(YunOrderModel model) {

                yunOrderModel = model;

                String json = gson.toJson(model);
                Log.i("--YunOrder", json);
                setData();
            }

        });
    }

    private void setListener() {
        imgBack.setOnClickListener(this);
    }

    public void setData() {
        List<YunOrderModel.DataBean.WaybillBean> waybill = yunOrderModel.getData().getWaybill();
        if (adapter == null) {
            adapter = new YunOrderAdapter(mContext, waybill, mOrderId);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.act_order_num, null);
        TextView orderText = (TextView) view.findViewById(R.id.order_id);
        orderText.setText("订单号:" + mOrderId);
        listview.addHeaderView(view);
        listview.setGroupIndicator(null);
        listview.setAdapter(adapter);
        listview.setFocusable(false);
        listview.setDividerHeight(0);
        //设置父节点(章目录)不可点击
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true,表示不可点击
            }
        });
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listview.expandGroup(i);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }
}
