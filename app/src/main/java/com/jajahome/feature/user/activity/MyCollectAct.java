package com.jajahome.feature.user.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.user.adapter.ItemCollectAdapter;
import com.jajahome.feature.user.fragment.ItemFragment;
import com.jajahome.feature.user.fragment.SetFragment;
import com.jajahome.feature.user.fragment.ShowFragment;

import butterknife.BindView;

/**
 * 我得收藏
 */
public class MyCollectAct extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.viewSet)
    LinearLayout viewSet;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.viewItem)
    LinearLayout viewItem;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.viewShow)
    LinearLayout viewShow;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.DIY)
    LinearLayout DiyLL;
    @BindView(R.id.tv_diy)
    TextView diy_tv;
    /**
     * 灰色字体
     */
    private int mColorGray;
    /**
     * 橙色字体
     */
    private int mColorOrgin;
    /**
     * 收藏列表适配器
     */
    private ItemCollectAdapter mAdapter;
    private FragmentTransaction ft;

    @Override
    protected int getViewId() {
        return R.layout.act_my_collect;
    }

    @Override
    protected void initEvent() {
        mColorGray = ContextCompat.getColor(mContext, R.color.text_gray);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);
        ibtnBack.setOnClickListener(this);
        viewSet.setOnClickListener(this);
        viewItem.setOnClickListener(this);
        viewShow.setOnClickListener(this);
        DiyLL.setOnClickListener(this);
        diy_tv.setTextColor(mColorOrgin);
        setSelect(3);

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
            case R.id.viewSet:
                /**
                 * 选择套装碎片
                 */
                tvSet.setTextColor(mColorOrgin);
                tvShow.setTextColor(mColorGray);
                tvItem.setTextColor(mColorGray);
                diy_tv.setTextColor(mColorGray);
                setSelect(0);
                break;
            case R.id.viewItem:
                /**
                 * 选择单品碎片
                 */
                tvItem.setTextColor(mColorOrgin);
                tvShow.setTextColor(mColorGray);
                tvSet.setTextColor(mColorGray);
                diy_tv.setTextColor(mColorGray);
                setSelect(1);
                break;
            case R.id.viewShow:
                /**
                 * 选择秀家碎片
                 */
                tvShow.setTextColor(mColorOrgin);
                tvSet.setTextColor(mColorGray);
                tvItem.setTextColor(mColorGray);
                diy_tv.setTextColor(mColorGray);
                setSelect(2);
                break;
            case R.id.DIY:
                diy_tv.setTextColor(mColorOrgin);
                tvSet.setTextColor(mColorGray);
                tvItem.setTextColor(mColorGray);
                tvShow.setTextColor(mColorGray);
                setSelect(3);
                break;
        }
    }

    SetFragment setFragment = null;
    ItemFragment itemFragment = null;
    ShowFragment showFragment = null;
    MyProjectAct myProjectAct = null;

    /**
     * 选择碎片
     *
     * @param i：碎片位置
     */
    public void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragments();
        switch (i) {
            case 0:
                if (setFragment == null) {
                    setFragment = new SetFragment();
                    ft.add(R.id.frame, setFragment);
                }
                ft.show(setFragment);
                break;
            case 1:
                if (itemFragment == null) {
                    itemFragment = new ItemFragment();
                    ft.add(R.id.frame, itemFragment);
                }
                ft.show(itemFragment);
                break;
            case 2:
                if (showFragment == null) {
                    showFragment = new ShowFragment();
                    ft.add(R.id.frame, showFragment);
                }
                ft.show(showFragment);
                break;
            case 3:
                if(myProjectAct==null){
                    myProjectAct = new MyProjectAct();
                    ft.add(R.id.frame,myProjectAct);
                }
                ft.show(myProjectAct);
                break;
        }
        ft.commit();
    }

    /**
     * 隐藏碎片
     */
    private void hideFragments() {
        if (setFragment != null) {
            ft.hide(setFragment);
        }
        if (itemFragment != null) {
            ft.hide(itemFragment);
        }
        if (showFragment != null) {
            ft.hide(showFragment);
        }
        if(myProjectAct!=null){
            ft.hide(myProjectAct);
        }
    }


}
