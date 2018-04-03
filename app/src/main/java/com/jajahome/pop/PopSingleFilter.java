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
import com.jajahome.pop.adapter.SingleFilterAdapter;
import com.jajahome.model.ConfigModel;


/**
 * 类描述：弹出选择男女的
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopSingleFilter implements SingleFilterAdapter.OnItemClick {

	private OnSingleFilterSetListener mOnSingleFilterSetListener;
	private OnSingleItemListener mOnSingleItemListener;

	private Context mContext;

	private PopupWindow mPopupWindow;

	private ListView mList;

	private ConfigModel mConfigModel;
	private int mAction;
	private TextView mTvTitle, mTvBack;

	private SingleFilterAdapter adapter;

	public PopSingleFilter(Context context, int action, ConfigModel configModel) {
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
		adapter = new SingleFilterAdapter(mContext, configModel, action);
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
			case SingleFilterAdapter.ACTION_SET_ROOM:
				mTvTitle.setText(R.string.room);
				break;
			case SingleFilterAdapter.ACTION_SET_STYLE:
				mTvTitle.setText(R.string.style);
				break;
			case SingleFilterAdapter.ACTION_SET_COLOR:
				mTvTitle.setText(R.string.color);
				break;
			case SingleFilterAdapter.ACTION_SET_PRICE:
				mTvTitle.setText(R.string.price);
				break;
			case SingleFilterAdapter.ACTION_ITEM_STYLE:
				mTvTitle.setText(R.string.style);
				break;
			case SingleFilterAdapter.ACTION_ITEM_COLOR:
				mTvTitle.setText(R.string.color);
				break;
			case SingleFilterAdapter.ACTION_ITEM_PRICE:
				mTvTitle.setText(R.string.price);
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
			case SingleFilterAdapter.ACTION_SET_ROOM:
				if (mOnSingleFilterSetListener != null) {
					if (p == 0) {
						mOnSingleFilterSetListener.onRoomSelect(p, null);
						return;
					}
					mOnSingleFilterSetListener.onRoomSelect(p, mConfigModel.getData().getConfig().getSet_room().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_SET_STYLE:
				if (mOnSingleFilterSetListener != null) {
					if (p == 0) {
						mOnSingleFilterSetListener.onStyleSelect(p, null);
						return;
					}
					mOnSingleFilterSetListener.onStyleSelect(p, mConfigModel.getData().getConfig().getSet_style().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_SET_COLOR:
				if (mOnSingleFilterSetListener != null) {
					if (p == 0) {
						mOnSingleFilterSetListener.onColorSelect(p, null);
						return;
					}
					mOnSingleFilterSetListener.onColorSelect(p, mConfigModel.getData().getConfig().getSet_color().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_SET_PRICE:
				if (mOnSingleFilterSetListener != null) {
					if (p == 0) {
						mOnSingleFilterSetListener.onPriceSelect(p, null);
						return;
					}
					mOnSingleFilterSetListener.onPriceSelect(p, mConfigModel.getData().getConfig().getSet_price().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_ITEM_STYLE:
				if (mOnSingleItemListener != null) {
					if (p == 0) {
						mOnSingleItemListener.onStyleSelect(p, null);
						return;
					}
					mOnSingleItemListener.onStyleSelect(p, mConfigModel.getData().getConfig().getItem_style().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_ITEM_COLOR:
				if (mOnSingleItemListener != null) {
					if (p == 0) {
						mOnSingleItemListener.onColorSelect(p, null);
						return;
					}
					mOnSingleItemListener.onColorSelect(p, mConfigModel.getData().getConfig().getItem_color().get(p - 1));
				}
				break;
			case SingleFilterAdapter.ACTION_ITEM_PRICE:
				if (mOnSingleItemListener != null) {
					if (p == 0) {
						mOnSingleItemListener.onPriceSelect(p, null);
						return;
					}
					mOnSingleItemListener.onPriceSelect(p, mConfigModel.getData().getConfig().getItem_price().get(p - 1));
				}
				break;
		}
	}


	/**
	 * 套装 单选 选择监听
	 */
	public interface OnSingleFilterSetListener {

		void onRoomSelect(int index, ConfigModel.DataBean.ConfigBean.SetRoomBean bean);

		void onColorSelect(int index, ConfigModel.DataBean.ConfigBean.SetColorBean bean);

		void onStyleSelect(int index, ConfigModel.DataBean.ConfigBean.SetStyleBean bean);

		void onPriceSelect(int index, ConfigModel.DataBean.ConfigBean.SetPriceBean bean);
	}

	public interface OnSingleItemListener {
		void onColorSelect(int index, ConfigModel.DataBean.ConfigBean.ItemColorBean bean);

		void onStyleSelect(int index, ConfigModel.DataBean.ConfigBean.ItemStyleBean bean);

		void onPriceSelect(int index, ConfigModel.DataBean.ConfigBean.ItemPriceBean bean);
	}

	public void setOnSingleFilterSetListener(OnSingleFilterSetListener listener) {
		this.mOnSingleFilterSetListener = listener;
	}

	public void setOnSingleItemListener(OnSingleItemListener listener) {
		this.mOnSingleItemListener = listener;
	}

	/**
	 * 关闭popupwindow
	 */
	public void dismiss() {
		if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
	}

}
