package com.jajahome.feature.user.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.model.LoginModle;
import com.jajahome.util.LoginUtil;

import butterknife.BindView;

/**
 * 账户与安全
 * Created by laotang on 2016/7/19.
 */
public class AccountAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;

    @Override
    protected int getViewId() {
        return R.layout.act_account;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        rlChangePwd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginModle info = LoginUtil.getInfo(this);
        if (info != null) {
            tvVipName.setText(info.getData().getUser().getUsername());
            tvPhone.setText(info.getData().getUser().getMobile());
        }
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
            case R.id.rl_change_pwd:
                /**
                 * 跳转到修改密码
                 */
                gotoNewAty(ChangePwdAct.class);
                break;
        }
    }
}
