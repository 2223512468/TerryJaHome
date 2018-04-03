package com.jajahome.feature.house.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.SearchBuildListModel;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/11.
 */
public class SearchBuildAdapter extends BaseRecyclerAdapter<SearchBuildListModel.DataBean.BuildingsBean> {

    public OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<SearchBuildListModel.DataBean.BuildingsBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final String name = mItemDataList.get(i).getName().toString();
        final String id = mItemDataList.get(i).getId();
        holder.sortBuild.setText(name);
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(name, id);
                }
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_filter_muti;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView sortBuild;
        RelativeLayout itemContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            sortBuild = (TextView) findView(R.id.tv_filter);
            itemContainer = (RelativeLayout) findView(R.id.itemContainer);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String name, String id);
    }

}
