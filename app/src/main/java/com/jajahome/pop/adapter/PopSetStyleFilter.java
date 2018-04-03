package com.jajahome.pop.adapter;

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


/**
 * 类描述：弹出选择单品品牌种类的
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopSetStyleFilter implements SetStyleFilterAdapter.OnItemClick {
	private Context mContext;
	private PopupWindow mPopupWindow;
	private ListView mList;
	private ConfigModel mConfigModel;

	public void setListener(OnSetStyleListner listener) {
		this.listener = listener;
	}

	private OnSetStyleListner listener;
	private TextView mTvTitle, mTvBack;
	private SetStyleFilterAdapter adapter;
	public PopSetStyleFilter(Context context, ConfigModel configModel) {
		this.mContext = context;
		this.mConfigModel = configModel;
		init(configModel);
	}
	protected void init( ConfigModel configModel) {
		View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter, null);
		mTvBack = (TextView) rootView.findViewById(R.id.tv_filter_back);
		mTvTitle = (TextView) rootView.findViewById(R.id.tv_filter_title);
		mList = (ListView) rootView.findViewById(R.id.listView);
		adapter = new SetStyleFilterAdapter(mContext, configModel);
		adapter.setListener(this);
		mTvTitle.setText(R.string.style);
		mList.setAdapter(adapter);
		mTvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backPress();
			}
		});
		mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_transparent));
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		//监听弹窗消失
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				adapter.clear();
			}
		});
		rootView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * 点击返回键
	 */
	private void backPress(){
		if(adapter.getmCurrentLevel()==1){
			dismiss();
		}else {
			adapter.pressBack();
		}
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

	public interface OnSetStyleListner{
		void onSetStyleSelect(int id, String text);
	}

	/**
	 * @param id
	 * @param text
	 */
	@Override
	public void onItemClick(int id, String text) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
		listener.onSetStyleSelect(id,text);
	}
}
