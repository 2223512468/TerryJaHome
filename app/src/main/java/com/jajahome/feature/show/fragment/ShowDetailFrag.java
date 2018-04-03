package com.jajahome.feature.show.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.feature.show.adapter.ShowListAdapter;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.ShowListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.cacheutils.CacheUtils;
import com.jajahome.util.cacheutils.NetUtils;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/12.
 */
public class ShowDetailFrag extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener {

    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;

    public static final String STATUS = "STATUS";
    private int mStatus;
    private ShowListAdapter mAdapter; //列表adapter
    private ConfigModel configModel; //筛选配置
    private int offset = 1; //页码
    private boolean isFirst = true; //是否第一次加载
    private int idArray[];
    private Gson gson = new Gson();
    private ShowListModel showListModel;
    public static String dirName = "show.txt";


    @Override
    protected int getViewId() {
        return R.layout.act_show_detail_frag;
    }

    @Override
    protected void init() {
        mStatus = getArguments().getInt(STATUS, 0);
        initViewController(recycleView);
        mAdapter = new ShowListAdapter();
        recycleView.setAdapter(mAdapter);
        recycleView.setOnMutilRecyclerViewListener(this);
        showLoading(true, "");
        if (mStatus == 1) {
            idArray = new int[]{2, 4};
        } else if (mStatus == 2) {
            idArray = new int[]{3};
        } else if (mStatus == 3) {
            idArray = new int[]{5};
        } else if (mStatus == 4) {
            idArray = new int[]{1};
        }
        getCacheData();
    }

    public static ShowDetailFrag newInstance(int satus) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, satus);
        ShowDetailFrag imagePageFragment = new ShowDetailFrag();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    private void getCacheData() {
        if (!NetUtils.isNetworkAvailable(getActivity())) {
            try {
                String jsonModel = CacheUtils.readData(dirName);
                if (!StringUtil.isEmpty(jsonModel)) {
                    showListModel = gson.fromJson(jsonModel, ShowListModel.class);
                    setShowAdapter(showListModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getConfig();
        }
    }

    /**
     * 获取配置信息 ,先取本地存储 本地没有从网络获取
     */
    private void getConfig() {
        Observable<ConfigModel> obsNet = ApiImp.get().
                config(HttpUtil.getRequestBody(Constant.CONFIG_V2), HttpUtil.getSession(getContext()));

        Observable<ConfigModel> obsShare = Observable.create(new Observable.OnSubscribe<ConfigModel>() {
            @Override
            public void call(Subscriber<? super ConfigModel> subscriber) {
                subscriber.onNext(FilterSpUtil.getInfo(mContext));
                subscriber.onCompleted();
            }
        });
        Observable<ConfigModel> observer = Observable.concat(obsShare, obsNet).first(new Func1<ConfigModel, Boolean>() {
            @Override
            public Boolean call(ConfigModel configModel) {
                return configModel != null;
            }
        });
        mSubscription = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConfigModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ConfigModel model) {
                        configModel = model;
                        getList(idArray);
                        setListener();
                    }
                });
    }

    private void getList(int idArray[]) {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setIdArray(idArray);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.SHOWLIST2);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().showlist(requestBody).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ShowListModel>() {
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
                    public void onNext(ShowListModel model) {
                        String jsonModel = gson.toJson(model);
                        CacheUtils.writeData(jsonModel, dirName);
                        setShowAdapter(model);
                    }
                });
    }

    private void setShowAdapter(ShowListModel model) {
        if (offset == 1) {
            if (isFirst) {
                showLoading(false, "");
                isFirst = false;
            }
            List<ShowListModel.DataBean.ItemBean> list = model.getData().getItem();
            if (list == null || list.size() == 0) {
                mAdapter.clear();
                showEmpty(true, "未找到相关内容", null);
            } else {
                showEmpty(false, "未找到相关内容", null);
                mAdapter.resetItems(model.getData().getItem());
            }
        } else {
            mAdapter.addItems(model.getData().getItem());
        }
    }


    /**
     * 设置监听器
     */
    private void setListener() {

    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList(idArray);
    }

    @Override
    public void onLoadMore() {
        offset = 1 + offset;
        getList(idArray);
    }
}
