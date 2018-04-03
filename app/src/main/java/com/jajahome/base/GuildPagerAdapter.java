package com.jajahome.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.jajahome.R;


/**
 * 引导页
 */
public class GuildPagerAdapter extends FragmentPagerAdapter {

    /**
     * 轮播图片信息
     */
    private int[]ids;

    public GuildPagerAdapter(FragmentManager fm) {
        super(fm);
        ids=new int[]{R.mipmap.guide_image_1,R.mipmap.guide_image_2,R.mipmap.guide_image_3,R.mipmap.guide_image_4,R.mipmap.guide_image_5};
    }

    @Override
    public Fragment getItem(int position) {
        return GuildPageFragment.newInstance(ids[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return ids.length;
    }
}
