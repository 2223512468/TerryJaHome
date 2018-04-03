package com.jajahome.feature.user.adapter;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.UserMessageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by laotang on 2016/8/1.
 */
public class MessageAdapter extends BaseRecyclerAdapter<UserMessageModel.DataBean.MessageBean> {
    private int mColorBlack;
    private int mColorOrigen;

    public OnMessageItemOperation getListener() {
        return listener;
    }

    public void setListener(OnMessageItemOperation listener) {
        this.listener = listener;
    }

    /**
     * 设置消息已读
     * @param index：第几条
     */
    public void setMessageRead(int index){
        if(mList!=null&&mList.size()>index){
            mList.get(index).setIs_read("1");
            notifyDataSetChanged();
        }
    }

    private OnMessageItemOperation listener;

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, List<UserMessageModel.DataBean.MessageBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        UserMessageModel.DataBean.MessageBean messageBean=mItemDataList.get(i);
        holder.tv_message.setText(messageBean.getTitle());
        holder.tv_time.setText(messageBean.getTime());
        Picasso.with(mContext).load(messageBean.getIcon().getSmall()).into(holder.iv_message);
        if("0".equals(messageBean.getIs_read())){
            holder.tv_message.setTextColor(mColorOrigen);
            holder.tv_time.setTextColor(mColorOrigen);
        }else {
            holder.tv_message.setTextColor(mColorBlack);
            holder.tv_time.setTextColor(mColorBlack);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(i);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mList.get(i).getIs_read().equals("0")){
                    //消息未读
                    listener.onMessageOperation(1,i,Integer.valueOf(mList.get(i).getId()));
                }
                H5Act.gotoH5(mList.get(i).getTitle(),mContext,mList.get(i).getContent());
                //进入消息详情
            }
        });
    }

    private void showDialog(final  int index) {
        LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
        builder.setMessage("是否删除该消息?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               listener.onMessageOperation(2,index,Integer.valueOf(mList.get(index).getId()));
            }
        });
        builder.create().show();
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_message;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        mColorBlack= ContextCompat.getColor(mContext,R.color.text_black);
        mColorOrigen= ContextCompat.getColor(mContext,R.color.orange_ll);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tv_message, tv_time;
        ImageView iv_message;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_message = (ImageView) itemView.findViewById(R.id.iv_message);
        }
    }
    public interface OnMessageItemOperation {
       void onMessageOperation(int operation,int index,int id);
    }
}
