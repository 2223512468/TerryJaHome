package com.jajahome.feature.show;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.adapter.ShowCollectAdapter;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.ShowDetailModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.pop.PopShare;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ProgressWebView;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 秀家H5 详情
 */
public class ShowH5DetailAct extends BaseActivity {
    public static final String SHOW_ID = "SHOW_ID";
    public static final String SHOW_COLLECT = "SET_COLLECT";
    public static final String SHOW_DELETECOLLECT = "SET_DELETECOLLECT";
    public static final String TYPE = "TYPE";
    public static final String BEAN = "BEAN"; //传入一个秀家实体
    public static final String SHOW_URI_H5 = "SHOW_URL_H5";
    public static final String SHOW_TEXT_H5 = "SHOW_TEXT_H5";

    @BindView(R.id.progress_bar)
    ProgressWebView progressBar;
    @BindView(R.id.bottom_img_msg)
    LinearLayout bottomImgMsg;
    @BindView(R.id.bottom_img_collect)
    ImageView bottomImgCollect;
    @BindView(R.id.bottom_img_collected)
    ImageView bottomImgCollected;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.bottom_img_share)
    ImageView bottomImgShare;
    @BindView(R.id.bottom_img_share_ll)
    LinearLayout bottomImgShareLL;


    private PopShare mPopShare;   //分享弹窗
    private IWXAPI mIwxapi;       //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api
    private String mUrl;          //分享的url
    private String cityName;


    private String mShowId; //秀家id
    private String Tag;
    private String index;
    private ShowDetailModel mShowModel;
    private String showUri, showText;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                isMyCollect(Tag);
            }
        }
    };

    @Override
    protected int getViewId() {
        return R.layout.act_show_h5_detail;
    }

    @Override
    protected void initEvent() {
        textView2.setText(R.string.show_detail);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mShowId = getIntent().getStringExtra(SHOW_ID);
        Tag = getIntent().getStringExtra(SHOW_COLLECT);
        index = getIntent().getStringExtra(SHOW_DELETECOLLECT);
        showUri = getIntent().getStringExtra(SHOW_URI_H5);
        showText = getIntent().getStringExtra(SHOW_TEXT_H5);
        getDetail();

        //联系我们
        bottomImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {*/
                if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                    cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
                } else {
                    cityName = "";
                }
                String about_us = Constant.ABOUT_US + "&city=" + cityName;
                H5Act.gotoH5(mContext, about_us, "联系我们");
                // }
            }
        });
        //收藏
        bottomImgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mShowModel != null) {
                        if (mShowModel.getData().getShow().getFavorite() == 0) {
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
                    if (mShowModel != null) {
                        if (mShowModel.getData().getShow().getFavorite() == 1) {
                            delFav();
                        } else {
                            addFav();
                        }
                    }
                }
            }
        });
    }

    /**
     * 设置秀家（美图类型）
     */
    private void setShowImg() {
        //初始化微信api
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        //新浪微博分享
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
        mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        //底部可见
        bottomImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(mUrl)) { //链接为空时 使分享失效
                    return;
                }
                if (mPopShare == null) {
                    mPopShare = new PopShare(ShowH5DetailAct.this, mIwxapi, mIWeiboShareAPI, bottomImgShare);
                    mPopShare.setmUrl(mUrl);
                    mPopShare.setShowURI(showUri);
                } else {
                    mPopShare.setShowURI(showUri);
                    mPopShare.setShowText(showText);
                    mPopShare.show();
                }
            }
        });


    }


    /**
     * 添加收藏
     */
    private void addFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mShowId));
        contentBean.setType(2);
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
                mShowModel.getData().getShow().setFavorite(1);
            }
        });
    }

    public void delFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mShowId));
        contentBean.setType(2);
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
                mShowModel.getData().getShow().setFavorite(0);
                bottomImgCollect.setVisibility(View.VISIBLE);
                bottomImgCollected.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventMessage(3, index));
            }
        });
    }


    /**
     * 获取秀家详细信息
     */
    private void getDetail() {
        mSubscription = ApiImp.get().
                show(HttpUtil.getRequestBodyDetail(Constant.SHOW, mShowId), HttpUtil.getSession(mContext)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShowDetailModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast("网络错误");
                        finish();
                    }

                    @Override
                    public void onNext(ShowDetailModel model) {
                        mShowModel = model;
                        setShowImg();
                        /*mUrl = model.getData().getShow().getPreview().getUrl();*/
                        mUrl = Constant.HTML5URL + model.getData().getShow().getId();
                        ShowDetailModel.DataBean.ShowBean showBean = model.getData().getShow();
                        if (showBean.getType() == 0) {//是秀家美图
                            //进入秀家美图
                            Intent intent = new Intent(mContext, ShowImgDetailAct.class);
                            intent.putExtra(ShowH5DetailAct.BEAN, model);
                            mContext.startActivity(intent);
                            finish();
                            return;
                        }
                        progressBar.loadData(model.getData().getShow().getContent());
                        bottomImgCollect.setVisibility(View.VISIBLE);
                        mHandler.sendEmptyMessage(new Message().what = 1);
                    }
                });
    }

    /*判断是否是从我的收藏跳转过来*/
    public void isMyCollect(String Tag) {
        if (Tag != null && Tag.equals(ShowCollectAdapter.Tag)) {
            bottomImgCollect.setVisibility(View.GONE);
            bottomImgCollected.setVisibility(View.VISIBLE);
            mShowModel.getData().getShow().setFavorite(1);
        }
    }
}
