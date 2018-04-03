package com.jajahome.feature.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.H5Act;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ApkDownload;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于我们
 * Created by laotang on 2016/7/19.
 */
public class AboutUsAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_news)
    RelativeLayout rlNews;
    @BindView(R.id.rl_us)
    RelativeLayout rlUs;
    @BindView(R.id.check_upgrade)
    RelativeLayout checkUpgrade;
    public static String Tag = "AboutUsAct";
    private String cityName;
    private ApkDownload apk;

    @Override
    protected int getViewId() {
        return R.layout.act_about_us;
    }

    @Override
    protected void initEvent() {

        ibtnBack.setOnClickListener(this);
        rlNews.setOnClickListener(this);
        rlUs.setOnClickListener(this);
        checkUpgrade.setOnClickListener(this);

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
                finish();
                break;
            case R.id.rl_news:
                /**
                 * 跳转到版权信息h5页面
                 */
                H5Act.gotoH5(mContext, Constant.COPYRIGHT, "版权信息");
                break;
            case R.id.rl_us:
                /**
                 * 跳转到联系我们h5页面
                 */
                if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                    cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
                } else {
                    cityName = "";
                }
                String about_us = Constant.ABOUT_US + "&city=" + cityName;
                H5Act.gotoH5(mContext, about_us, "联系我们");
                break;

            case R.id.check_upgrade:
                apk= new ApkDownload(this, Tag);
                apk.check();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(apk!=null){
            apk.setContextAlive(false);
        }
    }
}
