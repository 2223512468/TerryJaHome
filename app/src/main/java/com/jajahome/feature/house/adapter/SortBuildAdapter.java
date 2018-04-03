package com.jajahome.feature.house.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.house.HouseListAct;
import com.jajahome.model.BuildListModel;
import com.jajahome.widget.AutoListView;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/11.
 */
public class SortBuildAdapter extends BaseRecyclerAdapter<BuildListModel.DataBean.SortBuildingsBean> implements SortBuildItemAdapter.OnItemClickListener {


    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<BuildListModel.DataBean.SortBuildingsBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.sortBuild.setText(mItemDataList.get(i).getFirstChar().toString());
        SortBuildItemAdapter adapter = new SortBuildItemAdapter(mContext, mItemDataList.get(i).getBuildings());
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
        Intent intent = new Intent(mContext, HouseListAct.class);
        intent.putExtra(HouseListAct.ID, id);
        intent.putExtra(HouseListAct.TITLE, text);
        mContext.startActivity(intent);
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        TextView sortBuild;
        AutoListView listView;

        public ViewHolder(View itemView) {
            super(itemView);
            sortBuild = (TextView) findView(R.id.firstChar);
            listView = (AutoListView) findView(R.id.listview);
        }
    }
}
