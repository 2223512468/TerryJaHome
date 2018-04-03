package com.jajahome.feature.search;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jajahome.R;
import com.jajahome.feature.search.adapter.AdapterPopSelect;

/**
 * 选择搜索条件
 */
public class PopSelectSearch extends PopupWindow {

    private ListView mListView;
    private View mMenuView;
    private Activity mContext;
    private String type;
    private AdapterPopSelect mAdapterPopSelectCountry;

    public void setOnSelectCountryListener(OnSelectListener onSelectCountryListener) {
        mOnSelectCountryListener = onSelectCountryListener;
    }

    private OnSelectListener mOnSelectCountryListener;

    public PopSelectSearch(final Activity context, String type) {
        super(context);
        this.mContext = context;
        this.type = type;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_select_search, null);
        mListView = (ListView) mMenuView.findViewById(R.id.listview);
        mAdapterPopSelectCountry = new AdapterPopSelect(mContext,type);
        mListView.setAdapter(mAdapterPopSelectCountry);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        mAdapterPopSelectCountry.setOnItemSelectListener(new AdapterPopSelect.OnItemSelectListener() {
            @Override
            public void onItemSelected(int index) {
                mOnSelectCountryListener.onSelectListener(index, mAdapterPopSelectCountry.getIndexStr(index));
                dismiss();
            }
        });

    }

    public void show(View showView) {
        this.showAsDropDown(showView, 20, 0);
    }

    public interface OnSelectListener {
        void onSelectListener(int index, String country);
    }
}
