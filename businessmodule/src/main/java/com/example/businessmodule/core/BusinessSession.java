package com.example.businessmodule.core;

import android.content.Context;

import com.example.businessmodule.bean.AccountBean;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

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
    private LoginInfo loginInfo=null;
    private NimUserInfo userInfo=null;
    private AccountBean accountInfo=null;


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
    public void setUserInfo(NimUserInfo info){
        this.userInfo=info;
    }

    public NimUserInfo getUserInfo() {
        return userInfo;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void clearActiveSession(){

    }

    public AccountBean getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountBean accountInfo) {
        this.accountInfo = accountInfo;
    }
}
