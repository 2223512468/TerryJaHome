package com.jajahome.feature.user.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.SetCollectAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.SetListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.T;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by laotang on 2016/7/22.
 */
public class SetFragment extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener {
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private SetCollectAdapter mAdapter;


    @Override
    protected int getViewId() {
        return R.layout.frag_set_collect;
    }

    @Override
    protected void init() {


        initViewController(recyclerView);
        mAdapter = new SetCollectAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setGridLayout(2);
        recyclerView.setOnMutilRecyclerViewListener(this);
        getSetCollect();
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
     * 喊出套装收藏
     *
     * @param i
     */
    private void deleteItem(final int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.parseInt(mAdapter.getDataList().get(i).getId()));
        contentBean.setType(0);
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

    private int offset = 1;

    private void getSetCollect() {

        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.FAVORITE_SET);

        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().getSetCollect(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SetListModel>() {
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
                    public void onNext(SetListModel setListModel) {
                        List<SetListModel.DataBean.SetBean> set = setListModel.getData().getSet();
                        if (offset == 1) {
                            if (set == null || set.size() == 0) {
                                showEmpty(true, "还没有收藏的套装哦", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "还没有收藏的套装哦", null);
                                mAdapter.resetItems(set);
                            }
                        } else {
                            if (set == null || set.size() == 0) {
                            } else {
                                mAdapter.addItems(set);
                            }
                        }
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getSetCollect();
    }

    @Override
    public void onLoadMore() {
        offset++;
        getSetCollect();
    }


    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == 5) {
            Log.i("--SetFra5", "5");
            int index = Integer.parseInt(event.msg);
            getSetCollect();
            deleteItem(index);
        }
    }
}
