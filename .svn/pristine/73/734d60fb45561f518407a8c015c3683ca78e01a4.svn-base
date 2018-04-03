package com.jajahome.feature.diy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.diy.adapter.DiySelectItemListAdapter;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopItemBrandFilter;
import com.jajahome.pop.PopSingleFilter;
import com.jajahome.pop.adapter.PopItemStyleFilter;
import com.jajahome.pop.adapter.SingleFilterAdapter;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.io.Serializable;
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
 * DIY 选择单品的act
 */
public class DiySelectItemAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener,
        PopSingleFilter.OnSingleItemListener,
        View.OnClickListener, PopItemBrandFilter.OnItemBrandSelectListener,PopItemStyleFilter.OnItemStyleListner  {
    public final static int RESULT_CODE=0X67;//返回结果码
    public final static String DATA = "DATA";//返回的选择单品数据


    public final static String ID = "ID";//套装位置id

    public final static String TYPE = "TYPE";// ** 0 所有模板 **  ** 1 我的列表 **
    public final static String POS = "POS";//那个单品

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字

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

    private DiySelectItemListAdapter mAdapter;
    private int offset = 1;
    ConfigModel configModel;
    private PopItemBrandFilter mPopBrand;
    private PopSingleFilter mPopColor;
    private PopItemStyleFilter mPopStyle;
    private PopSingleFilter mPopPrice;
    private int mBrandId;
    private boolean isFirst = true;
    private String mId;//`SET_POS` 编号
    private int id_type;//请求类型  ** 0 所有模板 **  ** 1 我的列表 **
    private int pos;//请求类型  ** 0 所有模板 **  ** 1 我的列表 **
    private int mStyleId;       //风格
    private List<SetItemModel.DataBean.ListBean> mSelectedList;
    @Override
    protected int getViewId() {
        return R.layout.act_select_diy_item;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mId = getIntent().getStringExtra(ID);
        id_type = getIntent().getIntExtra(TYPE, 0);
        pos = getIntent().getIntExtra(POS, 0);
        mSelectedList= (List<SetItemModel.DataBean.ListBean>) getIntent().getSerializableExtra(DATA);
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);
        mAdapter = new DiySelectItemListAdapter();
        mAdapter.setmSelectedList(mSelectedList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setGridLayout(2);
        recyclerView.setOnMutilRecyclerViewListener(this);
        getConfig();
        initViewController(recyclerView);
        showLoading(true, "");
        getList();

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(mAdapter!=null){
                  List <SetItemModel.DataBean.ListBean> list=mAdapter.getSelectList();
                  Intent intent=getIntent();
                  Bundle bundle=new Bundle();
                  bundle.putInt(POS,pos);
                  bundle.putSerializable(DATA, (Serializable) list);
                //  EventBus.getDefault().post(new EventMessage(9,Constant.DIDSELECTED));
                  intent.putExtras(bundle);
                  DiySelectItemAct.this.setResult(RESULT_CODE,intent);
                  DiySelectItemAct.this.finish();
              }else {
                  finish();
              }
            }
        });
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

    /**
     * 设置监听器
     */
    private void setListener() {
        viewBrand.setOnClickListener(this);
        viewColor.setOnClickListener(this);
        viewStyle.setOnClickListener(this);
        viewPrice.setOnClickListener(this);
    }

    /**
     * 获取配置信息 ,先取本地存储 本地没有从网络获取
     */
    private void getConfig() {
        Observable<ConfigModel> obsNet = ApiImp.get().
                config(HttpUtil.getRequestBody(Constant.CONFIG_V2),HttpUtil.getSession(this));

        Observable<ConfigModel> obsShare = Observable.create(new Observable.OnSubscribe<ConfigModel>() {
            @Override
            public void call(Subscriber<? super ConfigModel> subscriber) {
                subscriber.onNext(FilterSpUtil.getInfo(mContext));
                subscriber.onCompleted();
            }
        });
        Observable<ConfigModel> observer = Observable.concat(obsShare, obsNet).first(new Func1<ConfigModel, Boolean>() {
            @Override
            public Boolean call(ConfigModel configModel) {
                return configModel != null;
            }
        });
        mSubscription = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConfigModel>() {
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
                    public void onNext(ConfigModel model) {
                        configModel = model;
                        setListener();
                    }
                });
    }

    /**
     * 获取单品列表
     */
    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        //第几页
        paginationBean.setOffset(offset);
        //每页数据
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setFilter(getFilterBean());
        //单品位置id
        contentBean.setId(mId);
        contentBean.setId_type(String.valueOf(id_type));
        //全部
        contentBean.setType("1");
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.DIY_ITEMLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getDiyItemList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<SetItemModel>() {
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
                    public void onNext(SetItemModel model) {
                        if (offset == 1) {
                            mAdapter.resetItems(model.getData().getList());
                        } else {
                            mAdapter.addItems(model.getData().getList());
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        offset = 1;
        getList();
    }

    /**
     * 加载更多
     */
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
            case R.id.viewBrand://选择品牌
                if (mPopBrand == null) {
                    mPopBrand = new PopItemBrandFilter(mContext, configModel);
                    mPopBrand.setListener(this);
                }
                mPopBrand.show(recyclerView);
                break;
            case R.id.viewStyle://选择风格
                if (mPopStyle == null) {
                    mPopStyle = new PopItemStyleFilter(mContext,configModel);
                    mPopStyle.setListener(this);
                }
                mPopStyle.show(recyclerView);
                break;
            case R.id.viewColor://选择色系
                if (mPopColor == null) {
                    mPopColor = new PopSingleFilter(mContext, SingleFilterAdapter.ACTION_ITEM_COLOR, configModel);
                    mPopColor.setOnSingleItemListener(this);
                }
                mPopColor.show(recyclerView);
                break;
            case R.id.viewPrice://选择价格
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
     * @return 过滤 请求参数
     */
    private ReqPage.ContentBean.FilterBean getFilterBean() {
        ReqPage.ContentBean.FilterBean filterBean = new ReqPage.ContentBean.FilterBean();
        if (mItemColorBean != null) { //色系
            filterBean.setColor(mItemColorBean.getId());
        }
        if (mStyleId>0) {//风格
            filterBean.setStyle(mStyleId);
        }
        if (mItemPriceBean != null) { //价格
            filterBean.setPrice(mItemPriceBean);
        }
        if (mBrandId > 0) {      //品牌
            filterBean.setBrand(mBrandId);
        }
        return filterBean;
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
}
