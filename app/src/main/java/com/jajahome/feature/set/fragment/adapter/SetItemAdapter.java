package com.jajahome.feature.set.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.SetModel;

import java.util.List;

import rx.Subscription;

/**
 * Created by lhz on 2016/7/21.
 */
public class SetItemAdapter extends BaseAdapter {
	private final String BRACKETS_RIGHT = ")";
	private final String BRACKETS_LEFT = "(";
	List<SetModel.DataBean.SetBean.ListBean> mList;
	private Context mContext;

	private Subscription mSubscription;
	public SetItemAdapter(Context mContext, List<SetModel.DataBean.SetBean.ListBean> mList) {
		this.mList = mList;
		this.mContext = mContext;
	}

	public void setmSubscription(Subscription mSubscription) {
		this.mSubscription = mSubscription;
	}


	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		SetModel.DataBean.SetBean.ListBean bean = mList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
			convertView = mInflater.inflate(R.layout.view_row, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.header_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(getItemName(bean));
		return convertView;
	}

	private String getItemName(SetModel.DataBean.SetBean.ListBean bean) {
		StringBuilder sb = new StringBuilder();
		sb.append(bean.getName()).append(BRACKETS_LEFT).append(bean.getItem_size()).append(BRACKETS_RIGHT);
		return sb.toString();
	}

	class ViewHolder {
		TextView tvName;
	}
}
