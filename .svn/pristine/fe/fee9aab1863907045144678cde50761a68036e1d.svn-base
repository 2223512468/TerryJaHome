package com.jajahome.feature.user.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.user.fragment.CouFrag;
import com.jajahome.widget.commontablayout.CommonTabLayout;
import com.jajahome.widget.commontablayout.CustomTabEntity;
import com.jajahome.widget.commontablayout.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/22.
 */
public class ValueAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.textView2)
    TextView textView;
    @BindView(R.id.ibtn_back)
    ImageButton imgBack;
    @BindView(R.id.common_tab)
    CommonTabLayout mTabLayout;


    private ArrayList<Fragment> list = new ArrayList<>();

    @Override
    protected int getViewId() {
        return R.layout.act_value;
    }

    @Override
    protected void initEvent() {
        textView.setText("优惠券");
        initFragments();
        setListener();
    }


    private void setListener() {
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }


    private void initFragments() {
        mTabLayout.setTabData(getTabEntity(), getSupportFragmentManager(), R.id.container, getFragments());
    }

    private ArrayList<CustomTabEntity> getTabEntity() {
        ArrayList<CustomTabEntity> mTabEntity = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.cou_states);
        for (int i = 0; i < arr.length; i++) {
            mTabEntity.add(new TabEntity(arr[i], 0, 0));
        }
        return mTabEntity;
    }

    private ArrayList<Fragment> getFragments() {
        list.add(CouFrag.newInstance(1));
        list.add(CouFrag.newInstance(0));
        return list;
    }

}
