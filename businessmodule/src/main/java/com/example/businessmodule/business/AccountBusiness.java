package com.example.businessmodule.business;

import com.example.businessmodule.bean.AccountBean;
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

import rx.Subscriber;
import rx.schedulers.Schedulers;

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
        LoginEvent.Rest request= getRetrofit().create(LoginEvent.Rest.class);
        request.request(event.request().getAccount())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AccountBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        responseError(e);
                    }

                    @Override
                    public void onNext(AccountBean userInfo) {
                        BusinessPrefences.getInstance().setUserInfo(userInfo);
                        BusinessSession.getInstance().setAccountInfo(userInfo);
                        nimLogin(userInfo.getAccount(),userInfo.getPwd(),event);
                    }
                });

    }
    private void nimLogin(final String account,final String token,final LoginEvent event){
        LoginInfo info = new LoginInfo(account,token); // config...
        NIMClient.getService(AuthService.class).login(info)
                .setCallback( new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {

                        event.setResponse(loginInfo);
                        BusinessSession.getInstance().setLoginInfo(loginInfo);
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
        BusinessPrefences.getInstance().setUserInfo(new AccountBean(null,null));
        BusinessSession.getInstance().setUserInfo(null);
        responseSuccess();
    }

    @Override
    public void abort() {

    }
}
