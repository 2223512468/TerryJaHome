package com.jajahome.feature.user.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.MessageAdapter;
import com.jajahome.model.SimpleModel;
import com.jajahome.model.UserMessageModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
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
 * 消息中心
 * Created by laotang on 2016/7/22.
 */
public class NewsAct extends BaseActivity implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener, MessageAdapter.OnMessageItemOperation {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private MessageAdapter mAdapter;
    int offset = 1;
    private boolean isFirst = true;

    @Override
    protected int getViewId() {
        return R.layout.act_news;
    }

    @Override
    protected void initEvent() {
        initViewController(recyclerView);
        ibtnBack.setOnClickListener(this);
        mAdapter = new MessageAdapter();
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);
        getUserMessage();
        recyclerView.setOnMutilRecyclerViewListener(this);
    }

    /**
     * 获取用户消息
     */
    private void getUserMessage() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);

        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);

        ReqPage reqPage = new ReqPage();
        reqPage.setCmd(Constant.USER_MESSAGE);
        reqPage.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getUserMessage(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMessageModel>() {
                    @Override
                    public void onCompleted() {
                        recyclerView.stopRefresh();
                        recyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserMessageModel userMessageModel) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<UserMessageModel.DataBean.MessageBean> list = userMessageModel.getData().getMessage();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "暂无消息", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "暂无消息", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(userMessageModel.getData().getMessage());
                        }
                    }
                });
    }


    /**
     * 用户消息处理
     *
     * @param operation :1已读 2删除
     * @param index
     * @param id
     */
    private void operation(final int operation, final int index, int id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_MESSAGE_OPERATION);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(id);
        contentBean.setOperation(operation);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        if (operation == 2) {
            showProgressDialog("正在删除");
        }
        ApiImp.get().operationMessage(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onNext(SimpleModel deleteModel) {
                        if (deleteModel.getStatus() == 0) {
                            if (operation == 2) {
                                mAdapter.remove(index);
                                mAdapter.notifyDataSetChanged();
                            } else if (operation == 1) {
                                mAdapter.setMessageRead(index);
                            }
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }

    /**
     * 下啦刷新
     */
    @Override
    public void onRefresh() {
        offset = 1;
        getUserMessage();
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        offset++;
        getUserMessage();
    }

    /**
     * 对消息进行操作
     *
     * @param operation ：1标识消息已读 2删除
     * @param index     ：index
     * @param id        :消息id
     */
    @Override
    public void onMessageOperation(int operation, int index, int id) {
        operation(operation, index, id);
    }
}
