package com.jajahome.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.jajahome.constant.Constant;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.user.activity.AboutUsAct;
import com.jajahome.feature.view.LoginDialog;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ApkDownload {
    private boolean isContextAlive=true;
    private int mLocalVersion = 1;  //本地版本
    private int mServerVersion = 2; //服务器版本
    private String mUserVersion;
    private Context mContext;
    private String uri;
    private String Tag;

    public ApkDownload(Context mContext, String Tag) {
        this.mContext = mContext;
        this.Tag = Tag;
    }


      /*版本更新*/

    private Handler mHandler = new Handler() {
        //Handler接收到相应消息进行刷新ui等操作
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //收到消息，在此进行ui相关操作，如将解析的内容显示出来。
                getLocal();

                if (Tag.equals(MainAty.Tag)) {
                    if (getServerVersion() < mServerVersion) {
                        if(mContext!=null&&isContextAlive){
                            upGradeDialog();
                        }
                    }
                } else if (Tag.equals(AboutUsAct.Tag)) {
                    if (mLocalVersion < mServerVersion) {
                        if(mContext!=null&&isContextAlive){
                            upGradeDialog();
                        }
                    } else {

                        Toast.makeText(mContext, "无最新版本", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    };
    public void check() {
        new Thread() {
            @Override
            public void run() {

                //需要在线程执行的方法
                try {
                    InputStream is = getXml();   //获取xml内容
                    UpdataInfo info = getUpdataInfo(is);  //调用解析方法
                    mServerVersion = info.getVersion();  //获得服务器版本
                    mUserVersion = info.getVersionname();
                    uri = info.getUrl();

                    Log.i("--Apk", mServerVersion + "");
                    Log.i("--Apk", mUserVersion + "");
                    mHandler.sendEmptyMessage(new Message().what = 1);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //给handler发送一个消息

            }
        }.start();
    }


    //获取本地版本方法
    public void getLocal() {
        PackageInfo packageInfo;
        try {
            packageInfo = mContext.getApplicationContext()
                    .getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            mLocalVersion = packageInfo.versionCode;

            Log.i("--Apk", mLocalVersion + "");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public InputStream getXml() throws Exception {
        String TAG = "URLConnect";
        String httpUrl = Constant.UPGRADE;

        HttpURLConnection conn = (HttpURLConnection) new URL(httpUrl).openConnection();
        conn.setReadTimeout(5 * 1000);  //设置连接超时的时间
        conn.connect(); //开始连接
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            return is;   //返回InputStream
        } else {
            Log.e(TAG, "---连接失败---");
        }
        conn.disconnect(); //断开连接
        return null;
    }

    public UpdataInfo getUpdataInfo(InputStream is) throws Exception {
        UpdataInfo info = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源，编码格式
        int event = parser.getEventType();
        Log.i("--ApkEvent", event + "");
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT: //开始解析
                    //可在此做初始化相关工作
                    info = new UpdataInfo();
                    Log.i("UpdatePullParser", "--START_DOCUMENT--");
                    break;
                case XmlPullParser.START_TAG:
                    Log.i("UpdatePullParser", "--START_TAG--");
                    String tag = parser.getName();
                    if ("version".equals(tag)) {
                        info.setVersion(new Integer(parser.nextText())); //获取版本号
                    } else if ("url".equals(tag)) {
                        info.setUrl(parser.nextText()); //获取url地址
                    } else if ("versionname".equals(tag)) {
                        info.setVersionname(parser.nextText());
                    }

                    break;
                case XmlPullParser.END_TAG://读完一个元素，如有多个元素，存入容器中
                    break;
                default:
                    break;
            }
            event = parser.next();
        }
        return info;   //返回一个UpdataInfo实体
    }
    public void setContextAlive(boolean isContextAlive){
        this.isContextAlive=isContextAlive;
    }
    /*检测到新版本弹出的对话框*/

    public void upGradeDialog() {
        LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
        builder.setMessage("检测到新版本，是否更新? \n" + "新版本：" + mUserVersion);
        builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveServerVersion(mServerVersion);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveServerVersion(mServerVersion);
                downLoadApk();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(mContext);
        pd.setMessage("正在下载...请稍候");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    openFile(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    //打开APK程序代码

    private void openFile(File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }


    /*保存當前服務器版本*/
    public void saveServerVersion(int curServerVersion) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SERVERVERSION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt(Constant.SERVERVERSIONDATA, curServerVersion);
        editor.commit();
    }

    public int getServerVersion() {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SERVERVERSION, mContext.MODE_PRIVATE);
        return preferences.getInt(Constant.SERVERVERSIONDATA, mLocalVersion);
    }


}
