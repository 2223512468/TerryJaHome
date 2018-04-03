package com.jajahome.feature.search.adapter;

import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by liuhaizhu on 2017/7/22.
 */

public class SearchHistoryAdapter extends BaseRecyclerAdapter<String> {
    private OnHistorySelectedListener mOnHistorySelectedListener;

    public void setOnHistorySelectedListener(OnHistorySelectedListener onHistorySelectedListener) {
        mOnHistorySelectedListener = onHistorySelectedListener;
    }

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<String> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final String itemBean = mItemDataList.get(i);
        holder.tvKey.setText(itemBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnHistorySelectedListener!=null){
                    mOnHistorySelectedListener.onHistorySelected(itemBean);
                }
            }
        });
    }

    /**
     * @return 适配器布局
     */
    @Override
    public int getListLayoutId() {
        return R.layout.item_search_history;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tvKey;
        public ViewHolder(View itemView) {
            super(itemView);
            tvKey = (TextView) findView(R.id.tv_key);
        }
    }

    public interface OnHistorySelectedListener{
        void onHistorySelected(String history);
    }
}