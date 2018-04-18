package com.example.businessmodule.business;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.FansBean;
import com.example.businessmodule.bean.FansContriButionsBean;
import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.bean.InComeBean;
import com.example.businessmodule.bean.LiveBean;
import com.example.businessmodule.bean.LiveStatisticsBean;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.account.LogoutEvent;
import com.example.businessmodule.event.user.FansContributionListEvent;
import com.example.businessmodule.event.user.FansListEvent;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.event.user.LiveRecordListEvent;
import com.example.businessmodule.event.user.LiveStatisticsEvent;
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

public class UserBusiness extends BaseBusiness{
    private Subscription subscription = null;
    public UserBusiness(BaseEvent event) {
        super(event);
    }

    @Override
    protected void process() {
        if(this.getEvent() instanceof LiveRecordListEvent){
            this.getLiveRecordList((LiveRecordListEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof LiveStatisticsEvent){
            this.getLiveStatistics((LiveStatisticsEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof IncomeListEvent){
            this.getIncomeList((IncomeListEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof FansListEvent){
            this.getFansList((FansListEvent)this.getEvent());
            return;
        }

        if(this.getEvent() instanceof FansContributionListEvent){
            this.getFansContributionList((FansContributionListEvent)this.getEvent());
            return;
        }

    }


    private void getLiveRecordList(final LiveRecordListEvent event){
        LiveRecordListEvent.Rest rest=getRetrofit().create(LiveRecordListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getPage(),event.request().getStartTime(),event.request().getEndTime()),new RestCallback<BaseListResponse<LiveBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<LiveBean> response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });

    }
    private void getLiveStatistics(final LiveStatisticsEvent event){
        LiveStatisticsEvent.Rest rest=getRetrofit().create(LiveStatisticsEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getPage(),event.request().getStartTime(),event.request().getEndTime()),new RestCallback<LiveStatisticsBean>(){
            @Override
            public boolean onResponse(LiveStatisticsBean response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });

    }
    private void getIncomeList(final IncomeListEvent event){
        IncomeListEvent.Rest rest=getRetrofit().create(IncomeListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getPage(),event.request().getStartTime(),event.request().getEndTime()),new RestCallback<BaseListResponse<InComeBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<InComeBean> response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });

    }
    private void getFansList(final FansListEvent event){
        FansListEvent.Rest rest=getRetrofit().create(FansListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getPage()),new RestCallback<BaseListResponse<FansBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<FansBean> response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });

    }

    private void getFansContributionList(final FansContributionListEvent event){
        FansContributionListEvent.Rest rest=getRetrofit().create(FansContributionListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getPage(),event.request().getStartTime(),event.request().getEndTime()),new RestCallback<BaseListResponse<FansContriButionsBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<FansContriButionsBean> response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });

    }

    @Override
    public void abort() {

    }
}
