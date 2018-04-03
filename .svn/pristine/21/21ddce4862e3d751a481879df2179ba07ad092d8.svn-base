package com.jajahome.feature.house;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.house.adapter.HouseListAdapter;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.HouseListModel;
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
 * 找我家 户型列表页
 */
public class HouseListAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener, HouseListAdapter.HouseListener {

    public final static String TITLE = "title"; //标题
    public final static String ID = "ID";// 楼盘id
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private boolean isFirst = true;//是否为第一次加载
    private int offset = 1; //分页数据 第几页
    private String mId; //楼盘id
    private HouseListAdapter mAdapter;

    @Override
    protected int getViewId() {
        return R.layout.act_house_list;
    }

    @Override
    protected void initEvent() {
        mId = getIntent().getStringExtra(ID);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView2.setText(getIntent().getStringExtra(TITLE));
        initViewController(recyclerView);
        mAdapter = new HouseListAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
        recyclerView.setOnMutilRecyclerViewListener(this);
        getList();
    }

    /**
     * 获取楼盘信息
     */
    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setId(mId);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.HOUSE_LIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().houseList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<HouseListModel>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            showLoading(false, "");
                            isFirst = false;
                        }
                        recyclerView.stopRefresh();
                        recyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(HouseListModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<HouseListModel.DataBean.HouseBean> list = model.getData().getHouse();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "未找到相关户型", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "未找到相关户型", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(model.getData().getHouse());
                        }
                    }
                });
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    //加载更多
    @Override
    public void onLoadMore() {
        offset = 1 + offset;
        getList();
    }

    @Override
    public void onAddHouse(String id, ImageView colView) {
        addFav(id, colView);
    }

    /**
     * 添加收藏
     */
    private void addFav(String id, final ImageView colView) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(id));
        contentBean.setType(3);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("收藏中");
        mSubscription = ApiImp.get().addFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
            @Override
            public void onCompleted() {
                dissmisProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dissmisProgressDialog();
                e.printStackTrace();
            }

            @Override
            public void onNext(AddFavoriteModel model) {
                showToast(R.string.collect_success);
                colView.setImageResource(R.mipmap.ic_house_select);
            }
        });
    }
}
