package com.jajahome.feature.house;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.BuildingModel;

import java.util.List;

/**
 *
 */
public class BuildingListAdapter extends BaseRecyclerAdapter<BuildingModel.DataBean.BuildingBean> {
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<BuildingModel.DataBean.BuildingBean> mItemDataList) {
        final BuildingModel.DataBean.BuildingBean buildingBean = mItemDataList.get(i);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.textView.setText(buildingBean.getName());
        holder.filterImg.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouseListAct.class);
                intent.putExtra(HouseListAct.TITLE, buildingBean.getName());
                intent.putExtra(HouseListAct.ID, buildingBean.getId());
                mContext.startActivity(intent);
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
        TextView textView;
        ImageView filterImg;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) findView(R.id.tv_filter);
            filterImg = (ImageView) findView(R.id.img_filter);
        }
    }

}
