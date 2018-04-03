package com.jajahome.feature;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.widget.PinchImageView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 显示大图的activity
 */
public class PagerActivity extends BaseActivity {
    public static final String DATA = "DATA";
    public static final String INDEX = "INDEX";
   // @BindView(R.id.root_view)
   // ImageView rootView;

    /**
     * 网络图片地址集合
     */
    private List<String> mList;
    /**
     * 当前位置
     */
    private int mIndex;

    /**
     * PinchImageView的缓存视图
     */
    LinkedList<PinchImageView> viewCache = new LinkedList<>();
    @BindView(R.id.pager)
    PinchImageViewPager pager;
    @Override
    protected int getViewId() {
        return R.layout.activity_pager;
    }

    @Override
    protected void initEvent() {
        mList = getIntent().getStringArrayListExtra(DATA);
        mIndex = getIntent().getIntExtra(INDEX, 0);
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList == null ? 0 : mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PinchImageView piv;
                if (viewCache.size() > 0) {
                    piv = viewCache.remove();
                    piv.reset();
                } else {
                    piv = new PinchImageView(PagerActivity.this);
                }
                Picasso.with(mContext).load(mList.get(position)).into(piv);
                container.addView(piv);
                return piv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                container.removeView(piv);
                viewCache.add(piv);
            }

        });
        pager.setCurrentItem(mIndex);

        /*rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }
}