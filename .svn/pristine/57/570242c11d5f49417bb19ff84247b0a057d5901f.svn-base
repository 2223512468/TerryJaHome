package com.jajahome.feature.user.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.jajahome.model.FeedbackModel;
import com.jajahome.model.LoginModle;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 服务与反馈
 * Created by laotang on 2016/7/19.
 */
public class FeedbackAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.edit_theme)
    EditText editTheme;
    @BindView(R.id.edit_data)
    EditText editData;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_reset)
    Button btnReset;

    @Override
    protected int getViewId() {
        return R.layout.act_feedback;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
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
                 * 提交反馈
                 */
                sendFeedback();
                break;
            case R.id.btn_reset:
                /**
                 * 重置提交信息
                 */
                editTheme.setText("");
                editData.setText("");
                break;

        }
    }

    /**
     * 提交反馈
     */
    private void sendFeedback() {
        String data = editData.getText().toString().trim();
        String theme = editTheme.getText().toString().trim();
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(theme)) {
            T.showShort(mContext, "请输入反馈内容");
        } else {
            BaseReq baseReq = new BaseReq();
            baseReq.setCmd(Constant.FEEDBACK);
            BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
            contentBean.setTitle(theme);
            contentBean.setContent(data);
            baseReq.setContent(contentBean);
            Gson gson = new Gson();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
            LoginModle loginModle=LoginUtil.getInfo(mContext);
            RequestBody requestBodyl;
            if(loginModle!=null){
                requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(loginModle.getData().getSession()));
            }else {
                requestBodyl = RequestBody.create(MediaType.parse("application/session"), "");
            }
            showProgressDialog("正在提交");
            mSubscription = ApiImp.get().sendFeedback(requestBody, requestBodyl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FeedbackModel>() {
                        @Override
                        public void onCompleted() {
                            editTheme.setText("");
                            editData.setText("");
                            dissmisProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            dissmisProgressDialog();
                        }

                        @Override
                        public void onNext(FeedbackModel feedbackModel) {
                            if (feedbackModel.getStatus() == 0) {
                                T.showShort(mContext, "提交成功");
                            }
                        }
                    });
        }

    }
}
