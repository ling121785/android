package com.example.businessmodule.event.roomBusiness;

import com.example.businessmodule.event.BaseEvent;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatData;

/**
 * Created by Administrator on 2018/3/22.
 */

public class JoinRoomEvent extends BaseEvent<JoinRoomEvent.Request,AVChatData> {
    public JoinRoomEvent(long eventId, String roomName, AVChatType type) {
        super(eventId);
        setRequest(new Request(roomName,type));
    }

    public class Request{
        private String roomName;
        private AVChatType type;

        public Request(String roomName, AVChatType type) {
            this.roomName = roomName;
            this.type = type;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public AVChatType getType() {
            return type;
        }

        public void setType(AVChatType type) {
            this.type = type;
        }
    }
}
