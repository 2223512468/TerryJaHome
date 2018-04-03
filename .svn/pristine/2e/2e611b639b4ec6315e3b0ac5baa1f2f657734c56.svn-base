package com.jajahome.pop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.ConfigModel;
import com.jajahome.pop.PopShowFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhz on 2016/7/19.
 */
public class ShowFilterAdapter extends BaseAdapter {
	public OnItemClick getListener() {
		return listener;
	}

	public void setListener(OnItemClick listener) {
		this.listener = listener;
	}

	private OnItemClick listener;

	List<String> mList;
	private ConfigModel mConfigModel;
	private int mAction;
	public int getmCurrentIndex() {
		return mCurrentIndex;
	}
	public void setmCurrentIndex(int mCurrentIndex) {
		this.mCurrentIndex = mCurrentIndex;
	}

	private int mCurrentIndex;
	private int mColorBlack;
	private int mColorYellow;
	private Context mContext;
	private final String FLAG = "-";

	public ShowFilterAdapter(Context context, ConfigModel configModel, int action) {
		this.mAction = action;
		this.mConfigModel = configModel;
		this.mContext = context;
		mColorBlack = ContextCompat.getColor(context, R.color.text_black);
		mColorYellow = ContextCompat.getColor(context, R.color.orange_ll);
		setConfigList();
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else
			return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
			convertView = mInflater.inflate(R.layout.item_filter, null);
			holder = new ViewHolder();
			holder.tvFilter = (TextView) convertView.findViewById(R.id.tvFilter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvFilter.setText(mList.get(position));
		if (position == mCurrentIndex) {
			holder.tvFilter.setTextColor(mColorYellow);
		} else {
			holder.tvFilter.setTextColor(mColorBlack);
		}
		holder.tvFilter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = position;
				notifyDataSetChanged();
				if(listener!=null)
				listener.onItemClick(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvFilter;
	}

	public interface OnItemClick{
		void onItemClick(int p);
	}

	private void setConfigList() {
		mList = new ArrayList<>();
		mList.add("全部");
		switch (mAction) {
			case PopShowFilter.ACTION_SHOW_USER:
				for (ConfigModel.DataBean.ConfigBean.ShowUserBean bean : mConfigModel.getData().getConfig().getShow_user()) {
					mList.add(bean.getText());
				}
				break;
			case PopShowFilter.ACTION_SHOW_TYPE:
				for (ConfigModel.DataBean.ConfigBean.ShowTypeBean bean : mConfigModel.getData().getConfig().getShow_type()) {
					mList.add(bean.getText());
				}
				break;
			case PopShowFilter.ACTION_SHOW_TIME:
				for (ConfigModel.DataBean.ConfigBean.ShowTimeBean bean : mConfigModel.getData().getConfig().getShow_time()) {
					mList.add(bean.getText());
				}
				break;

		}
	}

}

