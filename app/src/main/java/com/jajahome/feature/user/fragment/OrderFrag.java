package com.jajahome.feature.user.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.MyOrderListAdapter;
import com.jajahome.model.OrderListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的订单fragment
 */
public class OrderFrag extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener {

    private boolean isFragmentVisible=false; //当前页是否显示
    /**
     * 通知fragment订单状态有变化 更新数据
     */
    public final static int STATUS_CHANGE_ACTION=0X976;
    private boolean isStatusChanged=false; //订单状态是否有变化 默认无变化

    public static final String STATUS = "STATUS";
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    /**
     * 状态:
     * 0 - 待处理(未付款)
     * 1 - 已处理(已付款/待发货)
     * 2 - 订单取消(交易关闭)
     * 3 交易完成 (已收货）
     * 4 退款中
     * 5 退款成功(交易关闭)
     * 6 待询价
     * 7 已发货(待收货)
     * （statues为空，返回所有数据）
     */
    private int mStatus;
    /**
     * 页码
     */
    private int offset = 1;
    /**
     * 是否是首页获取数据
     */
    private boolean isFirst = true;
    private MyOrderListAdapter mAdapter;
    public static OrderFrag newInstance(int satus) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, satus);
        OrderFrag imagePageFragment = new OrderFrag();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_order_list;
    }

    @Override
    protected void init() {
        mStatus = getArguments().getInt(STATUS, 0);
        initViewController(recyclerView);
        mAdapter=new MyOrderListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
        showLoading(true,"");
        getList();
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    @Override
    public void onLoadMore() {
        offset = 1+offset;
        getList();
    }

    /**
     * 获取订单数据
     */
    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        if(mStatus!=-1){
            contentBean.setStatus(mStatus);
        }
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.ORDER_LIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().orderList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<OrderListModel>() {
                    @Override
                    public void onCompleted() {
                        recyclerView.stopRefresh();
                        recyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OrderListModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<OrderListModel.DataBean.ListBean> list = model.getData().getList();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "暂无相关订单", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "暂无相关订单", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(model.getData().getList());
                        }
                    }
                });
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);

        if(event.action==STATUS_CHANGE_ACTION){
            //订单状态变化 数据改变
            if(isFragmentVisible){
                //当前页面正在显示 立即刷新列表
                refresh();
            }else {
                isStatusChanged=true;
            }
        }
    }

    /**
     * 刷新页面
     */
    private void refresh(){
        isFirst=true;
        showLoading(true,"");
        offset=1;
        getList();
    }
    @Override
    public void onResume() {
        super.onResume();
        isFragmentVisible=true;
        //有订单状态变化 更新列表
        if(isStatusChanged){
            refresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentVisible=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mAdapter!=null){
            mAdapter.destory();
        }
    }
}
