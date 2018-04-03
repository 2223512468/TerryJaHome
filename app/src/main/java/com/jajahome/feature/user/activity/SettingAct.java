package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.view.CacheDialog;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.LoginModle;
import com.jajahome.util.DataCleanManager;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;

import butterknife.BindView;

/**
 * 个人中心  设置页面
 * Created by laotang on 2016/7/19.
 */
public class SettingAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rl_ziliao)
    RelativeLayout rlZiliao;
    @BindView(R.id.rl_safe)
    RelativeLayout rlSafe;
    @BindView(R.id.rl_inform)
    RelativeLayout rlInform;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.rl_cache)
    RelativeLayout rlCache;
    @BindView(R.id.rl_serve)
    RelativeLayout rlServe;
    @BindView(R.id.rl_aboutUs)
    RelativeLayout rlAboutUs;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.rl_bang)
    RelativeLayout bangRL;
    @BindView(R.id.band_tv)
    TextView bingTv;
    @BindView(R.id.change_password)
    RelativeLayout changeRL;
    @BindView(R.id.rl_address)
    RelativeLayout addressRL;


    private String totalCacheSize;//缓存大小
    private LoginModle info;

    @Override
    protected int getViewId() {
        return R.layout.act_setting;
    }

    @Override
    protected void initEvent() {
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ibtnBack.setOnClickListener(this);
        rlZiliao.setOnClickListener(this);
        rlSafe.setOnClickListener(this);
        rlInform.setOnClickListener(this);
        rlCache.setOnClickListener(this);
        rlServe.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        bangRL.setOnClickListener(this);
        changeRL.setOnClickListener(this);
        addressRL.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 返回键
                 */
                finish();
                break;
            case R.id.rl_ziliao:
                /**
                 * 个人资料
                 */
                gotoNewAty(DataAct.class);
                break;
            case R.id.rl_safe:
                /**
                 * 账户与安全
                 */
                gotoNewAty(AccountAct.class);
                break;
            case R.id.rl_inform:
                /**
                 *打开通知
                 */
                break;
            case R.id.rl_cache:
                //清除缓存
                CacheDialog.Builder builde = new CacheDialog.Builder(SettingAct.this);
                builde.setMessage("是否清除缓存")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DataCleanManager.cleanInternalCache(SettingAct.this);
                                T.showShort(SettingAct.this, "已清除" + totalCacheSize + "缓存");
                                try {
                                    totalCacheSize = DataCleanManager.getTotalCacheSize(SettingAct.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                builde.create().show();
                break;
            case R.id.rl_serve:
                /**
                 *  服务与反馈
                 */
                gotoNewAty(FeedbackAct.class);
                break;
            case R.id.rl_aboutUs:
                /**
                 *   关于我们h5
                 */
                gotoNewAty(AboutUsAct.class);
                break;
            case R.id.btn_exit:
                /**
                 * 退出登陆
                 */
                LoginDialog.Builder builder = new LoginDialog.Builder(this);
                builder.setMessage("是否退出登录?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loginOut();
                        gotoNewAty(LoginAct.class);
                        finish();
                    }
                });
                builder.create().show();
                break;
            case R.id.rl_bang:
                getAttach();
                break;
            case R.id.change_password:
                gotoNewAty(AccountAct.class);
                break;
            case R.id.rl_address:
                gotoNewAty(MyAddressAct.class);
                break;
        }
    }

    private void getAttach() {
        if (bingTv.getText().toString().equals("解绑手机")) {
            gotoNewAty(UnPhoneAct.class);
        } else if (bingTv.getText().toString().equals("绑定手机号")) {
            gotoNewAty(PhoneAct.class);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        info = LoginUtil.getInfo(this);
        if (info != null && info.getData() != null) {
            LoginModle.DataBean.UserBean userBean = info.getData().getUser();
            String loginType = userBean.getLoginType();
            if (!StringUtil.isEmpty(loginType) && loginType.equals("1")) {
                rlSafe.setVisibility(View.VISIBLE);
                bangRL.setVisibility(View.GONE);
            } else {
                rlSafe.setVisibility(View.GONE);
                bangRL.setVisibility(View.VISIBLE);

                if (userBean.getUsername().equals(userBean.getMobile())) {
                    bingTv.setText(R.string.band_phone);
                    changeRL.setVisibility(View.GONE);
                } else {
                    bingTv.setText(R.string.jiebang);
                    changeRL.setVisibility(View.VISIBLE);
                }
            }
        }

    }
}
