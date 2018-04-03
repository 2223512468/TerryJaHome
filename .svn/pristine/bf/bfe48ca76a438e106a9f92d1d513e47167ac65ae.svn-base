package com.jajahome.feature.user.fragment;

import android.content.DialogInterface;
import android.util.Log;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.ShowCollectAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.ShowModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.T;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by laotang on 2016/7/22.
 */
public class ShowFragment extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener {
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;

    private ShowCollectAdapter mAdapter;

    @Override
    protected int getViewId() {
        return R.layout.frag_show_collect;
    }

    @Override
    protected void init() {


        initViewController(recyclerView);
        mAdapter = new ShowCollectAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setGridLayout(2);
        recyclerView.setOnMutilRecyclerViewListener(this);
        getShowCollect();
        mAdapter.setmLongListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int id, final int index) {
                LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
                builder.setMessage("是否删除收藏?");
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
                        deleteItem(index);
                    }
                });
                builder.create().show();
            }
        });
    }

    /**
     * 删除秀家收藏
     *
     * @param i
     */
    private void deleteItem(final int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.parseInt(mAdapter.getDataList().get(i).getId()));
        contentBean.setType(2);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showDialog("正在删除");
        ApiImp.get().delFavorite(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddFavoriteModel>() {
                    @Override
                    public void onCompleted() {

                        dissmissDialog();
                        T.showShort(getContext(), "删除成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddFavoriteModel deleteModel) {
                        if (deleteModel.getStatus() == 0) {
                            mAdapter.remove(i);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    /**
     * 当前页数
     */
    int offset = 1;

    /**
     * 网络获取秀家收藏列表信息
     */
    private void getShowCollect() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.FAVORITE_SHOW);

        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().getShowCollect(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShowModel>() {
                    @Override
                    public void onCompleted() {
                        recyclerView.stopLoadingMore();
                        recyclerView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        T.showShort(mContext, getString(R.string.networkProblem));
                    }

                    @Override
                    public void onNext(ShowModel showModel) {
                        List<ShowModel.DataBean.ShowBean> show = showModel.getData().getShow();
                        String json = gson.toJson(showModel);
                        Log.i("--ShowFr",json);

                        if (offset == 1) {
                            if (show == null || show.size() == 0) {
                                showEmpty(true, "还没有收藏的秀家哦", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "还没有收藏的秀家哦", null);
                                mAdapter.resetItems(showModel.getData().getShow());
                            }
                        } else {
                            if (show == null || show.size() == 0) {
                            } else {
                                mAdapter.addItems(show);
                            }
                        }
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        offset = 1;
        getShowCollect();
    }

    /**
     * 上啦加载
     */
    @Override
    public void onLoadMore() {
        offset++;
        getShowCollect();
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == 3) {
            Log.i("--SetFra3", "3");
            int index = Integer.parseInt(event.msg);
            getShowCollect();
            deleteItem(index);
        }
    }
}
