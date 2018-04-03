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
import com.jajahome.pop.adapter.ShowFilterAdapter;


/**
 * 类描述：弹出选择男女的
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopShowFilter implements ShowFilterAdapter.OnItemClick {

	public static final int ACTION_SHOW_USER=0;
	public static final int ACTION_SHOW_TYPE=1;
	public static final int ACTION_SHOW_TIME=2;

	private OnShowFilterListener onShowFilterListener;

	private Context mContext;

	private PopupWindow mPopupWindow;

	private ListView mList;

	private ConfigModel mConfigModel;
	private int mAction;
	private TextView mTvTitle, mTvBack;

	private ShowFilterAdapter adapter;

	public PopShowFilter(Context context, int action, ConfigModel configModel) {
		this.mContext = context;
		this.mAction = action;
		this.mConfigModel = configModel;
		init(action, configModel);
	}

	protected void init(int action, ConfigModel configModel) {
		View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter, null);
		mTvBack = (TextView) rootView.findViewById(R.id.tv_filter_back);
		mTvTitle = (TextView) rootView.findViewById(R.id.tv_filter_title);
		setTitle();
		mList = (ListView) rootView.findViewById(R.id.listView);
		adapter = new ShowFilterAdapter(mContext, configModel, action);
		adapter.setListener(this);
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
		rootView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void setTitle() {
		switch (mAction) {
			case ACTION_SHOW_USER:
				mTvTitle.setText(R.string.publisher);
				break;
			case ACTION_SHOW_TYPE:
				mTvTitle.setText(R.string.type);
				break;
			case ACTION_SHOW_TIME:
				mTvTitle.setText(R.string.time);
				break;
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
	 * 用户点击相应的选项 关闭弹窗 回调选择数据
	 * 注： p=0时点击了全部
	 *
	 * @param p ：点击的item位置
	 */
	@Override
	public void onItemClick(int p) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
		switch (mAction) {
			case ACTION_SHOW_USER:
				if (onShowFilterListener != null) {
					if (p == 0) {
						onShowFilterListener.onUserSelect(p, null);
						return;
					}
					onShowFilterListener.onUserSelect(p, mConfigModel.getData().getConfig().getShow_user().get(p - 1));
				}
				break;
			case ACTION_SHOW_TYPE:
				if (onShowFilterListener != null) {
					if (p == 0) {
						onShowFilterListener.onTypeSelect(p, null);
						return;
					}
					onShowFilterListener.onTypeSelect(p, mConfigModel.getData().getConfig().getShow_type().get(p - 1));
				}
				break;
			case ACTION_SHOW_TIME:
				if (onShowFilterListener != null) {
					if (p == 0) {
						onShowFilterListener.onTimeSelect(p, null);
						return;
					}
					onShowFilterListener.onTimeSelect(p, mConfigModel.getData().getConfig().getShow_time().get(p - 1));
				}
				break;
		}
	}



	public interface OnShowFilterListener {
		void onUserSelect(int index, ConfigModel.DataBean.ConfigBean.ShowUserBean bean);

		void onTypeSelect(int index, ConfigModel.DataBean.ConfigBean.ShowTypeBean bean);

		void onTimeSelect(int index, ConfigModel.DataBean.ConfigBean.ShowTimeBean bean);
	}
	public void setOnSingleItemListener(OnShowFilterListener listener) {
		this.onShowFilterListener = listener;
	}

	/**
	 * 关闭popupwindow
	 */
	public void dismiss() {
		if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
	}

}
