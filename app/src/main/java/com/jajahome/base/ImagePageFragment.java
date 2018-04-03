package com.jajahome.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jajahome.R;
import com.jajahome.feature.home.adapter.HomeListAdapter;
import com.jajahome.model.HomeBannerModel;
import com.jajahome.util.StringUtil;
import com.squareup.picasso.Picasso;


/**
 * 被用于 ImagePagerAdapter 的图片显示
 */
public class ImagePageFragment extends Fragment {

    /**
     * 向轮播图Fragment传数据
     */
    public static final String BEAN = "BEAN";

    /**
     * 轮播图数据
     */
    public static ImagePageFragment newInstance(HomeBannerModel.DataBean.BannerBean bannerBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BEAN, bannerBean);
        ImagePageFragment imagePageFragment = new ImagePageFragment();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拿到数据
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final HomeBannerModel.DataBean.BannerBean bannerBean = (HomeBannerModel.DataBean.BannerBean) bundle.getSerializable(BEAN);
        View view = inflater.inflate(R.layout.item_banner, container, false);
        ImageView imgBanner = (ImageView) view.findViewById(R.id.item_banner_img);
        String smallUri = bannerBean.getImage().getSmall();
        if (!StringUtil.isEmpty(smallUri)) {
            Picasso.with(getContext())
                    .load(smallUri)
                    .placeholder(R.mipmap.ic_bottom_background)
                    .into(imgBanner);
        }

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据action跳转
                String title;
                if (StringUtil.isEmpty(bannerBean.getDescription())) {
                    title = "    ";
                } else {
                    title = bannerBean.getDescription();
                }
                HomeListAdapter.jumpToActByAction(getContext(), bannerBean.getAction(), bannerBean.getAction_id(), bannerBean.getUrl(), title);
            }
        });
        return view;
    }

}
