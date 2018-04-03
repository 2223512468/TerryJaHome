package com.jajahome.feature.user.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.base.BaseRecyclerWuLiuAdapter;
import com.jajahome.model.WuLiuModel;

import java.util.List;


/**
 * Created by Administrator on 2017/2/8.
 */
public class MaterAdapter extends BaseRecyclerWuLiuAdapter<WuLiuModel.LogisticsStatusBean.LogisticsArrayBean> {
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<WuLiuModel.LogisticsStatusBean.LogisticsArrayBean> mItemDataList) {

        ViewHolder holder = (ViewHolder) viewHolder;
        String context = mItemDataList.get(i).getContext();
        holder.title.setText(context);

        String time = mItemDataList.get(i).getTime();
        holder.time.setText(time);



        if (i == 0) {
            holder.viewPointTop.setVisibility(View.INVISIBLE);
            holder.pointView.setVisibility(View.VISIBLE);
        }

        if (i == mItemDataList.size() - 1) {
            holder.viewBottomLine.setVisibility(View.INVISIBLE);
            holder.viewShuLine.setVisibility(View.GONE);
        }

    }

    @Override
    public int getListLayoutId() {
        return R.layout.act_mater_item;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        private TextView title, time;
        private View viewPointTop, viewBottomLine, viewShuLine;
        private ImageView pointView;

        public ViewHolder(View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            viewPointTop = itemView.findViewById(R.id.view_0);
            viewBottomLine = itemView.findViewById(R.id.view_4);
            viewShuLine = itemView.findViewById(R.id.view_2);
            pointView = (ImageView) itemView.findViewById(R.id.image_red);

        }
    }

}
