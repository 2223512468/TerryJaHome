package com.jajahome.feature.user.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.model.AuthCodeModle;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.MpReq;
import com.jajahome.util.CommonUtils;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.SPUtils;
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
 * Created by Administrator on 2017/4/7.
 */
public class PhoneAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.edit_sms)
    EditText editSms;
    @BindView(R.id.tv_getno)
    TextView tvGetno;
    @BindView(R.id.tv_register)
    Button regiBtn;
    @BindView(R.id.confir_edit_pwd)
    EditText confirEt;
    @BindView(R.id.ibtn_back)
    ImageButton imageBtn;


    private String user;
    private String setPassword;
    private String confirPassword;
    private String smsNo;

    @Override
    protected int getViewId() {
        return R.layout.act_phone;
    }

    @Override
    protected void initEvent() {

        tvGetno.setOnClickListener(this);
        regiBtn.setOnClickListener(this);
        imageBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_getno:
                getNum();
                break;
            case R.id.tv_register:
                if (isCheckInput()) {
                    bingPhoneNum();
                }
                break;
            case R.id.ibtn_back:
                finish();
                break;


        }
    }

    /**
     * 获取验证码
     */
    private void getNum() {
        user = editUser.getText().toString().trim();
        setPassword = editPwd.getText().toString().trim();
        //检验手机号输入是否为空
        if (StringUtil.isEmpty(user)) {
            T.showShort(PhoneAct.this, getString(R.string.empty_phone));
            return;
        }
        //检验手机号输入是否合法
        if (!StringUtil.isTel(user)) {
            T.showShort(PhoneAct.this, getString(R.string.phone_is_illegal));
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
     * 对登录的信息进行一个判断
     *
     * @return
     */
    public boolean isCheckInput() {

        smsNo = editSms.getText().toString().trim();
        setPassword = editPwd.getText().toString().trim();
        confirPassword = confirEt.getText().toString().trim();
        user = editUser.getText().toString().trim();
        //手机号判断不能为空
        if (StringHelper.isEditTextEmpty(editUser)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_username_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editSms)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_sms_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editPwd)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_pwd_empty));
            return false;
        }
        if (StringHelper.isEditTextEmpty(confirEt)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_pwdAgain_empty));
            return false;
        }

        //手机号码在6-11位s
        if (setPassword.length() < Constant.APP_PASSWORD_LIMIT_LENGTH) {
            T.showShort(PhoneAct.this, getString(R.string.toast_password__limit_length));
            return false;
        }

        //验证手机的格式如果不是
        if (!CommonUtils.isMobileNO(user)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_phone_error));
            return false;
        }

        if (!setPassword.equals(confirPassword)) {
            T.showShort(PhoneAct.this, getString(R.string.toast_login_pwd_no));
            return false;
        }

        return true;
    }


    /**
     * 绑定手机号
     */

    private void bingPhoneNum() {
        MpReq baseReq = new MpReq();
        baseReq.setCmd(Constant.BINDINGMOBILEPHONE);
        MpReq.ContentBean contentBean = new MpReq.ContentBean();
        contentBean.setPhoneNum(user);
        contentBean.setPassword(setPassword);
        contentBean.setSms_token(smsNo);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        showProgressDialog("绑定中");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().binding_MobilePhone(requestBody, HttpUtil.getSession(mContext))
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
                    public void onNext(SimpleModel model) {
                        if (model.getStatus() == 0) {
                            showToast("绑定成功");
                            String json = gson.toJson(model);
                            LoginUtil.saveInfo(PhoneAct.this, gson.fromJson(json, LoginModle.class));
                            SPUtils.put(getApplication(), "isLogin", true);
                            finish();
                        } else if (model.getStatus() == -30) {
                            showToast("此手机号已注册");
                        } else if (model.getStatus() == -5) {
                            showToast("验证码错误");
                        } else {
                            showToast(model.getMessage());
                        }
                    }
                });
    }

}
