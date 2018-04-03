package com.jajahome.diyview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 *  图像镜像操作
 */
public class BitmapUtil {

	/**
	 * 镜像水平翻转 大小不变
	 *
	 * @param a ：要反转的bitmap
	 * @return 已经反转的bitmap
	 */
	public static Bitmap convert(Bitmap a) {
		int w = a.getWidth();
		int h = a.getHeight();
		Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		Matrix m = new Matrix();
		m.postScale(-1, 1);   //镜像水平翻转
		Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
		cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, w, h), null);
		a.recycle();
		return newb;
	}
}
