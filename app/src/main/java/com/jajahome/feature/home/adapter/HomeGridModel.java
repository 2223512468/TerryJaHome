package com.jajahome.feature.home.adapter;

/**
 * 类描述：首页中 套装筛选model
 * 创建人：lhz
 * 创建时间：2016/7/8 15:07
 * 修改时间：2016/7/8 15:07
 * 修改备注：
 */
public class HomeGridModel {
    private int  imgResId;

    public HomeGridModel(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }



    private int titleId;

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
