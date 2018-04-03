package com.jajahome.widget.banner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jajahome.R;
import com.squareup.picasso.Picasso;


/**
 * 被用于 ImagePagerAdapter 的图片显示
 */
public class ImagePageFragment extends Fragment {

    public static final String IMG_URL = "IMG_URL";
    public static final String DETAIL_LINK = "DETAIL_LINK";
    public static final String TITLE = "TITLE";
    private String mImgUrl, mDetailLink, mTitle;

    public static ImagePageFragment newInstance(String imgUrl, String detailLink, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(IMG_URL, imgUrl);
        bundle.putString(DETAIL_LINK, detailLink);
        bundle.putString(TITLE, title);
        ImagePageFragment imagePageFragment = new ImagePageFragment();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mImgUrl = bundle.getString(IMG_URL);
        mDetailLink = bundle.getString(DETAIL_LINK);
        mTitle = bundle.getString(TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_banner, container, false);
        ImageView imgBanner = (ImageView) view.findViewById(R.id.item_banner_img);
        Picasso.with(getContext())
                .load(mImgUrl)
                .into(imgBanner);
        return view;
    }

}
