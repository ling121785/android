package com.example.businessmodule.business;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.bean.QiniuBean;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.account.LogoutEvent;
import com.example.businessmodule.event.common.QiniuInfoEvent;
import com.example.businessmodule.event.user.LiveRecordListEvent;
import com.example.businessmodule.rest.BaseListResponse;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/22.
 */

public class CommonBusiness extends BaseBusiness{
    private Subscription subscription = null;
    public CommonBusiness(BaseEvent event) {
        super(event);
    }

    @Override
    protected void process() {
        if(this.getEvent() instanceof QiniuInfoEvent){
            this.getQiniuInfo((QiniuInfoEvent)this.getEvent());
            return;
        }
    }


    private void getQiniuInfo(final QiniuInfoEvent event){
        QiniuInfoEvent.Rest rest=getRetrofit().create(QiniuInfoEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        rest.request(accountBean.getUuid())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<QiniuBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        responseError(e);
                    }

                    @Override
                    public void onNext(QiniuBean qiniuBean) {
                      //上传文件
                    }
                });

    }


    @Override
    public void abort() {

    }
}
