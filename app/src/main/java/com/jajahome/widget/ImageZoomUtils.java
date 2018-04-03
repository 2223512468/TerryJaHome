package com.jajahome.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/05,0005.
 */
public class ImageZoomUtils {
    public static Bitmap imgZoom(String uri, int w, int h) {
        // Get the dimensions of the View
        int targetW = w;
        int targetH = h;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        // 该 值设为true那么将不返回实际的bitmap，也不给其分配内存空间这样就避免内存溢出了。但是允许我们查询图片的信息这其中就包括图片大小信息
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        // Math.min求最小值
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        // 设置恰当的inSampleSize可以使BitmapFactory分配更少的空间以消除该错误
        bmOptions.inSampleSize = scaleFactor;
        // 如果inPurgeable设为True的话表示使用BitmapFactory创建的Bitmap,用于存储Pixel的内存空间在系统内存不足时可以被回收
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(uri, bmOptions);
        // bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
        Log.i("--ImageZoom", "width" + bitmap.getWidth() + "---height" + bitmap.getHeight());
        return bitmap;
    }

    /*转化为二进制364位字节码*/
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public static Uri geturi(Context context, android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 图片等比缩放
     */

    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 图片源
        /*Bitmap bm = BitmapFactory.decodeFile(img);*/
        if (null != bm) {
            return zoomImgL(bm, newWidth, newHeight);
        }
        return null;
    }

    public static Bitmap zoomImg(Context context, String img, int newWidth, int newHeight) {
        // 图片源
        try {
            Bitmap bm = BitmapFactory.decodeStream(context.getAssets()
                    .open(img));
            if (null != bm) {
                return zoomImg(bm, newWidth, newHeight);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // 缩放图片
    public static Bitmap zoomImgL(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


}
