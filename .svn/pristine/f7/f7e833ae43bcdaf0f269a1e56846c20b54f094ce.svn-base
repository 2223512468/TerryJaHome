package com.jajahome.diyview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jajahome.constant.Constant;
import com.jajahome.model.ImageModel;
import com.jajahome.model.SetModel;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 套装 diy组合view（横屏下）
 */
public class ImageFrameLandscapeLayout extends FrameLayout {
    List<SetModel.DataBean.SetBean.ListBean> mList;

    /**
     * 已加载成功的视图数量
     */
    private int mLoadedNums = 0;
    /**
     * 实际数据和网络获取的数据 的缩放比例
     */
    private float mZoomScale;
    /**
     * 显示 透视图的 ImageView集合
     */
    public List<ImageView> imageViews = new ArrayList<>();
    /**
     * 横屏下 宽度
     */
    private int mWidth;
    /**
     * 横屏下 高度
     */
    private int mHeight;

    public void setmOnAllImageLoadedListener(OnAllImageLoadedListener mOnAllImageLoadedListener) {
        this.mOnAllImageLoadedListener = mOnAllImageLoadedListener;
    }

    private OnAllImageLoadedListener mOnAllImageLoadedListener;

    public ImageFrameLandscapeLayout(Context context) {
        super(context);
        init(context);
    }

    public ImageFrameLandscapeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化视图
     *
     * @param context
     */
    private void init(Context context) {
        float f = DensityUtil.getDisplayHeightDp(context);
        mHeight = (int) f;
        mWidth = (int) (f * Constant.SET_SCALE);
        mZoomScale = Constant.DEFAULT_HEIGHT / mHeight;
        setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));

    }

    public List<SetModel.DataBean.SetBean.ListBean> getmList() {
        return mList;
    }


    /**
     * 设置数据
     *
     * @param mList
     */
    public void setmList(List<SetModel.DataBean.SetBean.ListBean> mList) {
        Comparator comp = new SortComparator();
        Collections.sort(mList, comp);
        this.mList = mList;

        initChildView();
    }

    public void setBottomView(String image_blueprint) {
        if (!StringUtil.isEmpty(image_blueprint)) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(image_blueprint).into(imageView);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mWidth,
                    mHeight);//定义框架布局器参数
            addView(imageView, params);
        }
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
     * 添加每一层视图
     */
    private void addImgView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViews.add(imageView);
        addView(imageView);
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
        para.height = bitmapH;
        para.width = bitmapW;
        LayoutParams params = new LayoutParams(para);
        SetModel.DataBean.SetBean.ListBean.ItemInfoBean.PosBean posBean = bean.getPos();
        int x = posBean.getX();
        int y = posBean.getY();
        String parentZorder = mList.get(index).getParent_zorder();
        if (!parentZorder.equals("0")) {
            int pos[] = getTruePos(parentZorder, x, y);
            x = pos[0];
            y = pos[1];
        }
        x = (int) (x / mZoomScale);
        y = (int) (y / mZoomScale);
        int left = (int) (x - bitmapW * bean.getAnchor().getX());
        int top = (int) (y - bitmapH * bean.getAnchor().getY());
        params.setMargins(left, top, 0, 0);
        imageView.setLayoutParams(params);
    }

    ImageLoader imageLoader = ImageLoader.getInstance();

    /**
     * 设置图片地址
     *
     * @param index
     * @param url
     */
    private void resetImg(final int index, final String url) {
        setImageViewPos(index);
        if (mList.get(index).getItem_info().getFlip() == 1) { //镜像反转图
            imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    loadedImage = BitmapUtil.convert(loadedImage);
                    imageViews.get(index).setImageBitmap(loadedImage);
                    isAllLoaded();
                }
            });
        } else {
            //无镜像反转图
            if(!StringUtil.isEmpty(url)){
                Picasso.with(getContext()).load(url).into(imageViews.get(index), new Callback() {
                    @Override
                    public void onSuccess() {
                        isAllLoaded();

                    }

                    @Override
                    public void onError() {
                        isAllLoaded();
                    }
                });
            }
        }
    }


    /**
     * 是否全部加载完，如果是 通知activity 获取bitmap 用于缩放
     */
    private void isAllLoaded() {
        mLoadedNums = mLoadedNums + 1;
        if (mLoadedNums == mList.size()) {
            if (null != mOnAllImageLoadedListener)
                mOnAllImageLoadedListener.onAllImageLoaded();

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

        if (ImageFrameLayout.isReverse == true) {
            canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);
        } else {
            ImageFrameLayout.isReverse = false;
        }
        super.dispatchDraw(canvas);
    }

    public interface OnAllImageLoadedListener {
        void onAllImageLoaded();
    }
}
