package com.jajahome.feature.set;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.diyview.ImageFrameLayout;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.TencentWebV;
import com.jajahome.feature.comment.CommentAct;
import com.jajahome.feature.comment.CommentRecyclerAdapter;
import com.jajahome.feature.set.fragment.adapter.RelatedSetAdapter;
import com.jajahome.feature.set.fragment.adapter.SetItemExpandableAdapter;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.ShoppingCartAct;
import com.jajahome.feature.user.adapter.SetCollectAdapter;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.CommentModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.model.SetModel;
import com.jajahome.model.SetVRModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopShare;
import com.jajahome.util.Base64Helper;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.HorizontalRecycleView;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 套装详情页
 */
public class SetDetailAct extends BaseActivity implements SetItemExpandableAdapter.OnSetItemChangeListener, CommentRecyclerAdapter.OnCommentListener {
    public static final String SET_ID = "SET_ID";
    public static final String SET_URL = "SET_URL";
    public static final String SET_COLLECT = "SET_COLLECT";
    public static final String SET_DELETECOLLECT = "SET_DELETECOLLECT";
    public static final String SET_IMG = "SET_IMG";
    public static final String SET_TIP = "SET_TIP";

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.img_frame_layout)
    ImageFrameLayout imgFrameLayout;

    TextView tvName;
    TextView tvPriceDiscount;
    TextView tvPriceDes;
    TextView tvPriceBisic;
    TextView tvSetTips;
    Button btnSellList;
    @BindView(R.id.listview)
    ExpandableListView listview;
    @BindView(R.id.bottom_img_msg)
    ImageView bottomImgMsg;
    @BindView(R.id.bottom_img_collect)
    ImageView bottomImgCollect;
    @BindView(R.id.bottom_img_collected)
    ImageView bottomImgCollected;
    @BindView(R.id.bottom_img_share)
    ImageView bottomImgShare;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.btn_add_to_shopping_cart)
    Button btnAddToShoppingCart;
    @BindView(R.id.set_vr)
    ImageView setVr;
    @BindView(R.id.set_mod)
    ImageView setMod;
    @BindView(R.id.scale_onclick)
    ImageView scaleOnClick;

    @BindView(R.id.gone_tv)
    TextView gone_tv;
    @BindView(R.id.set_bottom_iv)
    ImageView bottomView;


    private PopShare mPopShare; //分享弹窗
    private IWXAPI mIwxapi; //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api
    private String mId;//单品id
    private String Tag;
    private String index;
    private String setUrl;
    private SetModel mSetModel;//套装数据
    private List<SetModel.DataBean.SetBean.ListBean> listItems;
    private String image_blueprint;
    SetItemExpandableAdapter adapter;
    private String vrUrl;
    private Boolean flag = true;
    private SetModel.DataBean.SetBean setBean;
    public static String SetDetailTag = "SetDetailAct";
    public static String mSetUrl;
    public static String vrName;
    private String cityName;
    private String SetTips;
    TextView tvTotalComment;
    TextView tvCommentEmpty;

    @Override
    protected int getViewId() {
        return R.layout.act_set_detail;
    }

    @Override
    protected void initEvent() {

        initViewController(listview);
        mId = getIntent().getStringExtra(SET_ID);
        mSetUrl = getIntent().getStringExtra(SET_URL);
        Tag = getIntent().getStringExtra(SET_COLLECT);
        index = getIntent().getStringExtra(SET_DELETECOLLECT);
        setUrl = getIntent().getStringExtra(SET_IMG);
        SetTips = getIntent().getStringExtra(SET_TIP);
        showLoading(true, "");
        getDetail();
        getVRset();

        textView2.setText("");
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setListener();
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        //新浪微博分享
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
        mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        initBottomComment();

    }

    private RecyclerView mRecyclerViewComment;
    private CommentRecyclerAdapter mCommentRecyclerAdapter;

    private void initBottomComment() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.view_bottom_comment, null);
        tvTotalComment = (TextView) footer.findViewById(R.id.tv_total_comment);
        tvCommentEmpty = (TextView) footer.findViewById(R.id.tv_empty);
        listview.addFooterView(footer);
        mRecyclerViewComment = (RecyclerView) footer.findViewById(R.id.recyclerView_bottom);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewComment.setLayoutManager(linearLayoutManager);
        mCommentRecyclerAdapter = new CommentRecyclerAdapter();
        mCommentRecyclerAdapter.setOnlyShowTwoReply();
        mCommentRecyclerAdapter.setOnCommentListener(this);
        mRecyclerViewComment.setAdapter(mCommentRecyclerAdapter);
        footer.findViewById(R.id.view_goto_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoComment();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCommentList();
    }

    /**
     * 进入评论
     */
    private void gotoComment() {
//        set | 套装
//       item | 单品
        Intent intent = new Intent(mContext, CommentAct.class);
        intent.putExtra("id", mId);
        intent.putExtra("content_type", "set");
        startActivity(intent);
    }

    /**
     * 获取评论
     */
    private void getCommentList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(1);
        paginationBean.setLimit(2);
        ReqPage reqPage = new ReqPage();
        ReqPage.ContentBean content = new ReqPage.ContentBean();
        content.setContent_id(mId);
        content.setContent_type("set");
        reqPage.setContent(content);
        content.setPagination(paginationBean);
        reqPage.setCmd(Constant.COMMENT_LIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getComment(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CommentModel userMessageModel) {
                        tvTotalComment.setText("累计评论(" + userMessageModel.getData().getPagination().getTotal() + ")");
                        List<CommentModel.DataBean.ListsBean> list = userMessageModel.getData().getLists();
                        if (list == null || list.size() == 0) {
                            tvCommentEmpty.setVisibility(View.VISIBLE);
                            mRecyclerViewComment.setVisibility(View.GONE);
                        } else {
                            mRecyclerViewComment.setVisibility(View.VISIBLE);
                            tvCommentEmpty.setVisibility(View.GONE);
                            mCommentRecyclerAdapter.resetItems(list);
                        }
                    }
                });
    }

    /**
     * 用户消息处理
     *
     * @param id
     */
    private void like(final int position, int id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.COMMENT_LIKE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(id);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("请稍等");
        ApiImp.get().operationMessage(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onNext(SimpleModel deleteModel) {
                        if (deleteModel.getStatus() == 0) {
                            mCommentRecyclerAdapter.like(position);
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }


    private void setListener() {
        bottomImgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mSetModel != null) {
                        if (mSetModel.getData().getSet().getFavorite() == 0) {
                            //还未收藏
                            addFav();
                        } else {
                            delFav();
                        }
                    }
                }
            }
        });

        bottomImgCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mSetModel != null) {
                        if (mSetModel.getData().getSet().getFavorite() == 1) {
                            //还未收藏
                            delFav();
                        } else {
                            addFav();
                        }
                    }
                }
            }
        });
        bottomImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                    cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
                } else {
                    cityName = "";
                }
                String about_us = Constant.ABOUT_US + "&city=" + cityName;

                H5Act.gotoH5(mContext, about_us, "联系我们");
                //  Intent intent = new Intent();
                //  intent.setClass(SetDetailAct.this, ChatActivity.class);
                //   startActivity(intent);
            }
        });


        bottomImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopShare == null) {
                    mPopShare = new PopShare(SetDetailAct.this, mIwxapi, mIWeiboShareAPI, bottomImgShare, SetDetailTag);
                    mPopShare.setmUrl(Constant.TZ + mId);
                    mPopShare.setShowURI(mSetUrl);
                }
                mPopShare.setShowURI(mSetUrl);
                mPopShare.setmSubhead(null);
                if (setBean != null) {
                    mPopShare.setText(setBean.getName() + "");
                }
                mPopShare.show();
            }
        });
        //立即购买
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSetModel == null) return;
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.gotoBuy(mContext, Constant.TZ_PRICE + getBuyOrShopCardUrl(loginModle, 2));
                }
            }
        });
        //添加购物车
        btnAddToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSetModel == null) return;
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.addToShoppingCart(mContext, Constant.TZ_PRICE + getBuyOrShopCardUrl(loginModle, 1));
                }
            }
        });

        setVr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vrUrl.length() > 0) {
                    setVr.setVisibility(View.VISIBLE);
                    TencentWebV.gotoH5(SetDetailAct.this, vrUrl);
                } else {
                    setVr.setVisibility(View.GONE);
                }

            }
        });

        setMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ImageFrameLayout.isReverse == true) {
                    ImageFrameLayout.isReverse = false;
                    setData();
                } else {
                    ImageFrameLayout.isReverse = true;
                    setData();
                }
            }
        });


        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (tvPriceDiscount != null) {
                    String price = tvPriceDiscount.getText().toString();

                    if (firstVisibleItem == 0) {
                        gone_tv.setVisibility(View.GONE);
                    } else {
                        gone_tv.setVisibility(View.VISIBLE);
                        gone_tv.setText(price);
                    }

                }
            }
        });

    }

    /*判断是否是从我的收藏跳转过来*/
    public void isMyCollect(String Tag) {
        if (Tag != null && Tag.equals(SetCollectAdapter.Tag)) {
            bottomImgCollect.setVisibility(View.GONE);
            bottomImgCollected.setVisibility(View.VISIBLE);
            mSetModel.getData().getSet().setFavorite(1);
        }
    }

    private String getBuyOrShopCardUrl(LoginModle loginModle, int i) {
        SetDiyPriceModel model = new SetDiyPriceModel();
        //1：套装，2：DIY
        model.setSet_type(1);
        //1加入购物车，2直接购买
        model.setType(i);
        //套装或DIY的id
        model.setId(mId);
        //用户信息
        model.setUser_id(loginModle.getData().getUser().getId()); //
        model.setUser_token(loginModle.getData().getSession());
        model.setItems(getItems());
        String json = new Gson().toJson(model);
        Log.e("json", json);
        return Base64Helper.encodeStringToBase64(json);
    }

    /**
     * 获取 套装信息 （购买 加入购物车
     *
     * @return
     */
    private List<SetDiyPriceModel.ItemsBean> getItems() {
        List<SetDiyPriceModel.ItemsBean> list = new ArrayList<>();
        List<SetModel.DataBean.SetBean.ListBean> listNew = imgFrameLayout.getmList();
        for (int i = 0; i < listNew.size(); i++) {
            SetModel.DataBean.SetBean.ListBean.ItemInfoBean itemInfoBean = getInfoBean(i, listNew);
            SetDiyPriceModel.ItemsBean itembean = new SetDiyPriceModel.ItemsBean();
            itembean.setId(itemInfoBean.getId());
            itembean.setFab_id(itemInfoBean.getFabric());
            itembean.setMat_id(itemInfoBean.getMaterial());
            list.add(itembean);
        }
        //排过序的
        return list;
    }

    private SetModel.DataBean.SetBean.ListBean.ItemInfoBean getInfoBean(int i, List<SetModel.DataBean.SetBean.ListBean> listNew) {
        for (SetModel.DataBean.SetBean.ListBean listBean : listNew) {
            if (listBean.getIndex() == i) {
                return listBean.getItem_info();
            }
        }
        return listNew.get(0).getItem_info();
    }

    /**
     * 获取套装详情信息
     */
    private void getDetail() {
        mSubscription = ApiImp.get().set(HttpUtil.getRequestBodyDetail(Constant.SET, mId), HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SetModel>() {
            @Override
            public void onCompleted() {
                showLoading(false, "");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showToast("未发现该商品");
                finish();
            }

            @Override
            public void onNext(SetModel model) {
                mSetModel = model;

                if (mSetModel != null) {
                    setMod.setVisibility(View.VISIBLE);
                    scaleOnClick.setVisibility(View.VISIBLE);
                    bottomImgCollect.setVisibility(View.VISIBLE);

                }
                setData();
                isMyCollect(Tag);
            }
        });
    }


    private void setData() {
        setBean = mSetModel.getData().getSet();
        listItems = setBean.getList();
        image_blueprint = setBean.getImage_blueprint();
        int i = 0;
        for (SetModel.DataBean.SetBean.ListBean b : listItems) {
            b.setIndex(i);
            i++;
        }

        if (adapter == null) {
            adapter = new SetItemExpandableAdapter(mContext, listItems, imgFrameLayout);
            if (!StringUtil.isEmpty(image_blueprint)) {
                imgFrameLayout.setBottomImage(image_blueprint);
            }
        }

        imgFrameLayout.setmList(listItems);
        listview.setGroupIndicator(null);
        adapter.setOnSetItemChangeListener(this);
        //添加头部
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_set_detail, null);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvPriceDes = (TextView) view.findViewById(R.id.tv_price_des);
        tvPriceBisic = (TextView) view.findViewById(R.id.tv_price_basic);
        btnSellList = (Button) view.findViewById(R.id.btn_sell_list);
        tvPriceDiscount = (TextView) view.findViewById(R.id.tv_price_discount);
        tvSetTips = (TextView) view.findViewById(R.id.setTips);
        if (!StringUtil.isEmpty(SetTips)) {
            tvSetTips.setText(SetTips);
        } else tvSetTips.setVisibility(View.GONE);

        btnSellList.setVisibility(View.VISIBLE);
        btnSellList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SellItemListAct.class);
                intent.putExtra(SellItemListAct.SET_MODEL, (Serializable) getItems());
                intent.putExtra(SellItemListAct.ID, mId);
                startActivity(intent);
            }
        });

        if (flag) {
            listview.addHeaderView(view);
            listview.setAdapter(adapter);
            flag = false;
        }

        adapter.notifyDataSetChanged();
        listview.setFocusable(false);
        tvName.setText(setBean.getName());
        vrName = setBean.getName();

        imgFrameLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetLandscapeAct.class);
                intent.putExtra(SetLandscapeAct.BITMAP, (Serializable) imgFrameLayout.getmList());
                intent.putExtra(SetLandscapeAct.MAP, adapter.isBigMap);
                intent.putExtra(SetLandscapeAct.BOTTOMURI, image_blueprint);
                startActivity(intent);
            }
        });

        scaleOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, SetLandscapeAct.class);
                intent.putExtra(SetLandscapeAct.BITMAP, (Serializable) imgFrameLayout.getmList());
                intent.putExtra(SetLandscapeAct.MAP, adapter.isBigMap);
                intent.putExtra(SetLandscapeAct.BOTTOMURI, image_blueprint);
                startActivity(intent);
            }
        });
        setPrice();
        //添加相关套装
        if (setBean.getRelated() != null && setBean.getRelated().size() > 0) {
            View viewFooter = LayoutInflater.from(mContext).inflate(R.layout.header_other_sets, null);
            HorizontalRecycleView relatedImgRecycle = (HorizontalRecycleView) viewFooter.findViewById(R.id.related_img_recycle);
            RelatedSetAdapter mRelatedAdapter = new RelatedSetAdapter();
            relatedImgRecycle.setAdapter(mRelatedAdapter);
            mRelatedAdapter.resetItems(setBean.getRelated());
            listview.addFooterView(viewFooter);
        }


        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                if (LoginUtil.isLogin(SetDetailAct.this)) {

                    if (clickMap.containsKey(i)) {

                        if (clickMap.get(i) == false) {
                            clickMap.put(i, true);
                            adapter.pushMap(clickMap);

                        } else {
                            clickMap.put(i, false);
                            adapter.pushMap(clickMap);
                        }

                    } else {
                        clickMap.put(i, true);
                        adapter.pushMap(clickMap);

                    }
                }
                return false;
            }
        });
    }

    private HashMap<Integer, Boolean> clickMap = new HashMap<>();


    /**
     * 获取套装中单品价格
     */
    private void setPrice() {
        int[] prices = imgFrameLayout.getSetPrice();
        int basicPriceMin = prices[0];  //不打折 最低价
        int basicPriceMax = prices[1];  //不打折 最高价
        int discountPriceMin = prices[2];//打折 最低价
        int discountPriceMax = prices[3];//打折 最高价
        double rate = mSetModel.getData().getSet().getDiscount_rate(); //套装折扣
        discountPriceMin = (int) (discountPriceMin * rate);
        discountPriceMax = (int) (discountPriceMax * rate);
        String priceDes = mSetModel.getData().getSet().getPrice_desc();
        if (!StringUtil.isEmpty(priceDes)) {
            tvPriceDes.setText(priceDes);
        }
        if (discountPriceMin == 0 || discountPriceMax == 0) {
            tvPriceDiscount.setText(R.string.pls_ask_price);
            tvPriceBisic.setVisibility(View.GONE);
        } else if (discountPriceMin == discountPriceMax) {
            tvPriceDiscount.setText(Constant.YUAN + discountPriceMin);
            if (discountPriceMin == basicPriceMin) {
                tvPriceBisic.setVisibility(View.GONE);
            } else {
                if (basicPriceMax == basicPriceMin) {
                    tvPriceBisic.setText(Constant.YUAN + basicPriceMin);
                } else {
                    tvPriceBisic.setText(Constant.YUAN + basicPriceMin + " - " + basicPriceMax);
                }
                tvPriceBisic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

        } else {
            tvPriceDiscount.setText(Constant.YUAN + discountPriceMin + " - " + discountPriceMax);
            if (discountPriceMin == basicPriceMin) {
                tvPriceBisic.setVisibility(View.GONE);
            } else {
                if (basicPriceMax == basicPriceMin) {
                    tvPriceBisic.setText(Constant.YUAN + basicPriceMin);
                } else {
                    tvPriceBisic.setText(Constant.YUAN + basicPriceMin + " - " + basicPriceMax);
                }
                tvPriceBisic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

    }

    /**
     * 添加收藏
     */
    private void addFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(0);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
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
                bottomImgCollect.setVisibility(View.GONE);
                bottomImgCollected.setVisibility(View.VISIBLE);
                mSetModel.getData().getSet().setFavorite(1);

            }
        });
    }


    /**
     * 删除收藏
     */
    private void delFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(0);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("取消收藏中");
        mSubscription = ApiImp.get().delFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
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
                showToast(R.string.del_collect_success);
                mSetModel.getData().getSet().setFavorite(0);
                bottomImgCollect.setVisibility(View.VISIBLE);
                bottomImgCollected.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventMessage(5, index));

            }
        });
    }

    @Override
    public void onSetItemChanged(String itemId, SetItemModel.DataBean.ListBean bean, String url) {
        imgFrameLayout.changeImage(itemId, bean, url);
        setPrice();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopSubscription();
        }
    }

    /**
     * 得到套装VR
     */

    public void getVRset() {
        if (mId == null) {
            showToast("id为空");
            return;
        }
        int setId = Integer.valueOf(mId);
        mSubscription = ApiImp.get().vr_get_and_create_vrXML(HttpUtil.getVR(Constant.vr_get_and_create_vrXML, setId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SetVRModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SetVRModel setVRModel) {
                vrUrl = setVRModel.getData().getXml_url();
                if (vrUrl.length() > 0) {
                    setVr.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageFrameLayout.isReverse = false;
        mSetUrl = null;
        vrName = null;
    }

    @Override
    public void onLike(int position, String id) {
        like(position, Integer.parseInt(id));
    }

    @Override
    public void onReply(int position, String id, String name) {

    }

    @Override
    public void onDel(int position, String id) {

    }
}
