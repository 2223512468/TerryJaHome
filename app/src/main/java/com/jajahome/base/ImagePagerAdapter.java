package com.jajahome.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jajahome.model.HomeBannerModel;

import java.util.List;


/**
 * Created by Kaizen on 2015/8/10.
 * 被 InterrogationFragment 用于图片轮播
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<HomeBannerModel.DataBean.BannerBean> mList;//轮播图片信息
    FragmentManager fm ;
    public ImagePagerAdapter(FragmentManager fm, List<HomeBannerModel.DataBean.BannerBean> mList) {
        super(fm);
        this.mList = mList;
        this.fm = fm;
    }

    /**
     * 设置轮播图 数据
     * @param mList 轮播图 数据
     */
    public void setData(List<HomeBannerModel.DataBean.BannerBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ImagePageFragment.newInstance(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * @return :图片数量
     */
    @Override
    public int getCount() {
        if (mList == null) return 0;
        return mList.size();
    }
}
