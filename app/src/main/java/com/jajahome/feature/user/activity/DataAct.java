package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.view.ChoseSexDialog;
import com.jajahome.model.DataModel;
import com.jajahome.model.LoginModle;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.widget.datepick.TimeSelector;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 个人资料
 * Created by laotang on 2016/7/19.
 */
public class DataAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.edit_nickname)
    EditText editNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_brithday)
    LinearLayout llBrithday;
    @BindView(R.id.view_three)
    View viewThree;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.view_four)
    View viewFour;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.btn_send)
    Button btnSend;
    private TimeSelector timeSelector;
    private String date;//当前日期
    private LoginModle info;

    @Override
    protected int getViewId() {
        return R.layout.act_data;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = sDateFormat.format(new java.util.Date());
    }

    @Override
    protected void onResume() {
        super.onResume();
        info = LoginUtil.getInfo(this);
        if (info != null) {
            editNickname.setText(info.getData().getUser().getNickname());
            String loginType = info.getData().getUser().getLoginType();

            if (!StringUtil.isEmpty(loginType) && loginType.equals("1")) {
                editPhone.setText(info.getData().getUser().getMobile());
                editPhone.setFocusable(false);
            } else if (!StringUtil.isEmpty(loginType) && loginType.equals("2")) {
                if (isBand(info.getData().getUser())) {
                    editPhone.setText(info.getData().getUser().getMobile());
                    editPhone.setFocusable(false);
                } else {
                    editPhone.setFocusable(false);
                    editPhone.setText("QQ登录暂无法编辑电话号码");
                    editPhone.setTextColor(getResources().getColor(R.color.text_black_light));
                }
            } else if (!StringUtil.isEmpty(loginType) && loginType.equals("3")) {
                if (isBand(info.getData().getUser())) {
                    editPhone.setText(info.getData().getUser().getMobile());
                    editPhone.setFocusable(false);
                } else {
                    editPhone.setFocusable(false);
                    editPhone.setText("微博登录暂无法编辑电话号码");
                    editPhone.setTextColor(getResources().getColor(R.color.text_black_light));
                }
            } else if (!StringUtil.isEmpty(loginType) && loginType.equals("4")) {
                if (isBand(info.getData().getUser())) {
                    editPhone.setText(info.getData().getUser().getMobile());
                    editPhone.setFocusable(false);
                } else {
                    editPhone.setFocusable(false);
                    editPhone.setText("微信登录暂无法编辑电话号码");
                    editPhone.setTextColor(getResources().getColor(R.color.text_black_light));
                }
            } else if (!StringUtil.isEmpty(loginType) && loginType.equals("5")) {
                if (isBand(info.getData().getUser())) {
                    editPhone.setText(info.getData().getUser().getMobile());
                    editPhone.setFocusable(false);
                } else {
                    editPhone.setFocusable(false);
                    editPhone.setText("邮件登录暂无法编辑电话号码");
                    editPhone.setTextColor(getResources().getColor(R.color.text_black_light));
                }
            }
            tvSex.setText(info.getData().getUser().getSex());
            if (!TextUtils.isEmpty(info.getData().getUser().getBirthday())) {
                tvBirthday.setText(info.getData().getUser().getBirthday());
            }
        }
    }

    private boolean isBand(LoginModle.DataBean.UserBean userBean) {
        if (userBean.getUsername().equals(userBean.getMobile())) {
            return false;
        }
        return true;
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
            case R.id.tv_sex:
                /**
                 * 选择性别
                 */
                showSexPop();
                break;
            case R.id.tv_birthday:
                /**
                 * 选择生日时间
                 */
                setTime();
                break;
            case R.id.rl_address:
                /**
                 * 跳转到我的收货列表
                 */
                gotoNewAty(MyAddressAct.class);
                break;
            case R.id.btn_send:
                /**
                 * 提交资料
                 */
                sendData();
                break;
        }
    }

    /**
     * 提交资料
     */
    private void sendData() {
        String nickName = editNickname.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
        String brithday = tvBirthday.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (TextUtils.isEmpty(brithday)) {
            T.showLong(mContext, "请选择生日");
        } else {
            BaseReq baseReq = new BaseReq();
            baseReq.setCmd(Constant.USER_INFO);
            BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
            if (isBand(info.getData().getUser()) && info.getData().getUser().getLoginType().equals("1")) {
                contentBean.setMobile(phone);
            }
            contentBean.setBirthday(brithday);
            contentBean.setSex(sex);
            contentBean.setNickname(nickName);
            baseReq.setContent(contentBean);
            Gson gson = new Gson();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
            RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
            showProgressDialog("正在提交");
            mSubscription = ApiImp.get().sendData(requestBody, requestBodyl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DataModel>() {
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
                        public void onNext(DataModel dataModel) {
                            if (dataModel.getStatus() == 0) {
                                T.showShort(mContext, "修改成功");
                                EventBus.getDefault().post(new EventMessage(UserAct.CHANGE_ACTION, ""));
                            } else {
                                T.showShort(mContext, "提交失败,请稍候再试");
                            }
                        }
                    });
        }
    }


    /**
     * 选择生日
     */
    private void setTime() {
        if (timeSelector == null)
            timeSelector = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
                @Override
                public void handle(String time) {
                    tvBirthday.setText(time);
                }
            }, "1950-10-30", "2999-12-29");
        timeSelector.show(tvBirthday);

        if (!StringUtil.isEmpty(LoginUtil.getInfo(mContext).getData().getUser().getBirthday())) {
            timeSelector.setDefaultData(LoginUtil.getInfo(mContext).getData().getUser().getBirthday());
        } else {
            timeSelector.setDefaultToday();
        }


    }

    /**
     * 选择性别
     */
    private void showSexPop() {
        ChoseSexDialog.Builder builder = new ChoseSexDialog.Builder(DataAct.this);
        builder.setManText(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSex.setText("男");
                dialog.dismiss();
            }
        });
        builder.setWomanText(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSex.setText("女");
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
