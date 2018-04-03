package com.jajahome.diyview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jajahome.constant.Constant;
import com.jajahome.model.ImageModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.model.SetModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 套装 diy组合view
 */
public class ImageDiyFrameLayout extends FrameLayout {
    List<SetModel.DataBean.SetBean.ListBean> mList;
    /**
     * 实际数据和网络获取的数据 的缩放比例
     */
    private float mZoomScale;
    /**
     * 显示 透视图的 ImageView集合
     */

    public List<ImageView> imageViews = new ArrayList<>();


    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 已加载成功的视图数量
     */
    private int mLoadedNums = 0;
    public static boolean isReverse;
    private OnAllImageLoadedListener mOnAllImageLoadedListener;
    public static int selectDiyIndex;

    public void setmOnAllImageLoadedListener(OnAllImageLoadedListener mOnAllImageLoadedListener) {
        this.mOnAllImageLoadedListener = mOnAllImageLoadedListener;
    }

    public ImageDiyFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public ImageDiyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 获取套装价格（原价 ，折扣价）
     *
     * @return 原价最低   原价最高，折扣价最低 折扣价最高
     */
    public int[] getPrice() {
        int[] prices = new int[4];
        int basicPriceMin = 0; //不打折 最低价
        int basicPriceMax = 0; //不打折 最高价
        int discountPriceMin = 0;//打折 最低价
        int discountPriceMax = 0;//打折 最高价
        if (mList != null) {
            for (SetModel.DataBean.SetBean.ListBean listBean : mList) {
                SetModel.DataBean.SetBean.ListBean.ItemInfoBean info = listBean.getItem_info();
                if (info != null && info.getPrice_basic() != null) {
                    int count = 1;
                    basicPriceMin = basicPriceMin + info.getPrice_basic().getMin() * count;
                    basicPriceMax = basicPriceMax + info.getPrice_basic().getMax() * count;
                    discountPriceMin = discountPriceMin + info.getPrice_discount().getMin() * count;
                    discountPriceMax = discountPriceMax + info.getPrice_discount().getMax() * count;
                    Log.i("count", "name:" + info.getName() + ",conut" + count);
                }
            }
            prices[0] = basicPriceMin;
            prices[1] = basicPriceMax;
            prices[2] = discountPriceMin;
            prices[3] = discountPriceMax;
        }
        return prices;
    }

    public int[] getSetPrice() {
        int[] prices = new int[4];
        int basicPriceMin = 0; //不打折 最低价
        int basicPriceMax = 0; //不打折 最高价
        int discountPriceMin = 0;//打折 最低价
        int discountPriceMax = 0;//打折 最高价
        if (mList != null) {
            for (SetModel.DataBean.SetBean.ListBean listBean : mList) {
                SetModel.DataBean.SetBean.ListBean.ItemInfoBean info = listBean.getItem_info();
                if (info != null && info.getPrice_basic() != null) {
                    int count = info.getCount();
                    basicPriceMin = basicPriceMin + info.getPrice_basic().getMin() * count;
                    basicPriceMax = basicPriceMax + info.getPrice_basic().getMax() * count;
                    discountPriceMin = discountPriceMin + info.getPrice_discount().getMin() * count;
                    discountPriceMax = discountPriceMax + info.getPrice_discount().getMax() * count;
                    Log.i("count", "name:" + info.getName() + ",conut" + count);
                }
            }
            prices[0] = basicPriceMin;
            prices[1] = basicPriceMax;
            prices[2] = discountPriceMin;
            prices[3] = discountPriceMax;
        }
        return prices;
    }

    private void init(Context context) {
        float f = DensityUtil.getDisplayWidthDp(context);
        mWidth = (int) f;
        mHeight = (int) (f / Constant.SET_SCALE);
        mZoomScale = Constant.DEFAULT_WEIGHT / mWidth;
        setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));

    }

    public void setBottomImage(String image_blueprint) {
        final ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(getContext()).load(image_blueprint).into(imageView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mWidth,
                mHeight);
        addView(imageView, params);
    }


    public List<SetModel.DataBean.SetBean.ListBean> getmList() {
        return mList;
    }


    public void setmList(List<SetModel.DataBean.SetBean.ListBean> mList) {
        Comparator comp = new SortComparator();
        Collections.sort(mList, comp);
        this.mList = mList;
        initChildView();
    }

    Gson gson = new Gson();

    private void changeModel(int index, SetItemModel.DataBean.ListBean.InfoBean bean) {
        String json = gson.toJson(bean);
        SetModel.DataBean.SetBean.ListBean.ItemInfoBean info = gson.fromJson(json, SetModel.DataBean.SetBean.ListBean.ItemInfoBean.class);
        if (info.getImage() != null)
            mList.get(index).setItem_info(info);
    }


    public static HashMap<Integer, Integer> map = new HashMap<>();


    /**
     * 改变透视图 （切换）
     *
     * @param itemId   ：单品id
     * @param listBean :单品数据
     * @param url      ：透视图地址
     */
    public void changeImage(String itemId, final SetItemModel.DataBean.ListBean listBean, String url, int groupPosition) {

        final SetItemModel.DataBean.ListBean.InfoBean bean = listBean.getInfo();
        final int index = getPosition(itemId);

        map.put(groupPosition, index);

        //改变透视图位置信息为所选单品的
        setImageViewPos(index, listBean);
        changeModel(index, bean);
        String zorder = mList.get(index).getZorder();
        setChildView(zorder);
        final ImageView imageView = imageViews.get(index);
        imageView.setImageBitmap(null);

        if (mList.get(index).getItem_info().getFlip() == 1) {
            imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    loadedImage = BitmapUtil.convert(loadedImage);
                    imageViews.get(index).setImageBitmap(loadedImage);
                }
            });
        } else {
            if (!StringUtil.isEmpty(url) && imageViews.get(index) != null) {
                Picasso.with(getContext()).load(url).into(imageViews.get(index));
            }
        }
    }


    private void setChildView(String zorder) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getParent_zorder().equals(zorder)) {
                setImageViewPos(i);
            }
        }
    }

    /**
     * 获取该单品位置index
     *
     * @param itemId :单品id
     * @return 位置index
     */
    private int getPosition(String itemId) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (mList.get(i).getId().equals(itemId)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 初始化 每一个视图
     */
    private void initChildView() {
        if (mList != null) {
            int size = mList.size();
            for (int i = 0; i < size; i++) {
                addImgView();
            }
        }
        Gson gson = new Gson();
        //判断有无透视图 没有不显示
        if (mList != null) {
            int size = mList.size();
            for (int i = 0; i < size; i++) {
                Object object = mList.get(i).getItem_info().getImage();
                try {
                    ImageModel model = gson.fromJson(gson.toJson(object), ImageModel.class);
                    resetImg(i, model.getUrl());
                } catch (JsonSyntaxException e) {

                }
            }
        }
    }

    /**
     * 镜像
     */

    private void addImgView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViews.add(imageView);
        addView(imageView);

    }

    ImageLoader imageLoader = ImageLoader.getInstance();

    private void resetImg(final int index, final String url) {
        setImageViewPos(index);

        if (mList.get(index).getItem_info().getFlip() == 1) {
            imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    loadedImage = BitmapUtil.convert(loadedImage);
                    imageViews.get(index).setImageBitmap(loadedImage);
                    isAllLoaded(imageViews, selectDiyIndex, mList);

                }
            });
        } else {
            Picasso.with(getContext()).load(url).into(imageViews.get(index));
            isAllLoaded(imageViews, selectDiyIndex, mList);
        }
    }


    private void isAllLoaded(List<ImageView> imageViews, int selectIndex, List<SetModel.DataBean.SetBean.ListBean> mList) {
        mLoadedNums = mLoadedNums + 1;
        if (mLoadedNums == mList.size()) {
            if (null != mOnAllImageLoadedListener)
                mOnAllImageLoadedListener.onAllImageLoaded();
            mOnAllImageLoadedListener.vOrg(imageViews, selectIndex, mList);


        }
    }

    /**
     * 设置ImageView 大小位置
     *
     * @param index :view index
     */
    private void setImageViewPos(int index, SetItemModel.DataBean.ListBean listBean) {
        ImageView imageView = imageViews.get(index);
        SetItemModel.DataBean.ListBean.InfoBean bean = listBean.getInfo();
        //图片实际显示时的 宽高
        int bitmapW = (int) (bean.getImage_size().getX() / mZoomScale * bean.getScale());
        int bitmapH = (int) (bean.getImage_size().getY() / mZoomScale * bean.getScale());
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        if (bitmapH > mHeight) bitmapH = mHeight;
        para.height = bitmapH;
        para.width = bitmapW;
        LayoutParams params = new LayoutParams(para);
        SetItemModel.DataBean.ListBean.InfoBean.PosBean posBean = bean.getPos();
        int x = posBean.getX();
        int y = posBean.getY();
        //判断有无亲子关系
        String parentZorder = mList.get(index).getParent_zorder();
        if (!parentZorder.equals("0")) {
            int pos[] = getTruePos(parentZorder, x, y);
            x = pos[0];
            y = pos[1];
        }
        x = (int) (x / mZoomScale);
        y = (int) (y / mZoomScale);
        int sX = (int) (bitmapW * bean.getAnchor().getX());
        int sY = (int) (bitmapH * bean.getAnchor().getY());
        int left = (x - sX);
        int top = (y - sY);
        if (top + bitmapH > mHeight) {
            top = mHeight - bitmapH;
        }

        params.setMargins(left, top, 0, 0);
        imageView.setLayoutParams(params);
    }

    /**
     * 设置ImageView 大小位置
     *
     * @param index :view index
     */
    private void setImageViewPos(int index) {
        ImageView imageView = imageViews.get(index);
        SetModel.DataBean.SetBean.ListBean.ItemInfoBean bean = mList.get(index).getItem_info();
        //图片实际显示时的 宽高
        int bitmapW = (int) (bean.getImage_size().getX() / mZoomScale * bean.getScale());
        int bitmapH = (int) (bean.getImage_size().getY() / mZoomScale * bean.getScale());
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        if (bitmapH > mHeight) bitmapH = mHeight;
        para.height = bitmapH;
        para.width = bitmapW;
        LayoutParams params = new LayoutParams(para);
        SetModel.DataBean.SetBean.ListBean.ItemInfoBean.PosBean posBean = bean.getPos();
        int x = posBean.getX();
        int y = posBean.getY();
        //判断有无亲子关系
        String parentZorder = mList.get(index).getParent_zorder();
        if (!parentZorder.equals("0")) {
            int pos[] = getTruePos(parentZorder, x, y);
            x = pos[0];
            y = pos[1];
        }
        x = (int) (x / mZoomScale);
        y = (int) (y / mZoomScale);
        int sX = (int) (bitmapW * bean.getAnchor().getX());
        int sY = (int) (bitmapH * bean.getAnchor().getY());
        int left = (x - sX);
        int top = (y - sY);
        if (top + bitmapH > mHeight) {
            top = mHeight - bitmapH;
        }

        params.setMargins(left, top, 0, 0);
        if (left <= mWidth || top <= mHeight) {
            imageView.setLayoutParams(params);
        } else {
            imageView.setVisibility(GONE);
        }
    }

    /**
     * 获取 亲子关系 的子视图位置
     *
     * @param parent_zorder ：父视图位置
     * @param x：            x
     * @param y             y
     * @return 子视图实际位置
     */
    private int[] getTruePos(String parent_zorder, int x, int y) {
        int[] pos = new int[2];
        for (int i = 0; i < mList.size(); i++) {
            if (parent_zorder.equals(mList.get(i).getZorder())) {
                SetModel.DataBean.SetBean.ListBean.ItemInfoBean itemBean = mList.get(i).getItem_info();
                SetModel.DataBean.SetBean.ListBean.ItemInfoBean.PosBean posBean = itemBean.getPos();
                int px = posBean.getX();
                int py = posBean.getY();
                pos[0] = px + x;
                pos[1] = py + y;
                return pos;
            }
        }
        return pos;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {

        if (isReverse == true) {
            canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);

        } else {
            isReverse = false;
        }
        super.dispatchDraw(canvas);
    }

    public interface OnAllImageLoadedListener {
        void onAllImageLoaded();

        void vOrg(List<ImageView> imageViews, int selectIndex, List<SetModel.DataBean.SetBean.ListBean> mList);
    }
}
