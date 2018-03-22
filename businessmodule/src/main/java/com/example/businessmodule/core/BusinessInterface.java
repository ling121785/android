package com.example.businessmodule.core;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.example.businessmodule.event.BaseEvent;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * 对上层的接口
 */
public class BusinessInterface {

    private static BusinessInterface instance = null;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Context mContext;

    public static BusinessInterface getInstance() {
        if (instance == null) {
            instance = new BusinessInterface();
        }
        return instance;
    }

    private boolean debugMode = false;

    /**
     * 初始化业务层，要使用的时候必须初始化
     *
     * @param context
     */
    public void init(Context context, boolean debugMode,boolean logEnable, String logTag,String baseUrl) {
        this.debugMode = debugMode;
        mContext=context;
        //日志
        Logger.init(logTag)
                .hideThreadInfo()  // default it is shown
                .logLevel(logEnable ? LogLevel.FULL: LogLevel.NONE);  // default : LogLevel.FULL;
        BusinessPrefences.getInstance().init(context);
        //如果是debugMode 登录页面允许切换 url,并记录
//        if(debugMode){
//            String debugUrl=BusinessPrefences.getInstance().getServerIp();
//            Logger.i("baseUrl="+BusinessPrefences.getInstance().getServerIp());
//            if(!StringUtils.isEmpty(debugUrl))
//                RestApiUrl.resetBaseUrl(debugUrl);
//        }else{
//            RestApiUrl.resetBaseUrl(baseUrl);//初始化url;
//        }
        Logger.e("service准备启动");
        context.startService(new Intent(context.getApplicationContext(), BusinessService.class));
    }

    /**
     * 退出应用的时候进行反初始化
     *
     * @param context
     */
    public void uninit(Context context) {
        // TODO 暂时不结束 business 服务
        context.stopService(new Intent(context.getApplicationContext(), BusinessService.class));
    }

    /**
     * 调试模式接口
     * @return
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * 业务请求
     * @param event
     */
    public void request(final Object event) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                BusinessBus.getInstance().postRequest(event);
            }
        });
    }

    /**
     * 提供上层调用，直接 post response
     * @param event
     */
    public void postResponse(final Object event) {
        BusinessBus.getInstance().postResponse(event);
    }

    /**
     * 注册响应接口
     * @param response
     */
    public void registerResponse(final Object response) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                BusinessBus.getInstance().registerResponse(response);
            }
        });
    }

    /**
     * 反注册响应接口
     * @param response
     */
    public void unregisterResponse(final Object response) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                BusinessBus.getInstance().unregisterResponse(response);
            }
        });
    }

    /**
     * 中断任务
     *
     * @param event
     */
    public void abort(Object event) {
        BusinessManager.getInstance().abortBusiness(event);
    }

    /**
     * 中断任务
     *
     *
     */
    public BaseEvent getEvent(long  eventId) {
        return BusinessManager.getInstance().getEvent(eventId);
    }




    /**
     * 清空登陆信息
     */
    public void clearActiveSession() {
        BusinessSession.getInstance().clearActiveSession();
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
