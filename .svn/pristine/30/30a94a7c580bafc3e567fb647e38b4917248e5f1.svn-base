package com.jajahome.feature.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.base.ImagePagerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.home.adapter.HomeGridAdapter;
import com.jajahome.feature.home.adapter.HomeGridModel;
import com.jajahome.feature.home.adapter.HomeListAdapter;
import com.jajahome.feature.home.adapter.ItemSetAdapter;
import com.jajahome.feature.home.adapter.SetItemAdapter;
import com.jajahome.feature.set.view.HomeRecyclerView;
import com.jajahome.model.HomeBannerModel;
import com.jajahome.model.IsSignModel;
import com.jajahome.model.RecommendModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.cacheutils.CacheUtils;
import com.jajahome.util.cacheutils.NetUtils;
import com.jajahome.widget.AutoGridView;
import com.jajahome.widget.banner.SyAutoScrollViewPager;
import com.jajahome.widget.banner.SyCirclePageIndicator;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页frag
 */
public class HomeFrag extends BaseFragment {
    public static final int ACTION_FILTER = 0X88;
    @BindView(R.id.multi_recycleview)
    MultiRecycleView multiRecycleview;

    private HomeGridAdapter.OnGridClickListener listener;
    private SyAutoScrollViewPager viewPager;
    private SyCirclePageIndicator indicator;
    ImagePagerAdapter adapters;
    AutoGridView mGirdView;
    private HomeListAdapter mAdapter;
    private String[] arrayFilters;
    HomeGridAdapter homeGridAdapter;
    private String userCity;
    private String cityUserName;
    public static String homeCacheR = "homer.txt";
    public static String homeCacheB = "homeb.txt";
    public static String homeCacheSI = "homesi.txt";
    private Gson gson = new Gson();
    private RecommendModel rModel, siModel;
    private HomeBannerModel bModel;
    private ImageView imgBackground, imgSecBackground;
    private HomeRecyclerView itemRecyclerView;
    private HomeRecyclerView setRecyclerView;
    private RelativeLayout itemTitle, setTitle;
    private ImageView itemTitleImv, setTitleImv;
    private TextView itemHomeTv, setHomeTv;


    @Override
    protected int getViewId() {
        return R.layout.frag_home;
    }

    @Override
    protected void init() {
        multiRecycleview.setRefreshEnable(false);
        multiRecycleview.setLoadMoreable(false);
        mAdapter = new HomeListAdapter();
        multiRecycleview.setAdapter(mAdapter);
        multiRecycleview.addHeaderView(getHeader());

        Bundle b = getArguments();
        userCity = b.getString(Constant.LOCATION, Constant.LOCATION_ACTION);
        cityUserName = b.getString(Constant.LOCATION_ACTION_CITY);
        setGrid();
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.LOCATION_ACTION);
        getActivity().registerReceiver(mLocationBroadcastReceiver, filter);
        getCacheData();
    }

    private LocationBroadcastReceiver mLocationBroadcastReceiver = new LocationBroadcastReceiver();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mLocationBroadcastReceiver);
        }
    }

    private void setGrid() {

        homeGridAdapter = new HomeGridAdapter(mContext, null, cityUserName);
        List<HomeGridModel> listGrid = new ArrayList<>();
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_01));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_02));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_03));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_04));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_05));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_06));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_07));
        listGrid.add(new HomeGridModel(R.mipmap.ic_home_10));
        //完整家居
        listGrid.add(new HomeGridModel(R.mipmap.ic_qiandao));
        homeGridAdapter.setItems(listGrid);
        homeGridAdapter.setListener(listener);
        arrayFilters = getResources().getStringArray(R.array.home_room_filter);
        mGirdView.setAdapter(homeGridAdapter);
    }

    private void getCacheData() {
        if (!NetUtils.isNetworkAvailable(getActivity())) {
            try {
                String modJsonR = CacheUtils.readData(homeCacheR);
                String modJsonB = CacheUtils.readData(homeCacheB);
                String modRSI = CacheUtils.readData(homeCacheSI);
                if (StringUtil.isEmpty(modJsonR) || StringUtil.isEmpty(modJsonB)) {
                    imgBackground.setVisibility(View.VISIBLE);
                    imgSecBackground.setVisibility(View.VISIBLE);
                    return;
                } else {
                    imgBackground.setVisibility(View.GONE);
                    imgSecBackground.setVisibility(View.GONE);
                    rModel = gson.fromJson(modJsonR, RecommendModel.class);
                    mAdapter.resetItems(rModel.getData().getRecommend());
                    bModel = gson.fromJson(modJsonB, HomeBannerModel.class);
                    setBanAdapter(bModel);
                    siModel = gson.fromJson(modRSI, RecommendModel.class);
                    SetRecommSetItemAdapter(siModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            getBannerData();
            getRecomment();
            getRecommentSet();
        }
    }

    /**
     * 获取头部binner
     */
    private void getBannerData() {
        mSubscription = ApiImp.get().home(HttpUtil.getLocation(Constant.HOME, userCity)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HomeBannerModel>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeBannerModel model) {
                imgSecBackground.setVisibility(View.GONE);
                setBanAdapter(model);
                String modelJson = gson.toJson(model);
                CacheUtils.writeData(modelJson, homeCacheB);
            }
        });
    }

    private void setBanAdapter(HomeBannerModel model) {
        if (adapters == null) {
            adapters = new ImagePagerAdapter(getChildFragmentManager(), model.getData().getBanner());
            viewPager.setAdapter(adapters);
            indicator.setViewPager(viewPager);
            if (viewPager != null) {
                viewPager.startAutoScroll();
                viewPager.setInterval(2000);
            }
        } else {
            adapters = new ImagePagerAdapter(getChildFragmentManager(), model.getData().getBanner());
            viewPager.setAdapter(adapters);
            indicator.setViewPager(viewPager);
            if (viewPager != null) {
                viewPager.startAutoScroll();
                viewPager.setInterval(2000);
            }
        }
    }

    private void getSignInfo() {
        if (LoginUtil.getInfo(mContext) == null) return;
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.GETSIGNINFO);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        ApiImp.get().get_sign_info(requestBody, requestBody1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<IsSignModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(IsSignModel model) {
                        if (model.getData().getSign() == 1) {
                            homeGridAdapter.isHasMsg(true);
                        } else if (model.getData().getSign() == 0) {
                            homeGridAdapter.isHasMsg(false);
                        }
                    }
                });
    }


    private void getRecomment() {
        mSubscription = ApiImp.get().recommend(HttpUtil.getLocation(Constant.RECOMMEND, userCity)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<RecommendModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RecommendModel model) {
                imgBackground.setVisibility(View.GONE);
                mAdapter.resetItems(model.getData().getRecommend());
                String modelJson = gson.toJson(model);
                CacheUtils.writeData(modelJson, homeCacheR);

            }
        });
    }

    private List<RecommendModel.DataBean.RecommendBean> itemList = new ArrayList<>();
    private List<RecommendModel.DataBean.RecommendBean> setList = new ArrayList<>();
    private ItemSetAdapter itemAdapter;
    private SetItemAdapter setAdapter;

    /**
     * 获取单品套装推荐
     */

    private void getRecommentSet() {
        mSubscription = ApiImp.get().recommendSet(HttpUtil.getLocation(Constant.RECOMMEND_SET, userCity)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<RecommendModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RecommendModel model) {
                String modelJson = gson.toJson(model);
                CacheUtils.writeData(modelJson, homeCacheSI);
                SetRecommSetItemAdapter(model);
            }
        });
    }

    private void SetRecommSetItemAdapter(RecommendModel model) {
        RecommendModel.DataBean data = model.getData();
        if (data == null) {
            setTitle.setVisibility(View.GONE);
            itemTitle.setVisibility(View.GONE);
            return;
        }
        List<RecommendModel.DataBean.RecommendBean> recommendList = model.getData().getRecommend();
        for (RecommendModel.DataBean.RecommendBean bean : recommendList) {
            if (bean.getAction().equals("set")) {
                if (setList.size() < 8) {
                    setList.add(bean);
                }
            } else if (bean.getAction().equals("item")) {
                if (itemList.size() < 8) {
                    itemList.add(bean);
                }
            }
        }

        if (itemList != null && itemList.size() > 0) {
            itemRecyclerView.setVisibility(View.VISIBLE);
            itemTitle.setVisibility(View.VISIBLE);
            itemHomeTv.setText("单品推荐");
            itemTitleImv.setImageResource(R.mipmap.ic_small_item);
            setHorizontalLayout(itemRecyclerView);
            itemAdapter = new ItemSetAdapter();
            itemRecyclerView.setAdapter(itemAdapter);
            itemAdapter.resetItems(itemList);
        } else {
            itemRecyclerView.setVisibility(View.GONE);
            itemTitle.setVisibility(View.GONE);
        }

        if (setList != null && setList.size() > 0) {
            setRecyclerView.setVisibility(View.VISIBLE);
            setTitle.setVisibility(View.VISIBLE);
            setHomeTv.setText("套装推荐");
            setTitleImv.setImageResource(R.mipmap.ic_small_set);
            setHorizontalLayout(setRecyclerView);
            setAdapter = new SetItemAdapter();
            setRecyclerView.setAdapter(setAdapter);
            setAdapter.resetItems(setList);
        } else {
            setRecyclerView.setVisibility(View.GONE);
            setTitle.setVisibility(View.GONE);
        }
    }


    /**
     * 获取首页列表头部
     *
     * @return 头部view
     */
    private View getHeader() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_home, null);
        viewPager = (SyAutoScrollViewPager) headerView.findViewById(R.id.autoScroll);
        indicator = (SyCirclePageIndicator) headerView.findViewById(R.id.indicator);
        mGirdView = (AutoGridView) headerView.findViewById(R.id.gridView);
        imgBackground = (ImageView) headerView.findViewById(R.id.first_lau);
        imgSecBackground = (ImageView) headerView.findViewById(R.id.sec_lau);
        itemRecyclerView = (HomeRecyclerView) headerView.findViewById(R.id.itemRecyclerView);
        setRecyclerView = (HomeRecyclerView) headerView.findViewById(R.id.setRecyclerView);
        itemTitle = (RelativeLayout) headerView.findViewById(R.id.item_title);
        setTitle = (RelativeLayout) headerView.findViewById(R.id.set_title);
        itemTitleImv = (ImageView) itemTitle.findViewById(R.id.homeImg);
        itemHomeTv = (TextView) itemTitle.findViewById(R.id.homeTitle);
        setTitleImv = (ImageView) setTitle.findViewById(R.id.homeImg);
        setHomeTv = (TextView) setTitle.findViewById(R.id.homeTitle);
        itemhomeMore = (TextView) itemTitle.findViewById(R.id.homeMore);
        sethomeMore = (TextView) setTitle.findViewById(R.id.homeMore);
        itemhomeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainAty.class);
                intent.putExtra(MainAty.ACTION, "-1");
                mContext.startActivity(intent);
            }
        });

        sethomeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, MainAty.class);
                intent.putExtra(MainAty.ACTION_CLASS, "set");
                mContext.startActivity(intent);
            }
        });

        return headerView;
    }

    private TextView itemhomeMore, sethomeMore;

    public void setHorizontalLayout(HomeRecyclerView mRecyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSignInfo();
        if (viewPager != null) {
            viewPager.startAutoScroll();
            viewPager.setInterval(2000);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (viewPager != null) {
            viewPager.stopAutoScroll();
        }
    }

    public void setListener(HomeGridAdapter.OnGridClickListener listener) {
        this.listener = listener;
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Constant.LOCATION_ACTION)) return;
            userCity = intent.getStringExtra(Constant.LOCATION);
            cityUserName = intent.getStringExtra(Constant.LOCATION_NAME);
            if (!StringUtil.isEmpty(cityUserName)) {
                getBannerData();
                getRecomment();
            }
            getActivity().unregisterReceiver(mLocationBroadcastReceiver);// 不需要时注销
            mLocationBroadcastReceiver = null;
        }
    }

}
