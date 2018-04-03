package com.jajahome.feature.user.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.home.MainAty;
import com.jajahome.model.CashModel;
import com.jajahome.model.PickAccountModel;
import com.jajahome.model.WxTokenModel;
import com.jajahome.model.WxUserInfoModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.CashReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopCash;
import com.jajahome.util.HttpUtils;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/5.
 */
public class CashAct extends BaseActivity {

    public static final int action = 0x01;
    public static final String CASHACT = "CASHACT";

    @BindView(R.id.ibtn_back)
    ImageButton iBackBtn;
    @BindView(R.id.textView2)
    TextView titleTv;
    @BindView(R.id.cb_weichat)
    CheckBox cbWeichat;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.rignov_cash)
    Button rigNowBtn;
    @BindView(R.id.input_num)
    TextView aliplyText;
    @BindView(R.id.input_num_wx)
    TextView wxplyText;
    @BindView(R.id.wx_add)
    TextView wxTv;
    @BindView(R.id.ali_add)
    TextView alipayTv;
    @BindView(R.id.cash)
    EditText cashET;
    @BindView(R.id.can_cash)
    TextView canCash;

    private WxTokenModel tokenModel;
    private WxUserInfoModel wxModel;
    private PickAccountModel pickModel;
    private PopCash mPopCash;
    private int mPayWay = -1;//支付方式 0微信 1支付宝,-1未选择
    private String alipayAccount;
    private String totalCash;

    private IWXAPI mIwxapi;
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
                wxplyText.setText(wxModel.getNickname());

            }
        }
    };

    private String getWxUserUri(String token, String openId) {
        String requestUserUri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
        return requestUserUri;
    }

    @Override
    protected int getViewId() {
        return R.layout.act_cash;
    }

    @Override
    protected void initEvent() {
        titleTv.setText("提现");
        //微信登录
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        mIwxapi.registerApp(Constant.WEICHAT_APP_ID);
        totalCash = getIntent().getStringExtra(CASHACT);
        canCash.setText("最多可提现金额" + totalCash + "元");
        pickCash_account();
        setListener();
    }

    private void setListener() {
        iBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cbWeichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 0) {
                    mPayWay = 0;
                    cbWeichat.setChecked(true);
                    cbAlipay.setChecked(false);
                } else {
                    mPayWay = -1;
                }
            }
        });
        cbAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 1) {
                    mPayWay = 1;
                    cbWeichat.setChecked(false);
                    cbAlipay.setChecked(true);
                } else {
                    mPayWay = -1;
                }
            }
        });

        rigNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPayWay == -1) {
                    showToast(R.string.select_way_pay);
                    return;
                } else if (mPayWay == 1) {
                    reward_cash(0);
                } else if (mPayWay == 0) {
                    reward_cash(1);
                }
            }
        });

        alipayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopCash == null) {
                    mPopCash = new PopCash(CashAct.this, alipayTv);
                }
                mPopCash.show();
            }
        });

        wxTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWeixinAvilible(CashAct.this)) {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    //发送授权登陆请求
                    mIwxapi.sendReq(req);
                } else {
                    Toast.makeText(CashAct.this, "快快安装一个微信客户端吧", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == action) {
            aliplyText.setText(event.msg);
            this.alipayAccount = event.msg;
        } else if (event.action == MainAty.WX_LOGIN) {
            startWxLogin(event.msg);
        }
    }

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

    private void reward_cash(int type) {
        if (LoginUtil.getInfo(mContext) == null) return;
        CashReq.ContentBean bean = new CashReq.ContentBean();
        String etNet = getResources().getString(R.string.add_cash_tip);
        if (pickModel != null && type == 0) {
            String accNet = pickModel.getData().getPickAccount().getAliAccount().toString();
            String accLocal = aliplyText.getText().toString();
            if (StringUtil.isEmpty(accNet) && accLocal.equals(etNet)) {
                showToast("请添加支付宝帐户");
                return;
            } else {
                if (!StringUtil.isEmpty(accLocal)) {
                    bean.setAliAccount(accLocal);
                }
            }
        } else if (pickModel != null && type == 1) {
            String accWxNetNick = pickModel.getData().getPickAccount().getWxNickName().toString();
            String accWxNetOpen = pickModel.getData().getPickAccount().getWxOpenid().toString();
            String accLocal = wxplyText.getText().toString();
            if (StringUtil.isEmpty(accWxNetNick) && StringUtil.isEmpty(accWxNetOpen) && accLocal.equals(etNet)) {
                showToast("请添加微信帐户");
                return;
            } else {
                bean.setWxNickName(accLocal);
                if (wxModel != null) {
                    bean.setWxOpenid(wxModel.getOpenid());
                }

            }
        }

        bean.setType(type);
        String cash = cashET.getText().toString();
        if (StringUtil.isEmpty(cash)) {
            showToast(R.string.input_reward_cash);
            return;
        }

        if (!StringUtil.isHuZhi(cash)) {
            showToast(R.string.input_num);
            return;
        }

        if (StringUtil.isEnoughFloat(cash) || cash.equals("0")) {
            showToast(R.string.cash_enough);
            return;
        }

        if (!StringUtil.isEmpty(cash)) {
            Double rewardCash = Double.parseDouble(cash);
            bean.setCash(rewardCash);
        }

        CashReq req = new CashReq();
        req.setContent(bean);
        req.setCmd(Constant.REWARD_CASH);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().reward_cash(requestBody, requestBody1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CashModel>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CashModel model) {
                if (model.getStatus() == 0) {
                    showToast(model.getMessage());
                    finish();
                } else {
                    showToast(model.getMessage());
                }
            }

        });
    }

    private void pickCash_account() {
        ReqPage baseReq = new ReqPage();
        baseReq.setCmd(Constant.PICKCAH_ACCOUNT);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("获取账户信息");
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
                        pickModel = model;
                        if (model.getStatus() == HttpCode.SUCCESS) {

                            String accNet = model.getData().getPickAccount().getAliAccount();
                            if (StringUtil.isEmpty(accNet)) {
                                aliplyText.setText("请点击右方添加按钮输入提现帐号");
                                alipayTv.setText("添加");

                            } else {
                                aliplyText.setText(accNet);
                                alipayTv.setText("更换");
                            }

                            String accWXNet = model.getData().getPickAccount().getWxNickName();
                            if (StringUtil.isEmpty(accWXNet)) {
                                wxplyText.setText("请点击右方添加按钮输入提现帐号");
                                wxTv.setText("添加");
                            } else {
                                wxplyText.setText(accWXNet);
                                wxTv.setText("更换");
                            }

                            int type = model.getData().getPickAccount().getType();
                            if (type == 0 && !StringUtil.isEmpty(accNet)) {
                                mPayWay = 1;
                                cbWeichat.setChecked(false);
                                cbAlipay.setChecked(true);
                            } else if (type == 1 && !StringUtil.isEmpty(accWXNet)) {
                                mPayWay = 0;
                                cbWeichat.setChecked(true);
                                cbAlipay.setChecked(false);
                            }
                        }
                    }
                });
    }

}
