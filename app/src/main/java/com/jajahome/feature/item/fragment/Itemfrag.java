package com.jajahome.feature.item.fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.application.JaJaHomeApplication;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.item.adapter.ItemListAdapter;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.ItemListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.DetailReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage1;
import com.jajahome.pop.PopItemBrandFilter;
import com.jajahome.pop.PopItemCategoryFilter;
import com.jajahome.pop.PopSingleFilter;
import com.jajahome.pop.adapter.PopItemStyleFilter;
import com.jajahome.pop.adapter.SingleFilterAdapter;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.cacheutils.CacheUtils;
import com.jajahome.util.cacheutils.NetUtils;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
 * 单品列表
 */
public class Itemfrag extends BaseFragment implements MultiRecycleView.OnMutilRecyclerViewListener,
        PopSingleFilter.OnSingleItemListener,
        View.OnClickListener,
        PopItemCategoryFilter.OnItemCategorySelectListener, PopItemBrandFilter.OnItemBrandSelectListener, PopItemStyleFilter.OnItemStyleListner {

    @BindView(R.id.item_first)
    TextView itemFirst;
    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字
    @BindView(R.id.tvFilterRoom)
    TextView tvFilterRoom;
    @BindView(R.id.viewRoom)
    LinearLayout viewRoom;
    @BindView(R.id.tvFilterStyle)
    TextView tvFilterStyle;
    @BindView(R.id.viewStyle)
    LinearLayout viewStyle;
    @BindView(R.id.tvFilterColor)
    TextView tvFilterColor;
    @BindView(R.id.viewColor)
    LinearLayout viewColor;
    @BindView(R.id.tvFilterBrand)
    TextView tvFilterBrand;
    @BindView(R.id.viewBrand)
    LinearLayout viewBrand;
    @BindView(R.id.tvFilterPrice)
    TextView tvFilterPrice;
    @BindView(R.id.viewPrice)
    LinearLayout viewPrice;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    private ItemListAdapter mAdapter;
    ConfigModel configModel;
    private PopItemCategoryFilter mPopCategory;
    private PopItemBrandFilter mPopBrand;
    private PopSingleFilter mPopColor;
    private PopItemStyleFilter mPopStyle;
    private PopSingleFilter mPopPrice;
    private int mCategoryId;       //分类id
    private int mStyleId;       //风格
    private int mBrandId;          //选择的品牌id
    private boolean isFirst = true;//是否为第一次加载
    private int offset = 1; //分页数据 第几页
    public static String dirName = "ItemFrag.txt";
    private Gson gson = new Gson();
    private ItemListModel itemListModel;
    private JaJaHomeApplication app;
    private String action;
    private String itemBrand = "brand";
    private String itemClass = "class";

    @Override
    protected int getViewId() {
        return R.layout.frag_item;
    }

    @Override
    protected void init() {
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);
        itemFirst.setText(R.string.category);
        app = (JaJaHomeApplication) getActivity().getApplication();
        String msgId = app.getmId();
        action = app.getAction();
        if (!StringUtil.isEmpty(msgId) && !StringUtil.isEmpty(action)) {
            if (action.equals(itemBrand)) {
                mBrandId = Integer.parseInt(msgId);
            } else if (action.equals(itemClass)) {
                mCategoryId = Integer.parseInt(msgId);
            }
        }


        getConfig();
        mAdapter = new ItemListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setGridLayout(2);
        recyclerView.setOnMutilRecyclerViewListener(this);
        initViewController(recyclerView);
        showLoading(true, "");
        getCacheData();
    }

    private void reLoadData() {
        isFirst = true;
        showLoading(true, "");
        offset = 1;
        getList();

    }

    /**
     * 设置监听器
     */
    private void setListener() {
        viewRoom.setOnClickListener(this);
        viewBrand.setOnClickListener(this);
        viewColor.setOnClickListener(this);
        viewStyle.setOnClickListener(this);
        viewPrice.setOnClickListener(this);
    }

    private void getCacheData() {
        if (!NetUtils.isNetworkAvailable(getActivity())) {
            String jsonModel = CacheUtils.readData(dirName);
            if (!StringUtil.isEmpty(jsonModel)) {
                try {
                    itemListModel = gson.fromJson(jsonModel, ItemListModel.class);
                    setItemFragAdapter(itemListModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            getList();
        }
    }
    // * 获取配置信息 ,先取本地存储 本地没有从网络获取
    private void getConfig() {
        //网络获取
        DetailReq req = new DetailReq();
        req.setCmd(Constant.CONFIG_V2);
        DetailReq.ContentBean contentBean = new DetailReq.ContentBean();
        /*contentBean.setCity(city);*/
        req.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        Observable<ConfigModel> obsNet = ApiImp.get().
                config_v2(requestBody, HttpUtil.getSession(getContext()));
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
                        if (!StringUtil.isEmpty(action) && action.equals(itemBrand)) {
                            List<ConfigModel.DataBean.ConfigBean.ItemBrandBean> itemBrandList = model.getData().getConfig().getItem_brand();
                            for (int i = 0; i < itemBrandList.size(); i++) {
                                if (hasNextLevel(i, mBrandId)) {
                                } else {
                                    if (mBrandId == itemBrandList.get(i).getId()) {
                                        tvFilterBrand.setText(itemBrandList.get(i).getText());
                                        tvFilterBrand.setTextColor(mColorOrgin);
                                    }
                                }
                            }
                            reLoadData();
                        }
                        if (!StringUtil.isEmpty(action) && action.equals(itemClass)) {
                            List<ConfigModel.DataBean.ConfigBean.ItemCategoryBean> itemCateList = model.getData().getConfig().getItem_category();
                            for (int i = 0; i < itemCateList.size(); i++) {
                                if (classHasNextLevel(i, mCategoryId)) {
                                } else {
                                    if (mCategoryId == itemCateList.get(i).getId()) {
                                        tvFilterRoom.setText(itemCateList.get(i).getText());
                                        tvFilterRoom.setTextColor(mColorOrgin);
                                    }
                                }
                            }
                            reLoadData();
                        }

                        setListener();
                    }
                });
    }


    /**
     * 设置请求参数 请求列表数据
     */
    private void getList() {

        ReqPage1.ContentBean.PaginationBean paginationBean = new ReqPage1.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage1.ContentBean contentBean = new ReqPage1.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setFilter(getFilterBean());
        if (!StringUtil.isEmpty(JaJaHomeApplication.city)) {
            contentBean.setCity(JaJaHomeApplication.city);
        }
        ReqPage1 reqPage = new ReqPage1();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.ITEM_LIST);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().itemList(requestBody).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ItemListModel>() {
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
                    public void onNext(ItemListModel model) {
                        EventBus.getDefault().post(new EventMessage(Constant.itemTag, model.getData().getPagination().getTotal().toString()));
                        String jsonModel = gson.toJson(model);
                        CacheUtils.writeData(jsonModel, dirName);
                        setItemFragAdapter(model);

                    }

                });
    }

    private void setItemFragAdapter(ItemListModel model) {
        if (offset == 1) {
            if (isFirst) {
                showLoading(false, "");
                isFirst = false;
            }
            List<ItemListModel.DataBean.ItemBean> list = model.getData().getItem();
            if (list == null || list.size() == 0) {
                showEmpty(true, "未找到相关单品", null);
                mAdapter.clear();
            } else {
                showEmpty(false, "未找到相关单品", null);
                mAdapter.resetItems(list);
            }
        } else {
            mAdapter.addItems(model.getData().getItem());
        }
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

    ConfigModel.DataBean.ConfigBean.ItemColorBean mItemColorBean;

    //色系选择 回调
    @Override
    public void onColorSelect(int index, ConfigModel.DataBean.ConfigBean.ItemColorBean bean) {
        mItemColorBean = bean;
        if (index == 0) {
            tvFilterColor.setTextColor(mColorGray);
            tvFilterColor.setText(R.string.all);
        } else {
            tvFilterColor.setText(bean.getText());
            tvFilterColor.setTextColor(mColorOrgin);
        }
        reLoadData();
    }


    //风格选择 回调
    @Override
    public void onStyleSelect(int index, ConfigModel.DataBean.ConfigBean.ItemStyleBean bean) {

    }

    ConfigModel.DataBean.ConfigBean.ItemPriceBean mItemPriceBean;

    //价格选择 回调
    @Override
    public void onPriceSelect(int index, ConfigModel.DataBean.ConfigBean.ItemPriceBean bean) {
        mItemPriceBean = bean;
        if (index == 0) {
            tvFilterPrice.setTextColor(mColorGray);
            tvFilterPrice.setText(R.string.all);
        } else {
            tvFilterPrice.setText(bean.getMin() + "-" + bean.getMax());
            tvFilterPrice.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewRoom://选择种类
                if (mPopCategory == null) {
                    mPopCategory = new PopItemCategoryFilter(mContext, configModel);
                    mPopCategory.setListener(this);
                }
                mPopCategory.show(recyclerView);
                break;
            case R.id.viewBrand://选择品牌
                if (mPopBrand == null) {
                    mPopBrand = new PopItemBrandFilter(mContext, configModel);
                    mPopBrand.setListener(this);
                }
                mPopBrand.show(recyclerView);
                break;
            case R.id.viewStyle:
                if (mPopStyle == null) {
                    mPopStyle = new PopItemStyleFilter(mContext, configModel);
                    mPopStyle.setListener(this);
                }
                mPopStyle.show(recyclerView);
                break;
            case R.id.viewColor:
                if (mPopColor == null) {
                    mPopColor = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_ITEM_COLOR, configModel);
                    mPopColor.setOnSingleItemListener(this);
                }
                mPopColor.show(recyclerView);
                break;
            case R.id.viewPrice:
                if (mPopPrice == null) {
                    mPopPrice = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_ITEM_PRICE, configModel);
                    mPopPrice.setOnSingleItemListener(this);
                }
                mPopPrice.show(recyclerView);
                break;
        }
    }

    /**
     * 设置分类 过滤 请求参数
     *
     * @return
     */
    private ReqPage1.ContentBean.FilterBean getFilterBean() {
        ReqPage1.ContentBean.FilterBean filterBean = new ReqPage1.ContentBean.FilterBean();
        if (mItemColorBean != null) { //色系
            filterBean.setColor(mItemColorBean.getId());
        }
        if (mStyleId > 0) {//风格
            filterBean.setStyle(mStyleId);
        }
        if (mItemPriceBean != null) {
            filterBean.setPrice(mItemPriceBean);
        }
        if (mCategoryId > 0) {
            filterBean.setCategory(mCategoryId);
        }
        if (mBrandId > 0) {
            filterBean.setBrand(mBrandId);
        }
        return filterBean;
    }

    @Override
    public void onItemCategorySelect(int id, String text) {
        mCategoryId = id;
        tvFilterRoom.setText(text);
        if (text.equals("全部")) {
            tvFilterRoom.setTextColor(mColorGray);
        } else {
            tvFilterRoom.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    @Override
    public void onItemBrandSelect(int id, String text) {
        mBrandId = id;
        tvFilterBrand.setText(text);
        if (text.equals("全部")) {
            tvFilterBrand.setTextColor(mColorGray);
        } else {
            tvFilterBrand.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    @Override
    public void onItemStyleSelect(int id, String text) {
        mStyleId = id;
        tvFilterStyle.setText(text);
        if (text.equals("全部")) {
            tvFilterStyle.setTextColor(mColorGray);
        } else {
            tvFilterStyle.setTextColor(mColorOrgin);
        }
        reLoadData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    //判断品牌是否有下一层

    private boolean hasNextLevel(int index, int id) {
        List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean> itemsList = configModel.getData().getConfig().getItem_brand().get(index).getItems();
        for (int i = 0; i < itemsList.size(); i++) {
            if (hasNextLevel2(index, id, i)) {

            } else {
                if (id == itemsList.get(i).getId()) {
                    tvFilterBrand.setText(itemsList.get(i).getText());
                    tvFilterBrand.setTextColor(mColorOrgin);
                    return true;
                }
            }
        }
        return false;
    }

    //判断品牌是否有第三层
    private boolean hasNextLevel2(int index, int id, int pos) {
        List<ConfigModel.DataBean.ConfigBean.ItemBrandBean.ItemsBean.ItemsBeans> itemsList2 = configModel.getData().getConfig().getItem_brand().get(index).getItems().get(pos).getItems();
        for (int i = 0; i < itemsList2.size(); i++) {
            if (id == itemsList2.get(i).getId()) {
                tvFilterBrand.setText(itemsList2.get(i).getText());
                tvFilterBrand.setTextColor(mColorOrgin);
                return true;
            }
        }
        return false;
    }

    //判断类别是否有下一层

    private boolean classHasNextLevel(int index, int id) {
        List<ConfigModel.DataBean.ConfigBean.ItemCategoryBean> itemCateList = configModel.getData().getConfig().getItem_category();
        for (int i = 0; i < itemCateList.size(); i++) {
            if (classHasNextLevel2(index, id, i)) {
            } else {
                if (id == itemCateList.get(i).getId()) {
                    tvFilterRoom.setText(itemCateList.get(i).getText());
                    tvFilterRoom.setTextColor(mColorOrgin);
                    return true;
                }
            }
        }
        return false;
    }

    //判断类别是否有第三层

    private boolean classHasNextLevel2(int index, int id, int pos) {
        List<ConfigModel.DataBean.ConfigBean.ItemCategoryBean.ItemsBean.ItemsBean2> itemsList = configModel.getData().getConfig().getItem_category().get(index).getItems().get(pos).getItems();
        for (int i = 0; i < itemsList.size(); i++) {
            if (id == itemsList.get(i).getId()) {
                tvFilterRoom.setText(itemsList.get(i).getText());
                tvFilterRoom.setTextColor(mColorOrgin);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
