package com.example.businessmodule.core;

import android.content.Context;

/**
 * 当前会话信息保存
 */
public class BusinessSession {

    private static BusinessSession instance = null;

    public static BusinessSession getInstance() {
        if (instance == null) {
            instance = new BusinessSession();
        }
        return instance;
    }

    private Context context = null;
    private String token = "";

    private BusinessSession() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getToken() {
        return token;
    }
}
