package com.example.businessmodule.business;

import com.example.businessmodule.bean.AccountBean;
import com.example.businessmodule.bean.GiftBean;
import com.example.businessmodule.bean.GuardBean;
import com.example.businessmodule.bean.LiveInfoBean;
import com.example.businessmodule.bean.LiveStyleBean;
import com.example.businessmodule.bean.QiniuBean;
import com.example.businessmodule.bean.RoomBean;
import com.example.businessmodule.core.BusinessInterface;
import com.example.businessmodule.core.BusinessPrefences;
import com.example.businessmodule.core.BusinessSession;
import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.common.QiniuInfoEvent;
import com.example.businessmodule.event.room.AngelEvent;
import com.example.businessmodule.event.room.CreateRoomEvent;
import com.example.businessmodule.event.room.GiftListEvent;
import com.example.businessmodule.event.room.LiveDetailEvent;
import com.example.businessmodule.event.room.LiveStyleListEvent;
import com.example.businessmodule.event.room.StartLiveEvent;
import com.example.businessmodule.event.room.StopLiveEvent;
import com.example.businessmodule.event.room.JoinRoomEvent;
import com.example.businessmodule.rest.BaseListResponse;
import com.example.businessmodule.utils.FileUploadManager;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import rx.Subscriber;
import rx.schedulers.Schedulers;


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
            this.createRoomWithFile((CreateRoomEvent)this.getEvent());
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
        if(this.getEvent() instanceof LiveDetailEvent){
            this.getLiveDetail((LiveDetailEvent)this.getEvent());
            return;
        }
        if(this.getEvent() instanceof LiveStyleListEvent){
            this.getLiveStyle((LiveStyleListEvent)this.getEvent());
        }

        if(this.getEvent() instanceof  AngelEvent){
            this.getCurAngel((AngelEvent)getEvent());
        }
    }

    private void createRoomWithFile(final CreateRoomEvent event){
        if(event.request().getmFile()==null||event.request().getPoster()!=null) {
            createRoom(event);
        }else{
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
                        public void onNext(final QiniuBean qiniuBean) {
                            UpCompletionHandler upCompletionHandler=new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    if(info!=null&&info.isOK()){
                                        event.request().setPoster(qiniuBean.getUrl()+key);
                                        BusinessInterface.getInstance().request(event);
                                    }

                                }
                            };
                            UploadOptions uploadOptions=null;
                            FileUploadManager.getInstance().uploadFiles(event.request().getmFile(),qiniuBean.getToken(),upCompletionHandler,uploadOptions);

                        }
                    });
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
        // 以登录一次不重试为例
        // roomId 表示聊天室ID
        EnterChatRoomData data = new EnterChatRoomData(event.request().getRommId());
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


    private void getLiveDetail(final LiveDetailEvent event){
        LiveDetailEvent.Rest rest=getRetrofit().create(LiveDetailEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request("live/"+event.request().getLiveId(),accountBean.getUuid()),new RestCallback<LiveInfoBean>(){
            @Override
            public boolean onResponse(LiveInfoBean response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });
    }


    private void getLiveStyle(final LiveStyleListEvent event){
        LiveStyleListEvent.Rest rest=getRetrofit().create(LiveStyleListEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid()),new RestCallback<BaseListResponse<LiveStyleBean>>(){
            @Override
            public boolean onResponse(BaseListResponse<LiveStyleBean> response) {
                return true;
            }

            @Override
            public boolean onError() {
                return false;
            }
        });
    }


    private void getCurAngel(final AngelEvent event){
        AngelEvent.Rest rest=getRetrofit().create(AngelEvent.Rest.class);
        AccountBean accountBean=BusinessSession.getInstance().getAccountInfo();
        if(accountBean==null||accountBean.getUuid()==null){
            responseError(ResultCode.AUTH_FAIL,"未登录");
            return;
        }
        startRest(rest.request(accountBean.getUuid(),event.request().getRoomId()),new RestCallback<GuardBean>(){
            @Override
            public boolean onResponse(GuardBean response) {
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
