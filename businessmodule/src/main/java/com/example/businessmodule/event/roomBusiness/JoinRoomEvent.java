package com.example.businessmodule.event.roomBusiness;

import com.example.businessmodule.event.BaseEvent;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;

/**
 * Created by Administrator on 2018/3/22.
 */

public class JoinRoomEvent extends BaseEvent<JoinRoomEvent.Request,EnterChatRoomResultData> {
    public JoinRoomEvent(long eventId, String roomId) {
        super(eventId);
        setRequest(new Request(roomId));
    }

    public class Request{
        private String rommId;

        public Request(String rommId) {
            this.rommId = rommId;
        }

        public String getRommId() {
            return rommId;
        }

        public void setRommId(String rommId) {
            this.rommId = rommId;
        }
    }
}
