package com.jajahome.feature.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.show.ShowH5DetailAct;
import com.jajahome.model.RecommendModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 首页用到的列表adapter
 */
public class HomeListAdapter extends BaseRecyclerAdapter<RecommendModel.DataBean.RecommendBean> {

    public static String Tag = "HomeListAdapter";

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<RecommendModel.DataBean.RecommendBean> mItemDataList) {
        //判断是否为空
        ViewHolder holder = (ViewHolder) viewHolder;
        RecommendModel.DataBean.RecommendBean.ImageBean bean = mItemDataList.get(i).getImage();
        if (bean != null && !StringUtil.isEmpty(bean.getSmall())) {
            Picasso.with(getContext())
                    .load(bean.getSmall())
                    .placeholder(R.mipmap.ic_bottom_background)
                    .into(holder.itemBannerImg);
        }
        setState(i, holder, mItemDataList);
        holder.textView.setText(mItemDataList.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendModel.DataBean.RecommendBean recommendBean = mItemDataList.get(i);
                jumpToActByAction(getContext(), recommendBean.getAction(), recommendBean.getAction_id(), recommendBean.getUrl(), recommendBean.getName());
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_home_list;
    }

    /**
     * 加载一个ViewHolder,为RecyclerViewHolderBase子类,直接返回子类的对象即可
     *
     * @param view item_frag_home_drag 的view
     * @return RecyclerViewHolderBase 基类ViewHolder
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg, leftImg;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBannerImg = (ImageView) findView(R.id.item_img);
            textView = (TextView) findView(R.id.textView);
            leftImg = (ImageView) findView(R.id.img);
        }
    }

    /**
     * 首页中 banner图和下方列表 通过action进行相应跳转 ，先判断action
     *
     * @param context  :context
     * @param action   :标识那一类
     * @param actionId ：套装 单品 秀家等id
     * @param url      : H5地址 ，当为diy 套装时分享用的图片链接
     */
    public static void jumpToActByAction(Context context, String action, String actionId, String url, String title) {
        if (StringUtil.isEmpty(action)) {
            //进入h5页面
            if (!StringUtil.isEmpty(url)) {
                H5Act.gotoH5(context, url, title, Tag);
            }
        } else if (action.equals("set")) {
            //进入套装
            Intent intent = new Intent(context, SetDetailAct.class);
            intent.putExtra(SetDetailAct.SET_ID, actionId);
            intent.putExtra(SetDetailAct.SET_URL, url);
            context.startActivity(intent);
        } else if (action.equals("item")) {
            //进入单品
            Intent intent = new Intent(context, ItemDetailAct.class);
            intent.putExtra(ItemDetailAct.ITEM_ID, actionId);
            context.startActivity(intent);
        } else if (action.equals("show")) {
            //进入秀家
            Intent intent = new Intent(context, ShowH5DetailAct.class);
            intent.putExtra(ShowH5DetailAct.SHOW_ID, actionId);
            context.startActivity(intent);
        }
    }

    private void setState(int p, ViewHolder holder, List<RecommendModel.DataBean.RecommendBean> mItemDataList) {
        String action = mItemDataList.get(p).getAction();
        int actionShow = mItemDataList.get(p).getAction_show();
        switch (action) {
            case "":
                holder.leftImg.setImageResource(R.mipmap.ic_small_essay);
                break;
            case "set":
                holder.leftImg.setImageResource(R.mipmap.ic_small_set);
                break;
            case "item":
                holder.leftImg.setImageResource(R.mipmap.ic_small_item);
                break;
            case "show":
                if (actionShow == 0) {
                    holder.leftImg.setImageResource(R.mipmap.ic_beautiful_pic);
                } else if (actionShow == 2) {
                    holder.leftImg.setImageResource(R.mipmap.ic_home_match_icon);
                } else if (actionShow == 3) {
                    holder.leftImg.setImageResource(R.mipmap.ic_home_original_icon);
                } else if (actionShow == 1) {
                    holder.leftImg.setImageResource(R.mipmap.ic_small_essay);
                }
                break;
            default:
                break;
        }
    }
}

