package com.example.businessmodule.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.businessmodule.business.AccountBusiness;
import com.example.businessmodule.business.BaseBusiness;
import com.example.businessmodule.business.RoomBusiness;
import com.example.businessmodule.business.UserBusiness;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.account.LogoutEvent;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.event.room.GiftListEvent;
import com.example.businessmodule.event.room.LiveDetailEvent;
import com.example.businessmodule.event.room.StartLiveEvent;
import com.example.businessmodule.event.room.StopLiveEvent;
import com.example.businessmodule.event.room.JoinRoomEvent;
import com.example.businessmodule.event.user.FansContributionListEvent;
import com.example.businessmodule.event.user.FansListEvent;
import com.example.businessmodule.event.user.IncomeListEvent;
import com.example.businessmodule.event.user.LiveRecordListEvent;
import com.example.businessmodule.event.user.LiveStatisticsEvent;
import com.squareup.otto.Subscribe;

/**
 * 业务处理服务 <br/>
 * 负责统筹处理全局的业务逻辑（如是否处于登陆状态等），分发各个模块的业务处理
 */
public class BusinessService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        BusinessBus.getInstance().registerRequest(this);
        BusinessPrefences.getInstance().init(getApplicationContext());
        BusinessSession.getInstance().setContext(getApplicationContext());
//        DatabaseHelper.newInstace(getApplicationContext());
//        String phone=BusinessPrefences.getInstance().getSessionPhonenum();
//        try{
//            if(!StringUtils.isEmpty(phone)){
//                long dbName = Long.parseLong(phone);
//                DatabaseHelper.getInstance().createDatabase(dbName);
//            }
//        }catch (Exception e){
//            Logger.i(e.getMessage());
//        }

//        if (BusinessSession.getInstance().getAccount() == null) {
//            BusinessInterface.getInstance().request(AccountLoginEvent.createAutoLogin(EventConstants.EVENT_ACCOUNT_AUTO_LOGIN));
//        }

    }

    @Override
    public void onDestroy() {
        BusinessBus.getInstance().unregisterRequest(this);
        BusinessManager.getInstance().abortAllBusiness();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 执行任务 <br/>
     *
     * @param business
     */
    private void executeBusiness(BaseBusiness business) {
        BusinessManager.getInstance().executeBusiness(business,
                business.new BusinessCallback() {

                    @Override
                    public void response(BaseEvent event) {
                        BusinessBus.getInstance().postResponse(event);
                    }
                });
    }
//account
    @Subscribe
    public void processEvent(LoginEvent event) {
        executeBusiness(new AccountBusiness(event));
    }
    // room
    @Subscribe
    public void processEvent(CreateRoomEvent event) {
        executeBusiness(new RoomBusiness(event));
    }
    @Subscribe
    public void processEvent(StopLiveEvent event) {
        executeBusiness(new RoomBusiness(event));
    }
    @Subscribe
    public void processEvent(StartLiveEvent event) {
        executeBusiness(new RoomBusiness(event));
    }

    @Subscribe
    public void processEvent(GiftListEvent event) {
        executeBusiness(new RoomBusiness(event));
    }
    @Subscribe
    public void processEvent(JoinRoomEvent event) {
        executeBusiness(new RoomBusiness(event));
    }

    @Subscribe
    public void processEvent(LiveDetailEvent event) {
        executeBusiness(new RoomBusiness(event));
    }

    @Subscribe
    public void processEvent(LogoutEvent event){
        executeBusiness(new AccountBusiness(event));
    }

    @Subscribe
    public void processEvent(FansListEvent event){
        executeBusiness(new UserBusiness(event));
    }
    @Subscribe
    public void processEvent(IncomeListEvent event){
        executeBusiness(new UserBusiness(event));
    }
    @Subscribe
    public void processEvent(LiveRecordListEvent event){
        executeBusiness(new UserBusiness(event));
    }

    @Subscribe
    public void processEvent(LiveStatisticsEvent event){
        executeBusiness(new UserBusiness(event));
    }
    @Subscribe
    public void processEvent(FansContributionListEvent event){
        executeBusiness(new UserBusiness(event));
    }

}
