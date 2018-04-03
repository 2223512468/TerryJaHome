package com.jajahome.feature.user.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.MaterAdapter;
import com.jajahome.model.WuLiuModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.WuLiuReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/8.
 */
public class MaterialsAct extends BaseActivity implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener {

    public final static String ORDER_ID = "ORDER_ID";
    public final static String MORDER_ID = "MORDER_ID";
    public final static String NUMBER = "NUMBER";
    public final static String URL = "URL";
    @BindView(R.id.lv_list)
    MultiRecycleView recycleView;
    @BindView(R.id.textView2)
    TextView textView;
    @BindView(R.id.ibtn_back)
    ImageButton imageButton;


    @BindView(R.id.default_ll)
    LinearLayout default_ll;


    private MaterAdapter adapter;
    private String mYunOrderId, mOrderId, mOrderNum;

    private TextView state, kuaiDi, yunText, numText;
    private ImageView imageView;
    private String imgUrl;

    @Override
    protected int getViewId() {
        return R.layout.act_mater;
    }

    @Override
    protected void initEvent() {
        initViewController(recycleView);
        textView.setText("物流详情");
        mYunOrderId = getIntent().getStringExtra(ORDER_ID);
        mOrderId = getIntent().getStringExtra(MORDER_ID);
        mOrderNum = getIntent().getStringExtra(NUMBER);
        imgUrl = getIntent().getStringExtra(URL);
        adapter = new MaterAdapter();
        recycleView.setAdapter(adapter);
        recycleView.setOnMutilRecyclerViewListener(this);

        searchState();
        setListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }


    private void setListener() {
        imageButton.setOnClickListener(this);
    }

    private void searchState() {
        if (LoginUtil.getInfo(mContext) == null) return;
        WuLiuReq.ContentBean bean = new WuLiuReq.ContentBean();
        bean.setOrder_id(mOrderId);
        bean.setWaybill_number(mYunOrderId);

        WuLiuReq req = new WuLiuReq();
        req.setContent(bean);
        req.setCmd(Constant.order_logistics_query);

        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().order_logistics_query(requestBody, requestBody1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WuLiuModel>() {

            @Override
            public void onCompleted() {
                recycleView.stopRefresh();
                recycleView.stopLoadingMore();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WuLiuModel model) {

                String states = model.getStatus()+"";
                if (states.equals("-997")||states.equals("500")) {
                    //showEmpty(true, "暂时无法查询该物流,请联系客服", null);
                    default_ll.setVisibility(View.VISIBLE);
                } else {
                    default_ll.setVisibility(View.GONE);
                    recycleView.addHeaderView(getHeader());
                    adapter.addItems(model.getLogistics_status().getLogisticsArray());
                    setData(model);
                }


            }

        });
    }

    private View getHeader() {

        View view = LayoutInflater.from(this).inflate(R.layout.act_mater_header, null);
        state = (TextView) view.findViewById(R.id.mater_state);
        kuaiDi = (TextView) view.findViewById(R.id.mater_kuai);
        yunText = (TextView) view.findViewById(R.id.yun_order);
        numText = (TextView) view.findViewById(R.id.num_count);
        imageView = (ImageView) view.findViewById(R.id.ic_lunch);
        yunText.setText(mYunOrderId);
        numText.setText("共" + mOrderNum + "件商品");
        if (!StringUtil.isEmpty(imgUrl)) {
            Picasso.with(this)
                    .load(imgUrl)
                    .into(imageView);
        }

        return view;
    }

    private void setData(WuLiuModel loginModel) {

        String status = loginModel.getLogistics_status().getState()+"";

        if (status.equals("0")) {
            state.setText("在途中");
        } else if (status.equals("1")) {
            state.setText(" 已揽收");
        } else if (status.equals("2")) {
            state.setText("疑难");
        } else if (status.equals("3")) {
            state.setText("已签收");
        } else if (status.equals("4")) {
            state.setText("退签");
        } else if (status.equals("5")) {
            state.setText("同城派送中");
        } else if (status.equals("6")) {
            state.setText("退回");
        } else if (status.equals("7")) {
            state.setText("转单");
        }

        kuaiDi.setText(loginModel.getLogistics_status().getCompany());
        yunText.setText(mYunOrderId);
        numText.setText("共" + mOrderNum + "件商品");


    }

    @Override
    public void onRefresh() {
        recycleView.stopRefresh();
        recycleView.stopLoadingMore();
    }

    @Override
    public void onLoadMore() {

    }
}
