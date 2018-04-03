package com.jajahome.feature.set;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.jajahome.R;
import com.jajahome.diyview.ImageDiyFrameLandscapeLayout;
import com.jajahome.model.SetModel;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.PinchDiyImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * 大图套装act
 */
public class SetDiyLandscapeAct extends Activity implements ImageDiyFrameLandscapeLayout.OnAllImageLoadedListener {
    public static final String BITMAP = "BITMAP";
    public static final String MAP = "MAP";
    public static final String BOTTOMURI = "BOTTOMURI";
    ImageDiyFrameLandscapeLayout imgFrameLayout;
    PinchDiyImageView imageView; //可缩放的imageview
    Subscription mSubscription;
    Bitmap bmp;
    private boolean isStop = false;
    private String image_blueprint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_diy_landscape);
        imgFrameLayout = (ImageDiyFrameLandscapeLayout) findViewById(R.id.img_frame_layout);
        imageView = (PinchDiyImageView) findViewById(R.id.image_view);
        imgFrameLayout.setmOnAllImageLoadedListener(this);
        initEvent();
    }

    protected void initEvent() {
        List<SetModel.DataBean.SetBean.ListBean> list = (List<SetModel.DataBean.SetBean.ListBean>) getIntent().getSerializableExtra(BITMAP);
        image_blueprint = getIntent().getStringExtra(BOTTOMURI);

        if (!StringUtil.isEmpty(image_blueprint)) {
            imgFrameLayout.setBottomView(image_blueprint);
        }
        imgFrameLayout.setmList(list);

        HashMap<Integer, Boolean> map = (HashMap<Integer, Boolean>) getIntent().getSerializableExtra(MAP);

        for (Map.Entry entry : map.entrySet()) {
            if ((Boolean) entry.getValue()) {
                if (imgFrameLayout.imageViews.size() > (int) entry.getKey()) {
                    imgFrameLayout.imageViews.get((int) entry.getKey()).setVisibility(View.VISIBLE);
                }
            } else {
                if (imgFrameLayout.imageViews.size() > (int) entry.getKey()) {
                    imgFrameLayout.imageViews.get((int) entry.getKey()).setVisibility(View.GONE);
                }
            }
        }

    }


    /**
     * 所有透视图加载完毕 ，回调 获取屏幕bitmap 这样可以进行手势缩放
     */
    @Override
    public void onAllImageLoaded() {
        mSubscription = Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                setBitmap();
                return null;
            }
        }).subscribe();
    }

    /**
     * 销毁数据
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁数据
        isStop = true;
        if (mSubscription != null) mSubscription.unsubscribe();
        imgFrameLayout.setmOnAllImageLoadedListener(null);
        if (bmp != null) {
            bmp.recycle();
            bmp = null;
        }
    }

    private void setBitmap() {
        if (isStop) return;//界面销毁后，不执行下方方法
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels;
        int h = outMetrics.heightPixels;
        //2.获取屏幕
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);

        View view = getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bitCache = view.getDrawingCache();
        if (bitCache.getHeight() >= h) {
            bmp = Bitmap.createBitmap(bitCache, 0, 0, w, h);
            imageView.setImageBitmap(bmp);
            imgFrameLayout.setVisibility(View.GONE);
          /*  bitCache.recycle();
            bmp.recycle();*/
        }
        // 销毁缓存信息
        view.destroyDrawingCache();
    }
}
