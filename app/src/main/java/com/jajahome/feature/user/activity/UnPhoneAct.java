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
import com.jajahome.network.UnMpReq;
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
public class UnPhoneAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_sms)
    EditText editSms;
    @BindView(R.id.tv_getno)
    TextView tvGetno;
    @BindView(R.id.tv_register)
    Button regiBtn;
    @BindView(R.id.ibtn_back)
    ImageButton imageBtn;

    private String user;
    private String smsNo;


    @Override
    protected int getViewId() {
        return R.layout.act_unphone;
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
        //检验手机号输入是否为空
        if (StringUtil.isEmpty(user)) {
            T.showShort(UnPhoneAct.this, getString(R.string.empty_phone));
            return;
        }
        //检验手机号输入是否合法
        if (!StringUtil.isTel(user)) {
            T.showShort(UnPhoneAct.this, getString(R.string.phone_is_illegal));
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
        user = editUser.getText().toString().trim();
        //手机号判断不能为空
        if (StringHelper.isEditTextEmpty(editUser)) {
            T.showShort(UnPhoneAct.this, getString(R.string.toast_login_username_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editSms)) {
            T.showShort(UnPhoneAct.this, getString(R.string.toast_login_sms_empty));
            return false;
        }

        //验证手机的格式如果不是
        if (!CommonUtils.isMobileNO(user)) {
            T.showShort(UnPhoneAct.this, getString(R.string.toast_login_phone_error));
            return false;
        }
        return true;
    }

    /**
     * 绑定手机号
     */

    private void bingPhoneNum() {
        UnMpReq baseReq = new UnMpReq();
        baseReq.setCmd(Constant.UNBINDINGMOBILEPHONE);
        UnMpReq.ContentBean contentBean = new UnMpReq.ContentBean();
        contentBean.setPhoneNum(user);
        contentBean.setSms_token(smsNo);
        baseReq.setContent(contentBean);
        final Gson gson = new Gson();
        showProgressDialog("正在解绑...");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        mSubscription = ApiImp.get().unbinding_MobilePhone(requestBody, HttpUtil.getSession(mContext))
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
                            showToast("解绑成功");
                            String json1 = gson.toJson(model);
                            LoginUtil.saveInfo(UnPhoneAct.this, gson.fromJson(json1, LoginModle.class));
                            SPUtils.put(getApplication(), "isLogin", true);
                            finish();
                        } else {
                            showToast(model.getMessage());
                        }
                    }
                });
    }


}
