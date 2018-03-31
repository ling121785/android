package com.example.businessmodule.business;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.event.room.GiftListEvent;
import com.example.businessmodule.event.room.StartLiveEvent;
import com.example.businessmodule.event.room.StopLiveEvent;
import com.example.businessmodule.event.room.JoinRoomEvent;
import com.example.businessmodule.rest.BaseListResponse;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2018/3/22.
 */

public class RoomBusiness extends BaseBusiness{
    public RoomBusiness(BaseEvent event) {
        super(event);
    }

    @Override
    protected void process() {
        if(this.getEvent() instanceof CreateRoomEvent){
            this.createRoom((CreateRoomEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof JoinRoomEvent){
            this.joinRoom((JoinRoomEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof StopLiveEvent){
            this.stopLive((StopLiveEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof StartLiveEvent){
            this.startLive((StartLiveEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof GiftListEvent){
            this.getGiftList((GiftListEvent)this.getEvent());
            return;
        }
    }

    private void createRoom(final CreateRoomEvent event){
        CreateRoomEvent.Rest rest=getRetrofit().create(CreateRoomEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request()),new RestCallback<RoomBean>(){

            @Override
            public boolean onResponse(RoomBean response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });
    }
    private void joinRoom(final JoinRoomEvent event){
        // roomId 表示聊天室ID
        EnterChatRoomData data = new EnterChatRoomData(event.request().getRommId());
        NimUserInfo userInfo= BusinessSession.getInstance().getUserInfo();
        data.setAvatar(userInfo.getAvatar());
        data.setNick(userInfo.getName());
        // 以登录一次不重试为例
        NIMClient.getService(ChatRoomService.class).enterChatRoomEx(data, 2).setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                // 登录成功
                event.setResponse(result);
                responseSuccess();
            }

            @Override
            public void onFailed(int code) {
                com.orhanobut.logger.Logger.i("加入房间失败"+code);
                // 登录失败
                responseError(ResultCode.NETEASE_ERR,"加入房间失败");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                responseError(exception);
            }
        });
    }
    private void stopLive(final StopLiveEvent event){
        StopLiveEvent.Rest rest=getRetrofit().create(StopLiveEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request("live/"+event.request().getLiveId(),accountBean.getUuid()),new RestCallback<String>(){
            @Override
            public boolean onResponse(String response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });
    }
    private void startLive(final StartLiveEvent event){
        StartLiveEvent.Rest rest=getRetrofit().create(StartLiveEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request("live/"+event.request().getLiveId(),accountBean.getUuid()),new RestCallback<String>(){
            @Override
            public boolean onResponse(String response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });
    }
    private void getGiftList(final GiftListEvent event){
        GiftListEvent.Rest rest=getRetrofit().create(GiftListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getRoomId()),new RestCallback<BaseListResponse<GiftBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<GiftBean> response) {
                if(response!=null){
                    BusinessSession.getInstance().setmGiftList(response.getDataList());
                    BusinessPrefences.getInstance().saveGiftList(response.getDataList());
                }
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
