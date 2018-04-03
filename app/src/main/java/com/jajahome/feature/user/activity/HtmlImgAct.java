package com.jajahome.feature.user.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.WindowManager;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.util.DensityUtil;
import com.jajahome.widget.PinchDiyImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class HtmlImgAct extends BaseActivity {

    public static String HTMLIMAG = "HTMLIMAG";
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @BindView(R.id.pic)
    PinchDiyImageView imageViews;

    @Override
    protected int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_html_img;
    }

    @Override
    protected void initEvent() {
        String url = getIntent().getStringExtra(HTMLIMAG);
        imageLoader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageViews.setImageBitmap(loadedImage);
              /*  float height = DensityUtil.getDisplayHeightDp(HtmlImgAct.this);
                float width = DensityUtil.getDisplayWidthDp(HtmlImgAct.this);
                imageViews.scaleImg(width / 2, height / 2);*/
            }
        });
    }

    public Bitmap big(Bitmap b) {
        float height = DensityUtil.getDisplayHeightDp(HtmlImgAct.this);
        float width = DensityUtil.getDisplayWidthDp(HtmlImgAct.this);
        int w = b.getWidth();
        int h = b.getHeight();
        float sy = (float) height / h;//要强制转换，不转换我的在这总是死掉。
        float sx = (float) width / w;
        sx = sy;
        sy = (sy / sx) * sy;

        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy); // 长和宽放大缩小的比例

        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
        return resizeBmp;
    }
}

