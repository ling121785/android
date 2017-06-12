package com.example.businessmodule.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.businessmodule.business.BaseBusiness;
import com.example.businessmodule.event.BaseEvent;
import com.orhanobut.logger.Logger;

import toolbox.ll.com.common.utility.StringUtils;

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
////account
//    @Subscribe
//    public void processEvent(AccountLoginEvent event) {
//        executeBusiness(new AccountBusiness(event));
//    }

}
