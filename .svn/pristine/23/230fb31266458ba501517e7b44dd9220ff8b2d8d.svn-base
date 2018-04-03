package com.jajahome.feature.user.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.model.AuthCodeModle;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.CommonUtils;
import com.jajahome.util.StringHelper;
import com.jajahome.util.T;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 忘记密码
 * Created by laotang on 2016/7/18.
 */
public class ForgetPwdAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.tv_send)
    Button tvSend;
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
    private String user;
    private String smsNo;
    private String pwd;
    private String pwdAgain;

    @Override
    protected int getViewId() {
        return R.layout.act_forget_pwd;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        tvGetno.setOnClickListener(this);
        tvSend.setOnClickListener(this);
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
            case R.id.tv_getno:
                /**
                 * 获取验证码
                 */
                getNum();
                break;
            case R.id.tv_send:
                /**
                 * 找回密码
                 */
                if (isCheckInput()) {
                    findPwd();
                }
                break;
        }
    }

    /**
     * 找回密码
     */
    private void findPwd() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_RECOVER);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setUsername(user);
        contentBean.setPassword(pwd);
        contentBean.setSms_token(smsNo);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("正在提交");
        mSubscription = ApiImp.get().findPwd(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SimpleModel>() {
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
                    public void onNext(SimpleModel findPwdModel) {
//                        if (findPwdModel.getStatus() == 0) {
//                            T.showShort(mContext, "修改成功，请重新登陆");
//                            finish();
//                        }
                        if (findPwdModel.getStatus() == 0) {
                            T.showShort(mContext, "修改成功");
                            finish();
                        } else {
                            showToast(findPwdModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 对找回密码的信息逐一进行判断
     *
     * @return
     */
    public boolean isCheckInput() {
        smsNo = editSms.getText().toString().trim();
        pwd = editPwd.getText().toString().trim();
        pwdAgain = editPwdAgain.getText().toString().trim();
        user = editUser.getText().toString().trim();
        //手机号判断不能为空
        if (StringHelper.isEditTextEmpty(editUser)) {
            T.showShort(mContext, getString(R.string.toast_login_username_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editSms)) {
            T.showShort(mContext, getString(R.string.toast_login_sms_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editPwd)) {
            T.showShort(mContext, getString(R.string.toast_login_pwd_empty));
            return false;
        }
        if (StringHelper.isEditTextEmpty(editPwdAgain)) {
            T.showShort(mContext, getString(R.string.toast_login_pwdAgain_empty));
            return false;
        }

        //手机号码在6-11位s
        if (pwd.length() < Constant.APP_PASSWORD_LIMIT_LENGTH) {
            T.showShort(mContext, getString(R.string.toast_password__limit_length));
            return false;
        }
        if (!pwd.equals(pwdAgain)) {
            T.showShort(mContext, getString(R.string.toast_login_pwd_no));
            return false;
        }
        //验证手机的格式如果不是
        if (!CommonUtils.isMobileNO(user)) {
            T.showShort(mContext, getString(R.string.toast_login_phone_error));
            return false;
        }
        return true;
    }


    /**
     * 获取验证码
     */
    private void getNum() {
        user = editUser.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            T.showShort(mContext, getString(R.string.empty_phone));
        } else {
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
    }

    /**
     * 验证码倒计时
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
}
