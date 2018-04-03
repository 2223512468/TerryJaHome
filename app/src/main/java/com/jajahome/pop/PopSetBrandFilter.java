package com.jajahome.pop;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.ConfigModel;
import com.jajahome.pop.adapter.SetBrandFilterAdapter;


/**
 * 类描述：弹出选择套餐品牌种类的
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopSetBrandFilter implements SetBrandFilterAdapter.OnItemClick {
	private Context mContext;
	private PopupWindow mPopupWindow;
	private ListView mList;
	private ConfigModel mConfigModel;

	public void setListener(OnItemBrandSelectListener listener) {
		this.listener = listener;
	}

	private OnItemBrandSelectListener listener;
	private TextView mTvTitle, mTvBack;
	private SetBrandFilterAdapter adapter;
	public PopSetBrandFilter(Context context, ConfigModel configModel) {
		this.mContext = context;
		this.mConfigModel = configModel;
		init(configModel);
	}
	protected void init( ConfigModel configModel) {
		View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter, null);
		mTvBack = (TextView) rootView.findViewById(R.id.tv_filter_back);
		mTvTitle = (TextView) rootView.findViewById(R.id.tv_filter_title);
		mList = (ListView) rootView.findViewById(R.id.listView);
		adapter = new SetBrandFilterAdapter(mContext, configModel);
		adapter.setListener(this);
		mTvTitle.setText(R.string.brand);
		mList.setAdapter(adapter);
		mTvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_transparent));
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		//监听弹窗消失
		rootView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}


	/**
	 * 显示popupwindow
	 *
	 * @param view
	 */
	public void show(View view) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			mPopupWindow.showAtLocation(view, 0, 0, 0);
		}
	}

	/**
	 * 显示popupwindow
	 *
	 * @param view
	 */
	public void show(View view, String text) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			mPopupWindow.showAtLocation(view, 0, 0, 0);
		}
	}

	/**
	 * 关闭popupwindow
	 */
	public void dismiss() {
		if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
	}

	public interface OnItemBrandSelectListener{
		void onSetBrandSelect(int id, String text);
	}
	@Override
	public void onItemClick(int id, String text) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
		listener.onSetBrandSelect(id,text);
	}
}
