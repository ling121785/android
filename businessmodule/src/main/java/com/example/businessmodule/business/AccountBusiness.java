package com.example.businessmodule.business;

import com.example.businessmodule.bean.UserInfo;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.account.LogoutEvent;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.orhanobut.logger.Logger;

import toolbox.ll.com.common.utility.JsonUtils;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AccountBusiness extends BaseBusiness{
    public AccountBusiness(BaseEvent event) {
        super(event);
    }

    @Override
    protected void process() {
        if(this.getEvent() instanceof LoginEvent){
            this.login((LoginEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof LogoutEvent){
            this.logout((LogoutEvent)this.getEvent());
            return;
        }
    }

    private void login(final LoginEvent event){
        LoginInfo info = new LoginInfo(event.request().getAccount(),event.request().getPassword()); // config...
        NIMClient.getService(AuthService.class).login(info)
                .setCallback( new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {

                        event.setResponse(loginInfo);
                        BusinessSession.getInstance().setLoginInfo(loginInfo);
                        BusinessPrefences.getInstance().setUserInfo(new UserInfo(event.request().getAccount(),event.request().getPassword()));
                        NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(loginInfo.getAccount());
                        BusinessSession.getInstance().setUserInfo(user);
                        responseSuccess();
                    }
                    @Override
                    public void onFailed(int i) {
                        responseError(ResultCode.NETEASE_ERR,"");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        responseError(throwable);
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                });
    }
    private void logout(final LogoutEvent event){
        NIMClient.getService(AuthService.class).logout();
        BusinessSession.getInstance().setLoginInfo(null);
        BusinessPrefences.getInstance().setUserInfo(new UserInfo(null,null));
        BusinessSession.getInstance().setUserInfo(null);
        responseSuccess();
    }

    @Override
    public void abort() {

    }
}
