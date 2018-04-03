package com.jajahome.feature.item.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListAdapter;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类描述：评论的adapter
 * 创建人：lhz
 * 创建时间：2016/7/8 15:07
 * 修改时间：2016/7/8 15:07
 * 修改备注：
 */
public class ItemCommentAdapter extends BaseListAdapter<ItemModel.DataBean.ItemBean.CommentListBean> {
    public ItemCommentAdapter(Context context, List<ItemModel.DataBean.ItemBean.CommentListBean> list) {
        super(context, list);
    }
    @Override
    public View initView(ItemModel.DataBean.ItemBean.CommentListBean model, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_commont, null);
            holder = new ViewHolder();
            holder.imgView = (CircleImageView) convertView.findViewById(R.id.item_img);
            holder.tvContent = (TextView) convertView.findViewById(R.id.item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String userImg=model.getUser().getAvatar().getSmall();
        if(!StringUtil.isEmpty(userImg)){
            Picasso.with(context).load(userImg).placeholder(R.mipmap.ic_holder_header_small).
                    error(R.mipmap.ic_holder_header_small)
                    .into(holder.imgView);
        }
        holder.tvContent.setText(model.getText());
        return convertView;
    }
    class ViewHolder {
        CircleImageView imgView;
        TextView tvContent;
    }
}
