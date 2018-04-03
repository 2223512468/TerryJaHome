package com.jajahome.pop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.CityListModel;

import java.util.List;

/**
 *  城市选择框adapter
 */
public class CityFilterAdapter extends BaseAdapter {
	public OnItemClick getListener() {
		return listener;
	}

	public int getmCurrentLevel() {
		return mCurrentLevel;
	}

	public void setmCurrentLevel(int mCurrentLevel) {
		this.mCurrentLevel = mCurrentLevel;
	}

	public int mCurrentLevel = 1;//当前分类层次 ，默认1

	public void setListener(OnItemClick listener) {
		this.listener = listener;
	}

	private OnItemClick listener;
	//item
	public int mIndex1 = 0; //第一级 选择的item  index
	public int mIndex2 = -1; //第二级 选择的item  index
	List<CityListModel.DataBean.CityBean> mList1; //城市选择一级
	List<CityListModel.DataBean.CityBean.ItemsBean> mList2;//城市选择二级
	private CityListModel CityListModel;
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
	public CityFilterAdapter(Context context, CityListModel model) {
		this.CityListModel = model;
		this.mContext = context;
		mColorBlack = ContextCompat.getColor(context, R.color.text_black);
		mColorYellow = ContextCompat.getColor(context, R.color.orange_ll);
		setConfigList();
	}

	@Override
	public int getCount() {
		switch (mCurrentLevel) {
			case 1:
				if (mList1 == null) return 0;
				else return mList1.size();
			case 2:
				if (mList2 == null) return 0;
				else return mList2.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
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
			convertView = mInflater.inflate(R.layout.item_filter_muti, null);
			holder = new ViewHolder();
			holder.tvFilter = (TextView) convertView.findViewById(R.id.tv_filter);
			holder.imgFlag = (ImageView) convertView.findViewById(R.id.img_filter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//根据不同的层级  做不同的判断显示相应的颜色
		switch (mCurrentLevel) {
			case 1:
				holder.tvFilter.setText(mList1.get(position).getText());
				if (position == mIndex1) {
					holder.tvFilter.setTextColor(mColorYellow);
				} else {
					holder.tvFilter.setTextColor(mColorBlack);
				}
				break;
			case 2:
				if (position == mIndex2) {
					holder.tvFilter.setTextColor(mColorYellow);
				} else {
					holder.tvFilter.setTextColor(mColorBlack);
				}
				holder.tvFilter.setText(mList2.get(position).getText());
				break;
		}
		if(position==0||!hasNextLevel(position)){
			holder.imgFlag.setVisibility(View.INVISIBLE);
		}else {
			holder.imgFlag.setVisibility(View.VISIBLE);
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemClicked(position);
			}
		});
		return convertView;
	}

	/**
	 * 该条目是否还有下一级
	 * @param index index
	 * @return 是否
	 */
	private boolean hasNextLevel(int index){
		switch (mCurrentLevel) {
			case 1:
				List<CityListModel.DataBean.CityBean.ItemsBean> list2 = mList1.get(index).getItems();
				return list2!=null&&list2.size()>0;
			case 2:
			return false;
		}
		return false;
	}

	private void itemClicked(final int position) {
		switch (mCurrentLevel) {
			case 1:
				if (position == 0) {
					listener.onItemClick(-1,"全部");
				}else if(hasNextLevel(position)){
					mIndex1=position;
					mCurrentLevel = 2;
					setConfigList();
					notifyDataSetChanged();
				}else {
					CityListModel.DataBean.CityBean bean =mList1.get(position);
					listener.onItemClick(bean.getId(),bean.getText());
				}
				break;
			case 2:
				if (position == 0) {
					CityListModel.DataBean.CityBean bean =mList1.get(mIndex1);
					listener.onItemClick(bean.getId(),bean.getText());
				}else {
					CityListModel.DataBean.CityBean.ItemsBean bean =mList2.get(position);
					listener.onItemClick(bean.getId(),bean.getText());
				}
				break;
		}
	}

	public void pressBack() {
		mCurrentLevel=mCurrentLevel-1;
		notifyDataSetChanged();
	}

	/**
	 * 弹窗消失的时候 返回到一级分类
	 */
	public void clearList() {
		mCurrentLevel=1;
		notifyDataSetChanged();
	}


	class ViewHolder {
		TextView tvFilter;
		ImageView imgFlag;
	}
	public interface OnItemClick {
		void onItemClick(int id, String text);
	}
	/**
	 * 填充数据
	 */
	private void setConfigList() {
		switch (mCurrentLevel) {
			case 1:
				mList1 = CityListModel.getData().getCity();
				CityListModel.DataBean.CityBean bean = new CityListModel.DataBean.CityBean();
				bean.setText("全部");
				mList1.add(0,bean);
				break;
			case 2:
				mList2 = mList1.get(mIndex1).getItems();
				if(mList2!=null&&mList2.get(0).getId()!=-1) {////第一次添加父分类
					CityListModel.DataBean.CityBean.ItemsBean itemsBean = new CityListModel.DataBean.CityBean.ItemsBean();
					itemsBean.setText(mList1.get(mIndex1).getText());
					itemsBean.setId(-1);
					mList2.add(0, itemsBean);
				}
				break;
		}
	}

}

