package toolbox.ll.com.toolbox.ui.live;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.effect.Effect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.roomBusiness.JoinRoomEvent;
import com.example.businessmodule.utils.EventId;
import com.netease.LSMediaCapture.Statistics;
import com.netease.LSMediaCapture.lsAudioCaptureCallback;
import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.LSMediaCapture.video.VideoCallback;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.NotificationType;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AUDIO;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.VIDEO;


import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.common.widget.CircleImageView;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.LiveMenuBean;
import toolbox.ll.com.toolbox.bean.LiveStreamingBean;
import toolbox.ll.com.toolbox.core.inject.BarrageAttachment;
import toolbox.ll.com.toolbox.core.inject.GiftAttachment;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;
import toolbox.ll.com.toolbox.ui.widget.MixAudioDialog;
import toolbox.ll.com.toolbox.ui.widget.NetWorkInfoDialog;


//由于直播推流的URL地址较长，可以直接在代码中的mliveStreamingURL设置直播推流的URL
public class LiveStreamingActivity extends BaseActivity implements  lsMessageHandler {

    private static final String TAG = "LiveStreamingActivity";
    //Demo控件
    private View filterLayout;
    private ImageView startPauseResumeBtn;
    private TextView mFpsView;
    private final int MSG_FPS = 1000;
    private String mliveStreamingURL = null;
    private String mMixAudioFilePath = null;
    private File mMP3AppFileDirectory = null;
    private Handler mHandler;
    //状态变量
    private boolean m_liveStreamingOn = false;
    private boolean m_liveStreamingInitFinished = false;
    private boolean m_tryToStopLivestreaming = false;
    private boolean m_startVideoCamera = false;
    private Intent mIntentLiveStreamingStopFinished = new Intent("LiveStreamingStopFinished");
    //伴音相关
    private AudioManager mAudioManager;
    private Intent mNetInfoIntent = new Intent(NetWorkInfoDialog.NETINFO_ACTION);
    private long mLastVideoProcessErrorAlertTime = 0;
    private long mLastAudioProcessErrorAlertTime = 0;
    //视频截图相关变量
    private String mScreenShotFilePath = "/sdcard/";//视频截图文件路径
    private String mScreenShotFileName = "test.jpg";//视频截图文件名
    //视频缩放相关变量
    private int mMaxZoomValue = 0;
    private int mCurrentZoomValue = 0;
    private float mCurrentDistance;
    private float mLastDistance = -1;
    //Demo广播相关变量
    private MsgReceiver msgReceiver;
    private audioMixVolumeMsgReceiver audioMixVolumeMsgReceiver;


    /** SDK 相关参数 **/
    private boolean mUseFilter;
    private boolean mNeedWater = false;
    private boolean mNeedGraffiti = false;
    private lsMediaCapture mLSMediaCapture = null;
    private lsMediaCapture.LiveStreamingPara mLiveStreamingPara;

    private boolean mVideoCallback = false; //是否对相机采集的数据进行回调（用户在这里可以进行自定义滤镜等）
    private boolean mAudioCallback = false; //是否对麦克风采集的数据进行回调（用户在这里可以进行自定义降噪等）

    //第三方滤镜
    private Effect mSenseEffect; //商汤的滤镜
    private  LiveStreamingBean mLSBean;

    private Toast mToast;
    private void showToast(final String text){
        if(mToast == null){
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        if(Thread.currentThread() != Looper.getMainLooper().getThread()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        }else {
            mToast.setText(text);
            mToast.show();
        }
    }



     private  void initLiveSream(Bundle savedInstanceState) {
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);

        //从直播设置页面获取推流URL和分辨率信息

        mliveStreamingURL = mLSBean.getPushUrl();
        mUseFilter = mLSBean.isUseFilter() & !mVideoCallback;
        mNeedWater = mLSBean.isWatermark();
        mNeedGraffiti = mLSBean.isGraffitiOn();

        m_liveStreamingOn = false;
        m_tryToStopLivestreaming = false;

        //以下为SDK调用主要步骤，请用户参考使用
        //1、创建直播实例
        lsMediaCapture.LsMediaCapturePara lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
        lsMediaCapturePara.setContext(getApplicationContext()); //设置SDK上下文（建议使用ApplicationContext）
        lsMediaCapturePara.setMessageHandler(this); //设置SDK消息回调
        lsMediaCapturePara.setLogLevel(lsLogUtil.LogLevel.INFO); //日志级别
        lsMediaCapturePara.setUploadLog(mLSBean.isUploadLog());//是否上传SDK日志
        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara);

        //2、设置直播参数
        mLiveStreamingPara = new lsMediaCapture.LiveStreamingPara();
        mLiveStreamingPara.setStreamType(mLSBean.getStreamType()); // 推流类型 AV、AUDIO、VIDEO
        mLiveStreamingPara.setFormatType(mLSBean.getFormatType()); // 推流格式 RTMP、MP4、RTMP_AND_MP4
        mLiveStreamingPara.setRecordPath(mLSBean.getRecordPath());//formatType 为 MP4 或 RTMP_AND_MP4 时有效
        mLiveStreamingPara.setQosOn(mLSBean.isQosEnable());
//		mLiveStreamingPara.setSyncTimestamp(true);//网易云透传时间戳，不依赖CDN(必须包含视频流)


        //3、 预览参数设置
        NeteaseView videoView = (NeteaseView) findViewById(R.id.videoview);
        boolean frontCamera = mLSBean.isFrontCamera(); // 是否前置摄像头
        boolean mScale_16x9 = mLSBean.isScale_16x9(); //是否强制16:9
        if(mLSBean.getStreamType() != AUDIO){ //开启预览画面
            lsMediaCapture.VideoQuality videoQuality = mLSBean.getVideoQuality(); //视频模板（SUPER_HIGH 1280*720、SUPER 960*540、HIGH 640*480、MEDIUM 480*360、LOW 352*288）
            mLSMediaCapture.startVideoPreview(videoView,frontCamera,mUseFilter,videoQuality,mScale_16x9);
        }

        m_startVideoCamera = true;
        if(mUseFilter){ //demo中默认设置为干净滤镜
            mLSMediaCapture.setBeautyLevel(5); //磨皮强度为5,共5档，0为关闭
            mLSMediaCapture.setFilterStrength(0.5f); //滤镜强度
            mLSMediaCapture.setFilterType(mLSBean.getFilterType());
        }

        //********** 摄像头采集原始数据回调（非滤镜模式下开发者可以修改该数据，美颜增加滤镜等，推出的流便随之发生变化） *************//
        if(mVideoCallback){
            senseEffect();
            mLSMediaCapture.setCaptureRawDataCB(new VideoCallback() {
                @Override
                /**
                 * 摄像头采集数据回调
                 * @param data 摄像头采集的原始数据(NV21格式)
                 * @param textureId  摄像头采集的纹理ID
                 * @param width 视频宽
                 * @param height 视频高
                 * @param orientation 相机采集角度
                 * @return 滤镜后的纹理ID (<=0 表示没有进行滤镜或是滤镜库返回的是buffer数据(NV21格式)，sdk将会使用buffer数据进行后续处理)
                 */
                public int onVideoCapture(byte[] data, int textureId, int width, int height, int orientation) {
                    if(mSenseEffect != null){
                        //返回纹理方式
//                        return mSenseEffect.effect(textureId,data,width,height,orientation);
                         mSenseEffect.apply(textureId,width,height,orientation);
                        return 0;
                    }else {
                        //返回buffer方式
                        for(int j = 0; j< width * height /4;j++){
                            data[j] = 0; //data必须还是原来的NV21格式
                        }
                        return 0;
                    }
                }
            });
        }


        //********** 麦克风采集原始数据回调（开发者可以修改该数据，进行降噪、回音消除等，推出的流便随之发生变化） *************//
        if(mAudioCallback){
            mLSMediaCapture.setAudioRawDataCB(new lsAudioCaptureCallback() {
                int i = 0;
                @Override
                public void onAudioCapture(byte[] data, int len) {
                    // 这里将data直接修改，SDK根据修改后的data数据直接推流
                    if(i % 10 == 0){
                        for(int j = 0; j< 1000;j++){
                            data[j] = 0;
                        }
                    }
                    i++;
                }
            });
        }

//		try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("appkey","1111");
//            jsonObject.put("uid","2222");
//            mLSMediaCapture.updateCustomStatistics(jsonObject);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        //4、发送统计数据到网络信息界面（Demo层实现，用户不需要添加该操作）
        staticsHandle();
        if(mLSBean.getStreamType() != AUDIO){
            //显示本地绘制帧率 (测试用)
            mHandler.sendEmptyMessageDelayed(MSG_FPS,1000);
        }



        //伴音相关操作，获取设备音频播放service
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //拷贝MP3文件到APP目录
        handleMP3();
        //动态注册广播接收器，接收Service的消息
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("AudioMix");
        registerReceiver(msgReceiver, intentFilter);

        //动态注册广播接收器，接收Service的消息
        audioMixVolumeMsgReceiver = new audioMixVolumeMsgReceiver();
        IntentFilter audioMixVolumeIntentFilter = new IntentFilter();
        audioMixVolumeIntentFilter.addAction("AudioMixVolume");
        registerReceiver(audioMixVolumeMsgReceiver, audioMixVolumeIntentFilter);
         //5、Demo控件的初始化（Demo层实现，用户不需要添加该操作）
         buttonInit();

    }

    //开始直播
    private boolean startAV(){
        //6、初始化直播
        m_liveStreamingInitFinished = mLSMediaCapture.initLiveStream(mLiveStreamingPara,mliveStreamingURL);
        if(mLSMediaCapture != null && m_liveStreamingInitFinished) {
            //7、开始直播
            mLSMediaCapture.startLiveStreaming();
            m_liveStreamingOn = true;

            if(mNeedWater){
                //8、设置视频水印参数（可选）
                addWaterMark();
                //9、设置视频动态水印参数（可选）
                addDynamicWaterMark();
            }
            if(mNeedGraffiti){
                //10、设置视频涂鸦参数（可选）
                addGraffiti();
            }
            return true;
        }
        return m_liveStreamingInitFinished;
    }

    private void stopAV(){
        mGraffitiOn = false;
        if(mGraffitiThread != null){
            try {
                mGraffitiThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(mLSMediaCapture != null){
            mLSMediaCapture.stopLiveStreaming();
        }
    }



    @Override
    protected void onPause(){
        Log.i(TAG,"Activity onPause");
        if(mLSMediaCapture != null) {
            if(!m_tryToStopLivestreaming && m_liveStreamingOn)
            {
                if(mLiveStreamingPara.getStreamType() != AUDIO) {
                    //推最后一帧图像
                    mLSMediaCapture.backgroundVideoEncode();
                }
                else {
                    //推静音帧
                    mLSMediaCapture.backgroundAudioEncode();
                }
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume(){
        Log.i(TAG,"Activity onResume");
        super.onResume();
        if(mLSMediaCapture != null && m_liveStreamingOn) {
            if(mLiveStreamingPara.getStreamType() != AUDIO) {
                //关闭推流固定图像，正常推流
                mLSMediaCapture.resumeVideoEncode();
            }
            else  {
                //关闭推流静音帧
                mLSMediaCapture.resumeAudioEncode();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //切换横竖屏，需要在manifest中设置 android:configChanges="orientation|keyboardHidden|screenSize"
        //防止Activity重新创建而断开推流
        if(mLSMediaCapture != null){
            mLSMediaCapture.onConfigurationChanged();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"activity onDestroy");
        onMyDestory();
        disMissNetworkInfoDialog();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        //伴音相关Receiver取消注册
        unregisterReceiver(msgReceiver);
        unregisterReceiver(audioMixVolumeMsgReceiver);
        //停止直播调用相关API接口
        if(mLSMediaCapture != null && m_liveStreamingOn) {

            //停止直播，释放资源
            stopAV();

            //如果音视频或者单独视频直播，需要关闭视频预览
            if(m_startVideoCamera)
            {
                mLSMediaCapture.stopVideoPreview();
                //消耗第三方滤镜
                releaseSenseEffect();
                mLSMediaCapture.destroyVideoPreview();
            }

            //反初始化推流实例，当它与stopLiveStreaming连续调用时，参数为false
            mLSMediaCapture.uninitLsMediaCapture(false);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 2);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        }
        else if(mLSMediaCapture != null && m_startVideoCamera)
        {
            mLSMediaCapture.stopVideoPreview();
            //消耗第三方滤镜
            releaseSenseEffect();
            mLSMediaCapture.destroyVideoPreview();

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            mLSMediaCapture.uninitLsMediaCapture(true);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        }
        else if(!m_liveStreamingInitFinished) {
            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(mIntentLiveStreamingStopFinished);

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            mLSMediaCapture.uninitLsMediaCapture(true);
        }

        if(m_liveStreamingOn) {
            m_liveStreamingOn = false;
        }

        super.onDestroy();
    }

    //处理伴音MP3文件
    public void handleMP3() {

        AssetManager assetManager = getAssets();

        String[] files = null;
        try {
            files = assetManager.list("mixAudio");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        mMP3AppFileDirectory = getExternalFilesDir(null);
        if(mMP3AppFileDirectory == null)
        {
            mMP3AppFileDirectory = getFilesDir();
        }

        for(String filename : files) {
            try {
                InputStream in = assetManager.open("mixAudio/" + filename);
                File outFile = new File(mMP3AppFileDirectory, filename);
                mMixAudioFilePath = outFile.toString();
                if(!outFile.exists()) {
                    FileOutputStream out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    in.close();
                    out.flush();
                    out.close();
                }
            } catch(IOException e) {
                Log.e("tag", "Failed to copy MP3 file", e);
            }
        }
    }


    //视频云Demo层显示实时音视频信息的操作
    public void staticsHandle() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case MSG_GET_STATICS_INFO:
                        Bundle bundle = msg.getData();
                        int videoFrameRate = bundle.getInt("FR");
                        int videoBitrate = bundle.getInt("VBR");
                        int audioBitrate = bundle.getInt("ABR");
                        int totalRealBitrate = bundle.getInt("TBR");
                        int networkLevel = bundle.getInt("networkLevel");
                        String resolution = bundle.getString("resolution");
                        try {
                            if (mNetInfoIntent != null) {
                                mNetInfoIntent.putExtra("videoFrameRate", videoFrameRate);
                                mNetInfoIntent.putExtra("videoBitRate", videoBitrate);
                                mNetInfoIntent.putExtra("audioBitRate", audioBitrate);
                                mNetInfoIntent.putExtra("totalRealBitrate", totalRealBitrate);
                                mNetInfoIntent.putExtra("networkLevel", networkLevel);
                                mNetInfoIntent.putExtra("resolution", resolution);
                                sendBroadcast(mNetInfoIntent);
                            }
                        } catch (IllegalStateException e) {

                        }
                        break;

                    case MSG_SPEED_CALC_SUCCESS:
                        showToast("测速成功");
                        String txt = (String)msg.obj;
                        if(txt != null && mSpeedResultTxt != null){
                            mSpeedResultTxt.setText(txt);
                        }
                        break;

                    case MSG_SPEED_CALC_FAIL:
                        showToast("测速失败");
                        break;

                    case MSG_FPS:  //本地显示帧率用（用户不需要处理）
                        if(mLSMediaCapture != null){
                            mFpsView.setText("camera size: " + mLSMediaCapture.getCameraWidth() + "x" + mLSMediaCapture.getCameraHeight() +
                                    "\ncamera fps: " + mLSMediaCapture.getCameraFps() +
                                    "\ntarget fps: " + mLSMediaCapture.getDecimatedFps() +
                                    "\nrender fps: " + mLSMediaCapture.getRenderFps());
                            sendEmptyMessageDelayed(MSG_FPS,2000);
                        }
                        break;

                    default:
                        break;
                }

            }
        };

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private boolean mFlashOn = false;
    long clickTime = 0L;
    private boolean mSpeedCalcRunning = false;
    private TextView mSpeedResultTxt = null;
    private Thread mThread;
    //按钮初始化
    public void buttonInit() {

        //网络信息按钮初始化
        View networkInfoBtn = findViewById(R.id.live_net_info);
        networkInfoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showNetworkInfoDialog(v);
            }
        });

        //测速
        mSpeedResultTxt = (TextView) findViewById(R.id.speedResult);
        findViewById(R.id.live_speed_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLSMediaCapture != null) {
                    if (mSpeedCalcRunning) {
                        mLSMediaCapture.stopSpeedCalc();
                        mSpeedCalcRunning = false;
                        showToast("结束测速");
                    } else {
                        showToast("开始测速");
                        mLSMediaCapture.startSpeedCalc(mliveStreamingURL, 1024 * 500);
                        mSpeedCalcRunning = true;
                    }
                }
            }
        });


        //开始直播按钮初始化
        startPauseResumeBtn = (ImageView) findViewById(R.id.live_start_btn);
        startPauseResumeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                long time = System.currentTimeMillis();
                if(time - clickTime < 1000){
                    return;
                }
                clickTime = time;
                startPauseResumeBtn.setClickable(false);
                if(!m_liveStreamingOn)
                {
                    //8、初始化直播推流
                    if(mThread != null){
                        showToast("正在开启直播，请稍后。。。");
                        return;
                    }
                    showToast("初始化中。。。");
                    mThread = new Thread(){
                        public void run(){
                            //正常网络下initLiveStream 1、2s就可完成，当网络很差时initLiveStream可能会消耗5-10s，因此另起线程防止UI卡住
                            if(!startAV()){
                                showToast("直播开启失败，请仔细检查推流地址, 正在退出当前界面。。。");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LiveStreamingActivity.this.finish();
                                    }
                                },5000);
                            }
                            mThread = null;
                        }
                    };
                    mThread.start();
                    startPauseResumeBtn.setImageResource(R.drawable.stop);
                }else {
                    showToast("停止直播中，请稍等。。。");
                    stopAV();
                    startPauseResumeBtn.setImageResource(R.drawable.restart);
                }
            }
        });

        if(mLiveStreamingPara.getStreamType() != AUDIO){
            View change = findViewById(R.id.live_camera_change);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFormat();
                }
            });
        }
        //滤镜
        if(mUseFilter && (mLiveStreamingPara.getStreamType() == AV || mLiveStreamingPara.getStreamType() == VIDEO)) {
            SeekBar filterSeekBar = ((SeekBar) findViewById(R.id.live_filter_seekbar));
            filterSeekBar.setVisibility(View.VISIBLE);
            filterSeekBar.setProgress(50);
            filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mLSMediaCapture != null){
                        float param;
                        param = (float)progress/100;
                        mLSMediaCapture.setFilterStrength(param);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            SeekBar beautySeekBar = ((SeekBar) findViewById(R.id.live_beauty_seekbar));
            beautySeekBar.setVisibility(View.VISIBLE);
            beautySeekBar.setProgress(100);
            beautySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mLSMediaCapture != null){
                        int param;
                        param = progress/20;
                        mLSMediaCapture.setBeautyLevel(param);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            SeekBar SeekbarExposure = (SeekBar) findViewById(R.id.live_Exposure_seekbar);
            SeekbarExposure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mLSMediaCapture != null){
                        int max = mLSMediaCapture.getMaxExposureCompensation();
                        mLSMediaCapture.setExposureCompensation((progress-50) * max /50);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        mFpsView = (TextView) findViewById(R.id.text_fps);

    }



    private NetWorkInfoDialog netWorkInfoDialog;
    private void showNetworkInfoDialog(View view) {
        disMissNetworkInfoDialog();
        netWorkInfoDialog = new NetWorkInfoDialog(this);
        netWorkInfoDialog.showAsDropDown(view);
    }

    private void disMissNetworkInfoDialog(){
        if(netWorkInfoDialog != null && netWorkInfoDialog.isShowing()){
            netWorkInfoDialog.dismiss();
        }
        netWorkInfoDialog = null;
    }

    private void showMixAudioDialog() {
        MixAudioDialog dialog = new MixAudioDialog(this);
        dialog.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
    }

    //切换前后摄像头
    private void switchCamera() {
        if(mLSMediaCapture != null) {
            mLSMediaCapture.switchCamera();
        }
    }

    private void capture(){
        if(mLSMediaCapture != null){
            mLSMediaCapture.enableScreenShot();
        }
    }

    int count = 0;
    private void changeFormat(){
        int index = count % 4;
        count ++ ;
        boolean is16x9 = true;
        switch (index){
            case 0:
                mLSMediaCapture.changeCaptureFormat(lsMediaCapture.VideoQuality.SUPER_HIGH,is16x9);
                break;
            case 1:
                mLSMediaCapture.changeCaptureFormat(lsMediaCapture.VideoQuality.SUPER,is16x9);
                break;
            case 2:
                mLSMediaCapture.changeCaptureFormat(lsMediaCapture.VideoQuality.HIGH,is16x9);
                break;
            case 3:
                mLSMediaCapture.changeCaptureFormat(lsMediaCapture.VideoQuality.MEDIUM,is16x9);
                break;
        }
    }


    /**
     * 部分特殊用户需要自己控制开始录制的时间
     * 	mLiveStreamingPara.setAutoRecord(false);
     *
     * 正常情况下用户不需要关心，只需设置成MP4或RTMP_AND_MP4模式SDK即可
     * 自动在推流开始时开启录制，推流结束时结束录制
     */
    private boolean mRecordOn = false;
    private void channgeRecord(){
        if(mLSMediaCapture == null)
        {
            return;
        }
        if(mRecordOn){
            stopRecord();
        }else {
            startRecord();
        }
        mRecordOn = !mRecordOn;
    }

    private void startRecord(){
        if(mLSMediaCapture != null){
            showToast("开始录制");
            mLSMediaCapture.startRecord("/sdcard/111/" + System.currentTimeMillis() + ".mp4");
        }
    }

    private void stopRecord(){
        if(mLSMediaCapture != null){
            showToast("结束录制");
            mLSMediaCapture.stopRecord();
        }
    }

    private void addWaterMark(){
        if(mLSMediaCapture != null){
            Bitmap water = BitmapFactory.decodeResource(getResources(),R.drawable.water);
            int x = 120;
            int y = 60;
            mLSMediaCapture.setWaterMarkPara(water, VideoEffect.Rect.leftTop,x,y);
        }
    }

    Bitmap[] bitmaps;
    private void addDynamicWaterMark(){
        if(mLSMediaCapture != null){
            int x = 0;
            int y = 0;
            int fps = 1; //水印的帧率
            boolean looped = true; //是否循环
            String[] waters;
            try {
                waters = getAssets().list("dynamicWaterMark");
                bitmaps = new Bitmap[waters.length];
                for(int i = 0; i< waters.length;i++){
                    waters[i] = "dynamicWaterMark/" + waters[i];
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap tmp = BitmapFactory.decodeStream(getAssets().open(waters[i]));
                    bitmaps[i] = tmp;
                }
                mLSMediaCapture.setDynamicWaterMarkPara(bitmaps,VideoEffect.Rect.center,x,y,fps,looped);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread mGraffitiThread;
    private boolean mGraffitiOn = false;
    private void addGraffiti(){
        if(mGraffitiThread != null){
            return;
        }
        mGraffitiOn = true;
        mGraffitiThread = new Thread(){
            @Override
            public void run() {
                int x = 180;
                int y = 180;
                while (mGraffitiOn && bitmaps != null && mLSMediaCapture != null){
                    for(Bitmap bitmap:bitmaps){
                        if(!mGraffitiOn){
                            break;
                        }
                        SystemClock.sleep(1000);
                        if(mLSMediaCapture != null){
                            mLSMediaCapture.setGraffitiPara(bitmap,x,y);
                        }
                    }
                }
            }
        };
        mGraffitiThread.start();
    }

    //商汤滤镜，若要测试商汤滤镜请替换商汤的授权文件，demo中的授权文件可能已过期(或将手机时间修改到2017/9/1之前)
    //demo演示时将美颜的参数写死了，用户自己接入时可以修改effect里面mStBeautifyNative的具体参数
    private void senseEffect(){
//        final boolean check = STLicenseUtils.checkLicense(LiveStreamingActivity.this);
//        if(check){
//            mSenseEffect = new Effect(this.getApplicationContext());
//            mSenseEffect.enableBeautify(true);
//            mSenseEffect.enableFaceAttribute(true);
//            mSenseEffect.enableFilter(true);
//            //copy filter models to sdcard and get file paths
//            ArrayList<FilterItem> mFilterItem = FileUtils.getFilterFiles(this,"filter_portrait");
//            mSenseEffect.setFilterStyle(mFilterItem.get(1).model);
//            mSenseEffect.setFilterStrength(0.5f);
//
//            FileUtils.copyStickerFiles(this, "2D");
////			FileUtils.copyStickerFiles(this, "3D");
////			FileUtils.copyStickerFiles(this, "hand_action");
////			FileUtils.copyStickerFiles(this, "segment");
////			FileUtils.copyStickerFiles(this, "deformation");
////			FileUtils.copyStickerFiles(this, "face_morph");
//            ArrayList<StickerItem> mStickerList = FileUtils.getStickerFiles(this,"2D");
//            mSenseEffect.setSticker(mStickerList.get(1).path);
//        }else {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(), "You should be authorized first!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    private void releaseSenseEffect(){
        //需要在destroyVideoPreview 之前调用
        if(mSenseEffect != null){
            mLSMediaCapture.postOnGLThread(new Runnable() {
                @Override
                public void run() {
                    mSenseEffect.release();
                    mSenseEffect = null;
                }
            });
        }
    }


    //处理SDK抛上来的异常和事件，用户需要在这里监听各种消息，进行相应的处理。
    @Override
    public void handleMessage(int msg, Object object) {
        switch (msg) {
            case MSG_INIT_LIVESTREAMING_OUTFILE_ERROR://初始化直播出错
            case MSG_INIT_LIVESTREAMING_VIDEO_ERROR:
            case MSG_INIT_LIVESTREAMING_AUDIO_ERROR:
            {
                showToast("初始化直播出错，正在退出当前界面");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LiveStreamingActivity.this.finish();
                    }
                },3000);
                break;
            }
            case MSG_START_LIVESTREAMING_ERROR://开始直播出错
            {
                showToast("开始直播出错：" + object);
                break;
            }
            case MSG_STOP_LIVESTREAMING_ERROR://停止直播出错
            {
                if(m_liveStreamingOn)
                {
                    showToast("MSG_STOP_LIVESTREAMING_ERROR  停止直播出错");
                }
                break;
            }
            case MSG_AUDIO_PROCESS_ERROR://音频处理出错
            {
                if(m_liveStreamingOn && System.currentTimeMillis() - mLastAudioProcessErrorAlertTime >= 10000)
                {
                    showToast("音频处理出错");
                    mLastAudioProcessErrorAlertTime = System.currentTimeMillis();
                }

                break;
            }
            case MSG_VIDEO_PROCESS_ERROR://视频处理出错
            {
                if(m_liveStreamingOn && System.currentTimeMillis() - mLastVideoProcessErrorAlertTime >= 10000)
                {
                    showToast("视频处理出错");
                    mLastVideoProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            }
            case MSG_START_PREVIEW_ERROR://视频预览出错，可能是获取不到camera的使用权限
            {
                Log.i(TAG, "test: in handleMessage, MSG_START_PREVIEW_ERROR");
                showToast("无法打开相机，可能没有相关的权限或者自定义分辨率不支持");
                break;
            }
            case MSG_AUDIO_RECORD_ERROR://音频采集出错，获取不到麦克风的使用权限
            {
                showToast("无法开启；录音，可能没有相关的权限");
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_RECORD_ERROR");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LiveStreamingActivity.this.finish();
                    }
                },3000);
                break;
            }
            case MSG_RTMP_URL_ERROR://断网消息
            {
                Log.i(TAG, "test: in handleMessage, MSG_RTMP_URL_ERROR");
                showToast("MSG_RTMP_URL_ERROR，推流已停止,正在退出当前界面");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LiveStreamingActivity.this.finish();
                    }
                },3000);
                break;
            }
            case MSG_URL_NOT_AUTH://直播URL非法，URL格式不符合视频云要求
            {
                showToast("MSG_URL_NOT_AUTH  直播地址不合法");
                break;
            }
            case MSG_SEND_STATICS_LOG_ERROR://发送统计信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_STATICS_LOG_ERROR");
                break;
            }
            case MSG_SEND_HEARTBEAT_LOG_ERROR://发送心跳信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_HEARTBEAT_LOG_ERROR");
                break;
            }
            case MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR://音频采集参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR://音频参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR");
                break;
            }
            case MSG_NEW_AUDIORECORD_INSTANCE_ERROR://音频实例初始化出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_NEW_AUDIORECORD_INSTANCE_ERROR");
                break;
            }
            case MSG_AUDIO_START_RECORDING_ERROR://音频采集出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_START_RECORDING_ERROR");
                break;
            }
            case MSG_QOS_TO_STOP_LIVESTREAMING://网络QoS极差，视频码率档次降到最低
            {
                showToast("MSG_QOS_TO_STOP_LIVESTREAMING");
                Log.i(TAG, "test: in handleMessage, MSG_QOS_TO_STOP_LIVESTREAMING");
                break;
            }
            case MSG_HW_VIDEO_PACKET_ERROR://视频硬件编码出错反馈消息
            {
                break;
            }
            case MSG_WATERMARK_INIT_ERROR://视频水印操作初始化出错
            {
                break;
            }
            case MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR://视频水印图像超出原始视频出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR");
                break;
            }
            case MSG_WATERMARK_PARA_ERROR://视频水印参数设置出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PARA_ERROR");
                break;
            }
            case MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR://camera采集分辨率不支持
            {
                //Log.i(TAG, "test: in handleMessage: MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_CAMERA_NOT_SUPPORT_FLASH:
                showToast("不支持闪光灯");
                break;
            case MSG_START_PREVIEW_FINISHED://camera采集预览完成
            {
                Log.i(TAG, "test: MSG_START_PREVIEW_FINISHED");
                break;
            }
            case MSG_START_LIVESTREAMING_FINISHED://开始直播完成
            {
                Log.i(TAG, "test: MSG_START_LIVESTREAMING_FINISHED");
                showToast("直播开始");
                m_liveStreamingOn = true;
                startPauseResumeBtn.setClickable(true);
                break;
            }
            case MSG_STOP_LIVESTREAMING_FINISHED://停止直播完成
            {
                Log.i(TAG, "test: MSG_STOP_LIVESTREAMING_FINISHED");
                showToast("停止直播已完成");
                m_liveStreamingOn = false;
                startPauseResumeBtn.setClickable(true);
                {
                    mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
                    sendBroadcast(mIntentLiveStreamingStopFinished);
                }

                break;
            }
            case MSG_STOP_VIDEO_CAPTURE_FINISHED:
            {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_VIDEO_CAPTURE_FINISHED");
                break;
            }
            case MSG_STOP_AUDIO_CAPTURE_FINISHED:
            {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_AUDIO_CAPTURE_FINISHED");
                break;
            }
            case MSG_SWITCH_CAMERA_FINISHED://切换摄像头完成
            {
                showToast("相机切换成功");
                break;
            }
            case MSG_SEND_STATICS_LOG_FINISHED://发送统计信息完成
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_STATICS_LOG_FINISHED");
                break;
            }
            case MSG_SERVER_COMMAND_STOP_LIVESTREAMING://服务器下发停止直播的消息反馈，暂时不使用
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SERVER_COMMAND_STOP_LIVESTREAMING");
                break;
            }
            case MSG_GET_STATICS_INFO://获取统计信息的反馈消息
            {


                Message message = Message.obtain(mHandler, MSG_GET_STATICS_INFO);
                Statistics statistics = (Statistics) object;

                Bundle bundle = new Bundle();
                bundle.putInt("FR", statistics.videoEncodeFrameRate);
                bundle.putInt("VBR", statistics.videoRealSendBitRate);
                bundle.putInt("ABR", statistics.audioRealSendBitRate);
                bundle.putInt("TBR", statistics.totalRealSendBitRate);
                bundle.putInt("networkLevel", statistics.networkLevel);
                bundle.putString("resolution", statistics.videoEncodeWidth + " x " + statistics.videoEncodeHeight);
                message.setData(bundle);
//				  Log.i(TAG, "test: audio : " + statistics.audioEncodeBitRate + "  video: " + statistics.videoEncodeBitRate + "  total: " + statistics.totalRealSendBitRate);

                if(mHandler != null) {
                    mHandler.sendMessage(message);
                }
                break;
            }
            case MSG_BAD_NETWORK_DETECT://如果连续一段时间（10s）实际推流数据为0，会反馈这个错误消息
            {
                showToast("MSG_BAD_NETWORK_DETECT");
                //Log.i(TAG, "test: in handleMessage, MSG_BAD_NETWORK_DETECT");
                break;
            }
            case MSG_SCREENSHOT_FINISHED://视频截图完成后的消息反馈
            {
                getScreenShotByteBuffer((Bitmap) object);

                break;
            }
            case MSG_SET_CAMERA_ID_ERROR://设置camera出错（对于只有一个摄像头的设备，如果调用了不存在的摄像头，会反馈这个错误消息）
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SET_CAMERA_ID_ERROR");
                break;
            }
            case MSG_SET_GRAFFITI_ERROR://设置涂鸦出错消息反馈
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SET_GRAFFITI_ERROR");
                break;
            }
            case MSG_MIX_AUDIO_FINISHED://伴音一首MP3歌曲结束后的反馈
            {
                //Log.i(TAG, "test: in handleMessage, MSG_MIX_AUDIO_FINISHED");
                break;
            }
            case MSG_URL_FORMAT_NOT_RIGHT://推流url格式不正确
            {
                //Log.i(TAG, "test: in handleMessage, MSG_URL_FORMAT_NOT_RIGHT");
                showToast("MSG_URL_FORMAT_NOT_RIGHT");
                break;
            }
            case MSG_URL_IS_EMPTY://推流url为空
            {
                //Log.i(TAG, "test: in handleMessage, MSG_URL_IS_EMPTY");
                break;
            }

            case MSG_SPEED_CALC_SUCCESS:
            case MSG_SPEED_CALC_FAIL:
                Message message = Message.obtain(mHandler, msg);
                message.obj = object;
                mHandler.sendMessage(message);
                mSpeedCalcRunning = false;
                break;

            default:
                break;

        }
    }

    //获取截屏图像的数据
    public void getScreenShotByteBuffer(Bitmap bitmap) {
        FileOutputStream outStream = null;
        String screenShotFilePath = mScreenShotFilePath + mScreenShotFileName;
        try {

            outStream = new FileOutputStream(String.format(screenShotFilePath));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outStream);
            showToast("截图已保存到SD下的test.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outStream != null){
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Demo层视频缩放和摄像头对焦操作相关方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.i(TAG, "test: down!!!");
                //调用摄像头对焦操作相关API
                if(mLSMediaCapture != null) {
                    mLSMediaCapture.setCameraFocus();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.i(TAG, "test: move!!!");
                /**
                 * 首先判断按下手指的个数是不是大于两个。
                 * 如果大于两个则执行以下操作（即图片的缩放操作）。
                 */
                if (event.getPointerCount() >= 2) {

                    float offsetX = event.getX(0) - event.getX(1);
                    float offsetY = event.getY(0) - event.getY(1);
                    /**
                     * 原点和滑动后点的距离差
                     */
                    mCurrentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);
                    if (mLastDistance < 0) {
                        mLastDistance = mCurrentDistance;
                    } else {
                        if(mLSMediaCapture != null) {
                            mMaxZoomValue = mLSMediaCapture.getCameraMaxZoomValue();
                            mCurrentZoomValue = mLSMediaCapture.getCameraZoomValue();
                        }

                        /**
                         * 如果当前滑动的距离（currentDistance）比最后一次记录的距离（lastDistance）相比大于5英寸（也可以为其他尺寸），
                         * 那么现实图片放大
                         */
                        if (mCurrentDistance - mLastDistance > 5) {
                            //Log.i(TAG, "test: 放大！！！");
                            mCurrentZoomValue+=2;
                            if(mCurrentZoomValue > mMaxZoomValue) {
                                mCurrentZoomValue = mMaxZoomValue;
                            }

                            if(mLSMediaCapture != null) {
                                mLSMediaCapture.setCameraZoomPara(mCurrentZoomValue);
                            }

                            mLastDistance = mCurrentDistance;
                            /**
                             * 如果最后的一次记录的距离（lastDistance）与当前的滑动距离（currentDistance）相比小于5英寸，
                             * 那么图片缩小。
                             */
                        } else if (mLastDistance - mCurrentDistance > 5) {
                            //Log.i(TAG, "test: 缩小！！！");
                            mCurrentZoomValue-=2;
                            if(mCurrentZoomValue < 0) {
                                mCurrentZoomValue = 0;
                            }
                            if(mLSMediaCapture != null) {
                                mLSMediaCapture.setCameraZoomPara(mCurrentZoomValue);
                            }

                            mLastDistance = mCurrentDistance;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //Log.i(TAG, "test: up!!!");
                if(mLayoutMenu!=null){
                    mLayoutMenu.setVisibility(View.GONE);
                }
                if(mLayoutBeauty!=null){
                    mLayoutBeauty.setVisibility(View.GONE);
                }

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        m_tryToStopLivestreaming = true;
    }



    //用于接收Service发送的消息，伴音开关
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int audioMixMsg = intent.getIntExtra("AudioMixMSG", 0);
            mMixAudioFilePath = mMP3AppFileDirectory.toString() + "/" + intent.getStringExtra("AudioMixFilePathMSG");

            //伴音开关的控制
            if(audioMixMsg == 1)
            {
                if(mMixAudioFilePath.isEmpty())
                    return;

                if(mLSMediaCapture != null) {
                    mLSMediaCapture.startPlayMusic(mMixAudioFilePath,false);
                }
            }
            else if (audioMixMsg == 2)
            {
                if(mLSMediaCapture != null){
                    mLSMediaCapture.resumePlayMusic();
                }
            }
            else if(audioMixMsg == 3)
            {
                if(mLSMediaCapture != null){
                    mLSMediaCapture.pausePlayMusic();
                }
            }
            else if(audioMixMsg == 4)
            {
                if(mLSMediaCapture != null){
                    mLSMediaCapture.stopPlayMusic();
                }
            }
        }
    }

    //用于接收Service发送的消息，伴音音量
    public class audioMixVolumeMsgReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int audioMixVolumeMsg = intent.getIntExtra("AudioMixVolumeMSG", 0);

            //伴音音量的控制
            int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxStreamVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            streamVolume = audioMixVolumeMsg*maxStreamVolume/10;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamVolume, 1);
        }
    }

    @BindView(R.id.live_iv_avatar)
    CircleImageView mIVAvatar;
    @BindView(R.id.live_tv_nick)
    TextView mTVNick;
    @BindView(R.id.live_tv_roomCount)
    TextView mTVRoomCount;
    @BindView(R.id.live_tv_roomId)
    TextView mTVRoomId;
    @BindView(R.id.live_ls_member)
    RecyclerView mRVMember;
    @BindView(R.id.live_lv_msg)
    ListView mLVMsg;
    @BindView(R.id.live_lv_gift)
    ListView mLVGift;
    @BindView(R.id.live_layout_menu)
    ViewGroup mLayoutMenu;
    @BindView(R.id.live_layout_menu_beauty)
    ViewGroup mLayoutBeauty;
    @BindView(R.id.live_gv_menu)
    GridView mGVMenu;


    private NimUserInfo mUserInfo;
    private ChatRoomInfo mChartRoomInfo=null;
    private BarrageListAdapter mBarrageAdapter=new BarrageListAdapter(this,null);
    private GiftListAdapter mGiftAdapter=new GiftListAdapter(this,null);
    private LiveMenuAdapter mLiveMenuAdapter=new LiveMenuAdapter(this,null);

    @Override
    public void beforeInit(Bundle savedInstanceState) {
        mUserInfo= BusinessSession.getInstance().getUserInfo();
        mLSBean=(LiveStreamingBean)getIntent().getSerializableExtra("data");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_live_streaming;
    }

    @Override
    public void initView() {
        super.initView();
        mLVMsg.setAdapter(mBarrageAdapter);
        mLVGift.setAdapter(mGiftAdapter);
        List<LiveMenuBean> mMenuList=new ArrayList<>();
        mMenuList.add(new LiveMenuBean("cinemaTurn","翻转镜头",R.drawable.ic_overturn));
        mMenuList.add(new LiveMenuBean("mirror","开启镜像",R.drawable.ic_open_mirror));
        mMenuList.add(new LiveMenuBean("screenshot","截图",R.drawable.ic_special_effect));
        mMenuList.add(new LiveMenuBean("beauty","美顔",R.drawable.ic_beauty));
        List<LiveMenuBean> mEffectList=new ArrayList<>();
        mEffectList.add(new LiveMenuBean("effect","怀旧",R.drawable.ic_special_effect,null,VideoEffect.FilterType.brooklyn));
        mEffectList.add(new LiveMenuBean("effect","干净",R.drawable.ic_special_effect,null,VideoEffect.FilterType.calm));
        mEffectList.add(new LiveMenuBean("effect","自然",R.drawable.ic_special_effect,null,VideoEffect.FilterType.nature));
        mEffectList.add(new LiveMenuBean("effect","健康",R.drawable.ic_special_effect,null,VideoEffect.FilterType.healthy));
        mEffectList.add(new LiveMenuBean("effect","复古",R.drawable.ic_special_effect,null,VideoEffect.FilterType.pixar));
        mEffectList.add(new LiveMenuBean("effect","温柔",R.drawable.ic_special_effect,null,VideoEffect.FilterType.tender));
        mEffectList.add(new LiveMenuBean("effect","美白",R.drawable.ic_special_effect,null,VideoEffect.FilterType.whiten));
        mEffectList.add(new LiveMenuBean("effect","无",R.drawable.ic_special_effect,null,VideoEffect.FilterType.none));
        mMenuList.add(new LiveMenuBean("specialEffect","特效",R.drawable.ic_special_effect,mEffectList,null));
        mLiveMenuAdapter.setDatas(mMenuList);
        mGVMenu.setAdapter(mLiveMenuAdapter);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        if(mUserInfo!=null){
            mTVNick.setText(mUserInfo.getName());
        }
        initLiveSream(savedInstanceState);
        BusinessInterface.getInstance().request(new JoinRoomEvent(EventId.ROOM_JOIN,mLSBean.getRoomId()));
    }
    private void onMyDestory(){
        initRoomService(false);
        NIMClient.getService(ChatRoomService.class).exitChatRoom(mLSBean.getRoomId());
    }

    private void showChatRoomInfo(ChatRoomInfo chatRoomInfo){
        this.mChartRoomInfo=chatRoomInfo;
        mTVRoomId.setText("房间ID:"+mChartRoomInfo.getRoomId());
        mTVRoomCount.setText(mChartRoomInfo.getOnlineUserCount()+"人");
    }

    @Subscribe
    public void joinRoomResponse(JoinRoomEvent event){
        if(event.isSuccess()){
            EnterChatRoomResultData result=event.response();
            showChatRoomInfo(result.getRoomInfo());
            ToastUtils.showToast(this,"加入房间成功");
            initRoomService(true);
            return;

        }
        ToastUtils.showToast(this,"加入房间失败");
    }


    //闪光灯
    @OnClick(R.id.live_flash)
    public void toggleFlash(){
        if(mLSMediaCapture != null){
            mFlashOn = !mFlashOn;
            mLSMediaCapture.setCameraFlashPara(mFlashOn);
//            if(mFlashOn){
//                flashBtn.setImageResource(R.drawable.flashstop);
//            }else {
//                flashBtn.setImageResource(R.drawable.flashstart);
//            }


        }
    }


    @OnItemClick(R.id.live_gv_menu)
    public void menuItemClick(int index){
        LiveMenuBean item=mLiveMenuAdapter.getItem(index);
        if(item.hasChildren()){
            mLiveMenuAdapter.setDatas(item.getChildren());
            mLiveMenuAdapter.notifyDataSetChanged();
            return;
        }
        switch (item.getId()){
            case "effect":
                mLSMediaCapture.setFilterType((VideoEffect.FilterType)item.getExtension());
                break;
            case "cinemaTurn":
                switchCamera();
                break;
            case "beauty":
                toggleMenu();
                toggleBeautyMenu();
                break;
            case "mirror":
                if(mLSMediaCapture != null){
                    mLSMediaCapture.setPreviewMirror(true);//没有获取当前状态
                    mLSMediaCapture.setVideoMirror(true);//没有获取当前状态
                }
                break;
            case "screenshot":
                capture();
                break;
            case "specialEffect":
                break;
            case "showAudio":
                showMixAudioDialog();
                break;
            case "water":
                if(mLSMediaCapture != null) {
                    mLSMediaCapture.setWaterPreview(false);
                    mLSMediaCapture.setGraffitiPreview(false);
                    mLSMediaCapture.setDynamicWaterPreview(false);
                }
                break;
        }

    }

    public void toggleBeautyMenu(){
        mLayoutBeauty.setVisibility(mLayoutBeauty.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
    }
    @OnClick(R.id.live_iv_gift)
    public void toggleMenu(){
        mLayoutMenu.setVisibility(mLayoutMenu.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
    }

    public void sendMesg(){
        String text = "这是聊天室文本消息";
// 创建聊天室文本消息
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(mLSBean.getRoomId(), text);
// 将文本消息发送出去
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        // 成功
                        ToastUtils.showToast(LiveStreamingActivity.this,"发送成功");
                    }

                    @Override
                    public void onFailed(int code) {
                        // 失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 错误
                    }
                });
    }

    public void showBrrage(ChatRoomMessage msg){
        mBarrageAdapter.add(msg);
        mLVMsg.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mLVMsg.smoothScrollToPosition(mBarrageAdapter.getCount() - 1);
            }
        });
    }
    public void showGift(ChatRoomMessage msg){
        mGiftAdapter.add(msg);
        mLVGift.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mLVGift.smoothScrollToPosition(mGiftAdapter.getCount() - 1);
            }
        });
    }

    private void initRoomService(boolean register){
        NIMClient.getService(ChatRoomServiceObserver.class).observeReceiveMessage(incomingChatRoomMsg, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeOnlineStatus(onlineStatus, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeKickOutEvent(kickOutObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotification, register);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }
    private Observer<List<ChatRoomMessage>> incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
        @Override
        public void onEvent(List<ChatRoomMessage> messages) {
            for( ChatRoomMessage msg:messages){
                // 处理新收到的消息
                Logger.i("收到消息来自"+msg.getFromAccount()+"的消息:"
                        +msg.getAttachment()+msg.getMsgType()+msg.getContent()+msg.getDirect());
                if (msg != null && msg.getAttachment() instanceof ChatRoomNotificationAttachment) {
                    // 通知类消息
                    ChatRoomNotificationAttachment attachment = (ChatRoomNotificationAttachment) msg.getAttachment();
                    if (attachment.getType() == NotificationType.ChatRoomMemberIn) {
                        showBrrage(msg);
                    } else if (attachment.getType() == NotificationType.ChatRoomInfoUpdated) {
                        Logger.i("房间信息更新"+attachment.getExtension());
                        ToastUtils.showToast(LiveStreamingActivity.this,"房间信息更新");
                    }
                }else if(msg != null && msg.getAttachment() instanceof GiftAttachment){
                    showGift(msg);
                }else if(msg != null && msg.getAttachment() instanceof BarrageAttachment){
                    showBrrage(msg);
                }
            }

        }
    };

    Observer<CustomNotification> customNotification = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification customNotification) {
            if (customNotification == null) {
                return;
            }

            String content = customNotification.getContent();
            try {
                JSONObject json = JSON.parseObject(content);
//                String fromRoomId = json.getString(PushLinkConstant.roomid);
//                if (!mLSBean.getRoomId().equals(fromRoomId)) {
//                    return;
//                }
//                int id = json.getIntValue(PushLinkConstant.command);
//                LogUtil.i(TAG, "receive command type:" + id);
//                if (id == PushMicNotificationType.JOIN_QUEUE.getValue()) {
//                    // 加入连麦队列
//                    joinQueue(customNotification, json);
//                } else if (id == PushMicNotificationType.EXIT_QUEUE.getValue()) {
//                    // 退出连麦队列
//                    exitQueue(customNotification);
//                } else if (id == PushMicNotificationType.CONNECTING_MIC.getValue()) {
//                    // 主播选中某人连麦
//                    onMicLinking(json);
//                } else if (id == PushMicNotificationType.DISCONNECT_MIC.getValue()) {
//                    // 被主播断开连麦
//                    onMicCanceling();
//                } else if (id == PushMicNotificationType.REJECT_CONNECTING.getValue()) {
//                    // 观众由于重新进入了房间而拒绝连麦
//                    rejectConnecting();
//                }

            } catch (Exception e) {
                Logger.e(e.toString());
            }
        }
    };

    Observer<ChatRoomStatusChangeData> onlineStatus = new Observer<ChatRoomStatusChangeData>() {
        @Override
        public void onEvent(ChatRoomStatusChangeData chatRoomStatusChangeData) {
            if (chatRoomStatusChangeData.status == StatusCode.CONNECTING) {
                ToastUtils.showToast(LiveStreamingActivity.this,"连接中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.UNLOGIN) {
                ToastUtils.showToast(LiveStreamingActivity.this,"退出...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
                ToastUtils.showToast(LiveStreamingActivity.this,"登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
                ToastUtils.showToast(LiveStreamingActivity.this,"登录成功...");
            } else if (chatRoomStatusChangeData.status.wontAutoLogin()) {
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
                ToastUtils.showToast(LiveStreamingActivity.this,"网络异常...");
            }
            ToastUtils.showToast(LiveStreamingActivity.this,"Chat Room Online Status:" + chatRoomStatusChangeData.status.name());
            Logger.i( "Chat Room Online Status:" + chatRoomStatusChangeData.status.name());
        }
    };

    /**
     * 用户状态变化
     */
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {

            }
        }
    };

    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
            ToastUtils.showToast(LiveStreamingActivity.this, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason());
            finish();
        }
    };



}
