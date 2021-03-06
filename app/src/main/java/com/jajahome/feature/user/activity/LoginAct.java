package com.jajahome.feature.user.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.view.LoginInputDialog;
import com.jajahome.model.LoginModle;
import com.jajahome.model.QQuserModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.model.SinaUserModel;
import com.jajahome.model.ThirdLoginModel;
import com.jajahome.model.WxTokenModel;
import com.jajahome.model.WxUserInfoModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.WeiXinReq;
import com.jajahome.network.WxReq;
import com.jajahome.util.AESUtils;
import com.jajahome.util.HttpUtils;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.SPUtils;
import com.jajahome.util.StringHelper;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.widget.thirdlogin.qq.BaseUiListener;
import com.jajahome.widget.thirdlogin.sina.AuthListener;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登陆页面
 * Created by laotang on 2016/7/18.
 */
public class LoginAct extends BaseActivity implements View.OnClickListener {
    public static final int WX_LOGIN = 0X00;
    public static final int QQ_LOGIN = 0X01;
    public static final int SINA_LOGIN = 0X02;
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.wx_login)
    ImageView wxLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.weibo_login)
    ImageView weiboLogin;
    /*@BindView(R.id.email_login)
    ImageView emailLogin;*/


    private String user;
    private String pwd;
    private IWXAPI mIwxapi;
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;

    @Override
    protected int getViewId() {
        return R.layout.act_login;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        wxLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        weiboLogin.setOnClickListener(this);
        /*emailLogin.setOnClickListener(this);*/

        //微信登录
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        mIwxapi.registerApp(Constant.WEICHAT_APP_ID);

        mTencent = Tencent.createInstance(Constant.APP_ID, LoginAct.this.getApplicationContext());

        //新浪微博登录
        mAuthInfo = new AuthInfo(this,
                Constant.WEIBO_APP_KEY, Constant.SINA_REDIRECT_URL,
                Constant.SINA_SCOPE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前页面
                 */
                finish();
                break;
            case R.id.btn_login:
                /**
                 * 进行登陆操作
                 */
                if (isCheckInput()) {
                    sendLogin();
                }
                break;
            case R.id.tv_register:
                /**
                 * 跳转到注册页面
                 */
                gotoNewAty(RegisterAct.class);
                break;
            case R.id.tv_forget_pwd:
                /**
                 * 跳转到忘记密码
                 */
                gotoNewAty(ForgetPwdAct.class);
                break;

            case R.id.wx_login:
                if (isWeixinAvilible(this)) {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    //发送授权登陆请求
                    mIwxapi.sendReq(req);
                } else {
                    Toast.makeText(this, "快快安装一个微信客户端吧", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.qq_login:
                mIUiListener = new BaseUiListener(LoginAct.this, mTencent);
                mTencent.login(LoginAct.this, "all", mIUiListener);
                break;

            case R.id.weibo_login:
                mSsoHandler = new SsoHandler(this, mAuthInfo);
                mSsoHandler.authorize(new AuthListener(LoginAct.this));
                break;
           /* case R.id.email_login:
                break;*/

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        } else if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 对登陆信息进行判断
     *
     * @return
     */
    private boolean isCheckInput() {
        user = editUser.getText().toString().trim();
        pwd = editPwd.getText().toString().trim();
        if (StringHelper.isEditTextEmpty(editUser)) {
            T.showShort(LoginAct.this, getString(R.string.toast_login_username_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editPwd)) {
            T.showShort(LoginAct.this, getString(R.string.toast_login_pwd_empty));
            return false;
        }
        return true;
    }

    /**
     * 登陆
     */
    private void sendLogin() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_SIGNIN);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setUsername(user);
        contentBean.setPassword(pwd);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("请稍等...");
        ApiImp.get().sendLogin(requestBody)
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
                        T.showLong(LoginAct.this, getString(R.string.networkProblem));
                    }

                    @Override
                    public void onNext(SimpleModel loginModle) {
                        if (loginModle.getStatus() == HttpCode.SUCCESS) {
                            String json = gson.toJson(loginModle);
                            LoginUtil.saveInfo(LoginAct.this, gson.fromJson(json, LoginModle.class));
                            T.showLong(LoginAct.this, "登录成功");
                            SPUtils.put(getApplication(), "isLogin", true);
                            EventBus.getDefault().post(new EventMessage(Constant.LOGIN, Constant.SIGNSUCCESS));
                            finish();
                        } else {
                            T.showLong(LoginAct.this, loginModle.getMessage());
                        }
                    }
                });
    }

    /**
     * 判断是否已安装微信
     */

    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == MainAty.WX_LOGIN) {
            loginType = 4;
            startWxLogin(event.msg);
        } else if (event.action == MainAty.QQ_LOGIN) {

            loginType = 2;
            String qqResult = event.msg;
            Gson gson = new Gson();
            qqUserModel = gson.fromJson(qqResult, QQuserModel.NameValuePairsBean.class);
            firstResigst(qqUserModel.getOpenid());
        } else if (event.action == MainAty.SINA_LOGIN) {
            loginType = 3;
            String sinaResult = event.msg;
            Gson gson = new Gson();
            sinaUserModel = gson.fromJson(sinaResult, SinaUserModel.class);
            firstResigst(sinaUserModel.getIdstr());

        }
    }

    public int loginType;
    //第三方登录查询是否首次注册

    private void firstResigst(String openId) {
        WeiXinReq baseReq = new WeiXinReq();
        baseReq.setCmd(Constant.QUERYOPENID);
        WeiXinReq.ContentBean contentBean = new WeiXinReq.ContentBean();
        contentBean.setOpenId(openId);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        ApiImp.get().queryOpenID(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThirdLoginModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        T.showLong(LoginAct.this, getString(R.string.networkProblem));
                    }

                    @Override
                    public void onNext(ThirdLoginModel model) {

                        if (model.getData().getIsFirstLogin() == 0) {
                            //第三方首次登录视为注册
                            final LoginInputDialog.Builder builder = new LoginInputDialog.Builder(mContext);

                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String msg = builder.getMessage();

                                    if (!StringUtil.isEmpty(msg)) {
                                        tLoginWx(loginType, msg, getTimestamp());
                                        dialog.dismiss();
                                    } else {
                                        showToast("请输入邀请码");
                                    }
                                }
                            });

                            builder.create().show();

                        } else if (model.getData().getIsFirstLogin() == 1) {
                            //第三方正常登录
                            tLoginWx(loginType, "", getTimestamp());
                        }

                    }
                });

    }
   /* 0 ->loginType  状态:
       1 -> 手机
       2 -> QQ
       3 -> 微博
       4 -> 微信
       5 -> 邮件*/

    private String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    private String key = "0jtiXere3s7LbCu4Ct6oOwaQUDGo3cTX";

    //开始第三方登录
    private void tLoginWx(int loginType, String invitecode, String timestamp) {
        WxReq baseReq = new WxReq();
        baseReq.setCmd(Constant.USER_THIRD_LOGIN);
        WxReq.ContentBean contentBean = new WxReq.ContentBean();
        switch (loginType) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                contentBean.setLoginType(loginType);
                contentBean.setOpenId(qqUserModel.getOpenid());
                contentBean.setNickName(qqUserModel.getNickname());
                contentBean.setHeadImageUrl(qqUserModel.getFigureurl_qq_2());
                contentBean.setSex(qqUserModel.getGender());
                contentBean.setInvitecode(invitecode);
                contentBean.setTimestamp(timestamp);
                contentBean.setLoginKey(AESUtils.md5(qqUserModel.getOpenid() + loginType + timestamp + key));
                if (!StringUtil.isEmpty(invitecode)) {
                    contentBean.setLoginKey(AESUtils.md5(qqUserModel.getOpenid() + loginType + invitecode + timestamp + key));
                } else {
                    contentBean.setLoginKey(AESUtils.md5(qqUserModel.getOpenid() + loginType + timestamp + key));
                }

                break;
            case 3:
                contentBean.setLoginType(loginType);
                contentBean.setOpenId(sinaUserModel.getIdstr());
                contentBean.setNickName(sinaUserModel.getName());
                contentBean.setHeadImageUrl(sinaUserModel.getAvatar_large());
                if (sinaUserModel.getGender().equals("m")) {
                    contentBean.setSex("男");
                } else {
                    contentBean.setSex("女");
                }
                contentBean.setInvitecode(invitecode);
                contentBean.setTimestamp(timestamp);
                contentBean.setLoginKey(AESUtils.md5(sinaUserModel.getIdstr() + loginType + timestamp + key));
                if (!StringUtil.isEmpty(invitecode)) {
                    contentBean.setLoginKey(AESUtils.md5(sinaUserModel.getIdstr() + loginType + invitecode + timestamp + key));
                } else {
                    contentBean.setLoginKey(AESUtils.md5(sinaUserModel.getIdstr() + loginType + timestamp + key));
                }
                break;
            case 4:
                contentBean.setLoginType(loginType);
                contentBean.setOpenId(wxModel.getOpenid());
                contentBean.setNickName(wxModel.getNickname());
                contentBean.setHeadImageUrl(wxModel.getHeadimgurl());
                if (wxModel.getSex() == 1) {
                    contentBean.setSex("男");
                } else {
                    contentBean.setSex("女");
                }
                contentBean.setInvitecode(invitecode);
                contentBean.setTimestamp(timestamp);
                contentBean.setLoginKey(AESUtils.md5(wxModel.getOpenid() + loginType + timestamp + key));
                if (!StringUtil.isEmpty(invitecode)) {
                    contentBean.setLoginKey(AESUtils.md5(wxModel.getOpenid() + loginType + invitecode + timestamp + key));
                } else {
                    contentBean.setLoginKey(AESUtils.md5(wxModel.getOpenid() + loginType + timestamp + key));
                }
                break;
            case 5:
                break;
        }

        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        showProgressDialog("正在登录...");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        ApiImp.get().user_third_login(requestBody)
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
                        T.showLong(LoginAct.this, getString(R.string.networkProblem));
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            String json = gson.toJson(model);
                            LoginUtil.saveInfo(LoginAct.this, gson.fromJson(json, LoginModle.class));
                            T.showLong(LoginAct.this, "登录成功");
                            SPUtils.put(getApplication(), "isLogin", true);
                            finish();
                        } else {
                            T.showLong(LoginAct.this, "登录失败");
                        }
                    }
                });
    }

    private WxTokenModel tokenModel;
    private WxUserInfoModel wxModel;
    private QQuserModel.NameValuePairsBean qqUserModel;
    private SinaUserModel sinaUserModel;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String tokenResult = (String) msg.obj;
                Gson gson = new Gson();
                tokenModel = gson.fromJson(tokenResult, WxTokenModel.class);
                if (tokenModel != null) {
                    //获取第三方用户model
                    final String thirdUserUri = getWxUserUri(tokenModel.getAccess_token(), tokenModel.getOpenid());
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            String userResult = HttpUtils.netConnect(thirdUserUri);
                            Message msg1 = new Message();
                            msg1.what = 2;
                            msg1.obj = userResult;
                            mHandler.sendMessage(msg1);

                        }
                    }.start();
                }
            } else if (msg.what == 2) {
                //获取了第三方用户信息开始查询是否是注册用户
                String Result = (String) msg.obj;
                Gson gson = new Gson();
                wxModel = gson.fromJson(Result, WxUserInfoModel.class);
                if (wxModel == null) {
                    return;
                }
                firstResigst(wxModel.getOpenid());
            }
        }
    };


    private void startWxLogin(String code) {
        //获取微信tokenModel
        final String tokenUri = getTokenUri(code);
        new Thread() {
            @Override
            public void run() {
                super.run();
                String tokenResult = HttpUtils.netConnect(tokenUri);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = tokenResult;
                mHandler.sendMessage(msg);

            }
        }.start();

    }


    private String getTokenUri(String code) {
        String requestTokenUri = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constant.WEICHAT_APP_ID + "&secret=" + Constant.SECRET + "&code=" + code + "&grant_type=authorization_code";
        return requestTokenUri;
    }

    private String getWxUserUri(String token, String openId) {
        String requestUserUri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
        return requestUserUri;
    }
}
