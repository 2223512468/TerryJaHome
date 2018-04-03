package com.jajahome.feature.house;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
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
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.ArrayList;
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
 * 找我家 套装列表
 */
public class HouseSetListAct extends BaseActivity implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener,
        PopSingleFilter.OnSingleFilterSetListener, PopSetBrandFilter.OnItemBrandSelectListener, PopSetStyleFilter.OnSetStyleListner {

    public final static String TITLE = "title"; //标题
    public final static String ID = "ID";// 户型编号

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
    ConfigModel configModel;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    private int mStyleId;
    private int offset = 1;   //分页
    private int mBrandId;    //品牌
    private boolean isFirst = true;
    private SetListAdapter mAdapter;
    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字
    private PopSingleFilter mPopRoom;  //空间选择
    private PopSingleFilter mPopColor;//色系选择
    private PopSetStyleFilter mPopStyle;//风格选择
    private PopSetBrandFilter mPopBrand;//品牌选择
    private PopSingleFilter mPopPrice; //价格
    private String mHouseId;//户型编号
    List<SetListModel.DataBean.SetBean> listHad;

    @Override
    protected int getViewId() {
        return R.layout.act_house_set_list;
    }

    @Override
    protected void initEvent() {
        mHouseId = getIntent().getStringExtra(ID);
        textView2.setText(getIntent().getStringExtra(TITLE));
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);
        getConfig();
        initViewController(recyclerView);
        mAdapter = new SetListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnMutilRecyclerViewListener(this);
        // recyclerView.setGridLayout(2);
        setRecyclerViewListener();
        getList();
    }

    /**
     * 获取配置信息 ,先取本地存储 本地没有从网络获取
     */
    private void getConfig() {
        //网络获取
        Observable<ConfigModel> obsNet = ApiImp.get().
                config(HttpUtil.getRequestBody(Constant.CONFIG_V2), HttpUtil.getSession(this));
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
                    }
                });
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

    private void getList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(mHouseId);
        contentBean.setPagination(paginationBean);
        contentBean.setFilter(getFilterBean());
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.HOUSE_SETLIST);
        Gson gson = new Gson();
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
                        if (offset == 1) {
                            if (isFirst) {
                                isFirst = false;
                            }
                            List<SetListModel.DataBean.SetBean> list = model.getData().getSet();
                            listHad = list;
                            if (list == null || list.size() == 0) {
                                mAdapter.clear();
                            } else {
                                mAdapter.resetItems(model.getData().getSet());
                            }
//                            getListAll();
                        } else {
                            mAdapter.addItems(model.getData().getSet());
                        }
                    }
                });
    }

    /**
     * 获取全部然后剔除 已有
     */
    private void getListAll() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setFilter(getFilterBean());
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.SETLIST);
        Gson gson = new Gson();
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
                        List<SetListModel.DataBean.SetBean> list = model.getData().getSet();
                        List<SetListModel.DataBean.SetBean> listFilter = new ArrayList<>();
                        if (list != null && list.size() > 0) {
                            if (listHad != null && listHad.size() > 0) {
                                //过滤后
                                for (SetListModel.DataBean.SetBean bean : list) {
                                    boolean has = false;
                                    for (SetListModel.DataBean.SetBean filterBean : listHad) {
                                        if (filterBean.getId().equals(bean.getId())) {
                                            has = true;
                                        }
                                    }
                                    if (!has) {
                                        listFilter.add(bean);
                                    }
                                }
                                mAdapter.addItems(listFilter);
                            } else {
                                mAdapter.addItems(list);
                            }

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
            tvFilterColor.setTextColor(mColorGray);
            tvFilterColor.setText(R.string.all);
        } else {
            tvFilterColor.setText(bean.getText());
            tvFilterColor.setTextColor(mColorOrgin);
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

    @Override
    public void onSetStyleSelect(int id, String text) {
        mStyleId = id;
        tvFilterStyle.setText(text);
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
            limit = 5;
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
