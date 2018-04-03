package com.jajahome.feature.show.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 秀家列表fragment
 */
public class ShowFrag extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.item_first)
    TextView itemFirst;
    @BindView(R.id.item_second)
    TextView itemSec;
    @BindView(R.id.item_three)
    TextView itemThree;
    @BindView(R.id.item_four)
    TextView itemFour;

    @BindView(R.id.bi_tv)
    TextView saiTv;
    @BindView(R.id.yuan_tv)
    TextView yuanTv;
    @BindView(R.id.pic_tv)
    TextView picTv;
    @BindView(R.id.wen_tv)
    TextView wenTv;

    @BindView(R.id.viewUser)
    RelativeLayout viewUser;
    @BindView(R.id.viewType)
    RelativeLayout viewType;
    @BindView(R.id.viewTime)
    RelativeLayout viewTime;
    @BindView(R.id.viewWen)
    RelativeLayout viewWen;

    private Map<Integer, List<View>> titleMap = new HashMap<>();
    private int mColorGray;//灰色字
    private int mColorOrgin;//橙色字

    private Fragment[] fragments;

    @Override
    protected int getViewId() {
        return R.layout.frag_show;
    }

    @Override
    protected void init() {
        mColorGray = ContextCompat.getColor(mContext, R.color.text_black);
        mColorOrgin = ContextCompat.getColor(mContext, R.color.orange_ll);

        fragments = new Fragment[]{ShowDetailFrag.newInstance(1), ShowDetailFrag.newInstance(2), ShowDetailFrag.newInstance(3), ShowDetailFrag.newInstance(4)};
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = fragments[i];
            transaction.add(R.id.fl_content, fragment);
            transaction.hide(fragment);
        }
        transaction.show(fragments[0]);
        transaction.commitAllowingStateLoss();
        setListener();
        putListView();
        List<View> listViews = titleMap.get(0);
        for (int i = 0; i < listViews.size(); i++) {
            if (i == 0) {
                TextView titleName = (TextView) listViews.get(i);
                titleName.setTextColor(mColorOrgin);
            }
            listViews.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void putListView() {
        List<View> tabList1 = new ArrayList<>();
        tabList1.add(itemFirst);
        tabList1.add(saiTv);

        List<View> tabList2 = new ArrayList<>();
        tabList2.add(itemSec);
        tabList2.add(yuanTv);

        List<View> tabList3 = new ArrayList<>();
        tabList3.add(itemThree);
        tabList3.add(picTv);

        List<View> tabList4 = new ArrayList<>();
        tabList4.add(itemFour);
        tabList4.add(wenTv);

        titleMap.put(0, tabList1);
        titleMap.put(1, tabList2);
        titleMap.put(2, tabList3);
        titleMap.put(3, tabList4);
    }

    private List<View> listViews;

    private void forEach(int pos) {
        for (Map.Entry e : titleMap.entrySet()) {
            int key = (int) e.getKey();
            listViews = (List<View>) e.getValue();
            if (key == pos) {
                for (int i = 0; i < listViews.size(); i++) {
                    if (i == 0) {
                        TextView titleName = (TextView) listViews.get(i);
                        titleName.setTextColor(mColorOrgin);
                    }
                    listViews.get(i).setVisibility(View.VISIBLE);
                }
            } else {
                for (int i = 0; i < listViews.size(); i++) {
                    if (i == 0) {
                        TextView titleName = (TextView) listViews.get(i);
                        titleName.setTextColor(getResources().getColor(R.color.text_black));
                    }
                    listViews.get(1).setVisibility(View.GONE);
                }
            }
        }
    }


    /**
     * 设置监听器
     */
    private void setListener() {
        viewUser.setOnClickListener(this);
        viewType.setOnClickListener(this);
        viewTime.setOnClickListener(this);
        viewWen.setOnClickListener(this);
    }

    private int checkIndex;


    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.viewUser:
                index = 0;
                break;
            case R.id.viewType:
                index = 1;
                break;
            case R.id.viewTime:
                index = 2;
                break;
            case R.id.viewWen:
                index = 3;
                break;
            default:
                index = 0;
                break;
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (checkIndex == index) {

        } else {
            transaction.show(fragments[index]);
            transaction.hide(fragments[checkIndex]);
            transaction.commitAllowingStateLoss();
        }
        checkIndex = index;
        forEach(index);

    }

}
