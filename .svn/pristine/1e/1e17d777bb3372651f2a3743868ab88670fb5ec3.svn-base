package com.jajahome.feature.show;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.pop.PopShare;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ShowTabLayout;
import com.jajahome.widget.ZoomView;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

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
 * 显示秀家美图
 */
public class ShowImgDetailAct extends BaseActivity {
    public static final String SHOW_ID = "SHOW_ID";
    public static final String SHOW_COLLECT = "SHOW_COLLECT";
    public static final String SHOW_DELETECOLLECT = "SHOW_DELETECOLLECT";
    public static final String SHOW_URI = "SHOW_URL";
    public static final String SHOW_TEXT = "SHOW_TEXT";
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.bottom_img_share)
    ImageView bottomImgShare;
    @BindView(R.id.bottom_img_collect)
    ImageView bottomImgCollect;
    @BindView(R.id.bottom_img_collected)
    ImageView bottomImgCollected;
    @BindView(R.id.bottom_img_detail)
    ImageView bottomImgDetail;
    @BindView(R.id.zoom_view)
    ZoomView zoomView;
    @BindView(R.id.frame_layout)
    ShowTabLayout frameLayout;
    @BindView(R.id.view_bottom)
    LinearLayout viewBottom;
    private PopShare mPopShare;   //分享弹窗
    private IWXAPI mIwxapi;       //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api
    private String mShowId;       //id 秀家id
    private String Tag;
    private String index;
    private String mUrl;          //分享的url
    private ShowDetailModel mShowModel;
    private String showUri, showText;
    SensorManager mSensorManager; //重力传感器

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
        return R.layout.act_show_img_detail;
    }

    @Override
    protected void initEvent() {
        textView2.setText(R.string.show_detail);
        initViewController(zoomView);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mShowId = getIntent().getStringExtra(SHOW_ID);
        Tag = getIntent().getStringExtra(SHOW_COLLECT);
        index = getIntent().getStringExtra(SHOW_DELETECOLLECT);
        showUri = getIntent().getStringExtra(SHOW_URI);
        showText = getIntent().getStringExtra(SHOW_TEXT);
        getDetail();
        showLoading(true, ""); //加载动画
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
        viewBottom.setVisibility(View.VISIBLE);
        bottomImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(mUrl)) { //链接为空时 使分享失效
                    return;
                }
                if (mPopShare == null) {
                    mPopShare = new PopShare(ShowImgDetailAct.this, mIwxapi, mIWeiboShareAPI, bottomImgShare);
                    mPopShare.setmUrl(mUrl);
                    mPopShare.setShowURI(showUri);
                } else {
                    mPopShare.setShowURI(showUri);
                    mPopShare.setShowText(showText);
                    mPopShare.show();
                }

            }
        });
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
                            //还未收藏
                            delFav();
                        } else {
                            addFav();
                        }
                    }
                }
            }
        });
        //进入详情参数
        bottomImgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5Act.gotoH5(mContext, Constant.SHOW_HOME + mShowId, "详情参数");
            }
        });
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {// 有注册监听器，就清除
            mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null) {// 取消监听器
            mSensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private final float CHANGE_VALUE = 0.2F; //变动 阈值  （）
    private float mCurrentVaule = 0; //当前X值
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            if (Math.abs(x - mCurrentVaule) > CHANGE_VALUE) {
                mCurrentVaule = x;
                frameLayout.rotateTabs(x * 9);
                Log.i("SHOW_ROAT", "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

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
        Gson gson = new Gson();
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
     * 通过id获取信息
     */
    private void getDetail() {
        //从上个页面获取的数据源
        Observable<ShowDetailModel> obsFromLastAct = Observable.create(new Observable.OnSubscribe<ShowDetailModel>() {
            @Override
            public void call(Subscriber<? super ShowDetailModel> subscriber) {
                Serializable ser = getIntent().getSerializableExtra(ShowH5DetailAct.BEAN);
                if (ser != null) {
                    subscriber.onNext((ShowDetailModel) ser);

                } else {
                    subscriber.onNext(null);
                }
                subscriber.onCompleted();
            }
        });
        //从网络获取
        Observable<ShowDetailModel> obsFromNet = ApiImp.get().
                show(HttpUtil.getRequestBodyDetail(Constant.SHOW, mShowId), HttpUtil.getSession(mContext));
        //没有数据 就从网络取
        Observable<ShowDetailModel> observer = Observable.concat(obsFromLastAct, obsFromNet).first(new Func1<ShowDetailModel, Boolean>() {
            @Override
            public Boolean call(ShowDetailModel model) {
                return model != null;
            }
        });
        mSubscription = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShowDetailModel>() {
                    @Override
                    public void onCompleted() {
                        showLoading(false, "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoading(false, "");
                        e.printStackTrace();
                        showToast("错误");
                        finish();
                    }

                    @Override
                    public void onNext(ShowDetailModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            mShowId = model.getData().getShow().getId();
                            mShowModel = model;
                            setShowImg();
                            mUrl = model.getData().getShow().getPreview().getUrl();
                            ShowDetailModel.DataBean.ShowBean bean = model.getData().getShow();
                            frameLayout.setData(bean);//显示数据
                            frameLayout.setFinishLoadedListener(new ShowTabLayout.FinishLoadedListener() {
                                @Override
                                public void onFinishLoaded() {
                                    zoomView.zoomTo(2, zoomView.getMeasuredWidth() / 2, zoomView.getMeasuredHeight() / 2); //放大图片
                                }
                            });
                            bottomImgCollect.setVisibility(View.VISIBLE);
                            mHandler.sendEmptyMessage(new Message().what = 1);

                        }
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
