package com.jajahome.feature.comment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.model.CommentModel;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liuhaizhu on 2017/6/20.
 */

public class CommentRecyclerAdapter extends BaseRecyclerAdapter<CommentModel.DataBean.ListsBean> {
    private boolean isShowTwo=false;
    public void setOnlyShowTwoReply(){
        isShowTwo=true;
    }
    private OnCommentListener mOnCommentListener;

    public void setOnCommentListener(OnCommentListener onCommentListener) {
        mOnCommentListener = onCommentListener;
    }
    public void like(int position){
        CommentModel.DataBean.ListsBean listsBean=mList.get(position);
        listsBean.setLike(listsBean.getLike()+1);
        mList.set(position,listsBean);
        notifyDataSetChanged();
    }
    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<CommentModel.DataBean.ListsBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        final CommentModel.DataBean.ListsBean bean = mItemDataList.get(i);
        if (bean.getUser().getAvatar() != null) {
            Picasso.with(getContext())
                    .load(bean.getUser().getAvatar().getSmall())
                    .placeholder(R.mipmap.ic_holder_header_big)
                    .error(R.mipmap.ic_holder_header_big)
                    .into(holder.imgView);
        } else {
            holder.imgView.setImageResource(R.mipmap.ic_holder_header_big);
        }
        holder.tvName.setText(bean.getUser().getNickname());
        holder.tvTime.setText(TimeUtil.getCommentTime(bean.getTime()));
        holder.tvContent.setText(bean.getComment());
        holder.tvNums.setText(String.valueOf(bean.getLike()));
        holder.viewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.isLogin(mContext)) {
                    mContext.startActivity(new Intent(mContext,LoginAct.class));
                    Toast.makeText(mContext,"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mOnCommentListener!=null){
                    mOnCommentListener.onLike(i,bean.getId());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnCommentListener!=null){
                    mOnCommentListener.onReply(i,bean.getId(),bean.getUser().getNickname());
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(LoginUtil.getInfo(mContext).getData().getUser().getId().equals(bean.getUser().getId())){
                    if(mOnCommentListener!=null){
                        mOnCommentListener.onDel(i,bean.getId());
                    }
                }
                return false;
            }
        });
        if(bean.getReplys()!=null&&bean.getReplys().size()==0){
            holder.viewReplys.setVisibility(View.GONE);
        }else {
            holder.viewReplys.setVisibility(View.VISIBLE);
            CommentInnerAdapter adapter=new CommentInnerAdapter();
            adapter.resetItems(bean.getReplys());
            List<CommentModel.DataBean.ListsBean.ReplysBean> replys = bean.getReplys();
            if (isShowTwo&&replys != null && replys.size() > 2) {
                replys = replys.subList(0, 2);
            }
            adapter.resetItems(replys);
            holder.mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_comment;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        CommentRecyclerAdapter.ViewHolder viewHolder = new CommentRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        CircleImageView imgView;
        TextView tvName;
        TextView tvTime;
        TextView tvContent;
        TextView tvNums;
        LinearLayout viewLike;
        LinearLayout viewReplys;
        RecyclerView mRecyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findView(R.id.item_name);
            tvTime = (TextView) findView(R.id.item_time);
            tvContent = (TextView) findView(R.id.tv_content);
            tvNums = (TextView) findView(R.id.tv_zan_num);
            imgView = (CircleImageView) findView(R.id.image_person);
            viewLike = (LinearLayout) findView(R.id.view_zan);
            viewReplys = (LinearLayout) findView(R.id.view_replys);
            mRecyclerView = (RecyclerView) findView(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public interface OnCommentListener {
        void onLike(int position, String id);

        void onReply(int position, String id, String name);

        void onDel(int position, String id);
    }
}
