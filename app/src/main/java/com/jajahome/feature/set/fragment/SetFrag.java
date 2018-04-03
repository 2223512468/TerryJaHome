package com.jajahome.feature.set.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.set.fragment.adapter.SetListAdapter;
import com.jajahome.feature.set.view.SetImageView;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.SetListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopSetBrandFilter;
import com.jajahome.pop.PopSingleFilter;
import com.jajahome.pop.adapter.PopSetStyleFilter;
import com.jajahome.pop.adapter.SingleFilterAdapter;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.cacheutils.CacheUtils;
import com.jajahome.util.cacheutils.NetUtils;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

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
 * 套装 首页列表
 */
public class SetFrag extends BaseFragment implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener,
        PopSingleFilter.OnSingleFilterSetListener, PopSetBrandFilter.OnItemBrandSelectListener, PopSetStyleFilter.OnSetStyleListner {
    @BindView(R.id.tvFilterRoom)
    TextView tvFilterRoom;
    @BindView(R.id.viewRoom)
    LinearLayout viewRoom;
    @BindView(R.id.tvFilterStyle)
    TextView tvFilterStyle;
    @BindView(R.id.viewStyle)
    LinearLayout viewStyle;
    @BindView(R.id.viewBrand)
    LinearLayout viewBrand;
    @BindView(R.id.tvFilterBrand)
    TextView tvFilterBrand;


    @BindView(R.id.tvFilterPrice)
    TextView tvFilterPrice;
    @BindView(R.id.viewPrice)
    LinearLayout viewPrice;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    ConfigModel configModel;
    private int offset = 1;
    private int mBrandId;
    private int mStyleId;
    private boolean isFirst = true;
    private SetListAdapter mAdapter;
    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字
    private PopSingleFilter mPopRoom;  //空间选择
    private PopSingleFilter mPopColor;//色系选择
    private PopSetStyleFilter mPopStyle, mSpopStyle;//风格选择
    private PopSetBrandFilter mPopBrand;//品牌选择
    private PopSingleFilter mPopPrice; //价格
    private String filter;
    private String brandFilter;//是否有完整家居 的筛选
    private SetListModel setListModel;
    public static String dirName = "SetFrag.txt";
    private Gson gson = new Gson();

    @Override
    protected int getViewId() {
        return R.layout.frag_set;
    }

    @Override
    protected void init() {
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);
        getConfig();
        initViewController(recyclerView);
        mAdapter = new SetListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
        showLoading(true, "");
        getCacheData();
        setRecyclerViewListener();

    }

    /**
     * 由首页中 点击进来  直接显示对于空间的套装列表
     *
     * @param string
     */
    public void onFilter(String string) {
        if (Constant.FULL_SET.equals(string)) { //完整家居
            brandFilter = Constant.FULL_SET;
            filter = null;
        } else {
            filter = string;
            brandFilter = null;
        }
        if (configModel != null) {
            filter();
        }
    }

    private void getCacheData() {

        if (!NetUtils.isNetworkAvailable(getActivity())) {
            try {
                String modelJson = CacheUtils.readData(dirName);
                if (!StringUtil.isEmpty(modelJson)) {
                    setListModel = gson.fromJson(modelJson, SetListModel.class);
                    setFragAdapter(setListModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getList();
        }
    }

    /**
     * 从首页传来的过滤
     */
    private void filter() {
        clearFilter();
        List<ConfigModel.DataBean.ConfigBean.SetRoomBean> list = configModel.getData().getConfig().getSet_room();
        List<ConfigModel.DataBean.ConfigBean.SetBrandBean> listBrand = configModel.getData().getConfig().getSet_brand();

        if (!StringUtil.isEmpty(filter) && !StringUtil.isEmpty(brandFilter)) {//两种筛选都不空
            //获取 其他筛选信息
            for (ConfigModel.DataBean.ConfigBean.SetRoomBean bean : list) {
                if (bean.getText().equals(filter)) {
                    mSetRoomBean = bean;
                    tvFilterRoom.setText(bean.getText());
                    tvFilterRoom.setTextColor(mColorOrgin);
                    break;
                }
            }
            //获取完整家居id
            for (ConfigModel.DataBean.ConfigBean.SetBrandBean bean : listBrand) {
                if (bean.getText().equals(brandFilter)) {
                    mBrandId = bean.getId();
                    tvFilterBrand.setText(Constant.FULL_SET);
                    tvFilterBrand.setTextColor(mColorOrgin);
                    break;
                }
            }
            reLoadData();
        } else if (!StringUtil.isEmpty(filter) && StringUtil.isEmpty(brandFilter)) {
            //只有其他筛选 （空间） 一种
            for (ConfigModel.DataBean.ConfigBean.SetRoomBean bean : list) {
                if (bean.getText().equals(filter)) {
                    mSetRoomBean = bean;
                    onRoomSelect(1, bean);
                    return;
                }
            }
        } else if (StringUtil.isEmpty(filter) && !StringUtil.isEmpty(brandFilter)) {
            //只有完整家居一个
            for (ConfigModel.DataBean.ConfigBean.SetBrandBean bean : listBrand) {
                if (bean.getText().equals(brandFilter)) {
                    mBrandId = bean.getId();
                    tvFilterBrand.setText(Constant.FULL_SET);
                    tvFilterBrand.setTextColor(mColorOrgin);
                    reLoadData();
                    break;
                }
            }
        }
    }

    /**
     * 获取配置信息 ,先取本地存储 本地没有从网络获取
     */
    private void getConfig() {
        //网络获取
        Observable<ConfigModel> obsNet = ApiImp.get().
                config(HttpUtil.getRequestBody(Constant.CONFIG_V2), HttpUtil.getSession(getContext()));
        //本地获取
        Observable<ConfigModel> obsShare = Observable.create(new Observable.OnSubscribe<ConfigModel>() {
            @Override
            public void call(Subscriber<? super ConfigModel> subscriber) {
                subscriber.onNext(FilterSpUtil.getInfo(mContext));
                subscriber.onCompleted();
            }
        });
        //设置本地取不到 网络取
        Observable<ConfigModel> observer = Observable.concat(obsShare, obsNet).first(new Func1<ConfigModel, Boolean>() {
            @Override
            public Boolean call(ConfigModel configModel) {
                return configModel != null;
            }
        });
        //设置本地取不到 网络取
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
                        setListener();
                        if (!StringUtil.isEmpty(filter) || !StringUtil.isEmpty(brandFilter)) {
                            filter();
                        }
                    }
                });
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        viewRoom.setOnClickListener(this);
        viewBrand.setOnClickListener(this);
        viewStyle.setOnClickListener(this);
        viewPrice.setOnClickListener(this);
    }

    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setFilter(getFilterBean());
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.SETLIST);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().setList(requestBody).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<SetListModel>() {


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
                    public void onNext(SetListModel model) {

                        String modelJson = gson.toJson(model);
                        CacheUtils.writeData(modelJson, dirName);
                        setFragAdapter(model);
                        EventBus.getDefault().post(new EventMessage(Constant.setTag, model.getData().getPagination().getTotal().toString()));
                    }

                });
    }

    private void setFragAdapter(SetListModel model) {
        if (offset == 1) {
            if (isFirst) {
                showLoading(false, "");
                isFirst = false;
            }
            List<SetListModel.DataBean.SetBean> list = model.getData().getSet();
            if (list == null || list.size() == 0) {
                showEmpty(true, "未找到相关套装", null);
                mAdapter.clear();
            } else {
                showEmpty(false, "未找到相关套装", null);
                mAdapter.resetItems(model.getData().getSet());
            }
        } else {
            mAdapter.addItems(model.getData().getSet());
        }
    }

    /**
     * 设置分类 过滤 请求参数
     *
     * @return
     */
    private ReqPage.ContentBean.FilterBean getFilterBean() {
        ReqPage.ContentBean.FilterBean filterBean = new ReqPage.ContentBean.FilterBean();
        if (mSetRoomBean != null) { //空间
            filterBean.setRoom(mSetRoomBean.getId());
        }

        if (mSetColorBean != null) { //色系
            filterBean.setColor(mSetColorBean.getId());
        }
        if (mStyleId > 0) {//风格
            filterBean.setStyle(mStyleId);
        }
        if (mSetPriceBean != null) {
            filterBean.setSetPrice(mSetPriceBean);
        }

        if (mBrandId > 0) {
            filterBean.setBrand(mBrandId);
        }
        return filterBean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewRoom://选择种类
                if (mPopRoom == null) {
                    mPopRoom = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_SET_ROOM, configModel);
                    mPopRoom.setOnSingleFilterSetListener(this);
                }
                mPopRoom.show(recyclerView);
                break;
            case R.id.viewBrand://选择品牌
                if (mPopBrand == null) {
                    mPopBrand = new PopSetBrandFilter(mContext, configModel);
                    mPopBrand.setListener(this);
                }
                mPopBrand.show(recyclerView);
                break;
            case R.id.viewStyle:
                if (mPopStyle == null) {
                    mPopStyle = new PopSetStyleFilter(mContext, configModel);
                    mPopStyle.setListener(this);
                }
                mPopStyle.show(recyclerView);

                break;
            case R.id.viewColor:
                if (mPopColor == null) {
                    mPopColor = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_SET_COLOR, configModel);
                    mPopColor.setOnSingleFilterSetListener(this);
                }
                mPopColor.show(recyclerView);
                break;
            case R.id.viewPrice:
                if (mPopPrice == null) {
                    mPopPrice = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_SET_PRICE, configModel);
                    mPopPrice.setOnSingleFilterSetListener(this);
                }
                mPopPrice.show(recyclerView);
                break;
        }
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

    @Override
    public void onSetBrandSelect(int id, String text) {
        mBrandId = id;
        tvFilterBrand.setText(text);
        brandFilter = null;
        if (text.equals("全部")) {
            tvFilterBrand.setTextColor(mColorGray);
        } else {
            tvFilterBrand.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    ConfigModel.DataBean.ConfigBean.SetRoomBean mSetRoomBean;

    @Override
    public void onRoomSelect(int index, ConfigModel.DataBean.ConfigBean.SetRoomBean bean) {
        mSetRoomBean = bean;
        if (index == 0) {
            tvFilterRoom.setTextColor(mColorGray);
            tvFilterRoom.setText(R.string.all);
        } else {
            tvFilterRoom.setText(bean.getText());
            tvFilterRoom.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    ConfigModel.DataBean.ConfigBean.SetColorBean mSetColorBean;

    @Override
    public void onColorSelect(int index, ConfigModel.DataBean.ConfigBean.SetColorBean bean) {
        mSetColorBean = bean;
        if (index == 0) {
            //tvFilterColor.setTextColor(mColorGray);
            // tvFilterColor.setText(R.string.all);
        } else {
            //  tvFilterColor.setText(bean.getText());
            // tvFilterColor.setTextColor(mColorOrgin);
        }
        reLoadData();
    }


    @Override
    public void onStyleSelect(int index, ConfigModel.DataBean.ConfigBean.SetStyleBean bean) {

    }

    ConfigModel.DataBean.ConfigBean.SetPriceBean mSetPriceBean;

    @Override
    public void onPriceSelect(int index, ConfigModel.DataBean.ConfigBean.SetPriceBean bean) {
        mSetPriceBean = bean;
        if (index == 0) {
            tvFilterPrice.setTextColor(mColorGray);
            tvFilterPrice.setText(R.string.all);
        } else {
            tvFilterPrice.setText(bean.getMin() + "-" + bean.getMax());
            tvFilterPrice.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    /**
     * 情空已选
     */
    private void clearFilter() {

        mPopRoom = null;
        mPopBrand = null;
        mPopStyle = null;
        mPopColor = null;

        mSetPriceBean = null;
        tvFilterPrice.setTextColor(mColorGray);
        tvFilterPrice.setText(R.string.all);
        mPopPrice = null;

        mStyleId = 0;
        tvFilterStyle.setTextColor(mColorGray);
        tvFilterStyle.setText(R.string.all);
        mPopStyle = null;

        mSetColorBean = null;
        //tvFilterColor.setTextColor(mColorGray);
        // tvFilterColor.setText(R.string.all);
        mPopColor = null;

        mSetRoomBean = null;
        tvFilterRoom.setTextColor(mColorGray);
        tvFilterRoom.setText(R.string.all);
        mPopRoom = null;

        mBrandId = 0;
        tvFilterBrand.setTextColor(mColorGray);
        tvFilterBrand.setText(R.string.all);
        mPopBrand = null;
    }

    @Override
    public void onSetStyleSelect(int id, String text) {
        mStyleId = id;
        tvFilterStyle.setText(text);
        brandFilter = null;
        if (text.equals("全部")) {
            tvFilterStyle.setTextColor(mColorGray);
        } else {
            tvFilterStyle.setTextColor(mColorOrgin);
        }

        reLoadData();
    }

    private Map<Integer, SetImageView> setImageViewMap;
    private int y;
    String model = android.os.Build.MODEL; // 手机型号

    private void setRecyclerViewListener() {
        setImageViewMap = mAdapter.getSetImageViewMap();
        int w = (int) DensityUtil.getDisplayWidthDp(mContext);

        if (model.equals("A31")) {
            return;
        } else {
            limit = (w * Constant.mHLimit) / Constant.mWLimit;
        }

        recyclerView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (dy > 0) {
                            y += 2;
                        } else {
                            y -= 2;
                        }
                        if (setImageViewMap != null) {
                            switch (action) {
                                case MotionEvent.ACTION_MOVE:
                                    for (Map.Entry e : setImageViewMap.entrySet()) {
                                        SetImageView image = (SetImageView) e.getValue();
                                        if (dy > 0 && y <= limit) {
                                            image.smoothScrollTo(0, y);
                                        } else if (dy < 0 && y > -limit) {
                                            image.smoothScrollTo(0, y);
                                        } else {
                                            if (dy > 0) {
                                                y = limit;
                                            } else if (dy < 0) {
                                                y = -limit;
                                            }
                                        }
                                    }
                                    break;
                                case MotionEvent.ACTION_UP:
                                    break;
                            }
                        }
                        return false;
                    }

                });
            }
        });
    }

    private int limit;
}
