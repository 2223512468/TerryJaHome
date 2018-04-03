package com.jajahome.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jajahome.R;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.model.ShowDetailModel;
import com.jajahome.util.DensityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
/**
 * 秀家中带有重力感应的标签
 */
public class ShowTabLayout extends FrameLayout {
	List<ShowDetailModel.DataBean.ShowBean.ListBean> mList;//标签数据集合
	private float mZoomScale; //初始缩放比例
	private List<ImageView> imageViews = new ArrayList<>();//标签view集合
	private ImageView mImageView;//显示图片的imageview
	private int mWidth;//视图宽
	private int mTabHeight;
	private int mTabWeight;

	public void setFinishLoadedListener(FinishLoadedListener finishLoadedListener) {
		this.finishLoadedListener = finishLoadedListener;
	}

	/**
	 * 图片显示完回掉
	 */
	private FinishLoadedListener finishLoadedListener;

	public ShowTabLayout(Context context) {
		super(context);
	}

	public ShowTabLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setData(final ShowDetailModel.DataBean.ShowBean showBean){
		mWidth = (int) DensityUtil.getDisplayWidthDp(getContext());//屏幕宽度
		mZoomScale=(Float.valueOf(showBean.getWidth()))/mWidth;
		//设置FrameLayout大小
		LayoutParams params=new LayoutParams(mWidth, (int)(Float.valueOf(showBean.getHeight())/mZoomScale));
		params.gravity= Gravity.CENTER;
		setLayoutParams(params);
		//添加秀家图片
		mImageView=new ImageView(getContext());
		addView(mImageView);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.loadImage(showBean.getPreview().getUrl(),new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				//loadedImage = BitmapUtil.convert(loadedImage);
				mImageView.setImageBitmap(loadedImage);
				//图片显示后放大图片
				if(finishLoadedListener!=null){
					finishLoadedListener.onFinishLoaded();
				}
				//设置图片上的标签
				List<ShowDetailModel.DataBean.ShowBean.ListBean>listTabs=showBean.getList();
				if(listTabs!=null&&listTabs.size()>0){
					mTabHeight=DensityUtil.dip2px(getContext(),30);
					mTabWeight=DensityUtil.dip2px(getContext(),18);
					for(ShowDetailModel.DataBean.ShowBean.ListBean listBean:listTabs){
						setImageTab(listBean);
					}
				}
			}
		} );

	}

	/**
	 * 设置ImageView 大小位置
	 */
	private void setImageTab(final ShowDetailModel.DataBean.ShowBean.ListBean listBean){
		ImageView imageView = new ImageView(getContext());
		addView(imageView);
		ViewGroup.LayoutParams para;
		para = imageView.getLayoutParams();
		para.height=mTabHeight;
		para.width=mTabWeight;
		LayoutParams params = new LayoutParams(para);
		int x = (int) (listBean.getPos().getX()/mZoomScale);
		int y = (int) (listBean.getPos().getY()/mZoomScale);
		params.setMargins(x, y, 0, 0);
		imageView.setImageResource(R.mipmap.ic_tab);
		imageView.setLayoutParams(params);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onTabClicked(listBean);
			}
		});
		imageViews.add(imageView);
	}

	/**
	 * 标签被点击
	 * @param listBean ：标签数据
	 */
	private void onTabClicked(ShowDetailModel.DataBean.ShowBean.ListBean listBean) {
		if(listBean.getAction().equals("item")){
			Intent intent = new Intent(getContext(), ItemDetailAct.class);
			intent.putExtra(ItemDetailAct.ITEM_ID,listBean.getAction_id());
			getContext().startActivity(intent);
		}
	}


	/**
	 * 标签手机旋转角度旋转
	 * @param v
	 */
	private float fromRotate;
	public void rotateTabs(float v) {
		//设置旋转动画
		RotateAnimation anim=new RotateAnimation(fromRotate,v,mTabWeight/2,0);
		anim.setDuration(100);
		anim.setFillAfter(true);
		fromRotate=v;
		if(imageViews!=null&&imageViews.size()>0){
			for (int i=0;i<imageViews.size();i++){
				imageViews.get(i).startAnimation(anim);
			}
		}
	}

	/**
	 * 图片加载完成
	 */
	public interface FinishLoadedListener{
		void onFinishLoaded();
	}

}
