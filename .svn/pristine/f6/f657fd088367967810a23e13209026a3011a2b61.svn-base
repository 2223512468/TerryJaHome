package com.jajahome.feature.house.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.constant.Constant;
import com.jajahome.feature.house.SelectBuildingListAct;
import com.jajahome.model.SortCityModel;
import com.jajahome.widget.AutoListView;

import java.util.List;

/**
 * Created by ${Terry} on 2017/10/18.
 */
public class SortCityAdapter extends BaseRecyclerAdapter<SortCityModel.DataBean.SortCitysBean> implements SortCityItemAdapter.OnItemClickListener {

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<SortCityModel.DataBean.SortCitysBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.firstChar.setText(mItemDataList.get(i).getFirstChar());
        SortCityItemAdapter adapter = new SortCityItemAdapter(mContext, mItemDataList.get(i).getCitys());
        holder.listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public int getListLayoutId() {
        return R.layout.act_sort_city_item;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void OnItemClick(String text, String id) {
        Intent intent = new Intent(mContext, SelectBuildingListAct.class);
        intent.putExtra(Constant.LOCATION, text);
        intent.putExtra(SelectBuildingListAct.CODE, id);
        mContext.startActivity(intent);
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        TextView firstChar;
        AutoListView listView;

        public ViewHolder(View itemView) {
            super(itemView);
            firstChar = (TextView) findView(R.id.firstChar);
            listView = (AutoListView) findView(R.id.listview);
        }
    }


}
