package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.UserLogAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.UserLogModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuhaizhu on 2017/4/26.
 */

public class UserLogAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener {
    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.ibtn_news)
    ImageButton mIbtnNews;
    @BindView(R.id.scale_onclick)
    ImageView mScaleOnclick;
    @BindView(R.id.set_mod)
    ImageView mSetMod;
    @BindView(R.id.set_vr)
    ImageView mSetVr;
    @BindView(R.id.recyclerView)
    MultiRecycleView mRecyclerView;
    private int offset = 1;
    private UserLogAdapter mUserLogAdapter;

    @Override
    protected int getViewId() {
        return R.layout.act_user_log;
    }

    @Override
    protected void initEvent() {
        mIbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTextView2.setText(R.string.my_user_log);
        initViewController(mRecyclerView);
        mUserLogAdapter = new UserLogAdapter();
        mRecyclerView.setAdapter(mUserLogAdapter);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setBackground(R.color.theme_bg);
        mRecyclerView.setOnMutilRecyclerViewListener(this);
        showLoading(true,"");
        getList();
        mUserLogAdapter.setmLongListener(new BaseRecyclerAdapter.OnItemLongClickListener(){
            @Override
            public void OnItemLongClick(int id,final int index) {
                LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
                builder.setMessage("是否删除该足迹?");
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
     * 删除足迹
     *
     * @param i
     */
    private void deleteItem(final int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_LOG_DELETE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setLog_id(Integer.parseInt(mUserLogAdapter.getDataList().get(i).getId()));
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showProgressDialog("正在删除");
        ApiImp.get().delFavorite(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddFavoriteModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddFavoriteModel deleteModel) {
                        dissmisProgressDialog();
                        if (deleteModel.getStatus() == 0) {
                            showToast("删除成功");
                            mUserLogAdapter.remove(i);
                            mUserLogAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.USER_LOG);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().user_log(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<UserLogModel>() {
                    @Override
                    public void onCompleted() {
                        mRecyclerView.stopRefresh();
                        mRecyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserLogModel model) {
                        List<UserLogModel.DataBean.ItemBean> list = model.getData().getItem();
                        if (offset == 1) {
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "还没有任何浏览记录哦", null);
                                mUserLogAdapter.clear();
                            } else {
                                showEmpty(false, "还没有任何浏览记录哦", null);
                                mUserLogAdapter.resetItems(list);
                            }
                        } else {
                            if (list == null || list.size() == 0) {
                            } else {
                                mUserLogAdapter.addItems(list);
                            }
                        }
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
        offset++;
        getList();
    }

}
