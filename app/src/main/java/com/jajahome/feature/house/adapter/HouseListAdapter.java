package com.jajahome.feature.house.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.house.HouseSetListAct;
import com.jajahome.model.HouseListModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * 户型列表 的adapter
 */
public class HouseListAdapter extends BaseRecyclerAdapter<HouseListModel.DataBean.HouseBean> {
    static int w; //宽度

    HouseListener listener;

    public void setListener(HouseListener listener) {
        this.listener = listener;
    }

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<HouseListModel.DataBean.HouseBean> mItemDataList) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        //取大图
        String img = mItemDataList.get(i).getImage().getUrl();
        //如果连接不为空 加载到bitmap  计算视图大小

        if (!StringUtil.isEmpty(img)) {
            ImageLoader.getInstance().loadImage(img, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);

                    int h = (int) (((float) loadedImage.getHeight()) / ((float) loadedImage.getWidth()) * w);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
                    holder.itemBannerImg.setLayoutParams(params);
                    holder.itemBannerImg.setImageBitmap(loadedImage);
                }
            });
        } else {
            holder.itemBannerImg.setImageResource(R.mipmap.ic_bottom_background);
        }
        holder.textView.setText(mItemDataList.get(i).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouseSetListAct.class);
                intent.putExtra(HouseSetListAct.ID, mItemDataList.get(i).getId());
                intent.putExtra(HouseSetListAct.TITLE, mItemDataList.get(i).getTitle());
                mContext.startActivity(intent);

            }
        });

        holder.collectedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddHouse(mItemDataList.get(i).getId(),holder.collectedImg);
                }
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_house_list;
    }

    /**
     * @param view item_frag_home_drag 的view
     * @return
     */
    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        w = (int) DensityUtil.getDisplayWidthDp(mContext);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        ImageView itemBannerImg, collectedImg;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBannerImg = (ImageView) findView(R.id.item_img);
            textView = (TextView) findView(R.id.textView);
            collectedImg = (ImageView) findView(R.id.collected);
        }
    }

    public interface HouseListener {
        void onAddHouse(String id,ImageView colView);
    }

}

