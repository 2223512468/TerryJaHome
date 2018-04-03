package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.base.BaseFragment;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.diy.fragment.DiyFrag;
import com.jajahome.feature.user.adapter.MyProjectAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.DiyListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的方案
 * Created by laotang on 2016/7/19.
 */
public class MyProjectAct extends BaseFragment implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener {
   // @BindView(R.id.ibtn_back)
   // ImageButton ibtnBack;
  //  @BindView(R.id.textView2)
  //  TextView textView2;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private MyProjectAdapter mAdapter;
    /**
     * 方案列表集合
     */

    @Override
    protected int getViewId() {
        return R.layout.act_my_project;
    }

    @Override
    protected void init() {
        initViewController(recyclerView);
        mAdapter = new MyProjectAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
        recyclerView.setGridLayout(2);
     //   ibtnBack.setOnClickListener(this);
        getDiyList();
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
                        deleteDiy(index);

                    }
                });
                builder.create().show();
            }
        });
        mAdapter.setType(1);
    }

    /**
     * 删除diy收藏
     *
     * @param i
     */
    private void deleteDiy(final int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.MYDIY_DEL);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.parseInt(mAdapter.mList.get(i).getId()));
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
     * 当前页面位置
     */
    int offset = 1;
    /**
     * type 类型
     */
    int type = 1;
    /**
     * 获取diy列表
     */
    private void getDiyList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setType(String.valueOf(type));
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.DIYLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().diyList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<DiyListModel>() {
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
                    public void onNext(DiyListModel model) {
                        if (offset == 1) {
                         List<DiyListModel.DataBean.SetBean>  list = model.getData().getSet();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "未找到相关方案", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "未找到相关方案", null);
                                mAdapter.resetItems(model.getData().getSet());
                            }
                        } else {
                            mAdapter.addItems(model.getData().getSet());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前界面
                 */
              //  finish();
                break;
        }
    }

    /**
     * 下啦刷新
     *
     * @param
     */
    @Override
    public void onRefresh() {
        offset = 1;
        getDiyList();
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        offset = 1 + offset;
        getDiyList();
    }

 /*   @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(new EventMessage(DiyFrag.ACTION_DIY_CHANGE, ""));
    }*/
}
