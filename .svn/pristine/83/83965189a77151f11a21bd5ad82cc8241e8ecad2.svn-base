package com.jajahome.feature.user.adapter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ImageZoomUtils;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

/**
 * Created by lhz on 2016/8/2.
 */
public class DesignApplySelectPhotoAdapter extends BaseAdapter {
    private Context mContext;

    private int mIndex;//在选择的index
    private String mTitle[];
    private int[] image = new int[]{R.mipmap.jajahome_use};
    private ImageView imageView;
    private Bitmap bitmap, localBit, waterBit;

    public String[] getmUrls() {
        return mUrls;
    }

    private String mUrls[];
    /**
     * 读取本地权限
     */
    public static final int PERMISSON_STORGE = 0X13;

    public DesignApplySelectPhotoAdapter(Context mContext) {
        this.mContext = mContext;
        mTitle = mContext.getResources().getStringArray(R.array.designer_commit);
        mUrls = new String[mTitle.length];
        imageView = new ImageView(mContext);
        imageView.setImageResource(image[0]);
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.list_designer_apply_select_photo, null);
            holder = new ViewHolder();
            holder.imgViewAdd = (ImageView) convertView.findViewById(R.id.img_added);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.viewAdd = convertView.findViewById(R.id.view_add);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (StringUtil.isEmpty(mUrls[position])) {
            holder.imgViewAdd.setVisibility(View.GONE);
        } else {
            holder.imgViewAdd.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("file:///" + mUrls[position]);
            localBit = getBitmapFromUri(uri);
            localBit = comp(localBit,mUrls[position]);

            waterBit = createWaterMaskBitmap(localBit, bitmap, 0, 0);
            holder.imgViewAdd.setImageBitmap(waterBit);
        }
        holder.imgViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex = position;
                checkPermission();
            }
        });
        holder.viewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex = position;
                checkPermission();
            }
        });
        holder.tvTitle.setText(mTitle[position]);
        return convertView;
    }

    public void setPath(String path) {
        mUrls[mIndex] = path;
        notifyDataSetChanged();
    }

    public Bitmap comp(Bitmap bitmap,String uri) {

        Bitmap bit = null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        if (w > h&&w>500) {
            w = 500;
            float scale = bitmap.getWidth() / 500;
            h = (int) (bitmap.getHeight() / scale);
            bit = ImageZoomUtils.imgZoom(uri,w,h);

        } else if(w<=h&&h>500){
            h = 500;
            float scale = bitmap.getHeight() / 500;
            w = (int) (bitmap.getWidth() / scale);
            bit = ImageZoomUtils.imgZoom(uri,w,h);
        }else{
            bit = bitmap;
        }

        return bit;
    }

    class ViewHolder {
        ImageView imgViewAdd;
        TextView tvTitle;
        View viewAdd;
    }

    /**
     * 获取读取内存卡，和 拍照的权限 Android 6.0 以后
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //应用还未获取读取本地文件 的权限，询问是否允许
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, DesignApplySelectPhotoAdapter.PERMISSON_STORGE);
        } else {
            selectPhoto();
        }
    }

    /**
     * 获取图片的Bitmap
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }


    /*添加水印代碼*/
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        if(width<height){
            src = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }

        //创建一个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newBitmap);
        //在画布 0，0坐标上开始绘制原始图片

        canvas.drawBitmap(src, 0, 0, null);

        //在画布上绘制水印图片
        //canvas.drawBitmap(watermark,paddingLeft, paddingTop, null);
        if(watermark!=null){

            int waterWidth = watermark.getWidth();
            int waterHeight = watermark.getHeight();
            Rect srcc = new Rect();// 图片
            Rect dst = new Rect();// 屏幕位置及尺寸

            srcc.left = 0; // 0,0
            srcc.top = 0;
            srcc.right = waterWidth;// 是桌面图的宽度，
            srcc.bottom = waterHeight;// 是桌面图的高度

            dst.left = 0; // 绘图的起点X位置
            dst.top = 0; // 相当于 桌面图片绘画起点的Y坐标
            dst.right = waterWidth + width - 60; // 表示需绘画的图片的右上角
            dst.bottom = waterHeight+height-60; // 表示需绘画的图片的右下
            canvas.drawBitmap(watermark, srcc, dst, null);// 在src的右下角画入水印
        }

        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newBitmap;
    }


    /**
     * 选择图片
     */
    public void selectPhoto() {
        int chose_mode = PickConfig.MODE_SINGLE_PICK;
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        new PickConfig.Builder((Activity) mContext)
                .actionBarcolor(Color.parseColor("#FF741A"))
                .statusBarcolor(Color.parseColor("#FF741A"))
                .isneedcamera(true)
                .isneedcrop(false)
                .isSqureCrop(false)
                //.cropOutX(500)
                //.cropOutY(500)
                .setUropOptions(options)
                .maxPickSize(1)
                .spanCount(3)
                .pickMode(chose_mode).build();
    }
}
