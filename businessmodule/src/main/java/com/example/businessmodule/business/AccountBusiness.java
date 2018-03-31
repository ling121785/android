package com.example.businessmodule.business;

import com.example.businessmodule.bean.UserInfo;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AccountBusiness extends BaseBusiness{
    private Subscription subscription = null;
    public AccountBusiness(BaseEvent event) {
        super(event);
    }

    @Override
    protected void process() {
        if(this.getEvent() instanceof LoginEvent){
            this.login((LoginEvent)this.getEvent());
            return;

        }
    }
    private void neteaseLogin(String account,String token,final LoginEvent event){
        LoginInfo info = new LoginInfo(account,token); // config...
        NIMClient.getService(AuthService.class).login(info)
                .setCallback( new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        event.setResponse(loginInfo);
                        BusinessSession.getInstance().setUserInfo(loginInfo);
                        BusinessPrefences.getInstance().setUserInfo(new UserInfo(event.request().getAccount(),event.request().getPassword()));
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
    private void login(final LoginEvent event){
        LoginEvent.Rest request = getRetrofit().create(LoginEvent.Rest.class);
        request.createRequest(event.request().getAccount())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        responseError(e);
                    }

                    @Override
                    public void onNext(UserInfo restResponse) {
                      neteaseLogin(restResponse.getAccount(),restResponse.getPwd(),event);
                    }
                });

    }

    @Override
    public void abort() {

    }
}
