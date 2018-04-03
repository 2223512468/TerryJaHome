package com.jajahome.feature.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringHelper;
import com.jajahome.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改登录密码
 * Created by laotang on 2016/7/20.
 */
public class ChangePwdAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.tv_old_pwd)
    TextView tvOldPwd;
    @BindView(R.id.edit_old_pwd)
    EditText editOldPwd;
    @BindView(R.id.rl_old_pwd)
    RelativeLayout rlOldPwd;
    @BindView(R.id.tv_new_pwd)
    TextView tvNewPwd;
    @BindView(R.id.edit_new_pwd)
    EditText editNewPwd;
    @BindView(R.id.rl_new_pwd)
    RelativeLayout rlNewPwd;
    @BindView(R.id.tv_again_pwd)
    TextView tvAgainPwd;
    @BindView(R.id.edit_again_pwd)
    EditText editAgainPwd;
    @BindView(R.id.btn_send)
    Button btnSend;
    private String oldPwd;
    private String newPwd;
    private String againPwd;

    @Override
    protected int getViewId() {
        return R.layout.act_change_pwd;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
            case R.id.btn_send:
                /**
                 * 保存密码
                 */
                if (isCheckInput()) {
                    sendPwd();
                }
                break;
        }
    }

    /**
     * 判断输入框内容
     *
     * @return
     */
    private boolean isCheckInput() {

        oldPwd = editOldPwd.getText().toString().trim();
        newPwd = editNewPwd.getText().toString().trim();
        againPwd = editAgainPwd.getText().toString().trim();

        if (StringHelper.isEditTextEmpty(editOldPwd)) {
            T.showShort(ChangePwdAct.this, getString(R.string.toast_pwd_old_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editNewPwd)) {
            T.showShort(ChangePwdAct.this, getString(R.string.toast_pwd_new_empty));
            return false;
        }

        if (StringHelper.isEditTextEmpty(editAgainPwd)) {
            T.showShort(ChangePwdAct.this, getString(R.string.toast__pwd_again_empty));
            return false;
        }
        //手机号码在6-11位s
        if (newPwd.length() < Constant.APP_PASSWORD_LIMIT_LENGTH) {
            T.showShort(ChangePwdAct.this, getString(R.string.toast_password__limit_length));
            return false;
        }
        if (!newPwd.equals(againPwd)) {
            T.showShort(ChangePwdAct.this, getString(R.string.toast_login_pwd_no));
            return false;
        }

        return true;
    }

    /**
     * 修改密码提交服务器
     */
    private void sendPwd() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_CHANGEPASSWORD);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setOld_password(oldPwd);
        contentBean.setNew_password(newPwd);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showProgressDialog("修改中");
        mSubscription = ApiImp.get().changePwd(requestBody, requestBodyl)
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
                    public void onNext(SimpleModel baseModel) {
                        if (baseModel.getStatus() == 0) {
                            T.showShort(mContext, "修改成功");
                            finish();
                        } else {
                            showToast(baseModel.getMessage());
                        }
                    }
                });
    }
}
