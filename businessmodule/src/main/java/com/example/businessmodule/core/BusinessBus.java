package com.example.businessmodule.core;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * 业务与UI通信的总线
 */
public class BusinessBus {

    private static BusinessBus instance = null;
    public static BusinessBus getInstance() {
        if (instance == null) {
            instance = new BusinessBus();
        }
        return instance;
    }

    private Bus requestBus = new RequestBus();
    private Bus responseBus = new ResponseBus();

    /**
     * 注册请求事件监听
     *
     * @param object
     */
    public void registerRequest(Object object) {
        requestBus.register(object);
    }

    /**
     * 反注册请求事件监听
     *
     * @param object
     */
    public void unregisterRequest(Object object) {
        requestBus.unregister(object);
    }

    /**
     * 注册响应事件监听
     *
     * @param object
     */
    public void registerResponse(Object object) {
        responseBus.register(object);
    }

    /**
     * 反注册响应事件监听
     *
     * @param object
     */
    public void unregisterResponse(Object object) {
        responseBus.unregister(object);
    }

    /**
     * 发送请求事件
     *
     * @param event
     */
    public void postRequest(Object event) {
        requestBus.post(event);
    }

    /**
     * 发送响应事件
     *
     * @param event
     */
    public void postResponse(Object event) {
        responseBus.post(event);
    }

    /**
     * 任务请求总线
     */
    private class RequestBus extends Bus {
        @Override
        public void post(Object event) {
            super.post(event);
        }
    }

    /**
     * 任务应答总线，在UI线程中回应
     */
    private class ResponseBus extends Bus {
        private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            mMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    ResponseBus.super.post(event);
                }
            });
        }
    }
}
