package com.jajahome.feature.user.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.user.fragment.OrderFrag;
import com.jajahome.widget.commontablayout.CommonTabLayout;
import com.jajahome.widget.commontablayout.CustomTabEntity;
import com.jajahome.widget.commontablayout.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的订单列表
 */
public class OrderListAct extends BaseActivity {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.common_tab)
    CommonTabLayout commonTab;

    @Override
    protected int getViewId() {
        return R.layout.act_order_list;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView2.setText(R.string.my_order);
        initFragments();
    }

    /**
     * 初始化页面控件（主要是Fragment）
     */
    private void initFragments() {
        commonTab.setTabData(genTabEntity(), getSupportFragmentManager(), R.id.frame, genFragmnet());
    }

    private ArrayList<CustomTabEntity> genTabEntity() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String status[] = getResources().getStringArray(R.array.my_order_status);
        for (int i = 0; i < status.length; i++) {
            mTabEntities.add(new TabEntity(status[i], 0, 0));
        }
        return mTabEntities;
    }

    /**
     * 获取需要关联的fragment的数据
     *
     * @return ：关联的fragment的数据
     */
    private ArrayList<Fragment> genFragmnet() {
        ArrayList<Fragment> list = new ArrayList<>();
        /**
         *  状态:
         * 0 - 待处理(未付款)
         * 1 - 已处理(已付款/待发货)
         * 2 - 订单取消(交易关闭)
         * 3 交易完成 (已收货）
         * 4 退款中
         * 5 退款成功(交易关闭)
         * 6 待询价
         * 7 已发货(待收货)
         （statues为空，返回所有数据）
         */
        list.add(OrderFrag.newInstance(-1)); //全部
        list.add(OrderFrag.newInstance(0)); //未付款
        list.add(OrderFrag.newInstance(1)); //待发货
        list.add(OrderFrag.newInstance(7)); //待收货
        list.add(OrderFrag.newInstance(6)); //待询价
        return list;
    }

}
