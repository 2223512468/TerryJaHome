package com.jajahome.feature.zxing;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.jajahome.R;
import com.jajahome.feature.TencentWebV;
import com.jajahome.feature.WebAct;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.view.LoginKnownDialog;
import com.jajahome.feature.zxing.camera.CameraManager;
import com.jajahome.feature.zxing.decoding.CaptureActivityHandler;
import com.jajahome.feature.zxing.decoding.InactivityTimer;
import com.jajahome.feature.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ImageView scanOff;
    private LinearLayout scanOn;
    private TextView scanText;
    private String JaJaHomeUri = "jajahome";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        CameraManager.init(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        ImageButton mButtonBack = (ImageButton) findViewById(R.id.button_back);
        scanOff = (ImageView) findViewById(R.id.scan_off);
        scanOn = (LinearLayout) findViewById(R.id.scan_on);
        scanText = (TextView) findViewById(R.id.text_on);

        mButtonBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MipcaActivityCapture.this.finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        goon();
    }

    private void goon() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            try {
                initCamera(surfaceHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();

    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * ?????????
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        final String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else if (resultString.contains("item_id") || resultString.contains("sid") || resultString.contains("show_id")) {
            String ShopTag[] = resultString.split("[?]");
            String Tag = ShopTag[1];
            String infoTag[] = Tag.split("=");
            String pageTag = infoTag[0];
            String pageId = infoTag[1];
            Intent intent = new Intent();
            if (pageTag.equals("item_id")) {
                intent.setClass(this, ItemDetailAct.class);
                intent.putExtra(ItemDetailAct.ITEM_ID, pageId);
            } else if (pageTag.equals("sid")) {
                intent.setClass(this, SetDetailAct.class);
                intent.putExtra(SetDetailAct.SET_ID, pageId);
            } else if (pageTag.equals("show_id")) {
                WebAct.gotoH5(this, resultString);
                return;
            }
            startActivity(intent);
            finish();
        } else if (resultString.contains(JaJaHomeUri)) {
            /*WebAct.gotoH5(MipcaActivityCapture.this, resultString);*/
            TencentWebV.gotoH5(MipcaActivityCapture.this, resultString);
            finish();
        } else {
            LoginKnownDialog.Builder builder = new LoginKnownDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("该链接将跳转到外部页面，可能存在风险" + resultString);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (handler != null) {
                        handler.quitSynchronously();
                        handler = null;
                    }
                    CameraManager.get().closeDriver();
                    goon();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WebAct.gotoH5(MipcaActivityCapture.this, resultString);
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder, scanText, scanOn, scanOff);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            try {
                initCamera(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}