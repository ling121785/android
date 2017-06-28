package com.example.baseui.ui;

import android.app.Application;
import android.content.Intent;

import com.example.baseui.BuildConfig;
import com.example.baseui.R;
import com.example.baseui.utils.ImageUtility;
import com.example.businessmodule.core.BusinessInterface;

import toolbox.ll.com.common.utility.CrashUtils;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MainApplication extends Application {
    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        instance = this;
        CrashUtils.getInstance().init(getApplicationContext());
        ImageUtility.init(this);
//        BusinessInterface.getInstance().init(this, BuildConfig.DEBUG,BuildConfig.LOG_ENABLE, BuildConfig.APP_NAME, this.getString(R.string.base_url));
        BusinessInterface.getInstance().registerResponse(this);
//        //推送服务
//        startService(new Intent(this, PushService.class));
//        //定位，地图初始化
//        LocationManager.getInstance().init(this);
//        //umeng 场景类型设置接口。
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        //PingppLog.DEBUG = true;
//        PingppLog.DEBUG = BuildConfig.LOG_ENABLE;

    }

    @Override
    public void onTerminate() {
        BusinessInterface.getInstance().unregisterResponse(this);
//        //定位，地图
//        LocationManager.getInstance().destory();
//        stopService(new Intent(this, ListenerService.class));
//        stopService(new Intent(this, PushService.class));
        super.onTerminate();
    }



}
