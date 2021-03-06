package com.jajahome.feature.user.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.ValueListAdapter;
import com.jajahome.model.ValueListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ValueReq;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${Terry} on 2017/9/12.
 */
public class CouFrag extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener {

    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;

    private ValueListAdapter listAdapter;
    private boolean isFirst = true;
    private int offset = 1;
    private static String STATUS = "STATUS";
    private int mStatus;
    private boolean isFragmentVisible = false; //当前页是否显示
    private boolean isStatusChanged = false; //订单状态是否有变化 默认无变化


    public static int REFRESH = 0x110;


    @Override
    protected int getViewId() {
        return R.layout.act_cou_frag;
    }

    @Override
    protected void init() {
        initViewController(recycleView);
        Bundle b = getArguments();
        mStatus = b.getInt(STATUS);
        listAdapter = new ValueListAdapter(mStatus,mContext);
        recycleView.setAdapter(listAdapter);
        recycleView.setOnMutilRecyclerViewListener(this);
        getList();
    }


    public static CouFrag newInstance(int status) {
        CouFrag couFrag = new CouFrag();
        Bundle b = new Bundle();
        b.putInt(STATUS, status);
        couFrag.setArguments(b);
        return couFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        isFragmentVisible = true;
        if (isStatusChanged) {
            refresh();
        }
    }


    private void refresh() {
        isFirst = true;
        showLoading(true, "");
        offset = 1;
        getList();
    }


    @Override
    public void onPause() {
        super.onPause();
        isFragmentVisible = false;
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == REFRESH) {
            if (isFragmentVisible) {
                refresh();
            } else {
                isStatusChanged = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    @Override
    public void onLoadMore() {
        offset = offset + 1;
        getList();
    }


    private void getList() {
        ValueReq.ContentBean.PaginationBean paginationBean = new ValueReq.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ValueReq.ContentBean contentBean = new ValueReq.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setType(mStatus);
        ValueReq req = new ValueReq();
        req.setCmd(Constant.COUPON_LIST);
        req.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().coupon_list(requestBody, HttpUtil.getSession(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ValueListModel>() {
                    @Override
                    public void onCompleted() {
                        recycleView.stopRefresh();
                        recycleView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ValueListModel actionModel) {
                        List<ValueListModel.DataBean.CouponItemBean> itemList = actionModel.getData().getCoupon_item();
                        if (offset == 1) {
                            if (isFirst) {
                                isFirst = false;
                                showLoading(false, "");
                            }
                            if (itemList == null || itemList.size() == 0) {
                                showEmpty(true, "未获取到优惠券", null);
                            } else {
                                listAdapter.resetItems(itemList);
                            }
                        } else {
                            listAdapter.addItems(itemList);
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listAdapter != null) {
            listAdapter.destory();
        }
    }
}
