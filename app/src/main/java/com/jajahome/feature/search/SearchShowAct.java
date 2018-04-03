package com.jajahome.feature.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.search.adapter.AdapterPopSelect;
import com.jajahome.feature.search.adapter.SearchHistoryAdapter;
import com.jajahome.feature.search.adapter.SearchListAdapter;
import com.jajahome.feature.search.adapter.SearchTipsAdapter;
import com.jajahome.feature.set.view.SetImageView;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.PickAccountModel;
import com.jajahome.model.SearchResultModel;
import com.jajahome.model.SearchTipsModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.util.RandomUtil;
import com.jajahome.util.SoftPanUtil;
import com.jajahome.util.SpSearchHistoryUtil;
import com.jajahome.util.StringUtil;
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
 * 秀家搜索
 */
public class SearchShowAct extends BaseActivity implements View.OnLayoutChangeListener,
        SearchHistoryAdapter.OnHistorySelectedListener, PopSelectSearch.OnSelectListener
        , SearchTipsAdapter.OnTipClickListener, MultiRecycleView.OnMutilRecyclerViewListener {
    public static final String TYPE_EXTRA = "TYPE_EXTRA";
    public static final String TYPE_ITEM = "item";
    public static final String TYPE_SET = "set";
    public static final String TYPE_SHOW = "show";
    private String TYPE = "item";
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tv_search_type)
    TextView tvSearch;
    @BindView(R.id.img_search_type)
    ImageView imgSearch;
    @BindView(R.id.view_root)
    LinearLayout mLinearLayout;
    //搜索历史
    @BindView(R.id.recycler_history)
    RecyclerView mRecyclerHistory;
    @BindView(R.id.recycler_tips)
    RecyclerView mRecyclerTips;
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private SearchTipsAdapter mSearchTipsAdapter;
    private PopSelectSearch mPopSelectSearch;
    private String searchText = ""; //搜索文字
    private ConfigModel configModel; //配置信息
    private boolean isSearching = false;
    private int mStatus;
    private SearchListAdapter mSearchListAdapter;//搜索结果
    private int offset = 1; //分页数据 第几页
    @BindView(R.id.recyclerView_result)
    MultiRecycleView mMultiRecycleView;

    @Override
    protected int getViewId() {
        return R.layout.act_search;
    }

    @Override
    protected void initEvent() {
        initViewController(R.id.frame_layout);
        TYPE = getIntent().getStringExtra(TYPE_EXTRA);
        getConfig();
        if (TYPE == null) {
            TYPE = TYPE_ITEM;
        }
        if (!TYPE_SHOW.equals(TYPE)) {
            imgSearch.setVisibility(View.GONE);
            tvSearch.setVisibility(View.GONE);
        } else {
            tvSearch.setText(AdapterPopSelect.filter_show[0]);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mLinearLayout.addOnLayoutChangeListener(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = s.toString();
                if (StringUtil.isEmpty(searchText)) {
                    showSearchHistory();
                    return;
                }
                if (!isSearching) {
                    //不是显示搜索结果
                    searchTips();
                } else {
                    //进行搜索
                    startSearch();
                }
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecyclerView();
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mPopSelectSearch) {
                    mPopSelectSearch = new PopSelectSearch(SearchShowAct.this, TYPE);
                    mPopSelectSearch.setOnSelectCountryListener(SearchShowAct.this);
                }
                mPopSelectSearch.show(tvSearch);
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //点击搜索按钮
                    searchText = editSearch.getText().toString();
                    if (!StringUtil.isEmpty(searchText)) {
                        startSearch();
                    }
                    return true;
                } else {
                    return false;
                }

            }
        });
    }


    private int[] getIdArray(int mStatus) {
        if (mStatus == 0) {
            return new int[]{2, 4};
        } else if (mStatus == 1) {
            return new int[]{3};
        } else if (mStatus == 2) {
            return new int[]{5};
        } else if (mStatus == 3) {
            return new int[]{1};
        }
        return new int[]{2, 4};
    }

    private void searchTips() {
        ReqPage baseReq = new ReqPage();
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setType(TYPE);
        if (TYPE.equals(TYPE_SHOW)) {
            contentBean.setSubtype(getIdArray(mStatus));
        }
        contentBean.setQuery(searchText);
        baseReq.setContent(contentBean);
        baseReq.setCmd(Constant.SEARCH_TIPS);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().searchTips(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchTipsModel>() {
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
                    public void onNext(SearchTipsModel model) {
                        if (model.getStatus() == 0) {
                            showSearchTips(model, searchText);
                        } else {
                            mRecyclerTips.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    /**
     * 开始搜索
     */
    private void search() {
        SpSearchHistoryUtil.saveHistory(mContext, TYPE, searchText);
        ReqPage baseReq = new ReqPage();
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setType(TYPE);
        if (TYPE.equals(TYPE_SHOW)) {
            contentBean.setSubtype(getIdArray(mStatus));
        }
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        contentBean.setQuery(searchText);
        baseReq.setContent(contentBean);
        contentBean.setPagination(paginationBean);
        baseReq.setCmd(Constant.SEARCH);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().search(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResultModel>() {
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
                    public void onNext(SearchResultModel model) {
                        showSearchResult(model);
                    }
                });
    }

    /**
     * 第一次搜索当前关键词
     */
    private void startSearch() {
        showLoading(true, "");
        offset = 1;
        search();
    }

    /**
     * 当前关键词加载更多
     */
    private void startLoadMore() {
        offset = 1 + offset;
        search();
    }

    private void searchHotList() {
        ReqPage baseReq = new ReqPage();
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setType(TYPE);
        baseReq.setContent(contentBean);
        baseReq.setCmd(Constant.SEARCH_HOTLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().pickCash_account(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PickAccountModel>() {
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
                    public void onNext(PickAccountModel model) {


                    }
                });
    }

    private void showSearchHistory() {
        mMultiRecycleView.setVisibility(View.INVISIBLE);
        mRecyclerTips.setVisibility(View.INVISIBLE);
        mRecyclerHistory.setVisibility(View.VISIBLE);
        List<String> list = SpSearchHistoryUtil.getAllHistory(mContext, TYPE);
        mSearchHistoryAdapter.resetItems(list);
    }

    private void showSearchTips(SearchTipsModel model, String key) {
        showEmpty(false, "", null);
        mRecyclerHistory.setVisibility(View.INVISIBLE);
        if (model.getData() != null && !StringUtil.isEmpty(searchText)) {
            List<String> list = model.getData().getItem();
            if (list != null && list.size() > 0) {
                mRecyclerTips.setVisibility(View.VISIBLE);
                mSearchTipsAdapter.setAllData(list, key);
                mRecyclerTips.bringToFront();
            } else {
                mRecyclerTips.setVisibility(View.INVISIBLE);
            }
        } else {
            mRecyclerTips.setVisibility(View.INVISIBLE);
        }
    }

    //显示搜索结果
    private void showSearchResult(SearchResultModel model) {
        isSearching = false;
        mMultiRecycleView.stopLoadingMore();
        mMultiRecycleView.stopRefresh();
        if (model.getStatus() == 0) {
            List<SearchResultModel.DataBean.ItemBean> itemBeans = model.getData().getItem();
            if (offset == 1) {
                //第一次搜索
                if (itemBeans == null || itemBeans.size() == 0) {
                    showEmpty(true, "暂无搜索结果", null);
                } else {
                    showEmpty(false, "暂无搜索结果", null);
                    mMultiRecycleView.setVisibility(View.VISIBLE);
                    mRecyclerTips.setVisibility(View.INVISIBLE);
                    mRecyclerHistory.setVisibility(View.INVISIBLE);
                    mSearchListAdapter.resetItems(itemBeans);
                }
            } else {
                mSearchListAdapter.addItems(itemBeans);
            }
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerHistory.setLayoutManager(manager);
        mSearchHistoryAdapter = new SearchHistoryAdapter();
        mSearchHistoryAdapter.setOnHistorySelectedListener(this);
        mRecyclerHistory.setAdapter(mSearchHistoryAdapter);

        LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerTips.setLayoutManager(manager2);
        mSearchTipsAdapter = new SearchTipsAdapter();
        mSearchTipsAdapter.setType(TYPE);
        mSearchTipsAdapter.setConfigModel(configModel);
        mSearchTipsAdapter.setOnTipClickListener(this);
        mRecyclerTips.setAdapter(mSearchTipsAdapter);

        mSearchListAdapter = new SearchListAdapter(TYPE);
        mMultiRecycleView.setAdapter(mSearchListAdapter);
        if (TYPE_SHOW.equals(TYPE)) {
            mMultiRecycleView.setLinearLayout();
        } else if (TYPE_SET.equals(TYPE)) {
            mMultiRecycleView.setLinearLayout();
            setRecyclerViewListener();
        } else {
            mMultiRecycleView.setGridLayout(2);
        }
        mMultiRecycleView.setOnMutilRecyclerViewListener(this);
    }

    int keyHeight = 300;

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            if (StringUtil.isEmpty(searchText)) {
                showSearchHistory();
            }
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
        }
    }

    @Override
    public void onHistorySelected(String history) {
        editSearch.setText(history);
        editSearch.setSelection(history.length());
    }

    @Override
    public void onSelectListener(int index, String country) {
        mStatus = index;
        tvSearch.setText(country);
    }

    /**
     * 获取配置信息 ,先取本地存储 本地没有从网络获取
     */
    private void getConfig() {

        Observable<ConfigModel> obsNet = ApiImp.get().
                config(HttpUtil.getRequestBody(Constant.CONFIG_V2), HttpUtil.getSession(this));

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ConfigModel model) {
                        configModel = model;
                        if (mSearchTipsAdapter != null) {
                            mSearchTipsAdapter.setConfigModel(configModel);
                        }

                    }
                });
    }

    private List<String> getRandomTips() {
        List<String> list = new ArrayList<>();
        switch (TYPE) {
            case TYPE_ITEM:
                List<ConfigModel.DataBean.ConfigBean.ItemColorBean> colorBeans = configModel.getData().getConfig().getItem_color();
                int[] random01 = RandomUtil.randomCommon(0, colorBeans.size() - 1, 3);
                for (int i : random01) {
                    list.add(colorBeans.get(i).getText());
                }
                List<ConfigModel.DataBean.ConfigBean.ItemBrandBean> brandBeans = configModel.getData().getConfig().getItem_brand();
                int[] random02 = RandomUtil.randomCommon(0, brandBeans.size() - 1, 3);
                for (int i : random02) {
                    list.add(brandBeans.get(i).getText());
                }
                break;
        }
        return list;
    }

    /**
     * 点击提示 开始搜索
     *
     * @param value 搜索关键词
     */
    @Override
    public void onTipClick(String value) {
        isSearching = true;
        SoftPanUtil.hideSoftKeyboard(this);
        editSearch.setText(value);
        editSearch.setSelection(value.length());
    }


    @Override
    public void onRefresh() {
        startSearch();
    }

    @Override
    public void onLoadMore() {
        startLoadMore();
    }

    private int limit;
    private Map<Integer, SetImageView> setImageViewMap;
    private int y;
    String model = android.os.Build.MODEL; // 手机型号

    private void setRecyclerViewListener() {
        setImageViewMap = mSearchListAdapter.getSetImageViewMap();
        int w = (int) DensityUtil.getDisplayWidthDp(mContext);
        if (model.equals("A31")) {
            limit = 5;
        } else {
            limit = (w * Constant.mHLimit) / Constant.mWLimit;
        }
        mMultiRecycleView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
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
}
