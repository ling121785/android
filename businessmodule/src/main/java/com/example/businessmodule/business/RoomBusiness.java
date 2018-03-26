package com.example.businessmodule.business;

import com.example.businessmodule.event.BaseEvent;
import com.example.businessmodule.event.account.LoginEvent;
import com.example.businessmodule.event.roomBusiness.CreateRoomEvent;
import com.example.businessmodule.event.roomBusiness.JoinRoomEvent;
import com.example.businessmodule.utils.ResultCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;

import java.util.logging.Logger;

import static com.netease.nimlib.sdk.avchat.constant.AVChatChannelProfile.CHANNEL_PROFILE_DEFAULT;

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
    }

    private void createRoom(final CreateRoomEvent event){
        AVChatManager.getInstance().createRoom(event.request().getRoomName(), event.request().getExtraMessage(), new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                event.setResponse(avChatChannelInfo);
                responseSuccess();
            }

            @Override
            public void onFailed(int i) {
                responseError(ResultCode.NETEASE_ERR,"房间创建失败"+i);
            }

            @Override
            public void onException(Throwable throwable) {
                responseError(throwable);
            }
        });
    }
    private void joinRoom(final JoinRoomEvent event){
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

    @Override
    public void abort() {

    }
}
