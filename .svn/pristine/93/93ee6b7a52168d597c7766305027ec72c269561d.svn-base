package com.jajahome.feature.user.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.H5Act;
import com.jajahome.model.AuthCodeModle;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpCode;
import com.jajahome.util.CommonUtils;
import com.jajahome.util.StringHelper;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 注册
 * Created by laotang on 2016/7/18.
 */
public class RegisterAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.edit_sms)
    EditText editSms;
    @BindView(R.id.tv_getno)
    TextView tvGetno;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.edit_pwd_again)
    EditText editPwdAgain;
    @BindView(R.id.edit_invite)
    EditText editInvite;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.tv_register)
    Button tvRegister;
    private String user;
    private String inviteNo;
    private String smsNo;
    private String pwd;
    private String pwdAgain;
    private boolean isAgree = true;

    @Override
    protected int getViewId() {
        return R.layout.act_register;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        tvGetno.setOnClickListener(this);
        tvXieyi.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        cbXieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree = isChecked;
            }
        });
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
            case R.id.tv_register:
                /**
                 * 注册
                 */
                if (isCheckInput()) {
                    getRegister();
                }
                break;
            case R.id.tv_getno:
                /**
                 * 发送验证码
                 */
                getNum();
                break;
            case R.id.tv_xieyi:
                /**
                 * 跳转到认证许可h5页面
                 */
                H5Act.gotoH5(mContext, Constant.PERMISSION, getString(R.string.treaty));
                break;
        }
    }

    /**
     * 开始倒计时
     *
     * @param time
     */
    private void startCountDown(int time) {
        TimeCount mTimeCount = null;
        if (mTimeCount == null) {
            Long times = Long.valueOf(time + "000");
            mTimeCount = new TimeCount(times, 1000);
            tvGetno.setEnabled(false);
            mTimeCount.start();
        }
    }

    /**
     * 重新发送验证码
     *
     * @param
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tvGetno.setEnabled(true);
            tvGetno.setText("重发");
            tvGetno.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetno.setText("" + millisUntilFinished / 1000 + "s 重发");
        }
    }

    /**
     * 获取验证码
     */
    private void getNum() {
        user = editUser.getText().toString().trim();
        //检验手机号输入是否为空
        if (StringUtil.isEmpty(user)) {
            T.showShort(RegisterAct.this, getString(R.string.empty_phone));
            return;
        }
        //检验手机号输入是否合法
        if (!StringUtil.isTel(user)) {
            T.showShort(RegisterAct.this, getString(R.string.phone_is_illegal));
            return;
        }

        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.SMS_REQUEST);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setMobile(user);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().getAuthCode(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthCodeModle>() {

                    private int time;

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("验证码还未过期");
                    }

                    @Override
                    public void onNext(AuthCodeModle authCodeModle) {
                        AuthCodeModle.DataBean data = authCodeModle.getData();
                        time = data.getTime();
                        startCountDown(time);
                    }
                });

    }


    /**
     * 对登录的信息进行一个判断
     *
     * @return
     */
    public boolean isCheckInput() {
        if (!isAgree) {
            showToast(R.string.agree_xiyi);
            return false;
        }
        inviteNo = editInvite.getText().toString().trim();
        smsNo = editSms.getText().toString().trim();
        pwd = editPwd.getText().toString().trim();
        pwdAgain = editPwdAgain.getText().toString().trim();
        user = editUser.getText().toString().trim();
        //手机号判断不能为空
        if (StringHelper.isEditTextEmpty(editUser)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_username_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editSms)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_sms_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editPwd)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_pwd_empty));
            return false;
        }
        if (StringHelper.isEditTextEmpty(editPwdAgain)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_pwdAgain_empty));
            return false;
        }
        if (StringHelper.isEditTextEmpty(editInvite)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_invite_no_empty));
            return false;
        }
        //手机号码在6-11位s
        if (pwd.length() < Constant.APP_PASSWORD_LIMIT_LENGTH) {
            T.showShort(RegisterAct.this, getString(R.string.toast_password__limit_length));
            return false;
        }
        if (!pwd.equals(pwdAgain)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_pwd_no));
            return false;
        }
        //验证手机的格式如果不是
        if (!CommonUtils.isMobileNO(user)) {
            T.showShort(RegisterAct.this, getString(R.string.toast_login_phone_error));
            return false;
        }
        return true;
    }

    /**
     * 用户注册
     */
    private void getRegister() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_SIGNUP);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setUsername(user);
        contentBean.setPassword(pwd);
        contentBean.setSms_token(smsNo);
        contentBean.setInvite_code(inviteNo);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        showProgressDialog("注册中");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().getRegister(requestBody)
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel registerModle) {
                        if (registerModle.getStatus() == HttpCode.SUCCESS) {
                            gotoNewAty(LoginAct.class);
                            showToast("注册成功");
                            finish();
                        } else {
                            showToast(registerModle.getMessage());
                        }
                    }
                });
    }
}
