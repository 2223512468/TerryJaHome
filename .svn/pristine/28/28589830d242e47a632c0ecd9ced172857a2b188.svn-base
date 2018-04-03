package com.jajahome.feature.comment;

import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.CommentModel;

import java.util.List;

/**
 * Created by liuhaizhu on 2017/6/20.
 */

public class CommentInnerAdapter extends BaseRecyclerAdapter<CommentModel.DataBean.ListsBean.ReplysBean> {
    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<CommentModel.DataBean.ListsBean.ReplysBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        final CommentModel.DataBean.ListsBean.ReplysBean bean = mItemDataList.get(i);
        holder.tvName.setText(bean.getUser().getNickname()+" 回复：");
        holder.tvContent.setText(bean.getComment());
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_comment_inner;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        CommentInnerAdapter.ViewHolder viewHolder = new CommentInnerAdapter.ViewHolder(view);
        return viewHolder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tvName;
        TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findView(R.id.item_name);
            tvContent = (TextView) findView(R.id.item_content);
        }
    }
}
