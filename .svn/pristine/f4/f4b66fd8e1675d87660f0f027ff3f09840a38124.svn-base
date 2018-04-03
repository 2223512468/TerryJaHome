package com.wq.photo.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public enum BitmapUtils {

    INSTANCE;
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/cn.bocweb.ch/";

    public String compress(String filePath) {
        FileOutputStream outputStream = null;
        Bitmap bitmap;
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String path = Environment.getExternalStorageDirectory() + "/cn.bocweb.ch/" + getPhotoFileName();
        try {
            outputStream = new FileOutputStream(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = caculateInSampleSize(options, 720, 1080);
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(filePath, options);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {

                return path;
            } else {
                return filePath;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }


    public String compressHalf(String filePath) {
        FileOutputStream outputStream = null;
        Bitmap bitmap;
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String path = Environment.getExternalStorageDirectory() + "/cn.bocweb.hl/" + getPhotoFileName();
        try {
            outputStream = new FileOutputStream(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(filePath, options);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {

                return path;
            } else {
                return filePath;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public String compress(String filePath, int i) {
        FileOutputStream outputStream = null;
        Bitmap bitmap = null;
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String path = Environment.getExternalStorageDirectory() + "/cn.bocweb.hl/" + i + getPhotoFileName();
        try {
            outputStream = new FileOutputStream(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = caculateInSampleSize(options, 480, 800);
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(filePath, options);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                return path;
            } else {
                return filePath;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                     int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }


    /**
     * 用当前时间给取得的图片命名
     */
    public String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public String getPhotoPathFromContentUri(Context context, Uri uri) {
        String photoPath = "";
        if (context == null || uri == null) {
            return photoPath;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if (isExternalStorageDocument(uri)) {
                String[] split = docId.split(":");
                if (split.length >= 2) {
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        photoPath = Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
            } else if (isDownloadsDocument(uri)) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                photoPath = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                String[] split = docId.split(":");
                if (split.length >= 2) {
                    String type = split[0];
                    Uri contentUris = null;
                    if ("image".equals(type)) {
                        contentUris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUris = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{split[1]};
                    photoPath = getDataColumn(context, contentUris, selection, selectionArgs);
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            photoPath = uri.getPath();
        } else {
            photoPath = getDataColumn(context, uri, null, null);
        }

        return photoPath;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir() {
        File dir = new File(ALBUM_PATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹

            dir.delete();// 删除目录本身
        }
    }
}
