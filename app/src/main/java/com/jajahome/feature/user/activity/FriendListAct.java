package com.jajahome.feature.user.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.InviteAdapter;
import com.jajahome.model.InviteModel;
import com.jajahome.model.LoginModle;
import com.jajahome.network.ApiImp;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
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
 * 邀请的好友列表
 * Created by laotang on 2016/7/20.
 */
public class FriendListAct extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, MultiRecycleView.OnMutilRecyclerViewListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_much)
    TextView tvMuch;
    @BindView(R.id.tv_person_two)
    TextView tvPersonTwo;
    @BindView(R.id.tv_much_two)
    TextView tvMuchTwo;
    @BindView(R.id.tv_person_total)
    TextView totalPerson;
    @BindView(R.id.tv_my)
    TextView tvMy;

    /**
     * 好友列表的适配器
     */
    private InviteAdapter mAdapter;


    @Override
    protected int getViewId() {
        return R.layout.act_friend_list;
    }

    @Override
    protected void initEvent() {

        if (LoginUtil.getInfo(mContext) != null && LoginUtil.getInfo(mContext).getData() != null) {
            String inviteCode = LoginUtil.getInfo(mContext).getData().getUser().getInvitecode();
            if (StringUtil.isEmpty(inviteCode)) {
                showToast("还没有邀请的好友哦");
            } else {
                getInviteList();
            }
        }
        ibtnBack.setOnClickListener(this);
        initViewController(recyclerView);
        mAdapter = new InviteAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
    }

    int offset = 1;

    /**
     * 获取邀请人列表
     */

    private void getInviteList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        ReqPage reqPage = new ReqPage();
        reqPage.setCmd(Constant.INVITE_LIST);
        reqPage.setContent(contentBean);
        Gson gson = new Gson();
        LoginModle.DataBean.SessionBean sessionBean = null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        if (LoginUtil.getInfo(mContext) != null && LoginUtil.getInfo(mContext).getData() != null) {
            sessionBean = LoginUtil.getInfo(mContext).getData().getSession();
        }
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("applicationssion"), gson.toJson(sessionBean));
        mSubscription = ApiImp.get().getInviteList(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InviteModel>() {
                    @Override
                    public void onCompleted() {
                        recyclerView.stopRefresh();
                        recyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        T.showShort(mContext, getString(R.string.networkProblem));
                    }

                    @Override
                    public void onNext(InviteModel inviteModel) {
                        List<InviteModel.DataBean.InvitesBean> invites = inviteModel.getData().getInvites();
                        InviteModel.DataBean.TotalBean total = inviteModel.getData().getTotal();
                        if (total != null) {
                            tvMuch.setText("￥" + total.getOne_payAmounts());
                            tvMuchTwo.setText("￥" + total.getTwo_payAmounts() + "");
                            tvPerson.setText(total.getOne_friends() + "人");
                            tvPersonTwo.setText(total.getTwo_friends() + "人");
                            totalPerson.setText(total.getOne_friends() + total.getTwo_friends() + "人");
                            tvMy.setText("￥" + total.getMy_payAmounts());
                        }

                        if (offset == 1) {
                            if (invites == null || invites.size() == 0) {
                                showEmpty(true, "还没有邀请的好友哦", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "还没有邀请的好友哦", null);
                                mAdapter.resetItems(inviteModel.getData().getInvites());
                            }
                        } else {
                            if (invites == null || invites.size() == 0) {
                            } else {
                                mAdapter.addItems(invites);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前页面
                 */
                finish();
                break;
        }
    }


    @Override
    public void onLoadMore() {
        offset++;
        getInviteList();
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getInviteList();
    }
}
