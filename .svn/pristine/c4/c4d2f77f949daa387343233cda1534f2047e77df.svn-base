package com.jajahome.feature.home.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.model.RecommendModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */
public class SetItemAdapter extends BaseRecyclerAdapter<RecommendModel.DataBean.RecommendBean> {
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, final List<RecommendModel.DataBean.RecommendBean> mItemDataList) {
        ViewHoler holder = (ViewHoler) viewHolder;
        String url = mItemDataList.get(i).getImage().getSmall();
        if (!StringUtil.isEmpty(url)) {
            Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_bottom_background).into(holder.titleImg);
        }
        holder.bannerDesc.setText(mItemDataList.get(i).getName());
        holder.titleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, mItemDataList.get(i).getAction_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.act_item_set_banner;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHoler(view);
    }

    public class ViewHoler extends BaseRecyclerViewHolder {

        private ImageView titleImg;
        private TextView bannerDesc;
        private int m, w;

        public ViewHoler(View itemView) {
            super(itemView);
            titleImg = (ImageView) itemView.findViewById(R.id.banner_home);
            bannerDesc = (TextView) itemView.findViewById(R.id.banner_desc);
            w = (int) DensityUtil.getDisplayWidthDp(mContext);
            m = DensityUtil.dip2px(mContext, 18);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleImg.getLayoutParams();
            params.width = w - 2 * m;
            params.setMargins(18, 0, 0, 0);
            titleImg.setLayoutParams(params);

        }
    }
}
