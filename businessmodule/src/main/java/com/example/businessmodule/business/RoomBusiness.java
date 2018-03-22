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
        //开启音视频引擎
        AVChatManager.getInstance().enableRtc();
        //设置场景, 如果需要高清音乐场景，设置 AVChatChannelProfile#CHANNEL_PROFILE_HIGH_QUALITY_MUSIC
        AVChatManager.getInstance().setChannelProfile(CHANNEL_PROFILE_DEFAULT);
        //设置通话可选参数
        AVChatParameters parameters = new AVChatParameters();
        AVChatManager.getInstance().setParameters(parameters);
        //视频通话设置
        AVChatManager.getInstance().enableVideo();
//        AVChatManager.getInstance().setupLocalVideoRender(IVideoRender render, false, AVChatVideoScalingType.SCALE_ASPECT_FILL);
        //设置视频采集模块
        AVChatCameraCapturer videoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
        AVChatManager.getInstance().setupVideoCapturer(videoCapturer);
        //设置视频质量调整策略
//        AVChatManager.getInstance().setVideoQualityStrategy(boolean preferImageQuality);
        //开启视频预览
        AVChatManager.getInstance().startVideoPreview();
        AVChatManager.getInstance().joinRoom2(event.request().getRoomName(), event.request().getType(), new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                event.setResponse(avChatData);
                responseSuccess();
            }

            @Override
            public void onFailed(int i) {
                responseError(ResultCode.NETEASE_ERR,"加入房间失败"+i);
            }

            @Override
            public void onException(Throwable throwable) {
                responseError(throwable);
            }
        });
    }

    @Override
    public void abort() {

    }
}
