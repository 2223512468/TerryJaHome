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
import com.jajahome.model.CityListModel;
import com.jajahome.pop.adapter.CityFilterAdapter;


/**
 * 类描述：找我家 城市列表选择弹窗
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopCityFilter implements CityFilterAdapter.OnItemClick {
	private Context mContext;
	private PopupWindow mPopupWindow; //弹窗
	private ListView mList;
	private CityListModel mConfigModel; //城市列表数据

	public void setListener(OnCitySelectListener listener) {
		this.listener = listener;
	}

	private OnCitySelectListener listener;
	private TextView mTvTitle, mTvBack; //标题 ，返回
	private CityFilterAdapter adapter; //适配器

	public PopCityFilter(Context context, CityListModel configModel) {
		this.mContext = context;
		this.mConfigModel = configModel;
		init(configModel);
	}

	/**
	 * 初始化
	 * @param configModel
	 */
	protected void init(CityListModel configModel) {
		View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter, null);
		mTvBack = (TextView) rootView.findViewById(R.id.tv_filter_back);
		mTvTitle = (TextView) rootView.findViewById(R.id.tv_filter_title);
		mList = (ListView) rootView.findViewById(R.id.listView);
		adapter = new CityFilterAdapter(mContext, configModel);
		adapter.setListener(this);
		mTvTitle.setText(R.string.city);
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

		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				adapter.clearList();
			}
		});
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
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
	private void backPress() {
		if (adapter.getmCurrentLevel() == 1) {
			dismiss();
		} else {
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

	public interface OnCitySelectListener {
		/**
		 * 城市被选中结果
		 * @param id
		 * @param text
		 */
		void onCitySelect(int id, String text);
	}
	@Override
	public void onItemClick(int id, String text) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
		listener.onCitySelect(id, text);
	}
}
