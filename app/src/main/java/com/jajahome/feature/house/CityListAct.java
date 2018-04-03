package com.jajahome.feature.house;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.application.JaJaHomeApplication;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.model.BuildingModel;
import com.jajahome.model.CityListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopCityFilter;
import com.jajahome.util.SpCityUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 找我家 楼盘列表页
 */
public class CityListAct extends BaseActivity implements PopCityFilter.OnCitySelectListener, MultiRecycleView.OnMutilRecyclerViewListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    /* @BindView(R.id.tvCity)
     TextView tvCity;
     @BindView(R.id.view_city)
     LinearLayout viewCity;*/
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private PopCityFilter mPopCityFilter;//城市选择器弹窗
    private CityListModel mCityListModel;//城市列表model
    private boolean isFirst = true;//是否为第一次加载
    private int offset = 1; //分页数据 第几页
    private int mCityId; //城市id
    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字
    private BuildingListAdapter mAdapter;
    private String userName;

    @Override
    protected int getViewId() {
        return R.layout.act_city_list;
    }

    @Override
    protected void initEvent() {
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);

        userName = getIntent().getStringExtra(Constant.LOCATION);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView2.setText("选择楼盘");

        initViewController(recyclerView);
        mAdapter = new BuildingListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
        mCityId = SpCityUtil.getCityCode(mContext);
        String name = SpCityUtil.getCityName(mContext);
        /*tvCity.setText(name);
        if (name.equals("全部")) {
            tvCity.setTextColor(mColorGray);
        } else {
            tvCity.setTextColor(mColorOrgin);
        }*/
        getCityList();
        getList();
        if (!StringUtil.isEmpty(JaJaHomeApplication.city)) {
            userName = JaJaHomeApplication.city;
        }

      /*  if (userName != null) {
            tvCity.setText(userName);
        } else {
            tvCity.setText("全部");
        }*/

    }

    /**
     * 获取城市选择的数据
     */
    protected void getCityList() {
        mSubscription = ApiImp.get().citylist((HttpUtil.getRequestBody(Constant.CITYLIST))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<CityListModel>() {
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
                    public void onNext(CityListModel model) {
                        CityListAct.this.mCityListModel = model;

                        for (int i = 0; i < model.getData().getCity().size(); i++) {

                            if (userName != null) {
                                if (userName.equals(model.getData().getCity().get(i).getText())) {
                                    mCityId = model.getData().getCity().get(i).getId();
                                    getList();
                                }
                            }

                            for (int j = 0; j < model.getData().getCity().get(i).getItems().size(); j++) {

                                if (userName != null) {
                                    if (userName.equals(model.getData().getCity().get(i).getItems().get(j).getText())) {
                                        mCityId = model.getData().getCity().get(i).getItems().get(j).getId();
                                        getList();
                                    }
                                }
                            }
                        }

                      /*  viewCity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showFilterPop();
                            }
                        });*/


                    }
                });
    }


    /**
     * 显示城市选择弹窗
     */
    private void showFilterPop() {
        if (mPopCityFilter == null) {
            mPopCityFilter = new PopCityFilter(mContext, mCityListModel);
            mPopCityFilter.setListener(this);
        }
        // mPopCityFilter.show(viewCity);
    }

    /**
     * 选择完城市回调
     *
     * @param id
     * @param text
     */
    @Override
    public void onCitySelect(int id, String text) {
        SpCityUtil.setCityCode(mContext, id);
        SpCityUtil.setCityName(mContext, text);
        mCityId = id;
        // tvCity.setText(text);
        if (text.equals("全部")) {
            //     tvCity.setTextColor(mColorGray);
        } else {
            //     tvCity.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        if (mCityId > 0)
            contentBean.setCity(mCityId);
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.BUILDING_LIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().buildingList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<BuildingModel>() {
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
                    public void onNext(BuildingModel model) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<BuildingModel.DataBean.BuildingBean> list = model.getData().getBuilding();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "未找到相关信息", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "未找到相关信息", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(model.getData().getBuilding());
                        }
                    }
                });
    }

    /**
     * 设置分类 过滤 请求参数
     *
     * @return
     */
    private ReqPage.ContentBean.FilterBean getFilterBean() {
        ReqPage.ContentBean.FilterBean filterBean = new ReqPage.ContentBean.FilterBean();
        if (mCityId > 0) {
            filterBean.setCity(mCityId);
        }
        return filterBean;
    }

    /**
     * 选择过赛选器后重新获取列表
     */
    private void reLoadData() {
        isFirst = true;
        showLoading(true, "");
        offset = 1;
        getList();
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    @Override
    public void onLoadMore() {
        offset = 1 + offset;
        getList();
    }
}
