package com.jajahome.feature.user.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.alipay.PayResult;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.adapter.DesignApplySelectPhotoAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AlipayModel;
import com.jajahome.model.AvatarModel;
import com.jajahome.model.JWeichatPayModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SimpleModel;
import com.jajahome.model.TimeModel;
import com.jajahome.model.UserMessageModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopPay;
import com.jajahome.pop.PopShare;
import com.jajahome.util.Base64Helper;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.util.TimeUtil;
import com.jajahome.wxapi.WXPayEntryActivity;
import com.jajahome.wxapi.WxPayApi;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 设置中心
 * Created by laotang on 2016/7/18.
 */
public class UserAct extends BaseActivity implements View.OnClickListener {
    public static final int CHANGE_ACTION = 0X46;
    public static final int MPAY_METHOD = 0x33;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.image_person)
    CircleImageView imagePerson;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;

    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.iv_shareApp)
    ImageView ivShareApp;
    @BindView(R.id.rl_shareApp)
    RelativeLayout rlShareApp;
    @BindView(R.id.iv_invite_no)
    ImageView ivInviteNo;
    @BindView(R.id.rl_no)
    RelativeLayout rlNo;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    /*@BindView(R.id.iv_friend)
    ImageView ivFriend;*/
    @BindView(R.id.rl_friend)
    LinearLayout rlFriend;
    @BindView(R.id.rl_user_log)
    RelativeLayout rlUserLog;
    @BindView(R.id.tv_inviteNo)
    TextView tvInviteNo;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.tv_new_x)
    TextView tvNewX;
    @BindView(R.id.ibtn_news)
    FrameLayout ibtnNews;
    @BindView(R.id.img_v)
    ImageView imgV;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.ll_wait_pay)
    LinearLayout llWaitPay;
    @BindView(R.id.ll_wait_shipments)
    LinearLayout llWaitShipments;
    @BindView(R.id.ll_wait_takeGood)
    LinearLayout llWaitTakeGood;
    @BindView(R.id.ll_wait_assess)
    LinearLayout llWaitAssess;
    @BindView(R.id.ll_good_back)
    LinearLayout llGoodBack;
    @BindView(R.id.rl_my_wallet)
    LinearLayout walletRL;
    @BindView(R.id.rl_my_rebate)
    LinearLayout rebateRL;
    @BindView(R.id.inte_tv)
    TextView inteTv;
    @BindView(R.id.wallet_tv)
    TextView walletTv;
    @BindView(R.id.friend_tv)
    TextView friendTv;
    @BindView(R.id.tv_super_designer)
    TextView superDesigner;
    @BindView(R.id.base_view)
    LinearLayout baseLL;
    @BindView(R.id.be_super_designer)
    TextView beSuperDesigner;
    @BindView(R.id.time_desc)
    TextView timeDesc;
    @BindView(R.id.pay_btn)
    TextView payBtn;
    @BindView(R.id.tv_apply_name)
    TextView tvApplyName;
    @BindView(R.id.rl_value_log)
    RelativeLayout valueLog;


    private LoginModle info;
    private String path;
    private String str;
    private PopShare mPopShare;
    private PopPay mPopPay;
    private IWXAPI mIwxapi;
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api
    /**
     * 是否可以点击进入申请设计师
     */
    private boolean isClick = false;
    public static String Tag = "UserAct";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SUPER_DESIGNER_APLIAY = 0x06;

    @Override
    protected int getViewId() {
        return R.layout.act_user;
    }

    @Override
    protected void initEvent() {
        ibtnNews.setVisibility(View.VISIBLE);
        ibtnBack.setOnClickListener(this);
        imagePerson.setOnClickListener(this);
        rlCollect.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        rlShareApp.setOnClickListener(this);
        rlNo.setOnClickListener(this);
        rlFriend.setOnClickListener(this);
        tvApply.setOnClickListener(this);
        tvApplyName.setOnClickListener(this);
        ibtnNews.setOnClickListener(this);
        rlOrder.setOnClickListener(this);
        rlUserLog.setOnClickListener(this);
        valueLog.setOnClickListener(this);
        initViewController(baseLL);
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        //新浪微博分享
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
        mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端


        walletRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNewAty(RebateInfoAct.class);
            }
        });
        rebateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNewAty(IntegrateAct.class);
            }
        });

        superDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopPay == null) {
                    mPopPay = new PopPay(UserAct.this, superDesigner);
                }
                mPopPay.show();

            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopPay == null) {
                    mPopPay = new PopPay(UserAct.this, superDesigner);
                }
                mPopPay.show();
            }
        });

    }

    /**
     * 连接服务器，获取用户信息
     */
    private void getUserInfo() {
        if (LoginUtil.getInfo(mContext) == null) return;
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USERINFO);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().userInfo(requestBody, requestBody1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        if (model.getStatus() == HttpCode.INVALID_LOGIN_STATE) {
                            /*showLoginStatusErrorDialog(0);*/
                        } else if (model.getStatus() != HttpCode.SUCCESS) {
                            showToast(model.getMessage());
                        } else if (model.getStatus() == HttpCode.SUCCESS) {
                            Gson gson = new Gson();
                            LoginModle loginModle = gson.fromJson(gson.toJson(model), LoginModle.class);
                            info = LoginUtil.getInfo(mContext);
                            info.getData().setUser(loginModle.getData().getUser());
                            LoginUtil.saveInfo(mContext, info);
                            setData();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // info = LoginUtil.getInfo(this);
        getUserInfo();
        setData();
        getUserMessage();
    }

    /**
     * 获取数据后，显示到界面
     */
    private void setData() {
        if (info != null && info.getData() != null) {
            LoginModle.DataBean.UserBean userBean = info.getData().getUser();
            if (userBean == null) return;
            tvName.setText(userBean.getNickname());
            inteTv.setText(userBean.getIntegral() + "");
            walletTv.setText(userBean.getAssets() + "");
            friendTv.setText(userBean.getFriends() + "");
            setType(userBean.getType());
            String inviteCode = info.getData().getUser().getInvitecode();
            if (StringUtil.isEmpty(inviteCode)) {
                rlShareApp.setVisibility(View.VISIBLE);
                rlNo.setVisibility(View.GONE);
            } else {
                rlNo.setVisibility(View.VISIBLE);
                rlShareApp.setVisibility(View.GONE);
                tvInviteNo.setText("我的邀请码(" + info.getData().getUser().getInvitecode() + ")");
            }
            LoginModle.DataBean.UserBean.AvatarBean avatarBean = info.getData().getUser().getAvatar();
            if (avatarBean != null) {
                String avatarUrlBig = avatarBean.getSmall();
                if (!StringUtil.isEmpty(avatarUrlBig)) {
                    Picasso.with(this).load(info.getData().getUser().getAvatar().getUrl()).error(R.mipmap.ic_holder_header_small).placeholder(R.mipmap.ic_holder_header_big).into(imagePerson);
                } else {
                    imagePerson.setImageResource(R.mipmap.ic_holder_header_big);
                }
            } else {
                imagePerson.setImageResource(R.mipmap.ic_holder_header_big);
            }

        }
    }


    private void getUserTime() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.GETDESIGNERTIME);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().get_designer_time(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TimeModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TimeModel model) {
                        timeDesc.setText(model.getData().getStart_time() + "至" + model.getData().getEnd_time());
                        Long noticeTime = Long.parseLong(TimeUtil.getTime(model.getData().getNotice_time()));

                        if (System.currentTimeMillis() > noticeTime) {
                            payBtn.setVisibility(View.VISIBLE);
                            timeDesc.setVisibility(View.VISIBLE);

                        } else {
                            payBtn.setVisibility(View.GONE);
                            timeDesc.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前界面
                 */
                finish();
                break;
            case R.id.image_person:
                /**
                 * 选择图片
                 */
                checkPermission();
                break;
            case R.id.rl_collect:
                /**
                 * 跳转到我的收藏页面
                 */
                gotoNewAty(MyCollectAct.class);
                break;

            case R.id.rl_setting:
                /**
                 * 跳转到设置列表
                 */
                gotoNewAty(SettingAct.class);
                break;
            case R.id.rl_user_log:
                /**
                 * 跳转到设置列表
                 */
                gotoNewAty(UserLogAct.class);
                break;
            case R.id.rl_shareApp:
                /**
                 * 分享app
                 */
                if (mPopShare == null) {
                    mPopShare = new PopShare(mContext, mIwxapi, mIWeiboShareAPI, rlNo, Tag);
                }
                mPopShare.setmUrl(Constant.APP_DOWNLOAD_URL);
                mPopShare.setmSubhead("JAJAHOME下载链接");
                mPopShare.setText(null);
                mPopShare.show();
                break;
            case R.id.rl_no:
                /**
                 * 分享邀请码
                 */
                if (mPopShare == null) {
                    mPopShare = new PopShare(mContext, mIwxapi, mIWeiboShareAPI, rlNo, Tag);
                    mPopShare.showER();
                    mPopShare.setCodeText("邀请码:" + info.getData().getUser().getInvitecode());
                }
                // mPopShare.setmUrl(null);
                // mPopShare.setText("JAJAHOME注册邀请码:" + info.getData().getUser().getInvitecode() + " " + Constant.APP_DOWNLOAD_URL);
                mPopShare.setmUrl(Constant.APP_DOWNLOAD_URL);
                mPopShare.setText("用" + "\"艺术+技术\"" + "实现美好生活" + "\"家\"" + "搜索即设计，所见即所得!");
                mPopShare.setmSubhead(null);
                mPopShare.show();
                break;
            case R.id.rl_friend:
                /**
                 * 跳转到邀请好友列表
                 */
                gotoNewAty(FriendListAct.class);
                break;
            case R.id.rl_order:
                /**
                 * 跳转到我得订单
                 */
                gotoNewAty(OrderListAct.class);
                break;
            case R.id.tv_apply_name:
                /**
                 * 跳转到申请设计师列表
                 */
                if (isClick)
                    if (!setPhone()) {
                        LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
                        builder.setMessage("您还未绑定手机号\n是否绑定?");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        });
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                gotoNewAty(PhoneAct.class);
                            }
                        });
                        builder.create().show();
                    } else {
                        gotoNewAty(DesignerApplyAct.class);
                    }
                break;
            case R.id.ibtn_news:
                /**
                 * 跳转到消息中心列表
                 */
                gotoNewAty(NewsAct.class);
                break;
            case R.id.rl_value_log:
                gotoNewAty(ValueAct.class);
                break;

        }
    }

    private boolean setPhone() {
        //判断是否是第三方登录是否绑定手机号；
        info = LoginUtil.getInfo(this);
        if (info != null) {
            LoginModle.DataBean data = info.getData();
            if (data != null) {
                if (data.getUser().getLoginType().equals("1")) {
                    return true;
                } else {
                    LoginModle.DataBean.UserBean user = data.getUser();
                    if (user != null) {
                        String userName = user.getUsername();
                        String userMobile = user.getMobile();
                        if (userName.equals(userMobile)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * 获取读取内存卡，和 拍照的权限 Android 6.0 以后
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //应用还未获取读取本地文件 的权限，询问是否允许
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, DesignApplySelectPhotoAdapter.PERMISSON_STORGE);
        } else {
            selectPhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == DesignApplySelectPhotoAdapter.PERMISSON_STORGE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto();
            } else {
                showToast("权限被禁止，无法选取图片");
            }
        }
    }

    /**
     * 选择图片
     */
    private void selectPhoto() {
        int chose_mode = Constant.MODE_SINGLE_PICK;
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setMaxBitmapSize(500);
        options.setCompressionQuality(90);
        new PickConfig.Builder(this)
                .isneedcrop(true)
                .actionBarcolor(Color.parseColor("#FF741A"))
                .statusBarcolor(Color.parseColor("#FF741A"))
                .isneedcamera(true)
                .isSqureCrop(true)
                .cropOutX(256)
                .cropOutY(256)
                .setUropOptions(options)
                .maxPickSize(1)
                .spanCount(3)
                .pickMode(chose_mode).build();
    }

    /**
     * 剪切图片完成回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            ArrayList<String> paths = data.getStringArrayListExtra("data");
            path = paths.get(0);
            upBase64();
        }
    }

    /**
     * 将path转化成base64上传
     */
    private void upBase64() {
        str = Base64Helper.encodeBase64File(path);
        updateAvatar();
    }

    /**
     * 上传头像
     */
    private void updateAvatar() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_AVATAR);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setAvatar(str);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showProgressDialog("上传中");
        mSubscription = ApiImp.get().sendAvatar(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AvatarModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        showToast("上传失败");
                    }

                    @Override
                    public void onNext(AvatarModel avatarModel) {
                        if (avatarModel.getStatus() == 0) {
                            T.showShort(mContext, "上传成功");
                            Picasso.with(UserAct.this).load(avatarModel.getData().getUser().getAvatar().getUrl()).error(R.mipmap.ic_holder_header_big).into(imagePerson);
                            info.getData().getUser().getAvatar().setSmall(avatarModel.getData().getUser().getAvatar().getSmall());
                            info.getData().getUser().getAvatar().setThumb(avatarModel.getData().getUser().getAvatar().getThumb());
                            info.getData().getUser().getAvatar().setUrl(avatarModel.getData().getUser().getAvatar().getUrl());
                            LoginUtil.saveInfo(mContext, info);
                        }
                    }
                });
    }


    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == SettingAct.LOGIN_OUT_ACTION) {
            finish();
        } else if (event.action == CHANGE_ACTION) {
            getUserInfo();
        } else if (event.action == MPAY_METHOD) {
            String mPay = event.msg;
            int mPayWay = Integer.valueOf(mPay);
            if (mPayWay == 0) {
                //使用微信支付
                getWeichatPayInfo();
            } else if (mPayWay == 1) {
                getAlipayInfo();
            } else {
                showToast(R.string.select_way_pay);
            }
        } else if (event.action == WXPayEntryActivity.WEICAT_PAY_RESULT_ACTION) {
            dissmisProgressDialog();
            //微信支付结果
            int code = Integer.valueOf(event.msg);
            if (code == BaseResp.ErrCode.ERR_USER_CANCEL) {
                showToast("已取消支付");
            } else if (code == BaseResp.ErrCode.ERR_OK) {
                showToast("支付成功");
                getUserInfo();
            }
        } else if (event.action == SUPER_DESIGNER_APLIAY) {
            getUserInfo();
        }
    }

    private void setType(String type) {
        if (StringUtil.isEmpty(type)) return;
        //0 游客 ,1 注册用户(一般用户), 2 设计师(申请中), 3 认证设计师(审核通过), 4装饰公司,5 城市合伙人,6协会 7 服务商,8 内部员工
        switch (type) {
            case "1":
                isClick = true;
                //   tvApply.setText(R.string.apply_designer);
                //   tvApply.setVisibility(View.GONE);
                tvApply.setVisibility(View.GONE);
                tvApplyName.setVisibility(View.VISIBLE);
                tvApplyName.setText(R.string.apply_designer);
                imgV.setVisibility(View.INVISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "2":
                isClick = false;
                tvApply.setText(R.string.applying_designer);
                imgV.setVisibility(View.INVISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "3":
                superDesigner.setVisibility(View.VISIBLE);
                if (info != null) {
                    int type_class = info.getData().getUser().getType_class();
                    isClick = false;
                    if (type_class == 1) {
                        tvApply.setText(R.string.designer);
                        imgV.setImageResource(R.mipmap.ic_v_designer);
                        imgV.setVisibility(View.VISIBLE);
                    } else if (type_class == 5) {
                        // tvApply.setText(R.string.super_designer);
                        tvApply.setVisibility(View.GONE);
                        imgV.setImageResource(R.mipmap.ic_v_super_designer);
                        imgV.setVisibility(View.VISIBLE);
                        superDesigner.setVisibility(View.GONE);
                        beSuperDesigner.setVisibility(View.VISIBLE);
                        getUserTime();
                    } else {
                        tvApply.setText(R.string.designer);
                        imgV.setImageResource(R.mipmap.ic_v_designer);
                        imgV.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case "4":
                isClick = false;
                tvApply.setText(R.string.decoration_company);
                imgV.setImageResource(R.mipmap.ic_v_decoration_company);
                imgV.setVisibility(View.VISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "5":
                isClick = false;
                tvApply.setText(R.string.city_partner);
                imgV.setImageResource(R.mipmap.ic_v_city_partner);
                imgV.setVisibility(View.VISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "6":
                isClick = false;
                tvApply.setText(R.string.xiehui);
                imgV.setImageResource(R.mipmap.ic_v_xiehui);
                imgV.setVisibility(View.VISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "7":
                isClick = false;
                tvApply.setText(R.string.facilitator);
                imgV.setImageResource(R.mipmap.ic_v_facilitator);
                imgV.setVisibility(View.VISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
            case "8":
                isClick = false;
                tvApply.setText(R.string.internastaff);
                imgV.setImageResource(R.mipmap.ic_v_internastaff);
                imgV.setVisibility(View.VISIBLE);
                superDesigner.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 获取用户消息
     */
    private void getUserMessage() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(1);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage reqPage = new ReqPage();
        reqPage.setCmd(Constant.USER_MESSAGE);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getUserMessage(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMessageModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(UserMessageModel userMessageModel) {
                        if (userMessageModel.getStatus() == HttpCode.SUCCESS) {
                            String newNums = userMessageModel.getData().getNewX();
                            if (StringUtil.isEmpty(newNums) || "0".equals(newNums)) {
                                tvNewX.setVisibility(View.GONE);
                            } else {
                                tvNewX.setText(newNums);
                                tvNewX.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * 获取微信支付预处理信息
     */
    private void getWeichatPayInfo() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.DESIGNERMPAY);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("获取微信支付信息");
        mSubscription = ApiImp.get().designer_wxpay(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JWeichatPayModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                        showLoading(false, "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        showLoading(false, "");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(JWeichatPayModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            showProgressDialog("进入微信支付");
                            mIwxapi.sendReq(WxPayApi.genPayReq(model));
                        }
                    }
                });
    }


    /**
     * 获取支付宝预处理
     */
    private void getAlipayInfo() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.DESIGNERALIPAY);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("获取支付信息");
        mSubscription = ApiImp.get().designer_alipay(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlipayModel>() {
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
                    public void onNext(AlipayModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            startAlipay(model.getData().getOrder_string());
                        }
                    }
                });
    }


    private void startAlipay(final String alipayInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UserAct.this);
                Map<String, String> result = alipay.payV2(alipayInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付结果在此回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功 通知 我的订单处刷新列表
                        EventBus.getDefault().post(new EventMessage(SUPER_DESIGNER_APLIAY, ""));

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}

